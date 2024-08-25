package net.pulsir.lunar.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

public class WorldListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (MiniMessage.miniMessage().serialize(event.getView().title())
                .equalsIgnoreCase(Lunar.getInstance().getConfiguration().getConfiguration().getString("world-inventory.title"))
                || LegacyComponentSerializer.legacyAmpersand().serialize(event.getView().title()).equalsIgnoreCase(Lunar.getInstance()
                .getConfiguration().getConfiguration().getString("world-inventory.title"))) {
            event.setCancelled(true);
        }

        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null) return;
        String world = event.getCurrentItem().getItemMeta().getPersistentDataContainer().get(Lunar.getInstance().getWorldKey(), PersistentDataType.STRING);
        if (world == null) return;

        World newWorld = Bukkit.getWorld(world);
        if (newWorld == null) return;

        player.teleport(newWorld.getSpawnLocation());
    }
}
