package net.pulsir.lunar.command.restore;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LastInventoryCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.command.lastinventory"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }
        if (!(sender instanceof Player player)) return false;

        if (args.length == 0) {
            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("LAST-INVENTORY.USAGE")) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
            }
        } else {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("PLAYER-NULL"))
                        .replace("{player}", args[0])));
                return false;
            }

            if (!Lunar.getInstance().getInventoryManager().getInventories().containsKey(target.getUniqueId())) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("RESTORE.NO-DATABASE"))
                        .replace("{player}", target.getName())));
                return false;
            }

            Inventory inventory = Bukkit.createInventory(player, Lunar.getInstance().getConfiguration().getConfiguration().getInt("last-inventory.size"),
                    Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration().getString("last-inventory.title")));

            int inventoryIndex = 0;
            int armorIndex = 0;

            List<Integer> inventoryValues = Lunar.getInstance().getConfiguration().getConfiguration().getIntegerList("last-inventory.slots");
            List<Integer> armorValues = Lunar.getInstance().getConfiguration().getConfiguration().getIntegerList("last-inventory.armor-slots");
            boolean isOffHandNull = Lunar.getInstance().getInventoryManager().getInventories().get(target.getUniqueId()).getInventory()[40] == null;

            for (int i = 0; i < Lunar.getInstance().getInventoryManager().getInventories().get(target.getUniqueId()).getInventory().length; i++) {
                if (i > 35 && i < 40) {
                    inventory.setItem(armorValues.get(armorIndex) - 1, Lunar.getInstance().getInventoryManager().getInventories().get(target.getUniqueId()).getInventory()[i]);
                    armorIndex++;
                }
                if (i == 40) {
                    inventory.setItem(Lunar.getInstance().getConfiguration().getConfiguration().getInt("last-inventory.off-hand-slot") - 1,
                            Lunar.getInstance().getInventoryManager().getInventories().get(target.getUniqueId()).getInventory()[i]);
                }

                if (inventoryIndex < inventoryValues.size()) {
                    inventory.setItem(inventoryValues.get(inventoryIndex) - 1, Lunar.getInstance().getInventoryManager().getInventories().get(target.getUniqueId()).getInventory()[i]);
                }

                inventoryIndex++;
            }

            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("last-inventory.overlay")) {
                if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("last-inventory.slot-overlay")) {
                    for (int i = 0; i < inventory.getSize(); i++) {
                        if (inventory.getItem(i) == null) {
                            inventory.setItem(i, new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration()
                                    .getConfiguration().getString("last-inventory.overlay-item"))));
                        }
                    }
                } else {
                    for (int i = 0; i < inventory.getSize(); i++) {
                        if (inventory.getItem(i) == null && !inventoryValues.contains(i + 1) && !armorValues.contains(i + 1) && !isOffHandNull) {
                            inventory.setItem(i, new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration()
                                    .getConfiguration().getString("last-inventory.overlay-item"))));
                        }
                    }
                }
            }

            player.openInventory(inventory);
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> players = new ArrayList<>();
            for (final Player player : Bukkit.getOnlinePlayers()) {
                players.add(player.getName());
            }
            return players;
        }

        return new ArrayList<>();
    }
}
