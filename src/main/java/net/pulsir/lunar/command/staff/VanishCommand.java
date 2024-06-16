package net.pulsir.lunar.command.staff;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class VanishCommand implements CommandExecutor {

    @Override
    @Deprecated
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.staff"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
            Lunar.getInstance().getData().getVanish().remove(player.getUniqueId());
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayers.hasPermission("lunar.staff")) {
                    onlinePlayers.showPlayer(player);
                }
            }
            
            Lunar.getInstance().getData().getOnlinePlayers().add(player.getUniqueId());
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("vanish-invis")) {
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
            }
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("VANISH.DISABLED")));
        } else {
            Lunar.getInstance().getData().getVanish().add(player.getUniqueId());
            for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                if (!onlinePlayers.hasPermission("lunar.staff")) {
                    onlinePlayers.hidePlayer(player);
                }
            }

            Lunar.getInstance().getData().getOnlinePlayers().remove(player.getUniqueId());
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("vanish-invis")) {
                player.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(99999999, 1));
            }
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("VANISH.ENABLED")));
                    
        }

        if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.vanish.slot"), Staff.unvanish());
        } else {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.vanish.slot"), Staff.vanish());
        }

        return true;
    }
}
