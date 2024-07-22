package net.pulsir.lunar.manager;

import net.pulsir.api.inventory.InventoryManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryManagerImpl implements InventoryManager {

    @Override
    public boolean hasSavedInventory(Player player) {
        return Lunar.getInstance().getInventoryPlayerManager().getInventories().containsKey(player.getUniqueId());
    }

    @Override
    public ItemStack[] getSavedInventory(Player player) {
       return Lunar.getInstance().getInventoryPlayerManager().getInventories().get(player.getUniqueId()).getInventory();
    }
}
