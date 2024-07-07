package net.pulsir.lunar.listener;

import io.papermc.paper.event.entity.EntityDamageItemEvent;
import lombok.Setter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.config.Config;
import net.pulsir.lunar.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Set;
import java.util.UUID;

public class VanishListener implements Listener {

    /*
    Hides joining staff to already online players who don't have permissions.
     */
    @EventHandler
    @SuppressWarnings("ALL")
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Config config = Lunar.getInstance().getConfiguration();
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();

        if (player.hasPermission("lunar.command.vanish") && config.getConfiguration().getBoolean("force-vanish")) {
            vanishSet.add(player.getUniqueId());
            Lunar.getInstance().getData().getOnlinePlayers().remove(player.getUniqueId());

            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(online -> !vanishSet.contains(online.getUniqueId()))
                    .forEach(onlinePlayer -> onlinePlayer.hidePlayer(Lunar.getInstance(), player));
        }

        if (!player.hasPermission("lunar.command.vanish")) {
            if (Lunar.getInstance().getData().getVanish().isEmpty()) return;
            for (final UUID vanishedPlayers : Lunar.getInstance().getData().getVanish()) {
                Player vanishedPlayer = Bukkit.getPlayer(vanishedPlayers);

                if (vanishedPlayer != null) {
                    player.hidePlayer(vanishedPlayer);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();
        if (event.getEntity() instanceof Player player) {
            if (vanishSet.contains(player.getUniqueId())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        Message message = Lunar.getInstance().getMessage();
        Config language = Lunar.getInstance().getLanguage();
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();

        if (event.getEntity() instanceof Player player) {
            if (vanishSet.contains(player.getUniqueId())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }

        if (event.getDamager() instanceof Player player) {
            if (vanishSet.contains(player.getUniqueId())) {
                player.sendMessage(message.getMessage(language.getConfiguration().getString("VANISH.DECLINE-ATTACK")));
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDamage(EntityDamageItemEvent event) {
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();

        if (event.getEntity() instanceof Player player) {
            if (vanishSet.contains(player.getUniqueId())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByBlockEvent event) {
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();

        if (event.getEntity() instanceof Player player) {
            if (vanishSet.contains(player.getUniqueId())) {
                event.setDamage(0);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Message message = Lunar.getInstance().getMessage();
        Config language = Lunar.getInstance().getLanguage();
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();

        if (vanishSet.contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(message.getMessage(language.getConfiguration().getString("VANISH.DECLINE-PLACE-BLOCK")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Message message = Lunar.getInstance().getMessage();
        Config language = Lunar.getInstance().getLanguage();
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();

        if (vanishSet.contains(event.getPlayer().getUniqueId())) {
            event.getPlayer().sendMessage(message.getMessage(language.getConfiguration().getString("VANISH.DECLINE-BREAK-BLOCK")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLivingEntityTarget(EntityTargetLivingEntityEvent event) {
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();

        if (event.getTarget() instanceof Player player) {
            if (vanishSet.contains(player.getUniqueId())) {
                event.setTarget(null);
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();

        if (event.getTarget() instanceof Player player) {
            if (vanishSet.contains(player.getUniqueId())) {
                event.setTarget(null);
                event.setCancelled(true);
            }
        }
    }
}
