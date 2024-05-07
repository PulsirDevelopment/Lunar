package net.pulsir.lunar.command.player;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class ReportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (Lunar.getInstance().getData().getReportCooldown().get(player.getUniqueId()) != null && Lunar.getInstance().getData().getReportCooldown().get(player.getUniqueId()) > 1) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("REPORT.COOLDOWN"))
                    .replace("{time}", String.valueOf(Lunar.getInstance().getData().getReportCooldown().get(player.getUniqueId()))))));
            return false;
        }

        if (args.length == 0) {
            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("REPORT.USAGE")) {
                player.sendMessage(MiniMessage.miniMessage().deserialize(lines));
            }
        } else {
            String target = args[0];

            if (args.length == 1) {
                for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("REPORT.USAGE")) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize(lines));
                }
            } else {
                String message = "";

                for (int i = 1; i < args.length; i++) {
                    if (message.isEmpty()) {
                        message = args[i];
                    } else {
                        message = message + " " + args[i];
                    }
                }

                Lunar.getInstance().getData().getReportCooldown().put(player.getUniqueId(),
                        Lunar.getInstance().getConfiguration().getConfiguration().getInt("report-cooldown"));
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("REPORT.FORMAT"))
                            .replace("{player}", player.getName())
                            .replace("{message}", message))
                            .replace("{target}", target)));
                }
                player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("REPORT.SUCCESS"))));
            }
        }

        return true;
    }
}
