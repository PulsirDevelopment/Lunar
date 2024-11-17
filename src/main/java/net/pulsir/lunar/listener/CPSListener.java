package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerQuitEvent;

public class CPSListener implements Listener {

    @EventHandler
    public void onClick(PlayerAnimationEvent event) {
        if (event.getAnimationType().equals(PlayerAnimationType.ARM_SWING)) {
            if (Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().containsKey(event.getPlayer().getUniqueId())) {
                Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().replace(event.getPlayer().getUniqueId(),
                        Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().get(event.getPlayer().getUniqueId()),
                        Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().get(event.getPlayer().getUniqueId()) + 1);
            } else {
                Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().put(event.getPlayer().getUniqueId(), 1);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Lunar.getInstance().getCpsPlayerManager().getFollowedPlayers().remove(event.getPlayer().getUniqueId());
    }
}
