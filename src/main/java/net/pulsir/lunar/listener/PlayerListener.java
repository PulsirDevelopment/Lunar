package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("lunar.staff")) {
            Lunar.getInstance().getData().getStaffMembers().add(event.getPlayer().getUniqueId());
        } else if (event.getPlayer().hasPermission("lunar.admin")) {
            Lunar.getInstance().getData().getAdminMembers().add(event.getPlayer().getUniqueId());
        } else if (event.getPlayer().hasPermission("lunar.owner")) {
            Lunar.getInstance().getData().getOwnerMembers().add(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Lunar.getInstance().getData().getStaffMembers().remove(event.getPlayer().getUniqueId());
        Lunar.getInstance().getData().getAdminMembers().remove(event.getPlayer().getUniqueId());
        Lunar.getInstance().getData().getOwnerMembers().remove(event.getPlayer().getUniqueId());

        if (Lunar.getInstance().getData().getInventories().containsKey(event.getPlayer().getUniqueId())) {
            event.getPlayer().getInventory().clear();
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            event.getPlayer().getInventory().setContents(Lunar.getInstance().getData().getInventories().get(event.getPlayer().getUniqueId()));

            Lunar.getInstance().getData().getInventories().remove(event.getPlayer().getUniqueId());
        }
    }
}
