package net.pulsir.api.offline;

import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface OfflineInventoryManager {

    ItemStack[] getOfflinePlayerInventory(UUID uuid);
    ItemStack[] getOfflinePlayerEnderChestInventory(UUID uuid);
    void fetchOfflinePlayerInventory(UUID uuid);
}
