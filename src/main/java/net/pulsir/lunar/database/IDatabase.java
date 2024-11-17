package net.pulsir.lunar.database;

import net.pulsir.lunar.maintenance.Maintenance;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public interface IDatabase {

    void saveInventory();
    void loadInventory(UUID uuid);

    void saveMaintenance(Maintenance paramMaintenance);
    void loadMaintenances();

    void deleteMaintenance(String name);

    void loadOfflineInventory(UUID uuid);
    void saveOfflineInventory(UUID uuid);
}
