package net.pulsir.lunar.utils.inventory.impl;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.LInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InspectionInventory implements LInventory {

    private final Player target;

    public InspectionInventory(Player target) {
        this.target = target;
    }

    @Override
    public Inventory inventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player,
                Lunar.getInstance().getConfiguration().getConfiguration().getInt("inspect-inventory.size"),
                MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration()
                        .getString("inspect-inventory.title"))));

        List<ItemStack> armor = getItemStacks();

        List<ItemStack> playerInventory = new ArrayList<>();
        playerInventory.addAll(Arrays.asList(target.getInventory().getContents()));
        armor.forEach(a -> playerInventory.removeIf(b -> b != null && b.isSimilar(a)));

        for (ItemStack itemStack : playerInventory) {
            if (itemStack != null) {
                inventory.addItem(itemStack);
            }
        }

        armor.forEach(inventory::addItem);

        return inventory;
    }

    @NotNull
    private List<ItemStack> getItemStacks() {
        ItemStack helmet = target.getInventory().getHelmet();
        ItemStack chestplate = target.getInventory().getChestplate();
        ItemStack leggings = target.getInventory().getLeggings();
        ItemStack boots = target.getInventory().getBoots();

        List<ItemStack> armor = new ArrayList<>();

        if (helmet != null) armor.add(helmet);
        if (chestplate != null) armor.add(chestplate);
        if (leggings != null) armor.add(leggings);
        if (boots != null) armor.add(boots);
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
