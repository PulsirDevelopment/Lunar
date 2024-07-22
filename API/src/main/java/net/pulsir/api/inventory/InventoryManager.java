package net.pulsir.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface InventoryManager {

    boolean hasSavedInventory(Player player);
    ItemStack[] getSavedInventory(Player player);
}