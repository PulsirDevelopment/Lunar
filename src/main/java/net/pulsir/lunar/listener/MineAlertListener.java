package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;
import java.util.UUID;

public class MineAlertListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent event) {
        if (!Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("mine-alert.enabled")) return;

        for (final String block : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("mine-alert.block-trigger")) {
            if (event.getBlock().getType().name().equalsIgnoreCase(block)) {
                if (!Lunar.getInstance().getData().getMineAlerts().isEmpty()) {
                    for (final UUID uuid : Lunar.getInstance().getData().getMineAlerts()) {
                        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                        .getInstance().getLanguage().getConfiguration().getString("MINE-ALERTS.ALERT"))
                                .replace("{player}", event.getPlayer().getName())
                                .replace("{block}", event.getBlock().getType().name())));
                    }
                }
                break;
            }
        }
    }
}
