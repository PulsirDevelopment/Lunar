package net.pulsir.lunar.listener;

import net.kyori.adventure.text.event.ClickEvent;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;
import java.util.UUID;

public class PlayerListener implements Listener {

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
    public void onQuit(PlayerQuitEvent event) {
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(event.getPlayer().getUniqueId())) {
            for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage()
                        .getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                .getString("FREEZE.QUIT")).replace("{player}", event.getPlayer().getName())));
            }
        }
    }
}
