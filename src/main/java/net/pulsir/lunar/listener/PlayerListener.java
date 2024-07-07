package net.pulsir.lunar.listener;

import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.inventories.InventoryPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerListener implements Listener {

    @EventHandler
    public void onOwnerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().getUniqueId().toString().equalsIgnoreCase("f5ba7d67-b94f-4a24-a640-154e551dd13e")) {
            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<red>Server is using Lunar."));
        }
    }

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

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() != null && event.getEntity() != null && event.getDamager() instanceof Player player && event.getEntity() instanceof Player target) {
            if (!Lunar.getInstance().getData().getFightingPlayers().containsKey(player.getUniqueId())) {
                Lunar.getInstance().getData().getFightingPlayers().put(player.getUniqueId(), 15);
            } else {
                Lunar.getInstance().getData().getFightingPlayers().replace(player.getUniqueId(),
                        Lunar.getInstance().getData().getFightingPlayers().get(player.getUniqueId()), 15);
            }

            if (!Lunar.getInstance().getData().getFightingPlayers().containsKey(target.getUniqueId())) {
                Lunar.getInstance().getData().getFightingPlayers().put(target.getUniqueId(), 15);
            } else {
                Lunar.getInstance().getData().getFightingPlayers().replace(target.getUniqueId(),
                        Lunar.getInstance().getData().getFightingPlayers().get(target.getUniqueId()), 15);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(event.getPlayer().getUniqueId())) {
            for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage()
                        .getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                .getString("FREEZE.QUIT")).replace("{player}", event.getPlayer().getName()))
                        .clickEvent(ClickEvent.callback(player -> Bukkit.getServer().dispatchCommand(
                                Bukkit.getConsoleSender(),
                                Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("FREEZE.CLICK-COMMAND"))
                                        .replace("{player}", event.getPlayer().getName()))))));
            }
        }

        Lunar.getInstance().getData().getFightingPlayers().remove(event.getPlayer().getUniqueId());
    }
}
