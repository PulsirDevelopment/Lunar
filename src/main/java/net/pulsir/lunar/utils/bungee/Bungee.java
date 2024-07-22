package net.pulsir.lunar.utils.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.bungee.message.ChannelType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Bungee {

    public static void sendMessage(Player player, String message, ChannelType channelType){

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF(channelType.getString() + "Channel");

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);

        try {
            msgout.writeUTF(message);
        } catch (IOException exception){
            exception.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        player.sendPluginMessage(Lunar.getInstance(), "BungeeCord", out.toByteArray());
    }

    public static void sendMessage(Player player, String message, String channel){
        ChannelType channelType = getChannelFromString(channel);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Forward");
        out.writeUTF("ALL");
        out.writeUTF(channelType.getString() + "Channel");

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);

        try {
            msgout.writeUTF(message);
        } catch (IOException exception){
            exception.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        player.sendPluginMessage(Lunar.getInstance(), "BungeeCord", out.toByteArray());
    }

    private static ChannelType getChannelFromString(String channel){

        if (channel.equalsIgnoreCase("global-chat")) {
            return ChannelType.GLOBAL;
        } else if (channel.equalsIgnoreCase("owner-chat")) {
            return ChannelType.OWNER;
        } else if (channel.equalsIgnoreCase("admin-chat")) {
            return ChannelType.ADMIN;
        } else if (channel.equalsIgnoreCase("staff-chat")) {
            return ChannelType.STAFF;
        }

        return null;
    }
}
