package net.pulsir.lunar.utils.inventory.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.config.Config;
import net.pulsir.lunar.utils.inventory.LInventory;
import net.pulsir.lunar.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InspectionInventory implements LInventory {

    private final Player target;
    private final Config config;
    private final Message message;

    public InspectionInventory(Player target) {
        this.target = target;
        this.message = Lunar.getInstance().getMessage();
        this.config = Lunar.getInstance().getConfiguration();
    }

    @Override
    public Inventory inventory(Player ignored) {
        Component title = message.getMessage(config.getConfiguration().getString("inspect-inventory.title"));
        Inventory inventory = Bukkit.createInventory(target, 54, title);

        PlayerInventory targetInventory = target.getInventory();
        ItemStack[] contents = targetInventory.getContents();

        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item != null) {
                inventory.setItem(i < 36 ? i : i + 9, item);
            }
        }

        if (config.getConfiguration().getBoolean("inspect-inventory.statistics-item")) {
            setStatisticItem(inventory);
        }

        return inventory;
    }

    private void setStatisticItem(Inventory inventory) {
        ItemStack itemStack = new ItemStack(Material.valueOf(config.getConfiguration().getString("inspect-inventory.item")));
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(message.getMessage(Objects.requireNonNull(config.getConfiguration().getString("inspect-inventory.name"))
                .replace("{player}", target.getName())).decoration(TextDecoration.ITALIC, false));

        List<Component> lore = config.getConfiguration().getStringList("inspect-inventory.lore")
                .stream()
                .map(line -> message.getMessage(
                        line.replace("{health}", String.valueOf(Math.round(target.getHealth())))
                                .replace("{health_max}", String.valueOf(Math.round(target.getHealthScale())))
                                .replace("{hunger}", String.valueOf(target.getFoodLevel()))
                                .replace("{gamemode}", target.getGameMode().name())
                ).decoration(TextDecoration.ITALIC, false))
                .collect(Collectors.toList());

        meta.lore(lore);
        itemStack.setItemMeta(meta);
        inventory.setItem(53, itemStack);
    }

    @Override
    public void open(Player player) {
        player.openInventory(inventory(target));
    }

    @Override
    public void close(Player player) {
        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
    }
}
