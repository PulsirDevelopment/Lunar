package net.pulsir.lunar.offline;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.Inventory;

@Getter
@Setter
public class OfflinePlayerInventory {

    private Inventory offlineInventory, offlineEnderChest;

    public OfflinePlayerInventory(Inventory offlineInventory, Inventory offlineEnderChest) {
        this.offlineInventory = offlineInventory;
        this.offlineEnderChest = offlineEnderChest;
    }
}
