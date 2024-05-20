package net.pulsir.lunar.command.staff;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class SpyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!(sender.hasPermission("lunar.spy"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("SPY.USAGE")) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
            }
        } else if (args[0].equalsIgnoreCase("add")){
            if (args.length == 1) {
                for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("SPY.USAGE")) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
                }
            } else {
                Player target = Bukkit.getPlayer(args[1]);

                if (target == null || !target.isOnline()) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("PLAYER-NULL"))
                            .replace("{player}", args[1])));
                    return false;
                }

                if (target.hasPermission("luanr.staff") && !Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-staff-spy")) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("SPY.STAFF")));
                    return false;
                }

                if (!Lunar.getInstance().getData().getSpy().containsKey(player.getUniqueId())) {
                    Lunar.getInstance().getData().getSpy().put(player.getUniqueId(), new HashSet<>(Set.of(target.getUniqueId())));
                } else {
                    Set<UUID> uuids = Lunar.getInstance().getData().getSpy().get(player.getUniqueId());
                    uuids.add(target.getUniqueId());
                    Lunar.getInstance().getData().getSpy().replace(player.getUniqueId(),
                            Lunar.getInstance().getData().getSpy().get(player.getUniqueId()), uuids);
                }
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("SPY.ADDED"))
                        .replace("{player}", target.getName())));
            }
        } else if (args[0].equalsIgnoreCase("remove")) {
            if (args.length == 1) {
                for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("SPY.USAGE")) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
                }
            } else {
                Player target = Bukkit.getPlayer(args[1]);

                if (target == null || !target.isOnline()) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("PLAYER-NULL"))
                            .replace("{player}", args[1])));
                    return false;
                }

                if (Lunar.getInstance().getData().getSpy().isEmpty() || Lunar.getInstance().getData().getSpy().get(player.getUniqueId()) == null) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("SPY.EMPTY")));
                    return false;
                }

                Lunar.getInstance().getData().getSpy().get(player.getUniqueId()).remove(target.getUniqueId());
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("SPY.REMOVED"))
                        .replace("{player}", target.getName())));
            }
        } else {
            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("SPY.USAGE")) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("add", "remove"));
        } else if (args.length == 2) {
            List<String> players = new ArrayList<>();
            for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) players.add(onlinePlayer.getName());
            return players;
        }

        return new ArrayList<>();
    }
}
