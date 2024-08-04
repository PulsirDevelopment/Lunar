package net.pulsir.lunar.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCloseEvent.Reason;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.inventory.impl.CaptchaInventory;

public class FirstJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if (!(event.getPlayer() instanceof Player))
        {
            return;
        }

        Player player = (Player) event.getPlayer();
        if (player.hasPermission("lunar.bot.bypass"))
        {
            return;
        }

        if (!Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("captcha-on-join"))
        {
            return;
        }

        // if (!event.getPlayer().hasPlayedBefore())
        // {
        //     Bukkit.getScheduler().runTaskLater(Lunar.getInstance(), () -> new CaptchaInventory().open(player), 5L);
        // }
        Bukkit.getScheduler().runTaskLater(Lunar.getInstance(), () -> new CaptchaInventory().open(player), 5L);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null)
        {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        ItemMeta itemMeta = currentItem.getItemMeta();
        if (itemMeta == null)
        {
            return;
        }

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        PersistentDataContainer itemMetaData = itemMeta.getPersistentDataContainer();

        if ((playerData == null) || (itemMetaData == null))
        {
            return;
        }

        if (!playerData.has(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER))
        {
            return;
        }

        if (!itemMetaData.has(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER))
        {
            return;
        }

        event.setCancelled(true);
        
        if (itemMetaData.get(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER)==1)
        {
            Lunar.getInstance().getLogger().info("pass");
            playerData.set(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER, 0);
            player.closeInventory(Reason.CANT_USE);
            return;
        }
        
        if (itemMetaData.get(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER)==0)
        {
            Lunar.getInstance().getLogger().info("fail");
            String kickMessage = Lunar.getInstance().getLanguage().getConfiguration().getString("CAPTCHA.KICK-MESSAGE");
            player.kick(Lunar.getInstance().getMessage().getMessage(kickMessage));
            return;
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event)
    {
        Player player = (Player) event.getPlayer();

        PersistentDataContainer playerData = player.getPersistentDataContainer();

        if (!playerData.has(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER))
        {
            return;
        }

        if (playerData.get(Lunar.getInstance().getCaptchaKey(), PersistentDataType.INTEGER)==0)
        {
            return;
        }

        Bukkit.getScheduler().runTaskLater(Lunar.getInstance(), () -> new CaptchaInventory().open(player), 5L);
    }
}
