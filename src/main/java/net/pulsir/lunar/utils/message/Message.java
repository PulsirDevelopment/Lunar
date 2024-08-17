package net.pulsir.lunar.utils.message;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.message.type.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Objects;

@Getter
public class Message {

    private final MessageType messageType;

    public Message(){
        if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("message-format")).equalsIgnoreCase("legacy")) {
            messageType = MessageType.LEGACY;
            Bukkit.getConsoleSender().sendMessage("[Lunar] Successfully loaded LEGACY message format.");
        } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("message-format")).equalsIgnoreCase("modern")) {
            messageType = MessageType.COMPONENT;
            Bukkit.getConsoleSender().sendMessage("[Lunar] Successfully loaded COMPONENT message format.");
        } else {
            Bukkit.getConsoleSender().sendMessage("[Lunar] Unsupported message format. Loading default one [LEGACY].");
            messageType = MessageType.LEGACY;
        }
    }

    public Component getMessage(String message) {
        if (messageType.equals(MessageType.LEGACY)) {
            return LegacyComponentSerializer.legacyAmpersand().deserializeOr(message, Component.empty())
                    .decoration(TextDecoration.ITALIC, false);
        } else if (messageType.equals(MessageType.COMPONENT)) {
            return MiniMessage.miniMessage().deserialize(message).decoration(TextDecoration.ITALIC, false);
        }

        return MiniMessage.miniMessage().deserialize(message);
    }

    public String getComponent(Component component) {
        if (messageType.equals(MessageType.LEGACY)) {
            return LegacyComponentSerializer.legacyAmpersand().serialize(component);
        } else if (messageType.equals(MessageType.COMPONENT)) {
            return MiniMessage.miniMessage().serialize(component);
        }

        return LegacyComponentSerializer.legacyAmpersand().serialize(component);
    }

    @SuppressWarnings("ALL")
    public String forceLegacy(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
