package example;

import net.simoncameron.library.persistence.DataColl;

import java.util.UUID;

public class PlayerColl extends DataColl<UUID, PlayerData> {

    public PlayerColl() {
        super("test.json");
    }

}
