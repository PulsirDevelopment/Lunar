package net.pulsir.lunar.manager;

import net.pulsir.api.offline.OfflinePlayerManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class OfflinePlayerManagerImpl implements OfflinePlayerManager {

    @Override
    public ItemStack[] getOfflinePlayerInventoryItems(UUID uuid) {
        return Lunar.getInstance().getOfflinePlayerInventoryManager().getOfflinePlayersInventory()
                .get(uuid).getPersonalInventory();
    }

    @Override
    public ItemStack[] getOfflinePlayerEnderChestItems(UUID uuid) {
        return Lunar.getInstance().getOfflinePlayerInventoryManager().getOfflinePlayersInventory()
                .get(uuid).getEnderChestInventory();
    }
}
