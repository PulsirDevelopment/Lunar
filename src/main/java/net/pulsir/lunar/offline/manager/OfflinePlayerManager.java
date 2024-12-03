package net.pulsir.lunar.offline.manager;

import lombok.Getter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class OfflinePlayerManager {

    private final Map<UUID, OfflinePlayerInventory> offlinePlayerInventoryMap = new HashMap<>();

    public void saveAll() {
        this.addAllPlayers();

        offlinePlayerInventoryMap.forEach((uuid, inventory) -> {
            Lunar.getInstance().getDatabase().saveOfflineInventory(uuid, inventory);
        });
    }

    private void addAllPlayers() {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            if (!offlinePlayerInventoryMap.containsKey(player.getUniqueId())) {
                offlinePlayerInventoryMap.put(player.getUniqueId(),  new OfflinePlayerInventory(player.getInventory().getContents(),
                        player.getEnderChest().getContents()));
            }
        }
    }
}
