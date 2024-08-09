package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Objects;
import java.util.UUID;

public class MineAlertListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent event) {
        if (!Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("mine-alert.enabled")) return;

        for (final String block : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("mine-alert.block-trigger")) {
            if (event.getBlock().getType().name().equalsIgnoreCase(block)) {
                if (!Lunar.getInstance().getData().getMineAlerts().isEmpty() && !event.getBlock().hasMetadata("mined")) {
                    if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("mine-alert.detect-amount")) {
                        int amount = 0;

                        for (int x = -3; x < 3; ++x) {
                            for (int y = -3; y < 3; ++y) {
                                for (int z = -3; z < 3; ++z) {
                                    Block oreBlock = event.getBlock().getLocation().add(x,y,z).getBlock();
                                    oreBlock.setMetadata("mined", new FixedMetadataValue(Lunar.getInstance(), "mined"));

                                    if (oreBlock.getType().name().equalsIgnoreCase(block)) {
                                        amount += 1;
                                    }
                                }
                            }
                        }

                        for (final UUID uuid : Lunar.getInstance().getData().getMineAlerts()) {
                            Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                            .getInstance().getLanguage().getConfiguration().getString("MINE-ALERTS.ALERT"))
                                    .replace("{player}", event.getPlayer().getName())
                                    .replace("{block}", event.getBlock().getType().name())
                                    .replace("{amount}", String.valueOf(amount))));
                        }
                    } else {
                        for (final UUID uuid : Lunar.getInstance().getData().getMineAlerts()) {
                            Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                            .getInstance().getLanguage().getConfiguration().getString("MINE-ALERTS.ALERT"))
                                    .replace("{player}", event.getPlayer().getName())
                                    .replace("{block}", event.getBlock().getType().name())));
                        }
                    }
                }
                break;
            }
        }
    }
}
