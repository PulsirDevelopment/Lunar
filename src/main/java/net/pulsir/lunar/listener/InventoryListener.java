package net.pulsir.lunar.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.UUID;

public class InventoryListener implements Listener {

    @EventHandler
    public void onFreezeClick(InventoryClickEvent event) {
        if (MiniMessage.miniMessage().serialize(event.getView().title())
                .equalsIgnoreCase(Lunar.getInstance().getConfiguration().getConfiguration().getString("freeze-inventory.title"))
        || LegacyComponentSerializer.legacyAmpersand().serialize(event.getView().title()).equalsIgnoreCase(Lunar.getInstance()
                .getConfiguration().getConfiguration().getString("freeze-inventory.title"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvseeClick(InventoryClickEvent event) {
        if (MiniMessage.miniMessage().serialize(event.getView().title())
                .equalsIgnoreCase(Lunar.getInstance().getConfiguration().getConfiguration().getString("inspect-inventory.title"))
        || LegacyComponentSerializer.legacyAmpersand().serialize(event.getView().title()).equalsIgnoreCase(Lunar.getInstance()
                .getConfiguration().getConfiguration().getString("inspect-inventory.title"))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onStaffClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (MiniMessage.miniMessage().serialize(event.getView().title())
                .equalsIgnoreCase(Lunar.getInstance().getConfiguration().getConfiguration().getString("online-inventory.title"))
                || LegacyComponentSerializer.legacyAmpersand().serialize(event.getView().title()).equalsIgnoreCase(Lunar.getInstance()
                .getConfiguration().getConfiguration().getString("online-inventory.title"))) {
            event.setCancelled(true);
        }

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().displayName() == null) return;
        if (event.getCurrentItem().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getOnlineStaffKey())) {
            UUID uuid = UUID.fromString(Objects.requireNonNull(event.getCurrentItem()
                    .getItemMeta().getPersistentDataContainer().get(Lunar.getInstance().getOnlineStaffKey(), PersistentDataType.STRING)));

            if (uuid != null && Bukkit.getPlayer(uuid) != null) {
                player.teleport(Objects.requireNonNull(Bukkit.getPlayer(uuid)).getLocation());
            }
        }
    }

    @EventHandler
    public void onLastInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (MiniMessage.miniMessage().serialize(event.getView().title())
                .equalsIgnoreCase(Lunar.getInstance().getConfiguration().getConfiguration().getString("last-inventory.title"))
                || LegacyComponentSerializer.legacyAmpersand().serialize(event.getView().title()).equalsIgnoreCase(Lunar.getInstance()
                .getConfiguration().getConfiguration().getString("last-inventory.title"))) {
            event.setCancelled(true);
        }
    }
}
