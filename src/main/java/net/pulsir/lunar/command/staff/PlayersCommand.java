package net.pulsir.lunar.command.staff;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlayersCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(sender.hasPermission("lunar.command.players"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        int playersWorld = 0;
        int playersNether = 0;
        int playersEnd = 0;

        for (final Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            String world = onlinePlayers.getLocation().getWorld().getName();
            switch (world) {
                case "world" -> playersWorld++;
                case "world_nether" -> playersNether++;
                case "world_the_end" -> playersEnd++;
            }
        }

        for (final String line : Lunar.getInstance().getLanguage().getConfiguration().getStringList("PLAYERS.LIST")) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(line
                    .replace("{players_world}", String.valueOf(playersWorld))
                    .replace("{players_nether}", String.valueOf(playersNether))
                    .replace("{players_end}", String.valueOf(playersEnd))));
        }

        return true;
    }
}
