package net.pulsir.lunar.utils.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

public class BungeeListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
        if (!channel.equalsIgnoreCase("BungeeCord")) return;

        ByteArrayDataInput byteArrayDataInput = ByteStreams.newDataInput(message);
        String subChannel = byteArrayDataInput.readUTF();

        if (subChannel.equalsIgnoreCase("StaffChannel")) {
            String input = byteArrayDataInput.readUTF();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("lunar.staff")) {
                    onlinePlayer.sendMessage(MiniMessage.miniMessage().deserialize(input));
                    onlinePlayer.sendMessage((input));
                }
            }
        } else if (subChannel.equalsIgnoreCase("AdminChannel")) {
            String input = byteArrayDataInput.readUTF();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("lunar.admin")) {
                    onlinePlayer.sendMessage(MiniMessage.miniMessage().deserialize(input));
                }
            }
        } else if (subChannel.equalsIgnoreCase("OwnerChannel")) {
            String input = byteArrayDataInput.readUTF();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("lunar.owner")) {
                    onlinePlayer.sendMessage(MiniMessage.miniMessage().deserialize(input));
                }
            }
        }
    }
}
