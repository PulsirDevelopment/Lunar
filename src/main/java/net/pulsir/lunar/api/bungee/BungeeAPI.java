package net.pulsir.lunar.api.bungee;

import net.pulsir.lunar.utils.bungee.Bungee;
import net.pulsir.lunar.utils.bungee.message.ChannelType;
import org.bukkit.entity.Player;

public class BungeeAPI {

    public void sendChannelMessage(Player player, String message, ChannelType channelType) {
        Bungee.sendMessage(player, message, channelType);
    }
}
