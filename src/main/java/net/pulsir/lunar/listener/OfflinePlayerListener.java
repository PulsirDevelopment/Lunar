package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OfflinePlayerListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Lunar.getInstance(),
                () -> Lunar.getInstance().getOfflinePlayerManager().saveInventories(event.getPlayer()));
    }
}
