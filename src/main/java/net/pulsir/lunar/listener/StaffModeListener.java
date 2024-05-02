package net.pulsir.lunar.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.Random;

public class StaffModeListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (Lunar.getInstance().getData().getStaffMode().contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    @Deprecated /*(Reason: Compatibility with Spigot & Paper)*/
    public void onPickup(PlayerPickupItemEvent event) {
        if (Lunar.getInstance().getData().getStaffMode().contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (Lunar.getInstance().getData().getStaffMode().contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (Lunar.getInstance().getData().getStaffMode().contains(event.getPlayer().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (Lunar.getInstance().getData().getStaffMode().contains(event.getWhoClicked().getUniqueId()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onRandomTeleport(PlayerInteractEvent event) {
        if (!event.getPlayer().hasPermission("lunar.staff")) return;
        if (!event.hasItem() || event.getItem() == null) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getNamespacedKey())) return;

        String key = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING);

        if (key == null || !key.equalsIgnoreCase("randomtp")) return;

        int index = new Random().nextInt(Bukkit.getOnlinePlayers().size());
        Player randomPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[index];

        if (randomPlayer != null) {
            event.getPlayer().teleport(randomPlayer.getLocation());
            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("STAFF.TELEPORTED"))
                    .replace("{player}", randomPlayer.getName()))));
        }
    }
}