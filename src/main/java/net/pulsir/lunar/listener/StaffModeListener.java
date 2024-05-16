package net.pulsir.lunar.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.impl.FreezeInventory;
import net.pulsir.lunar.utils.inventory.impl.InspectionInventory;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class StaffModeListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("lunar.staff")) {
            Lunar.getInstance().getData().getOnlinePlayers().add(event.getPlayer().getUniqueId());
            Lunar.getInstance().getData().getStaffMembers().add(event.getPlayer().getUniqueId());
        }
        if (event.getPlayer().hasPermission("lunar.admin")) {
            Lunar.getInstance().getData().getOnlinePlayers().add(event.getPlayer().getUniqueId());
            Lunar.getInstance().getData().getAdminMembers().add(event.getPlayer().getUniqueId());
        }
        if (event.getPlayer().hasPermission("lunar.owner")) {
            Lunar.getInstance().getData().getOnlinePlayers().add(event.getPlayer().getUniqueId());
            Lunar.getInstance().getData().getOwnerMembers().add(event.getPlayer().getUniqueId());
        }
        if (event.getPlayer().hasPermission("lunar.forcevanish")) {
            Lunar.getInstance().getData().getVanish().add(event.getPlayer().getUniqueId());
            Lunar.getInstance().getData().getOnlinePlayers().remove(event.getPlayer().getUniqueId());
        }

        if (event.getPlayer().hasPermission("lunar.staff")) {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("join-message")) {
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Player player = Bukkit.getPlayer(uuid);
                    Objects.requireNonNull(player).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("STAFF.JOIN-MESSAGE"))
                            .replace("{player}", event.getPlayer().getName())
                            .replace("{server}", Bukkit.getServer().getName())));
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("quit-message")) {
            if (event.getPlayer().hasPermission("lunar.staff")) {
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Player player = Bukkit.getPlayer(uuid);
                    Objects.requireNonNull(player).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("STAFF.QUIT-MESSAGE"))
                            .replace("{player}", event.getPlayer().getName())
                            .replace("{server}", event.getPlayer().getName())));
                }
            }
        }

        Lunar.getInstance().getData().getStaffMembers().remove(event.getPlayer().getUniqueId());
        Lunar.getInstance().getData().getAdminMembers().remove(event.getPlayer().getUniqueId());
        Lunar.getInstance().getData().getOwnerMembers().remove(event.getPlayer().getUniqueId());
        Lunar.getInstance().getData().getStaffMode().clear();
        Lunar.getInstance().getData().getVanish().remove(event.getPlayer().getUniqueId());

        if (Lunar.getInstance().getData().getInventories().containsKey(event.getPlayer().getUniqueId())) {
            event.getPlayer().getInventory().clear();
            event.getPlayer().setGameMode(GameMode.SURVIVAL);
            event.getPlayer().getInventory().setContents(Lunar.getInstance().getData().getInventories().get(event.getPlayer().getUniqueId()));

            Lunar.getInstance().getData().getInventories().remove(event.getPlayer().getUniqueId());
        }

        Location location = event.getPlayer().getLocation();

        if (location.getBlock().getType().equals(Material.AIR)) {

            for (int i = location.getBlockY(); i > 1; i--) {
                Location newLocation = new Location(location.getWorld(), location.getBlockX(), i, location.getBlockZ());

                if (!newLocation.getBlock().getType().equals(Material.AIR)) {
                    event.getPlayer().teleport(new Location(location.getWorld(), location.getBlockX(), i + 1, location.getBlockZ()));
                    break;
                }
            }
        }
    }

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
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer() == null) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getNamespacedKey())) return;

        String key = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING);

        if (key == null || !key.equalsIgnoreCase("randomtp")) return;

        int index = new Random().nextInt(Bukkit.getOnlinePlayers().size());
        Player randomPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[index];

        if (randomPlayer != null) {
            event.getPlayer().teleport(randomPlayer.getLocation());
            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                            .getConfiguration().getString("STAFF.TELEPORTED"))
                    .replace("{player}", randomPlayer.getName())));
        }
    }

    @EventHandler
    public void onOnlineStaff(PlayerInteractEvent event) {
        if (!event.getPlayer().hasPermission("lunar.staff")) return;
        if (!event.hasItem() || event.getItem() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getNamespacedKey())) return;

        String key = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING);

        if (key == null || !key.equalsIgnoreCase("online")) return;

        Inventory inventory = Bukkit.createInventory(event.getPlayer(),
                Lunar.getInstance().getConfiguration().getConfiguration().getInt("online-inventory.size"),
                Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration().getString("online-inventory.title")));

        for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
            String vanish = Lunar.getInstance().getData().getVanish().contains(uuid) ? "Enabled" : "Disabled";
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            ItemMeta meta = itemStack.getItemMeta();
            meta.displayName(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration()
                            .getString("online-inventory.item-format.name"))
                    .replace("{player}", Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName())).decoration(TextDecoration.ITALIC, false));

            List<Component> lore = new ArrayList<>();
            for (final String lines : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("online-inventory.item-format.lore")) {
                lore.add(Lunar.getInstance().getMessage().getMessage(lines.replace("{vanished}", vanish)).decoration(TextDecoration.ITALIC, false));
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
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer() == null) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getNamespacedKey())) return;
        if (!(event.getRightClicked() instanceof Player target)) return;
        if (!(event.getHand().equals(EquipmentSlot.HAND))) return;

        String key = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING);

        if (key == null || !key.equalsIgnoreCase("freeze")) return;

        if (Lunar.getInstance().getData().getStaffMembers().contains(target.getUniqueId())) {
            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("FREEZE.STAFF")));
            return;
        }

        if (Lunar.getInstance().getData().getFrozenPlayers().contains(target.getUniqueId())) {
            Lunar.getInstance().getData().getFrozenPlayers().remove(target.getUniqueId());
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("inventory-on-freeze")) {
                new FreezeInventory().close(target);
            }

            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("freeze-chat")) {
                Lunar.getInstance().getData().getFreezeChat().remove(target.getUniqueId());
            }

            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                            .getString("FREEZE.UNFROZEN"))
                    .replace("{player}", target.getName())));
            for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                .getInstance().getLanguage().getConfiguration().getString("FREEZE.STAFF-UNFROZEN"))
                        .replace("{player}", target.getName())
                        .replace("{staff}", event.getPlayer().getName())));
            }
        } else {
            Lunar.getInstance().getData().getFrozenPlayers().add(target.getUniqueId());
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("inventory-on-freeze")) {
                new FreezeInventory().open(target);
            }

            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("freeze-chat")) {
                Lunar.getInstance().getData().getFreezeChat().add(target.getUniqueId());
            }

            event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                            .getString("FREEZE.FROZEN"))
                    .replace("{player}", target.getName())));

            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("FREEZE-CHAT.MESSAGE")) {
                target.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
            }
            for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                .getInstance().getLanguage().getConfiguration().getString("FREEZE.STAFF-FROZEN"))
                        .replace("{player}", target.getName())
                        .replace("{staff}", event.getPlayer().getName())));
            }
        }
    }

    @EventHandler
    public void onInspect(PlayerInteractAtEntityEvent event) {
        if (!event.getPlayer().hasPermission("lunar.staff")) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getItemMeta() == null) return;
        if (event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer() == null) return;
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Lunar.getInstance().getNamespacedKey())) return;
        if (!(event.getRightClicked() instanceof Player target)) return;
        if (!(event.getHand().equals(EquipmentSlot.HAND))) return;
        Player player = event.getPlayer();

        String key = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getNamespacedKey(), PersistentDataType.STRING);

        if (key == null || !key.equalsIgnoreCase("inspect")) return;

        InspectionInventory inventory = new InspectionInventory(target);
        inventory.open(player);
    }
}