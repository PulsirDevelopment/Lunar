package net.pulsir.lunar.utils.inventory.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.kyori.adventure.text.Component;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.LInventory;

public class CaptchaInventory implements LInventory {

    @Override
    public Inventory inventory(Player player) {
        YamlConfiguration config = Lunar.getInstance().getConfiguration().getConfiguration();

        int inventorySize = config.getInt("captcha-inventory.size");
        Component inventoryTitle = Lunar.getInstance().getMessage().getMessage(config.getString("captcha-inventory.title"));

        Inventory captchaInventory = Bukkit.createInventory(player, inventorySize, inventoryTitle);

        String confirmItemMaterialName = config.getString("captcha-inventory.items.confirm.item");
        String faulireItemMaterialName = config.getString("captcha-inventory.items.failure.item");

        String confirmItemName = config.getString("captcha-inventory.items.confirm.name");
        String failureItemName = config.getString("captcha-inventory.items.failure.name");

        List<Component> confirmItemLoreList = new ArrayList<>();
        List<Component> failureItemLoreList = new ArrayList<>();

        for (String line : config.getStringList("captcha-inventory.items.confirm.lore")) {
            confirmItemLoreList.add(Lunar.getInstance().getMessage().getMessage(line));
        }

        for (String line : config.getStringList("captcha-inventory.items.failure.lore")) {
            failureItemLoreList.add(Lunar.getInstance().getMessage().getMessage(line));
        }

        Material confirmItemMaterial = Material.valueOf(confirmItemMaterialName);
        Material failureItemMaterial = Material.valueOf(faulireItemMaterialName);

        ItemStack confirmItem = new ItemStack(confirmItemMaterial);
        ItemStack failureItem = new ItemStack(failureItemMaterial);

        ItemMeta confirmItemMeta = confirmItem.getItemMeta();
        ItemMeta failureItemMeta = failureItem.getItemMeta();

        confirmItemMeta.displayName(Lunar.getInstance().getMessage().getMessage(confirmItemName));
        failureItemMeta.displayName(Lunar.getInstance().getMessage().getMessage(failureItemName));

        confirmItemMeta.lore(confirmItemLoreList);
        failureItemMeta.lore(failureItemLoreList);

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        PersistentDataContainer confirmItemMetaData = confirmItemMeta.getPersistentDataContainer();
        PersistentDataContainer failureItemMetaData = failureItemMeta.getPersistentDataContainer();

        playerData.set(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER, 1);
        confirmItemMetaData.set(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER, 1);
        failureItemMetaData.set(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER, 0);

        confirmItem.setItemMeta(confirmItemMeta);
        failureItem.setItemMeta(failureItemMeta);

        for (int i = 0; i < captchaInventory.getSize(); i++) captchaInventory.setItem(i, failureItem);

        Random random = new Random();
        captchaInventory.setItem(random.nextInt(captchaInventory.getSize()), confirmItem);

        return captchaInventory;
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
