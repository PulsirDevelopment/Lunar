package net.pulsir.lunar.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.EnderChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class ChestListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (Lunar.getInstance().getData().getStaffMode().contains(event.getPlayer().getUniqueId())) {
                if (Objects.requireNonNull(event.getClickedBlock()).getType().equals(Material.CHEST)
                || event.getClickedBlock().getType().equals(Material.TRAPPED_CHEST)) {
                    Chest chest = (Chest) event.getClickedBlock().getState();

                    Inventory chestInventory = chest.getInventory();
                    Inventory playerInventory = Bukkit.createInventory(event.getPlayer(),
                            chestInventory.getSize(), "Silent chest");

                    playerInventory.setContents(chestInventory.getContents());
                    event.getPlayer().openInventory(playerInventory);
                    event.setCancelled(true);

                    event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<grey>Opening chest silently."));
                }

                if (event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
                    Inventory enderChestInventory = event.getPlayer().getEnderChest();

                    Inventory playerInventory = Bukkit.createInventory(event.getPlayer(),
                            enderChestInventory.getSize(), "Silent enderchest");
                    playerInventory.setContents(enderChestInventory.getContents());

                    event.getPlayer().openInventory(playerInventory);
                    event.setCancelled(true);

                    event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize("<grey>Opening enderchest silently."));
                }
            }
        }
    }
}
