package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class OfflineInventoryListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        OfflinePlayerInventory offlinePlayerInventory = new OfflinePlayerInventory(event.getPlayer().getInventory().getContents(),
                event.getPlayer().getEnderChest().getContents());
        Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().put(event.getPlayer().getUniqueId(), offlinePlayerInventory);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().remove(event.getPlayer().getUniqueId());
    }
}
