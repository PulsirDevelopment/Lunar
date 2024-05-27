package net.pulsir.lunar.api.inventory;

import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;

public class InventoryAPI {

    public boolean hasSavedInventory(Player player){
        return Lunar.getInstance().getInventoryManager().getInventories().containsKey(player.getUniqueId());
    }
}
