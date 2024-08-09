package net.pulsir.lunar.database;

import net.pulsir.lunar.maintenance.Maintenance;

import java.util.UUID;

public interface IDatabase {

    void saveInventory();
    void loadInventory(UUID uuid);

    void saveMaintenance(Maintenance paramMaintenance);
    void loadMaintenances();

    void deleteMaintenance(String name);
}
