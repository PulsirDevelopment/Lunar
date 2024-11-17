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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CPSCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.command.cps"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            usage(sender);
            return false;
        } else if (args[0].equalsIgnoreCase("check")) {
            if (args.length == 1) {
                usage(sender);
                return false;
            } else {
                Player target = Bukkit.getPlayer(args[1]);

                if (target == null || !target.isOnline()) {
                    sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("PLAYER-NULL"))
                            .replace("{player}", args[0])));
                    return false;
                }

                int cps = 0;
                if (Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().get(target.getUniqueId()) != null) {
                    cps = Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().get(target.getUniqueId());
                }

                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("CPS.CHECK"))
                        .replace("{player}", target.getName())
                        .replace("{cps}", String.valueOf(cps))));
            }
        } else if (args[0].equalsIgnoreCase("follow")) {
            if (!(sender instanceof Player player)) return false;

            if (args.length == 1) {
                usage(sender);
                return false;
            } else {
                Player target = Bukkit.getPlayer(args[1]);

                if (target == null || !target.isOnline()) {
                    sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("PLAYER-NULL"))
                            .replace("{player}", args[0])));
                    return false;
                }

                Lunar.getInstance().getCpsPlayerManager().getFollowedPlayers().put(player.getUniqueId(), target.getUniqueId());

                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                        .getString("CPS.FOLLOWED")).replace("{player}", target.getName())));

                return true;
            }
        } else if (args[0].equalsIgnoreCase("unfollow")) {
            if (!(sender instanceof Player player)) return false;

            if (args.length == 1) {
                usage(sender);
                return false;
            } else {
                Player target = Bukkit.getPlayer(args[1]);

                if (target == null || !target.isOnline()) {
                    sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("PLAYER-NULL"))
                            .replace("{player}", args[0])));
                    return false;
                }

                if (Lunar.getInstance().getCpsPlayerManager().getFollowedPlayers().get(player.getUniqueId()) == null) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("CPS.NONE-FOLLOWED")));
                    return false;
                }

                if (!Lunar.getInstance().getCpsPlayerManager().getFollowedPlayers().get(player.getUniqueId()).equals(target.getUniqueId())) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("CPS.WRONG-FOLLOWER")).replace("{player}", target.getName())));
                    return false;
                }

                Lunar.getInstance().getCpsPlayerManager().getFollowedPlayers().remove(player.getUniqueId());
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                        .getString("CPS.UN-FOLLOWED")).replace("{player}", target.getName())));
                return true;
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("check", "follow", "unfollow"));
        }

        return new ArrayList<>();
    }

    private void usage(CommandSender sender){
        for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("CPS.USAGE")) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
        }
    }
}
