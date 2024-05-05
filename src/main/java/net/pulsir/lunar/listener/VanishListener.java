package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.UUID;

public class VanishListener implements Listener {

    @EventHandler
    @Deprecated
    public void onJoin(PlayerJoinEvent event) {
        if (!(event.getPlayer().hasPermission("lunar.staff"))) {
            for (UUID uuid : Lunar.getInstance().getData().getVanish()) {
                event.getPlayer().hidePlayer(Objects.requireNonNull(Bukkit.getPlayer(uuid)));
            }
        }
    }
}
