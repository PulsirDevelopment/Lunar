package net.pulsir.lunar.offline.manager;

import lombok.Getter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class OfflinePlayerManager {

    private final Map<UUID, OfflinePlayerInventory> offlinePlayers = new HashMap<>();

    public void saveInventories(Player player) {
        Inventory playerInventory = player.getInventory();
        Inventory enderChest = player.getEnderChest();

        Lunar.getInstance().getDatabase().saveOfflineInventory(player.getUniqueId(), playerInventory, enderChest);
    }
}
