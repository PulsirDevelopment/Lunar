package net.pulsir.lunar.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class ChestListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("silent-chests")) {
                if (Lunar.getInstance().getData().getStaffMode().contains(event.getPlayer().getUniqueId())) {
                    if (Objects.requireNonNull(event.getClickedBlock()).getType().equals(Material.CHEST)
                            || event.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
                        Chest chest = (Chest) event.getClickedBlock().getState();

                        Inventory chestInventory = chest.getInventory();
                        Inventory playerInventory = Bukkit.createInventory(event.getPlayer(),
                                chestInventory.getSize(), Lunar.getInstance().getMessage()
                                        .getMessage(Lunar.getInstance().getConfiguration()
                                                .getConfiguration().getString("silent-inventory.chest-title")));

                        playerInventory.setContents(chestInventory.getContents());
                        event.getPlayer().openInventory(playerInventory);
                        event.setCancelled(true);

                        event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar
                                .getInstance().getLanguage().getConfiguration()
                                .getString("SILENT.CHEST")));
                    }

                    if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
                        Inventory enderChestInventory = event.getPlayer().getEnderChest();

                        Inventory playerInventory = Bukkit.createInventory(event.getPlayer(),
                                enderChestInventory.getSize(), Lunar.getInstance().getMessage()
                                        .getMessage(Lunar.getInstance().getConfiguration()
                                                .getConfiguration().getString("silent-inventory.enderchest-title")));
                        playerInventory.setContents(enderChestInventory.getContents());

                        event.getPlayer().openInventory(playerInventory);
                        event.setCancelled(true);

                        event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar
                                .getInstance().getLanguage().getConfiguration()
                                .getString("SILENT.ENDER-CHEST")));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSilentInventory(InventoryClickEvent event) {
        if (MiniMessage.miniMessage().serialize(event.getView().title())
                .equalsIgnoreCase(Lunar.getInstance().getConfiguration().getConfiguration().getString("silent-inventory.chest-title"))
                || LegacyComponentSerializer.legacyAmpersand().serialize(event.getView().title()).equalsIgnoreCase(Lunar.getInstance()
                .getConfiguration().getConfiguration().getString("silent-inventory.chest-title"))) {
            event.setCancelled(true);
        }

        if (MiniMessage.miniMessage().serialize(event.getView().title())
                .equalsIgnoreCase(Lunar.getInstance().getConfiguration().getConfiguration().getString("silent-inventory.enderchest-title"))
                || LegacyComponentSerializer.legacyAmpersand().serialize(event.getView().title()).equalsIgnoreCase(Lunar.getInstance()
                .getConfiguration().getConfiguration().getString("silent-inventory.enderchest-title"))) {
            event.setCancelled(true);
        }
    }
}
