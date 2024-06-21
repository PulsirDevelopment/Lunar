package net.pulsir.lunar.command.staff;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public class HideStaffCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.command.hidestaff"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (!(sender instanceof Player player)) return false;

        if (Lunar.getInstance().getData().getHideStaff().contains(player.getUniqueId())) {
            for (final UUID uuid : Lunar.getInstance().getData().getVanish()) {
                player.showPlayer(Lunar.getInstance(), Objects.requireNonNull(Bukkit.getPlayer(uuid)));
            }
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("HIDE-STAFF.DISABLED")));
        } else {
            for (final UUID uuid : Lunar.getInstance().getData().getVanish()) {
                player.hidePlayer(Lunar.getInstance(), Objects.requireNonNull(Bukkit.getPlayer(uuid)));
            }
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("HIDE-STAFF.ENABLED")));
        }

        return true;
    }
}
