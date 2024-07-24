package net.pulsir.lunar.database;

import java.util.UUID;

public interface IDatabase {

    void saveInventory();
    void loadInventory(UUID uuid);
    void fetchNotesAsynchronously(UUID uuid);
    void saveNotes();
}
