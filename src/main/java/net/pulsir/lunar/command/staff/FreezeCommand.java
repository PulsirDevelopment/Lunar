package net.pulsir.lunar.command.staff;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.impl.FreezeInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FreezeCommand implements CommandExecutor {

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
            } else {
                Lunar.getInstance().getData().getFrozenPlayers().add(target.getUniqueId());
                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("inventory-on-freeze")) {
                    new FreezeInventory().close(target);
                }

                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("freeze-chat")) {
                    Lunar.getInstance().getData().getFreezeChat().remove(target.getUniqueId());
                }
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("FREEZE.FROZEN"))
                        .replace("{player}", target.getName())));
            }
        }

        return true;
    }
}
