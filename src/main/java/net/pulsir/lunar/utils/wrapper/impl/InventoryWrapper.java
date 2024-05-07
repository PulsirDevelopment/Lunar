package net.pulsir.lunar.utils.wrapper.impl;

import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.utils.serializer.ItemStackSerializer;
import net.pulsir.lunar.utils.wrapper.IWrapper;
import org.bson.Document;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class InventoryWrapper implements IWrapper<Document, InventoryPlayer> {

    @Override
    public Document wrap(InventoryPlayer inventoryPlayer) {
        Document document = new Document();
        document.put("uuid", inventoryPlayer.getUuid().toString());
        document.put("inventory", inventoryPlayer.getInventoryString());
        return document;
    }

    @Override
    public InventoryPlayer unwrap(Document document) {
        return new InventoryPlayer(UUID.fromString(document.getString("uuid")),
                toInventory(document.getList("inventory", String.class)));
    }

    private ItemStack[] toInventory(List<String> string){
        ItemStack[] itemStacks = new ItemStack[string.size()];
        for (int i = 0; i < string.size(); i++) {
            itemStacks[i] = ItemStackSerializer.deSerialize(string.get(i));
        }
        return itemStacks;
    }
}
