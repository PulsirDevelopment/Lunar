package net.pulsir.lunar.manager;

import net.pulsir.api.redis.RedisManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;

public class RedisManagerImpl implements RedisManager {

    @Override
    public void sendChannelMessage(Player player, String message, String channelType) {
        Lunar.getInstance().getRedisAdapter().publish(channelType, message);
    }
}
