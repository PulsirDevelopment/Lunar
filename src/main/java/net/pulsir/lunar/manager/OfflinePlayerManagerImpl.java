package net.pulsir.lunar.manager;

import net.pulsir.api.offline.OfflinePlayerManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class OfflinePlayerManagerImpl implements OfflinePlayerManager {

    @Override
    public Inventory getOfflinePlayerInventory(UUID uuid) {
        return Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayers().get(uuid).getOfflineInventory();
    }

    @Override
    public Inventory getOfflinePlayerEnderChest(UUID uuid) {
        return Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayers().get(uuid).getOfflineEnderChest();
    }
}
