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
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InspectionInventory implements LInventory {

    private final Player target;

    public InspectionInventory(Player target) {
        this.target = target;
    }

    @Override
    public Inventory inventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player,
                54,
                Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration()
                        .getConfiguration().getString("inspect-inventory.title")));

        List<ItemStack> armor = getItemStacks();

        Map<Integer, ItemStack> playerInventory = new HashMap<>();
        for (int i = 0; i < target.getInventory().getContents().length; i++) {
            if (target.getInventory().getItem(i) != null) {
                playerInventory.put(i, target.getInventory().getItem(i));
            }
        }

        armor.forEach(armorItem -> {
            if (armorItem != null) playerInventory.values().remove(armorItem);
        });

        for (int i : playerInventory.keySet()) {
            inventory.setItem(i, playerInventory.get(i));
        }

        AtomicInteger integer = new AtomicInteger(44);
        armor.forEach(armorItem -> inventory.setItem(integer.incrementAndGet(), armorItem));

        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("inspect-inventory.item")));
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("inspect-inventory.name"))
                .replace("{player}", player.getName())).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("inspect-inventory.lore")
                .forEach(line ->
                        lore.add(Lunar.getInstance().getMessage().getMessage(line
                                .replace("{health}", String.valueOf(Math.round(player.getHealth())))
                                .replace("{health_max}", String.valueOf(Math.round(player.getHealthScale())))
                                .replace("{hunger}", String.valueOf(player.getFoodLevel()))
                                .replace("{gamemode}", player.getGameMode().name())).decoration(TextDecoration.ITALIC, false)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("inspect-inventory.statistics-item")) {
            inventory.setItem(53, itemStack);
        }

        return inventory;
    }

    @NotNull
    private List<ItemStack> getItemStacks() {
        ItemStack helmet = target.getInventory().getHelmet();
        ItemStack chestplate = target.getInventory().getChestplate();
        ItemStack leggings = target.getInventory().getLeggings();
        ItemStack boots = target.getInventory().getBoots();
        ItemStack outHand = target.getInventory().getItemInOffHand();

        List<ItemStack> armor = new ArrayList<>();

        if (helmet != null) armor.add(helmet);
        if (chestplate != null) armor.add(chestplate);
        if (leggings != null) armor.add(leggings);
        if (boots != null) armor.add(boots);
        if (outHand != null) armor.add(outHand);
        return armor;
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
