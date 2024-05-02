package net.pulsir.lunar.command.staff;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.staff"))) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS"))));
            return false;
        }

        if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
            Lunar.getInstance().getData().getVanish().remove(player.getUniqueId());
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayers.hasPermission("lunar.staff")) {
                    onlinePlayers.showPlayer(player);
                }
            }
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("VANISH.DISABLED"))));
        } else {
            Lunar.getInstance().getData().getVanish().add(player.getUniqueId());
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayers.hasPermission("lunar.staff")) {
                    onlinePlayers.hidePlayer(player);
                }
            }
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("VANISH.ENABLED"))));
        }

        return true;
    }
}
