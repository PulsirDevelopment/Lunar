package net.pulsir.lunar.command.offlineinventory;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class OfflineInventoryCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!(sender.hasPermission("lunar.command.offlineinventory"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            usage(player);
        } else if (args[0].equalsIgnoreCase("view")) {
            if (args.length == 1) {
                usage(player);
            } else if (args[1].equalsIgnoreCase("player")) {
                if (args.length == 2) {
                    usage(player);
                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);

                    if (offlinePlayer.isOnline()) {
                        return false;
                    }

                    if (!Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().containsKey(offlinePlayer.getUniqueId())) {
                        return false;
                    }

                    ItemStack[] inventoryContent = Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().get(offlinePlayer.getUniqueId())
                            .getPlayerInventory();


                }
            } else if (args[1].equalsIgnoreCase("enderchest")) {
                if (args.length == 2) {
                    usage(player);
                } else {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[2]);

                    if (offlinePlayer.isOnline()) {
                        return false;
                    }

                    if (!Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().containsKey(offlinePlayer.getUniqueId())) {
                        return false;
                    }

                    ItemStack[] enderChestContent = Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayerInventoryMap().get(offlinePlayer.getUniqueId())
                            .getEnderChestInventory();

                }
            } else {
                usage(player);
            }
        } else {
            usage(player);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("view"));
        } else if (args.length == 2) {
            return new ArrayList<>(List.of("player", "enderchest"));
        } else if (args.length == 3) {
            List<String> names = new ArrayList<>();
            for (final Player player : Bukkit.getOnlinePlayers()) {
                names.add(player.getName());
            }

            return names;
        }

        return new ArrayList<>();
    }

    private void usage(Player player) {
        for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("OFFLINE-INVENTORY.USAGE")) {
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
        }
    }
}
