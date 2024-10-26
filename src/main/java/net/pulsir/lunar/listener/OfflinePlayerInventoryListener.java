package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class OfflinePlayerInventoryListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Lunar.getInstance().getOfflinePlayerInventoryManager().getOfflinePlayersInventory().remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ItemStack[] personInventory = event.getPlayer().getInventory().getContents();
        ItemStack[] enderChestInventory = event.getPlayer().getEnderChest().getContents();

        Lunar.getInstance().getOfflinePlayerInventoryManager().getOfflinePlayersInventory().put(event.getPlayer().getUniqueId(),
                new OfflinePlayerInventory(personInventory, enderChestInventory));
    }
}
