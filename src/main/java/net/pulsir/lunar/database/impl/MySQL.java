package net.pulsir.lunar.database.impl;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.maintenance.Maintenance;
import net.pulsir.lunar.mysql.MySQLManager;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import net.pulsir.lunar.utils.base64.Base64;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.io.IOException;
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
    public void loadOfflineInventory(UUID uuid) {
        try {
            OfflinePlayerInventory offlinePlayerInventory = mySQLManager.findOfflineInventory(uuid);
            Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().put(uuid, offlinePlayerInventory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveOfflineInventory(UUID uuid, OfflinePlayerInventory offlinePlayerInventory) {
        Inventory playerInventory = Bukkit.getServer().createInventory(null, offlinePlayerInventory.getPlayerInventory().length, "");
        playerInventory.setContents(offlinePlayerInventory.getPlayerInventory());

        Inventory enderChestInventory = Bukkit.getServer().createInventory(null, offlinePlayerInventory.getEnderChestInventory().length, "");
        enderChestInventory.setContents(offlinePlayerInventory.getEnderChestInventory());

        String playerInventoryString = Base64.toBase64(playerInventory);
        String enderChestInventoryString = Base64.toBase64(enderChestInventory);

        mySQLManager.deleteOfflineInventory(uuid);
        mySQLManager.createOfflineInventory(uuid, playerInventoryString, enderChestInventoryString);
    }
}
