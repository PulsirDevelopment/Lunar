package net.pulsir.lunar.utils.inventory.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.LInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FreezeInventory implements LInventory {

    @Override
    public Inventory inventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, Lunar.getInstance().getConfiguration().getConfiguration()
                .getInt("freeze-inventory.size"), Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                .getConfiguration().getConfiguration().getString("freeze-inventory.title")));


        for (final String items : Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getConfigurationSection("freeze-inventory.items")).getKeys(false)) {
            ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("freeze-inventory.items." + items + ".item")));
            ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration().getString("freeze-inventory.items." + items + ".name"))
                    .decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("freeze-inventory.items." + items + ".lore")
                    .forEach(line -> lore.add(Lunar.getInstance().getMessage().getMessage(line)));

            meta.lore(lore);
            itemStack.setItemMeta(meta);

            inventory.setItem(Lunar.getInstance().getConfiguration().getConfiguration().getInt("freeze-inventory.items." + items + ".slot"), itemStack);

        }

        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("freeze-inventory.overlay")) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(i, new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration()
                            .getString("freeze-inventory.overlay-item"))));
                }
            }
        }

        return inventory;
    }

    @Override
    public void open(Player player) {
        player.openInventory(inventory(player));
    }

    @Override
    public void close(Player player) {
        player.closeInventory(InventoryCloseEvent.Reason.PLUGIN);
    }
}
