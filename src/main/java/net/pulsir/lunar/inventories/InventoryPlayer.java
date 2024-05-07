package net.pulsir.lunar.inventories;

import lombok.Getter;
import lombok.Setter;
import net.pulsir.lunar.utils.serializer.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class InventoryPlayer {

    private UUID uuid;
    private ItemStack[] inventory;

    public InventoryPlayer(UUID uuid, ItemStack[] inventory){
        this.uuid = uuid;
        this.inventory = inventory;
    }

    public List<String> getInventoryString() {
        List<String> string = new ArrayList<>();
        for (final ItemStack itemStack : inventory) {
            string.add(ItemStackSerializer.serialize(itemStack));
        }
        return string;
    }
}
