package net.pulsir.lunar.offline;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class OfflinePlayerInventory {

    private ItemStack[] playerInventory, enderChestInventory;

    public OfflinePlayerInventory(ItemStack[] playerInventory, ItemStack[] enderChestInventory) {
        this.playerInventory = playerInventory;
        this.enderChestInventory = enderChestInventory;
    }
}
