package net.simoncameron.library.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//Represents a data collection which serialized and deserializes data through JSON

//T parameter represents the unique identifier of each container
//V parameter represents the container type that is serialized/deserialized

@SuppressWarnings("unused")
public abstract class DataColl<T, V extends PersistentContainer<T>> {

    //File path
    private final String path;

    //Map which contains data after component is started, and from which data is read when component stops
    private final Map<T, V> coll = new ConcurrentHashMap<>();

    //Builder state before component is started, and final Gson object is built
    private final GsonBuilder builder = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation();

    //Gson object which is built from the builder object when the component starts
    private Gson gson;

    public DataColl(String path) {
        this.path = path;
    }

    //Loads all values from the file and adds them to the collection
    @SuppressWarnings("all")
    public void load() throws IOException {
        File file = new File(path);
        file.createNewFile();

        gson = builder.create();
        FileReader reader = new FileReader(file);

        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class<V> clazz = (Class<V>) type.getActualTypeArguments()[1];

        V[] array = (V[]) Array.newInstance(clazz, 0);

        array = (V[]) gson.fromJson(reader, array.getClass());

        if (array == null)
            return;

        for (V container : array)
            coll.put(container.getIdentifier(), container);

    }

    //Saves all values from the collection
    @SuppressWarnings("all")
    public void unload() throws IOException {
        File file = new File(path);
        Writer writer = new FileWriter(file);

        gson.toJson(coll.values(), writer);

        writer.flush();
        writer.close();

        coll.clear();
    }

    //Adds a new container to the coll
    public void add(V container) {
        coll.put(container.getIdentifier(), container);
    }

    //Returns a container from the coll using the key provided
    public V get(T key) {
        return coll.get(key);
    }

    //Returns a container from the coll
    //If the coll does not contain a container with the key provided, the default value is added to the coll and is returned
    public V getOrAdd(T key, V toAdd) {

        V toReturn = coll.get(key);

        if (toReturn == null)
            toReturn = toAdd;

        coll.put(key, toAdd);

        return toReturn;

    }

    //Whether the coll is unpopulated or not
    public boolean isEmpty() {
        return coll.isEmpty();
    }

    //Returns collection of all containers in the coll
    public Collection<V> all() {
        return coll.values();
    }

    //Removes container from coll with key
    public void remove(T key) {
        coll.remove(key);
    }

}
