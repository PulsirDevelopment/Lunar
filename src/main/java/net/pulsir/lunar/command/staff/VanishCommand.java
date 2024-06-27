package net.pulsir.lunar.command.staff;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

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
            disableVanish(player);
        } else {
            enableVanish(player);
        }

        updateVanishItem(player);
        return true;
    }

    private void disableVanish(Player player) {
        Lunar.getInstance().getData().getVanish().remove(player.getUniqueId());
        Lunar.getInstance().getData().getOnlinePlayers().add(player.getUniqueId());

        Bukkit.getOnlinePlayers().forEach(online -> online.showPlayer(Lunar.getInstance(), player));
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                .getConfiguration().getString("VANISH.DISABLED")));
    }

    @Deprecated
    private void enableVanish(Player player) {
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();
        vanishSet.add(player.getUniqueId());
        Lunar.getInstance().getData().getOnlinePlayers().remove(player.getUniqueId());

        Bukkit.getOnlinePlayers()
                .stream()
                .filter(online -> !vanishSet.contains(online.getUniqueId()))
                .forEach(online -> online.hidePlayer(Lunar.getInstance(), player));

        for (final String vanishEffect : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("vanish-effects")) {
            player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(vanishEffect)),
                    999999,
                    0)
                    .withParticles(false));
        }
        player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                .getConfiguration().getString("VANISH.ENABLED")));
    }

    private void updateVanishItem(Player player) {
        // If player is not in staff mode don't update item.
        if (!Lunar.getInstance().getData().getStaffMode().contains(player.getUniqueId())) return;

        if (Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.vanish.slot"), Staff.unvanish());
        } else {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.vanish.slot"), Staff.vanish());
        }
    }
}
