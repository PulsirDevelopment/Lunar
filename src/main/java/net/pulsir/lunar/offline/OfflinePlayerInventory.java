package net.pulsir.lunar.offline;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class OfflinePlayerInventory {

    private ItemStack[] offlineInventory, offlineEnderChest;

    public OfflinePlayerInventory(ItemStack[] offlineInventory, ItemStack[] offlineEnderChest) {
        this.offlineInventory = offlineInventory;
        this.offlineEnderChest = offlineEnderChest;
    }
}
