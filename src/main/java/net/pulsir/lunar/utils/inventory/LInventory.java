package net.pulsir.lunar.utils.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface LInventory {

    Inventory inventory(Player player);
    void open(Player player);
    void close(Player player);
}
