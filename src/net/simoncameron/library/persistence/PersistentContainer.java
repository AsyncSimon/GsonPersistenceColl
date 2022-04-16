package net.simoncameron.library.persistence;

//Class which represents a data container to be serialized/deserialized

//T parameter represents the type by which the class is identified and is unique when compared
//to other classes
public interface PersistentContainer<T> {

    T getIdentifier();

}
