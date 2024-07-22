package net.pulsir.lunar.redis;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.*;

import java.util.Objects;
import java.util.UUID;

public class RedisAdapter {

    private final JedisPool jedisPool;

    public RedisAdapter(boolean auth) {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(64);
        jedisPoolConfig.setMaxTotal(64);

        if (!auth) {
            ClassLoader previous = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(Jedis.class.getClassLoader());
            this.jedisPool = new JedisPool(jedisPoolConfig,
                    Lunar.getInstance().getConfiguration().getConfiguration().getString("redis.host"),
                    Lunar.getInstance().getConfiguration().getConfiguration().getInt("redis.port"), Protocol.DEFAULT_TIMEOUT);
            Thread.currentThread().setContextClassLoader(previous);
        } else {
            ClassLoader previous = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(Jedis.class.getClassLoader());
            this.jedisPool = new JedisPool(jedisPoolConfig,
                    Lunar.getInstance().getConfiguration().getConfiguration().getString("redis.host"),
                    Lunar.getInstance().getConfiguration().getConfiguration().getInt("redis.port"),
                    Protocol.DEFAULT_TIMEOUT,
                    Lunar.getInstance().getConfiguration().getConfiguration().getString("redis.password"));
            Thread.currentThread().setContextClassLoader(previous);
        }
    }

    public Jedis getJedis(){
        return this.jedisPool.getResource();
    }

    public void subscribe() {
        Jedis subscriber = getJedis();
        subscriber.connect();

        new Thread("Subscriber") {
            @Override
            public void run() {
                String[] chatChannels = {"staff-chat", "admin-chat", "owner-chat", "global-chat", "freeze-chat", "unfreeze-chat"};
                subscriber.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        String player = message.split("<splitter>")[0];
                        String server = message.split("<splitter>")[1];
                        String finalMessage = message.split("<splitter>")[2];

                        if (channel.equalsIgnoreCase("staff-chat")) {
                            for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage()
                                        .getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                                        .getString("STAFF-CHAT.FORMAT"))
                                                .replace("{player}", player)
                                                .replace("{server}", server)
                                                .replace("{message}", finalMessage)));
                            }
                        } else if (channel.equalsIgnoreCase("admin-chat")) {
                            for (UUID uuid : Lunar.getInstance().getData().getAdminMembers()) {
                                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage()
                                        .getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                                        .getString("ADMIN-CHAT.FORMAT"))
                                                .replace("{player}", player)
                                                .replace("{server}", server)
                                                .replace("{message}", finalMessage)));
                            }
                        } else if (channel.equalsIgnoreCase("owner-chat")) {
                            for (UUID uuid : Lunar.getInstance().getData().getOwnerMembers()) {
                                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage()
                                        .getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                                        .getString("OWNER-CHAT.FORMAT"))
                                                .replace("{player}", player)
                                                .replace("{server}", server)
                                                .replace("{message}", finalMessage)));
                            }
                        } else if (channel.equalsIgnoreCase("global-chat")) {
                            for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                                onlinePlayer.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                                                .getInstance().getLanguage().getConfiguration().getString("BROADCAST-MESSAGE"))
                                        .replace("{message}", finalMessage)));
                            }
                        } else if (channel.equalsIgnoreCase("freeze-chat")) {
                            for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage()
                                        .getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                                        .getString("FREEZE.STAFF-FROZEN"))
                                                .replace("{player}", player)
                                                .replace("{staff}", server)
                                                .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                                        .getConfiguration().getString("server-name")))));
                            }
                        } else if (channel.equalsIgnoreCase("unfreeze-chat")) {
                            for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                                Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage()
                                        .getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                                        .getString("FREEZE.STAFF-UNFROZEN"))
                                                .replace("{player}", player)
                                                .replace("{staff}", server)
                                                .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                                        .getConfiguration().getString("server-name")))));
                            }
                        }
                    }
                }, chatChannels);
            }
        }.start();
    }

    public void publish(String channel, String message) {
        try (Jedis publisher = getJedis()) {
            publisher.publish(channel, message);
        }
    }
}
