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

import java.util.Objects;

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
                onlinePlayers.showPlayer(player);
            }
            
            Lunar.getInstance().getData().getOnlinePlayers().add(player.getUniqueId());
            for (final String vanishEffect : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("vanish-effects")) {
                player.removePotionEffect(Objects.requireNonNull(PotionEffectType.getByName(vanishEffect)));
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
            for (final String vanishEffect : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("vanish-effects")) {
                player.addPotionEffect(Objects.requireNonNull(PotionEffectType.getByName(vanishEffect)).createEffect(999999, 0));
            }
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("VANISH.ENABLED")));
                    
        }

        if (Lunar.getInstance().getData().getStaffMode().contains(player.getUniqueId())) {
            if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
                player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                        .getInt("staff-items.vanish.slot"), Staff.unvanish());
            } else {
                player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                        .getInt("staff-items.vanish.slot"), Staff.vanish());
            }
        }

        return true;
    }
}
