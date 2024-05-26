package net.pulsir.lunar.utils.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.message.type.MessageType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BungeeListener implements PluginMessageListener {

    @Override
    @SuppressWarnings("ALL")
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!channel.equalsIgnoreCase("BungeeCord")) return;

        ByteArrayDataInput byteArrayDataInput = ByteStreams.newDataInput(message);
        String subChannel = byteArrayDataInput.readUTF();

        if (subChannel.equalsIgnoreCase("StaffChannel")) {
            char[] input = byteArrayDataInput.readUTF().toCharArray();
            char[] finalInput = Arrays.copyOfRange(input, 2, input.length);
            String string = "";

            for (char c : finalInput) {
                if (string.isEmpty()) {
                    string = String.valueOf(c);
                } else {
                    string = string + c;
                }
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("lunar.staff")) {
                    if (Lunar.getInstance().getMessage().getMessageType().equals(MessageType.COMPONENT)) {
                        onlinePlayer.sendMessage(MiniMessage.miniMessage().deserialize(string));
                    } else if (Lunar.getInstance().getMessage().getMessageType().equals(MessageType.LEGACY)) {
                        onlinePlayer.sendMessage(Lunar.getInstance().getMessage().forceLegacy(string));
                    }
                }
            }
        } else if (subChannel.equalsIgnoreCase("AdminChannel")) {
            char[] input = byteArrayDataInput.readUTF().toCharArray();
            char[] finalInput = Arrays.copyOfRange(input, 2, input.length);
            String string = "";

            for (char c : finalInput) {
                if (string.isEmpty()) {
                    string = String.valueOf(c);
                } else {
                    string = string + c;
                }
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("lunar.admin")) {
                    if (Lunar.getInstance().getMessage().getMessageType().equals(MessageType.COMPONENT)) {
                        onlinePlayer.sendMessage(MiniMessage.miniMessage().deserialize(string));
                    } else if (Lunar.getInstance().getMessage().getMessageType().equals(MessageType.LEGACY)) {
                        onlinePlayer.sendMessage(Lunar.getInstance().getMessage().forceLegacy(string));
                    }
                }
            }
        } else if (subChannel.equalsIgnoreCase("OwnerChannel")) {
            char[] input = byteArrayDataInput.readUTF().toCharArray();
            char[] finalInput = Arrays.copyOfRange(input, 2, input.length);
            String string = "";

            for (char c : finalInput) {
                if (string.isEmpty()) {
                    string = String.valueOf(c);
                } else {
                    string = string + c;
                }
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("lunar.owner")) {
                    if (Lunar.getInstance().getMessage().getMessageType().equals(MessageType.COMPONENT)) {
                        onlinePlayer.sendMessage(MiniMessage.miniMessage().deserialize(string));
                    } else if (Lunar.getInstance().getMessage().getMessageType().equals(MessageType.LEGACY)) {
                        onlinePlayer.sendMessage(Lunar.getInstance().getMessage().forceLegacy(string));
                    }
                }
            }
        } else if (subChannel.equalsIgnoreCase("GlobalChannel")) {
            char[] input = byteArrayDataInput.readUTF().toCharArray();
            char[] finalInput = Arrays.copyOfRange(input, 2, input.length);
            String string = "";

            for (char c : finalInput) {
                if (string.isEmpty()) {
                    string = String.valueOf(c);
                } else {
                    string = string + c;
                }
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                        .getLanguage().getConfiguration().getString("BROADCAST-MESSAGE")
                        .replace("{message}", string)));
            }
        }
    }
}