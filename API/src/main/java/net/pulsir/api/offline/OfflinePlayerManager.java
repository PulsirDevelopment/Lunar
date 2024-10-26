package net.pulsir.api.offline;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public interface OfflinePlayerManager {

    ItemStack[] getOfflinePlayerInventoryItems(UUID uuid);
    ItemStack[] getOfflinePlayerEnderChestItems(UUID uuid);
}
