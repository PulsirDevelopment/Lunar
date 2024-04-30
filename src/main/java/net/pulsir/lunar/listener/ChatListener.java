package net.pulsir.lunar.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
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
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("bungee")) {
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("STAFF-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName()))));
                }

                Bungee.sendMessage(event.getPlayer(),
                        Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("STAFF-CHAT.FORMAT"))
                                .replace("{message}", event.getMessage())
                                .replace("{player}", event.getPlayer().getName()),
                        ChannelType.STAFF);
            } else {
                for (UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("STAFF-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName()))));
                }
            }
            event.setCancelled(true);
        } else if (Lunar.getInstance().getData().getAdminChat().contains(event.getPlayer().getUniqueId())) {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("bungee")) {
                for (UUID uuid : Lunar.getInstance().getData().getAdminMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("ADMIN-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName()))));
                }

                Bungee.sendMessage(event.getPlayer(),
                        Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("ADMIN-CHAT.FORMAT"))
                                .replace("{message}", event.getMessage())
                                .replace("{player}", event.getPlayer().getName()),
                        ChannelType.ADMIN);
            } else {
                for (UUID uuid : Lunar.getInstance().getData().getAdminMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("ADMIN-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName()))));
                }
            }
            event.setCancelled(true);
        } else if (Lunar.getInstance().getData().getOwnerChat().contains(event.getPlayer().getUniqueId())) {
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("bungee")) {
                for (UUID uuid : Lunar.getInstance().getData().getOwnerMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("OWNER-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName()))));
                }

                Bungee.sendMessage(event.getPlayer(),
                        Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("OWNER-CHAT.FORMAT"))
                                .replace("{message}", event.getMessage())
                                .replace("{player}", event.getPlayer().getName()),
                        ChannelType.OWNER);
            } else {
                for (UUID uuid : Lunar.getInstance().getData().getOwnerMembers()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("OWNER-CHAT.FORMAT"))
                            .replace("{message}", event.getMessage())
                            .replace("{player}", event.getPlayer().getName()))));
                }
            }
            event.setCancelled(true);
        }
    }
}
