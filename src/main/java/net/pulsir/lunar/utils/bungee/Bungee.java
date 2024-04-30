package net.pulsir.lunar.utils.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.bungee.message.ChannelType;
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
            msgout.writeUTF(message); // You can do anything you want with msgout
        } catch (IOException exception){
            exception.printStackTrace();
        }

        out.writeShort(msgbytes.toByteArray().length);
        out.write(msgbytes.toByteArray());

        player.sendPluginMessage(Lunar.getInstance(), "BungeeCord", out.toByteArray());
    }
}
