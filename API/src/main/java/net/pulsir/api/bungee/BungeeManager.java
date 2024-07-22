package net.pulsir.api.bungee;

import org.bukkit.entity.Player;

public interface BungeeManager {

    /**
     * @param player - Player which those methods will be invoked on.
     * @param message - A message you will like to send across the bungeecord.
     * @param channelType - Channel where you want to send the message to.
     * chanelTypes = ("staff-chat", "admin-chat", "owner-chat", "global-chat", "freeze-chat", "unfreeze-chat")
     */
    void sendChannelMessage(Player player, String message, String channelType);
}
