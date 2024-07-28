package net.pulsir.lunar.database.impl;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.mysql.MySQLManager;

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
}
