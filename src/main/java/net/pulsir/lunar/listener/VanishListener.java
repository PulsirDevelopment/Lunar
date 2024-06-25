package net.pulsir.lunar.listener;

import io.papermc.paper.event.entity.EntityDamageItemEvent;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof Player player) {
            if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof Player player) {
            if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }

        if (event.getDamager() instanceof Player player) {
            if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                        .getLanguage().getConfiguration().getString("VANISH.DECLINE-ATTACK")));
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageItemEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof Player player) {
            if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent event) {
        if (event.getEntity() != null && event.getEntity() instanceof Player player) {
            if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (Lunar.getInstance().getData().getVanish().contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                    .getLanguage().getConfiguration().getString("VANISH.DECLINE-PLACE-BLOCK")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (Lunar.getInstance().getData().getVanish().contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                    .getLanguage().getConfiguration().getString("VANISH.DECLINE-BREAK-BLOCK")));
            event.setCancelled(true);
        }
    }
}
