package net.pulsir.lunar.offline.manager;

import lombok.Getter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import net.pulsir.lunar.utils.base64.Base64;
import net.pulsir.lunar.utils.serializer.ItemStackSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class OfflinePlayerManager {

    private final Map<UUID, OfflinePlayerInventory> offlinePlayers = new HashMap<>();

    public void saveInventories(Player player) {
        Inventory playerInventory = player.getInventory();
        Inventory enderChest = player.getEnderChest();

        Lunar.getInstance().getDatabase().saveOfflineInventory(player.getUniqueId(), playerInventory, enderChest);
    }

    public String getInventoryAsString(Inventory inventory) {
        List<String> stringedItems = new ArrayList<>();
        for (final ItemStack item : inventory.getContents()) {
            stringedItems.add(ItemStackSerializer.serialize(item));
        }

        return Base64.toBase64(stringedItems);
    }
}
