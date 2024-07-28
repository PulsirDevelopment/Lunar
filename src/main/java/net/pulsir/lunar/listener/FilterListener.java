package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;
import java.util.UUID;

public class FilterListener implements Listener {

    @EventHandler
    @Deprecated
    public void onFilter(AsyncPlayerChatEvent event){
        if (event.getPlayer().hasPermission("lunar.filter.bypass")) return;

        for (final String filteredWord : Lunar.getInstance().getFilter().getFilterWords().keySet()) {
            if (event.getMessage().contains(filteredWord)) {
                for (final UUID uuid : Lunar.getInstance().getData().getFilterChat()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                    .getInstance().getLanguage().getConfiguration().getString("FILTER-CHAT.FORMAT"))
                            .replace("{player}", event.getPlayer().getName())
                            .replace("{message}", event.getMessage())));
                }

                Bukkit.getScheduler().scheduleSyncDelayedTask(Lunar.getInstance(), () ->
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Lunar.getInstance().getFilter().getFilterWords().get(filteredWord)
                                .replace("{player}", event.getPlayer().getName())), 2L);

                event.setCancelled(true);
                return;
            }
        }
    }
}
