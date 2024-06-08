package net.pulsir.lunar.inventories.manager;

import lombok.Getter;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.utils.serializer.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class InventoryManager {

    private final Map<UUID, InventoryPlayer> inventories = new HashMap<>();

    public String getInventoryAsString(UUID uuid) {
        String inventory = "";

        for (final ItemStack itemStack : inventories.get(uuid).getInventory()) {
            if (inventory.isEmpty()) {
                inventory = ItemStackSerializer.serialize(itemStack);
            } else {
                inventory = inventory + "-" + ItemStackSerializer.serialize(itemStack);
            }
        }

        return inventory;
    }

    public ItemStack[] getInventoryFromString(UUID uuid, String inventory) {
        ItemStack[] itemStacks = new ItemStack[inventory.split("-").length];
        int index = 0;
        for (final String itemStack : inventory.split("-")) {
            itemStacks[index] = ItemStackSerializer.deSerialize(itemStack);
            index++;
        }

        return itemStacks;
    }
}
