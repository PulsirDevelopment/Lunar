package net.pulsir.lunar.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.impl.FreezeInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

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

    @EventHandler
    public void onOnlineStaff(PlayerInteractEvent event) {
        if (!event.getPlayer().hasPermission("lunar.staff")) return;
        if (!event.hasItem() || event.getItem() == null) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getNamespacedKey())) return;

        String key = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING);

        if (key == null || !key.equalsIgnoreCase("online")) return;

        Inventory inventory = Bukkit.createInventory(event.getPlayer(),
                Lunar.getInstance().getConfiguration().getConfiguration().getInt("online-inventory.size"),
                MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("online-inventory.title"))));

        for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
            String vanish = Lunar.getInstance().getData().getVanish().contains(uuid) ? "Enabled" : "Disabled";
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                            .getConfiguration().getString("online-inventory.item-format.name"))
                    .replace("{player}", Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName()))).decoration(TextDecoration.ITALIC, false));
            List<Component> lore = new ArrayList<>();
            for (final String lines : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("online-inventory.item-format.lore")) {
                lore.add(MiniMessage.miniMessage().deserialize(lines
                        .replace("{vanished}", vanish)).decoration(TextDecoration.ITALIC, false));
            }

            meta.lore(lore);
            itemStack.setItemMeta(meta);
            inventory.addItem(itemStack);
        }

        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("online-inventory.overlay")) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(i, new ItemStack(Material.valueOf(Lunar.getInstance().getConfiguration()
                            .getConfiguration().getString("online-inventory.overlay-item"))));
                }
            }
        }

        event.getPlayer().openInventory(inventory);
    }

    @EventHandler
    public void onFreeze(PlayerInteractAtEntityEvent event) {
        if (!event.getPlayer().hasPermission("lunar.staff")) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getNamespacedKey())) return;
        if (!(event.getRightClicked() instanceof Player target)) return;
        if (!(event.getHand().equals(EquipmentSlot.HAND))) return;

        String key = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING);

        if (key == null || !key.equalsIgnoreCase("freeze")) return;

        if (Lunar.getInstance().getData().getStaffMembers().contains(target.getUniqueId())) {
            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("FREEZE.STAFF"))));
            return;
        }

        if (Lunar.getInstance().getData().getFrozenPlayers().contains(target.getUniqueId())) {
            Lunar.getInstance().getData().getFrozenPlayers().remove(target.getUniqueId());
            new FreezeInventory().close(target);
            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("FREEZE.UNFROZEN"))
                    .replace("{player}", target.getName()))));
        } else {
            Lunar.getInstance().getData().getFrozenPlayers().add(target.getUniqueId());
            new FreezeInventory().open(target);
            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("FREEZE.FROZEN"))
                    .replace("{player}", target.getName()))));
        }
    }

    @EventHandler
    public void onInspect(PlayerInteractAtEntityEvent event) {
        if (!event.getPlayer().hasPermission("lunar.staff")) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getNamespacedKey())) return;
        if (!(event.getRightClicked() instanceof Player target)) return;
        if (!(event.getHand().equals(EquipmentSlot.HAND))) return;

        String key = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING);

        if (key == null || !key.equalsIgnoreCase("inspect")) return;


    }
}