package net.pulsir.api.offline;

import org.bukkit.inventory.Inventory;

import java.util.UUID;

public interface OfflinePlayerManager {

    Inventory getOfflinePlayerInventory(UUID uuid);
    Inventory getOfflinePlayerEnderChest(UUID uuid);
}
