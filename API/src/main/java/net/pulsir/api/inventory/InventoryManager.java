package net.pulsir.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface InventoryManager {

    /**
     * Returns true/false whether player has saved inventory in server memory.
     * @param player - Player which those methods will be invoked on.
     */
    boolean hasSavedInventory(Player player);

    /**
     * Returns array of itemstack of player saved inventory.
     * @param player - Player which those methods will be invoked on.
     */
    ItemStack[] getSavedInventory(Player player);
}