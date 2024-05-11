package net.pulsir.lunar.redis;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.net.URI;

public class RedisManager {

    private final JedisPool jedisPool;

    public RedisManager(boolean auth) {
        this.jedisPool = new JedisPool(new URI("gjhjhbjhbjhbjhbjj"));
    }

    public Jedis getJedis(){
        return this.jedisPool.getResource();
    }

    public void subscribe() {
        Jedis subscriber = getJedis();
        subscriber.connect();

        new Thread("Redis Subscriber") {
            @Override
            public void run() {
                String[] chatChannels = {"staff-chat", "admin-chat", "owner-chat"};
                subscriber.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        Bukkit.getConsoleSender().sendMessage("received " + message);
                    }
                }, chatChannels);
            }
        };
    }

    public void publish(String channel, String message) {
        try (Jedis publisher = new Jedis()) {
            publisher.publish(channel, message);
        }
    }
}
