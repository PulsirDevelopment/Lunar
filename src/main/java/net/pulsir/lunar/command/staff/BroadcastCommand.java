package net.pulsir.lunar.command.staff;

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

public class BroadcastCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.staff"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            for (final String usage : Lunar.getInstance().getLanguage().getConfiguration().getStringList("USAGE.BROADCAST")) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(usage));
            }
        } else {
            String type = args[0];
            if (args.length == 1) {
                for (final String usage : Lunar.getInstance().getLanguage().getConfiguration().getStringList("USAGE.BROADCAST")) {
                    sender.sendMessage(Lunar.getInstance().getMessage().getMessage(usage));
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

                if (type.equalsIgnoreCase("server")) {
                    Bukkit.broadcast(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("BROADCAST-MESSAGE")).replace("{message}", message)));
                } else if (type.equalsIgnoreCase("global")) {
                    if (!(sender instanceof Player player)) return false;

                    if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system")).equalsIgnoreCase("bungee")) {
                        Bungee.sendMessage(player, message, ChannelType.GLOBAL);
                        Bukkit.broadcast(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("BROADCAST-MESSAGE")).replace("{message}", message)));
                    } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system")).equalsIgnoreCase("redis")) {
                        Lunar.getInstance().getRedisAdapter().publish("global-chat", message);
                        Bukkit.broadcast(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("BROADCAST-MESSAGE")).replace("{message}", message)));
                    } else {
                        Bungee.sendMessage(player, message, ChannelType.GLOBAL);
                        Bukkit.broadcast(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("BROADCAST-MESSAGE")).replace("{message}", message)));
                    }
                } else {
                    for (final String usage : Lunar.getInstance().getLanguage().getConfiguration().getStringList("USAGE.BROADCAST")) {
                        sender.sendMessage(Lunar.getInstance().getMessage().getMessage(usage));
                    }
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return new ArrayList<>(List.of("server", "global"));
        } else if (args.length == 2) {
            return new ArrayList<>(List.of("Message"));
        }

        return new ArrayList<>();
    }
}
