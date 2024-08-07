package net.pulsir.lunar.database;

import net.pulsir.lunar.maintenance.Maintenance;

import java.util.UUID;

public interface IDatabase {

    void saveInventory();
    void loadInventory(UUID uuid);

    void saveMaintenance(Maintenance paramMaintenance);
    void updateMaintenance(Maintenance paramMaintenance);
    void removeMaintenance(Maintenance paramMaintenance);
    void loadMaintenances();
}
