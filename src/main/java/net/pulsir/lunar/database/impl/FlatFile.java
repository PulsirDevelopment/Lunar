package net.pulsir.lunar.database.impl;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.maintenance.Maintenance;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import net.pulsir.lunar.utils.base64.Base64;
import net.pulsir.lunar.utils.serializer.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.*;

public class FlatFile implements IDatabase {

    @Override
    public void saveInventory() {
        Lunar.getInstance().getInventoryPlayerManager().getInventories().values().forEach(inventory -> {
            List<String> string = new ArrayList<>();
            for (final ItemStack itemStack : inventory.getInventory()) {
                string.add(ItemStackSerializer.serialize(itemStack));
            }

            Lunar.getInstance().getInventory().getConfiguration().set("inventory." + inventory.getUuid().toString() + ".uuid", inventory.getUuid().toString());
            Lunar.getInstance().getInventory().getConfiguration().set("inventory." + inventory.getUuid().toString() + ".inventory", inventory.getInventoryString());
            Lunar.getInstance().getInventory().save();
        });
    }

    @Override
    public void loadInventory(UUID uuid) {
        if (Lunar.getInstance().getInventory().getConfiguration().getConfigurationSection("inventory") == null) return;
        for (final String inventory : Objects.requireNonNull(Lunar.getInstance().getInventory().getConfiguration().getConfigurationSection("inventory")).getKeys(false)) {
            List<String> strings = Lunar.getInstance().getInventory().getConfiguration().getStringList("inventory." + inventory + ".inventory");
            ItemStack[] itemStacks = new ItemStack[strings.size()];

            for (int i = 0; i < strings.size(); i++) {
                itemStacks[i] = ItemStackSerializer.deSerialize(strings.get(i));
            }

            Lunar.getInstance().getInventoryPlayerManager().getInventories()
                    .put(uuid, new InventoryPlayer(uuid, itemStacks));
        }
    }

    public void saveMaintenance(Maintenance maintenance) {
        Lunar.getInstance().getMaintenances().getConfiguration().set("maintenance." + maintenance.getName() + ".reason", maintenance.getReason());
        Lunar.getInstance().getMaintenances().getConfiguration().set("maintenance." + maintenance.getName() + ".duration", maintenance.getReason());
        Lunar.getInstance().getMaintenances().getConfiguration().set("maintenance." + maintenance.getName() + ".endDate", maintenance.getEndDate() != null ? maintenance.getEndDate().getTime() : -1);
        Lunar.getInstance().getMaintenances().save();
    }

    public void loadMaintenances() {
        if (Lunar.getInstance().getMaintenances().getConfiguration().getConfigurationSection("maintenance") == null) return;
        for (String maintenanceName : Objects.requireNonNull(Lunar.getInstance().getMaintenances().getConfiguration().getConfigurationSection("maintenance")).getKeys(false)) {
            String reason = Lunar.getInstance().getMaintenances().getConfiguration().getString("maintenance." + maintenanceName + ".reason");
            int duration = Lunar.getInstance().getMaintenances().getConfiguration().getInt("maintenance." + maintenanceName + ".duration");
            long endDate = Lunar.getInstance().getMaintenances().getConfiguration().getLong("maintenance." + maintenanceName + ".endDate");

            Lunar.getInstance().getServerMaintenanceManager().getMaintenances().add(new Maintenance(maintenanceName, reason, duration, endDate == -1 ? null : new Date(endDate)));
        }
    }

    @Override
    public void deleteMaintenance(String name) {

    }

    @Override
    public void loadOfflineInventory(UUID uuid) {
        String playerInventoryString = Lunar.getInstance().getOffline().getConfiguration().getString("player." + uuid.toString() + ".playerInventory");
        String enderChestInventorString = Lunar.getInstance().getOffline().getConfiguration().getString("player." + uuid + ".enderChestInventory");

        try {
            ItemStack[] playerInventory = Base64.fromBase64(playerInventoryString).getContents();
            ItemStack[] enderChestInventory = Base64.fromBase64(enderChestInventorString).getContents();

            Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().put(uuid,
                    new OfflinePlayerInventory(playerInventory, enderChestInventory));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveOfflineInventory(UUID uuid, OfflinePlayerInventory offlinePlayerInventory) {
        Inventory playerInventory = Bukkit.getServer().createInventory(null, 54, "");
        playerInventory.setContents(offlinePlayerInventory.getPlayerInventory());

        Inventory enderChestInventory = Bukkit.getServer().createInventory(null, 36, "");
        enderChestInventory.setContents(offlinePlayerInventory.getEnderChestInventory());

        String playerInventoryString = Base64.toBase64(playerInventory);
        String enderChestInventoryString = Base64.toBase64(enderChestInventory);

        Lunar.getInstance().getOffline().getConfiguration().set("player." + uuid.toString() + ".playerInventory", playerInventoryString);
        Lunar.getInstance().getOffline().getConfiguration().set("player." + uuid + ".enderChestInventory", enderChestInventoryString);
        Lunar.getInstance().getOffline().save();
    }
}
