package net.pulsir.lunar.manager;

import net.pulsir.api.offline.OfflineInventoryManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class OfflinePlayerManagerImpl implements OfflineInventoryManager {

    @Override
    public ItemStack[] getOfflinePlayerInventory(UUID uuid) {
        return Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().get(uuid).getPlayerInventory();
    }

    @Override
    public ItemStack[] getOfflinePlayerEnderChestInventory(UUID uuid) {
        return Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().get(uuid).getEnderChestInventory();
    }

    @Override
    public void fetchOfflinePlayerInventory(UUID uuid) {
        Lunar.getInstance().getDatabase().loadOfflineInventory(uuid);
    }
}
