package net.pulsir.lunar.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.impl.CaptchaInventory;

public class CaptchaListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("lunar.bot.bypass")) return;
        if (!Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("captcha-on-join")) return;
        if (player.getPersistentDataContainer().has(Lunar.getInstance().getCaptchaKey())) return;

        Bukkit.getScheduler().runTaskLater(Lunar.getInstance(), () -> new CaptchaInventory().open(player), 5L);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null
        || event.getCurrentItem().getItemMeta().getPersistentDataContainer()
                .get(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER) == null) return;

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        PersistentDataContainer itemMetaData = event.getCurrentItem().getItemMeta().getPersistentDataContainer();

        if (!playerData.has(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER)) return;
        if (!itemMetaData.has(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER)) return;

        if (itemMetaData.get(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER) == 1) {
            Lunar.getInstance().getLogger().info("pass");
            playerData.set(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER, 0);
            player.closeInventory(Reason.CANT_USE);
            return;
        }

        if (itemMetaData.get(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER) == 0) {
            Lunar.getInstance().getLogger().info("fail");
            String kickMessage = Lunar.getInstance().getLanguage().getConfiguration().getString("CAPTCHA.KICK-MESSAGE");
            player.kick(Lunar.getInstance().getMessage().getMessage(kickMessage));
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        PersistentDataContainer playerData = player.getPersistentDataContainer();

        if (!playerData.has(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER)) return;
        if (playerData.get(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER) == 0) return;

        Bukkit.getScheduler().runTaskLater(Lunar.getInstance(), () -> new CaptchaInventory().open(player), 5L);
    }
}
