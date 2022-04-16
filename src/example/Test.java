package example;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class Test {

    private static PlayerColl coll;

    public static void main(String[] args) throws IOException {

        coll = new PlayerColl();
        coll.load();

        if (coll.isEmpty())
            populate();

        coll.all().forEach(System.out::println);

        coll.unload();

    }

    private static void populate() {

        Random random = new Random();

        for (int i = 0; i < 100; i++) {

            PlayerData data = new PlayerData(UUID.randomUUID());

            for (int x = 0; x < 5; x++) {

                String crate = crates[random.nextInt(crates.length)];
                int amount = random.nextInt(10) + 1;
                data.give(crate, amount);
            }

            coll.add(data);

        }

    }

    private static final String[] crates = {
            "COMMON",
            "RARE",
            "EPIC",
            "LEGENDARY"
    };

}
