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

public class RequestCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (Lunar.getInstance().getData().getRequestCooldown().get(player.getUniqueId()) != null && Lunar.getInstance().getData().getRequestCooldown().get(player.getUniqueId()) > 1) {
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("REQUEST.COOLDOWN"))
                    .replace("{time}", String.valueOf(Lunar.getInstance().getData().getRequestCooldown().get(player.getUniqueId())))));
            return false;
        }

        if (args.length == 0) {
            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("REQUEST.USAGE")) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
            }
        } else {
            String message = "";
            for (String arg : args) {
                if (message.isEmpty()) {
                    message = arg;
                } else {
                    message = message + " " + arg;
                }
            }

            Lunar.getInstance().getData().getRequestCooldown().put(player.getUniqueId(),
                    Lunar.getInstance().getConfiguration().getConfiguration().getInt("request-cooldown"));
            for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("REQUEST.FORMAT"))
                        .replace("{player}", player.getName())
                        .replace("{message}", message)
                        .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                .getConfiguration().getString("server-name")))));
            }

            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-sync")) {
                Bungee.sendMessage(player, Lunar.getInstance().getMessage()
                        .getComponent(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                        .getConfiguration().getString("REQUEST.FORMAT"))
                                .replace("{player}", player.getName())
                                .replace("{message}", message)
                                .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                        .getConfiguration().getString("server-name"))))), ChannelType.STAFF);
            }

            if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("REQUEST.SUCCESS")).isEmpty()) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("REQUEST.SUCCESS")));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("Message"));
        }
        return new ArrayList<>();
    }
}
