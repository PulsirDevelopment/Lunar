package net.pulsir.lunar.offline;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Getter
public class OfflinePlayerInventory {

    private final ItemStack[] personalInventory, enderChestInventory;

    public OfflinePlayerInventory(ItemStack[] personalInventory, ItemStack[] enderChestInventory) {
        this.personalInventory = personalInventory;
        this.enderChestInventory = enderChestInventory;
    }
}
