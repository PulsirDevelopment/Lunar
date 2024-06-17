package net.pulsir.lunar.utils.staff;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Staff {

    private static ItemStack compass(){
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.compass.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.compass.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.compass.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "compass");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.compass.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.compass.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private static ItemStack inspect(){
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.inspect.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.inspect.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.inspect.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "inspect");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.inspect.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.inspect.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private static ItemStack freeze(){
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.freeze.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.freeze.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.freeze.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "freeze");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.freeze.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.freeze.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private static ItemStack randomTP(){
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.random.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.random.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.random.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "randomtp");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.random.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.random.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private static ItemStack online(){
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.online.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.online.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.online.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "online");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.online.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.online.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private static ItemStack wand(){
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.wand.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.wand.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.wand.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "wand");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.wand.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.wand.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack vanish(){
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.vanish.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.vanish.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.vanish.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "vanish");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.vanish.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.vanish.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static ItemStack unvanish(){
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.unvanish.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.unvanish.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.unvanish.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "vanish");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.unvanish.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.unvanish.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private static ItemStack randomMiners() {
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.random-miners.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.random-miners.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.random-miners.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "randomminertp");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.random-miners.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.random-miners.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    private static ItemStack randomFighter() {
        ItemStack itemStack = new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-items.random-fighter.item")));
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.random-fighter.enchanted")) {
            Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.random-fighter.enchants").forEach(enchant ->
                    itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1])));
        }
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING, "vanish");
        meta.displayName(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration()
                .getString("staff-items.random-fighter.name")).decoration(TextDecoration.ITALIC, false));
        List<Component> lore = new ArrayList<>();
        Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-items.random-fighter.lore").forEach(line ->
                lore.add(Lunar.getInstance().getMessage().getMessage(line)));

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public static void items(Player player) {
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.compass.enabled")) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.compass.slot"), compass());
        }

        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.inspect.enabled")) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.inspect.slot"), inspect());
        }
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.freeze.enabled")) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.freeze.slot"), freeze());
        }
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.random.enabled")) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.random.slot"), randomTP());
        }
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.online.enabled")) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.online.slot"), online());
        }
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.wand.enabled")) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.wand.slot"), wand());
        }
        if (!Lunar.getInstance().getData().getVanish().contains(player.getUniqueId())) {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.vanish.enabled")) {
                player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                        .getInt("staff-items.vanish.slot"), vanish());
            }
        } else {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.vanish.enabled")) {
                player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                        .getInt("staff-items.vanish.slot"), unvanish());
            }
        }
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.random-miners.enabled")) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.random-miners.slot"), randomMiners());
        }
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("staff-items.random-fighter.enabled")) {
            player.getInventory().setItem(Lunar.getInstance().getConfiguration().getConfiguration()
                    .getInt("staff-items.random-fighter.slot"), randomFighter());
        }
    }
}
