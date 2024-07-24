package net.pulsir.lunar.database;

import java.util.UUID;

public interface IDatabase {

    void saveInventory();
    void loadInventory(UUID uuid);
    void fetchAsynchronously(UUID uuid);
}
