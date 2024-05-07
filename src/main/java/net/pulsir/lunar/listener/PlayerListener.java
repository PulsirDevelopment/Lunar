package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        Lunar.getInstance().getDatabase().loadInventory(event.getUniqueId());
    }
}
