package net.pulsir.lunar.command.player;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.bungee.Bungee;
import net.pulsir.lunar.utils.bungee.message.ChannelType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ReportCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (Lunar.getInstance().getData().getReportCooldown().get(player.getUniqueId()) != null && Lunar.getInstance().getData().getReportCooldown().get(player.getUniqueId()) > 1) {
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("REPORT.COOLDOWN"))
                    .replace("{time}", String.valueOf(Lunar.getInstance().getData().getReportCooldown().get(player.getUniqueId())))));
            return false;
        }

        if (args.length == 0) {
            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("REPORT.USAGE")) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
            }
        } else {
            String target = args[0];

            if (args.length == 1) {
                for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("REPORT.USAGE")) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
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
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("REPORT.FORMAT"))
                            .replace("{player}", player.getName())
                            .replace("{message}", message)
                            .replace("{target}", target)
                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                    .getConfiguration().getString("server-name")))));
                }

                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-sync")) {
                    Bungee.sendMessage(player, Lunar.getInstance().getMessage()
                            .getComponent(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                            .getConfiguration().getString("REPORT.FORMAT"))
                                    .replace("{player}", player.getName())
                                    .replace("{message}", message)
                                    .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                            .getConfiguration().getString("server-name")))
                                    .replace("{target}", target))), ChannelType.STAFF);
                }

                if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("REPORT.SUCCESS")).isEmpty()) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("REPORT.SUCCESS")));
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> players = new ArrayList<>();
            for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) players.add(onlinePlayer.getName());
            return players;
        } else if (args.length == 2) {
            return new ArrayList<>(List.of("Message"));
        }
        return new ArrayList<>();
    }
}
