package net.pulsir.lunar.command.staff;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.bungee.Bungee;
import net.pulsir.lunar.utils.bungee.message.ChannelType;
import net.pulsir.lunar.utils.inventory.impl.FreezeInventory;
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

public class FreezeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.staff"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            for (final String usage : Lunar.getInstance().getLanguage().getConfiguration().getStringList("USAGE.FREEZE")) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(usage));
            }
        } else {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("PLAYER-NULL"))
                        .replace("{player}", args[0])));
                return false;
            }

            if (Lunar.getInstance().getData().getStaffMembers().contains(target.getUniqueId())) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("FREEZE.STAFF")));
                return false;
            }

            if (Lunar.getInstance().getData().getFrozenPlayers().contains(target.getUniqueId())) {
                Lunar.getInstance().getData().getFrozenPlayers().remove(target.getUniqueId());
                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("inventory-on-freeze")) {
                    new FreezeInventory().close(target);
                }

                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("freeze-chat")) {
                    Lunar.getInstance().getData().getFreezeChat().remove(target.getUniqueId());
                }

                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("FREEZE.UNFROZEN"))
                        .replace("{player}", target.getName())));
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                    .getInstance().getLanguage().getConfiguration().getString("FREEZE.STAFF-UNFROZEN"))
                            .replace("{player}", target.getName())
                            .replace("{staff}", sender.getName())
                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("server-name")))));
                }

                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-sync")) {
                    if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                            .equalsIgnoreCase("bungee")) {
                        if (sender instanceof Player player) {
                            Bungee.sendMessage(player,
                                    Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("FREEZE.STAFF-UNFROZEN"))
                                            .replace("{player}", target.getName())
                                            .replace("{staff}", sender.getName())
                                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance()
                                                    .getConfiguration().getConfiguration().getString("server-name"))),
                                    ChannelType.UNFREEZE);
                        }
                    } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                            .equalsIgnoreCase("redis")) {
                        String message = target.getName() + "<splitter>" + sender.getName() + "<splitter>" + ".";
                        Lunar.getInstance().getRedisManager().publish("unfreeze-chat", message);
                    }
                }
            } else {
                Lunar.getInstance().getData().getFrozenPlayers().add(target.getUniqueId());
                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("inventory-on-freeze")) {
                    new FreezeInventory().open(target);
                }

                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("freeze-chat")) {
                    Lunar.getInstance().getData().getFreezeChat().add(target.getUniqueId());
                }
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("FREEZE.FROZEN"))
                        .replace("{player}", target.getName())));
                for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("FREEZE-CHAT.MESSAGE")) {
                    target.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
                }
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                    .getInstance().getLanguage().getConfiguration().getString("FREEZE.STAFF-FROZEN"))
                            .replace("{player}", target.getName())
                            .replace("{staff}", sender.getName())
                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("server-name")))));
                }

                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-sync")) {
                    if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                            .equalsIgnoreCase("bungee")) {
                        if (sender instanceof Player player) {
                            Bungee.sendMessage(player,
                                    Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("FREEZE.STAFF-FROZEN"))
                                            .replace("{player}", target.getName())
                                            .replace("{staff}", sender.getName())
                                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance()
                                                    .getConfiguration().getConfiguration().getString("server-name"))),
                                    ChannelType.FREEZE);
                        }
                    } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                            .equalsIgnoreCase("redis")) {
                        String message = target.getName() + "<splitter>" + sender.getName() + "<splitter>" + ".";
                        Lunar.getInstance().getRedisManager().publish("freeze-chat", message);
                    }
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
        }

        return new ArrayList<>();
    }
}
