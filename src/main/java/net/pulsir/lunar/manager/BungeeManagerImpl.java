package net.pulsir.lunar.manager;

import net.pulsir.api.bungee.BungeeManager;
import net.pulsir.lunar.utils.bungee.Bungee;
import org.bukkit.entity.Player;

public class BungeeManagerImpl implements BungeeManager {

    @Override
    public void sendChannelMessage(Player player, String message, String channelType) {
        Bungee.sendMessage(player, message, channelType);
    }
}
