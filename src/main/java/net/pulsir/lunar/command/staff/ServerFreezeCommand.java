package net.pulsir.lunar.command.staff;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ServerFreezeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.command.serverfreeze"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        Lunar.getInstance().getData().setServerFrozen(!Lunar.getInstance().getData().isServerFrozen());
        boolean isFrozen = Lunar.getInstance().getData().isServerFrozen();

        if (isFrozen) {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("SERVER-FROZEN")));
            }
        } else {
            for (final Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("SERVER-UNFROZEN")));
            }
        }

        return true;
    }
}
