package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.bungee.Bungee;
import net.pulsir.lunar.utils.bungee.message.ChannelType;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;
import java.util.UUID;

public class ChatListener implements Listener {

    @EventHandler
    @Deprecated /*(Reason: Compatibility with Spigot & Paper)*/
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        if (Lunar.getInstance().getData().getStaffChat().contains(event.getPlayer().getUniqueId())) {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-sync")) {
                if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                        .equalsIgnoreCase("bungee")) {
                    for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                        .getConfiguration().getString("STAFF-CHAT.FORMAT"))
                                .replace("{message}", event.getMessage())
                                .replace("{player}", event.getPlayer().getName())
                                .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                        .getConfiguration().getString("server-name")))));
                    }

                    Bungee.sendMessage(event.getPlayer(),
                            Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("STAFF-CHAT.FORMAT"))
                                    .replace("{message}", event.getMessage())
                                    .replace("{player}", event.getPlayer().getName())
                                    .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                            .getConfiguration().getString("server-name"))),
                            ChannelType.STAFF);
                } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                        .equalsIgnoreCase("redis")) {
                    String message = event.getPlayer().getName() + "<splitter>" + Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                            .getConfiguration().getString("server-name")) + "<splitter>" + event.getMessage();
                    Lunar.getInstance().getRedisAdapter().publish("staff-chat", message);
                }
            } else {
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("STAFF-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName())
                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                    .getConfiguration().getString("server-name")))));
                }
            }
            event.setCancelled(true);
        } else if (Lunar.getInstance().getData().getAdminChat().contains(event.getPlayer().getUniqueId())) {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-sync")) {
                if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                        .equalsIgnoreCase("bungee")) {
                    for (UUID uuid : Lunar.getInstance().getData().getAdminMembers()) {
                        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                        .getConfiguration().getString("ADMIN-CHAT.FORMAT"))
                                .replace("{message}", event.getMessage())
                                .replace("{player}", event.getPlayer().getName())
                                .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                        .getConfiguration().getString("server-name")))));
                    }

                    Bungee.sendMessage(event.getPlayer(),
                            Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("ADMIN-CHAT.FORMAT"))
                                    .replace("{message}", event.getMessage())
                                    .replace("{player}", event.getPlayer().getName())
                                    .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                            .getConfiguration().getString("server-name"))),
                            ChannelType.ADMIN);
                } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                        .equalsIgnoreCase("redis")) {
                    String message = event.getPlayer().getName() + "<splitter>" + Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                            .getConfiguration().getString("server-name")) + "<splitter>" + event.getMessage();
                    Lunar.getInstance().getRedisAdapter().publish("admin-chat", message);
                }
            } else {
                for (UUID uuid : Lunar.getInstance().getData().getAdminMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("ADMIN-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName())
                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                    .getConfiguration().getString("server-name")))));
                }
            }
            event.setCancelled(true);
        } else if (Lunar.getInstance().getData().getOwnerChat().contains(event.getPlayer().getUniqueId())) {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-sync")) {
                if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                        .equalsIgnoreCase("bungee")) {
                    for (UUID uuid : Lunar.getInstance().getData().getOwnerMembers()) {
                        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                        .getConfiguration().getString("OWNER-CHAT.FORMAT"))
                                .replace("{message}", event.getMessage())
                                .replace("{player}", event.getPlayer().getName())
                                .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                        .getConfiguration().getString("server-name")))));
                    }

                    Bungee.sendMessage(event.getPlayer(),
                            Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("OWNER-CHAT.FORMAT"))
                                    .replace("{message}", event.getMessage())
                                    .replace("{player}", event.getPlayer().getName())
                                    .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                            .getConfiguration().getString("server-name"))),
                            ChannelType.OWNER);
                } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("sync-system"))
                        .equalsIgnoreCase("redis")) {
                    String message = event.getPlayer().getName() + "<splitter>" + Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                            .getConfiguration().getString("server-name")) + "<splitter>" + event.getMessage();
                    Lunar.getInstance().getRedisAdapter().publish("owner-chat", message);
                }
            } else {
                for (UUID uuid : Lunar.getInstance().getData().getOwnerMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                    .getConfiguration().getString("OWNER-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName())
                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                    .getConfiguration().getString("server-name")))));
                }
            }
            event.setCancelled(true);
        } else if (Lunar.getInstance().getData().getFreezeChat().contains(event.getPlayer().getUniqueId())) {
            if (Lunar.getInstance().getData().getStaffMembers().contains(event.getPlayer().getUniqueId())) {
                for (UUID uuid : Lunar.getInstance().getData().getFreezeChat()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("FREEZE-CHAT.FORMAT"))
                            .replace("{player}", event.getPlayer().getName())
                            .replace("{message}", event.getMessage())
                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                    .getConfiguration().getString("server-name")))));
                }
            } else {
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("FREEZE-CHAT.FORMAT"))
                            .replace("{player}", event.getPlayer().getName())
                            .replace("{message}", event.getMessage())
                            .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                    .getConfiguration().getString("server-name")))));
                }

                event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                .getLanguage().getConfiguration().getString("FREEZE-CHAT.FORMAT"))
                        .replace("{player}", event.getPlayer().getName())
                        .replace("{message}", event.getMessage())
                        .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration()
                                .getConfiguration().getString("server-name")))));
            }
            event.setCancelled(true);
        } else {
            if (Lunar.getInstance().getData().isChatMuted()) {
                if (!event.getPlayer().hasPermission("lunar.bypass.mute")) {
                    event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar
                            .getInstance().getLanguage().getConfiguration().getString("CHAT-MUTE.MUTED")));
                    event.setCancelled(true);
                }
            }

            if (Lunar.getInstance().getData().getChatSlowdown() > 0 && !event.getPlayer().hasPermission("lunar.bypass.slowdown")) {
                if (Lunar.getInstance().getData().getSlowdownedPlayers().containsKey(event.getPlayer().getUniqueId())) {
                    event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("CHAT-SLOWDOWN.COOLDOWN"))
                            .replace("{cooldown}",
                                    String.valueOf(Lunar.getInstance().getData().getSlowdownedPlayers().get(event.getPlayer().getUniqueId())))));
                    event.setCancelled(true);
                } else {
                    Lunar.getInstance().getData().getSlowdownedPlayers().put(event.getPlayer().getUniqueId(),
                            Lunar.getInstance().getData().getChatSlowdown());
                }
            }
        }
    }
}