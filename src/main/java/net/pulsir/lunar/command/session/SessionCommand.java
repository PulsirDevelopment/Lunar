package net.pulsir.lunar.command.session;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.session.SessionPlayer;
import net.pulsir.lunar.utils.time.Time;
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

public class SessionCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!sender.hasPermission("lunar.command.session")) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            for (final String usage : Lunar.getInstance().getLanguage().getConfiguration().getStringList("SESSION.USAGE")) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(usage));
            }
        } else if (args[0].equalsIgnoreCase("check")) {
            if (args.length == 1) {
                for (final String usage : Lunar.getInstance().getLanguage().getConfiguration().getStringList("SESSION.USAGE")) {
                    sender.sendMessage(Lunar.getInstance().getMessage().getMessage(usage));
                }
            } else {
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) return false;

                SessionPlayer sessionPlayer = Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().get(player.getUniqueId());
                if (sessionPlayer != null) {
                    if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("SESSION.CHECK")).isEmpty()) {
                        sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("SESSION.CHECK")).replace("{player}",
                                player.getName()).replace("{time}", Time.parse(sessionPlayer.getSessionTime()))));
                    }
                } else {
                    if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("SESSION.FAILED")).isEmpty()) {
                        sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                .getLanguage().getConfiguration().getString("SESSION.FAILED")).replace("{player}", args[1])));
                    }
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("check"));
        } else if (args.length == 2) {
            List<String> players = new ArrayList<>();

            for (final UUID sessionPlayerUUID : Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().keySet()) {
                Player player = Bukkit.getPlayer(sessionPlayerUUID);
                if (player != null) {
                    players.add(player.getName());
                }
            }

            return players;
        }

        return new ArrayList<>();
    }
}
