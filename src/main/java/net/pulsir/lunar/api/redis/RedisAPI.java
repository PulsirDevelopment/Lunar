package net.pulsir.lunar.api.redis;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.bungee.message.ChannelType;

public class RedisAPI {

    public void sendChannelMessage(String message, ChannelType channelType) {
        Lunar.getInstance().getRedisManager().publish(getChannelAsString(channelType), message);
    }

    private String getChannelAsString(ChannelType channelType){
        if (channelType.equals(ChannelType.GLOBAL)) {
            return "global-chat";
        } else if (channelType.equals(ChannelType.OWNER)) {
            return "owner-chat";
        } else if (channelType.equals(ChannelType.ADMIN)) {
            return "admin-chat";
        } else if (channelType.equals(ChannelType.STAFF)) {
            return "staff-chat";
        }

        return null;
    }
}
