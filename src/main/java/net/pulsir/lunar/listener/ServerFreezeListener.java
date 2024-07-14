package net.pulsir.lunar.listener;

import io.papermc.paper.event.entity.EntityDamageItemEvent;
import net.pulsir.lunar.Lunar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ServerFreezeListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen && !event.getPlayer().hasPermission("lunar.serverfreeze.bypass")) {
            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("SERVER-FROZEN")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen) {
            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("SERVER-FROZEN")));
        }
    }

    @EventHandler
    @Deprecated
    public void onChat(AsyncPlayerChatEvent event) {
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen && !event.getPlayer().hasPermission("lunar.serverfreeze.bypass")) {
            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("SERVER-FROZEN")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageItemEvent event) {
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent event) {
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen) {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent event) {
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen) {
            event.setCancelled(true);
        }
    }
}
