package net.pulsir.lunar.database.impl;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.maintenance.Maintenance;
import net.pulsir.lunar.mysql.MySQLManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.sql.SQLException;
import java.util.UUID;

public class MySQL implements IDatabase {

    private final MySQLManager mySQLManager = new MySQLManager();

    @Override
    public void saveInventory() {
        for (final UUID uuid : Lunar.getInstance().getInventoryPlayerManager().getInventories().keySet()) {
            try {
                if (mySQLManager.findInventory(uuid) != null) {
                    mySQLManager.updateInventory(uuid);
                } else {
                    mySQLManager.createInventory(uuid);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void loadInventory(UUID uuid) {
        try {
            InventoryPlayer inventoryPlayer = mySQLManager.findInventory(uuid);

            if (inventoryPlayer != null) {
                Lunar.getInstance().getInventoryPlayerManager().getInventories()
                        .put(uuid, inventoryPlayer);

                mySQLManager.clearInventory(uuid);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveMaintenance(Maintenance maintenance) {
        if (mySQLManager.findMaintenance(maintenance.getName()) == null) {
            mySQLManager.createMaintenance(maintenance);
        } else {
            mySQLManager.updateMaintenance(maintenance);
        }
    }

    public void loadMaintenances() {
        this.mySQLManager.loadMaintenances().forEach(maintenance -> Lunar.getInstance().getServerMaintenanceManager().getMaintenances().add(maintenance));
    }

    @Override
    public void deleteMaintenance(String name) {
        this.mySQLManager.deleteMaintenance(name);
    }

    @Override
    public void saveOfflineInventory(UUID uuid, Inventory playerInventory, Inventory enderChestInventory) {

    }

    @Override
    public void loadOfflineInventory(UUID uuid) {

    }
}
