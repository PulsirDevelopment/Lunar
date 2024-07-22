package net.pulsir.api.redis;

import org.bukkit.entity.Player;

public interface RedisManager {

    /**
     * @param player - Player which those methods will be invoked on.
     * @param message - A message you will like to send across the redis.
     * @param channelType - Channel where you want to send the message to.
     * chanelTypes = ("staff-chat", "admin-chat", "owner-chat", "global-chat", "freeze-chat", "unfreeze-chat")
     */
    void sendChannelMessage(Player player, String message, String channelType);
}
