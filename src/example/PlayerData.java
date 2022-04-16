package example;

import com.google.gson.annotations.Expose;
import net.simoncameron.library.persistence.PersistentContainer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//Example class representing a data module for a player within a crates plugin.
public class PlayerData implements PersistentContainer<UUID> {

    @Expose private final UUID id;

    @Expose
    private Map<String, Integer> cratesBalance = new ConcurrentHashMap<>();

    public PlayerData(UUID uuid) {
        this.id = uuid;
    }

    @Override
    public UUID getIdentifier() {
        return id;
    }

    public void give(String crate, int amount) {
        int current = cratesBalance.getOrDefault(crate, 0);
        current = current + amount;
        cratesBalance.put(crate, current);
    }

    public String toString() {
        return id.toString() + ":" + cratesBalance;
    }

}
