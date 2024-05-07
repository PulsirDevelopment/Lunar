package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.inventories.InventoryPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        Lunar.getInstance().getDatabase().loadInventory(event.getUniqueId());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Lunar.getInstance().getInventoryManager().getInventories().remove(event.getPlayer().getUniqueId());

        Lunar.getInstance().getInventoryManager().getInventories()
                .put(event.getPlayer().getUniqueId(),
                        new InventoryPlayer(event.getPlayer().getUniqueId(), event.getPlayer().getInventory().getContents()));
    }
}
