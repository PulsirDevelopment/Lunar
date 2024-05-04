package net.pulsir.lunar.listener;

import io.papermc.paper.event.entity.EntityDamageItemEvent;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.impl.FreezeInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FreezeListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(event.getPlayer().getUniqueId())) {
            Bukkit.getScheduler().runTaskLater(Lunar.getInstance(), () -> new FreezeInventory().open(player), 10L);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(player.getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
