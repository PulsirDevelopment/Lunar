package net.pulsir.lunar.chat;

import net.kyori.adventure.text.Component;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class ChatMuteCommand extends Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender.hasPermission(permission()))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return;
        }

        if (Lunar.getInstance().getData().isChatMuted()) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar
                    .getInstance().getLanguage().getConfiguration().getString("CHAT-MUTE.ALREADY-MUTED")));
            return;
        }

        Lunar.getInstance().getData().setChatMuted(true);
        sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                .getLanguage().getConfiguration().getString("CHAT-MUTE.SENDER"))));
        Bukkit.broadcast(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                        .getInstance().getLanguage().getConfiguration().getString("CHAT-MUTE.GLOBAL"))
                .replace("{sender}", sender.getName())));
    }

    @Override
    public String getName() {
        return "mute";
    }

    @Override
    public boolean allow() {
        return true;
    }

    @Override
    public Component getUsage() {
        return Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                .getLanguage().getConfiguration().getString("CHAT-MUTE.USAGE"));
    }

    @Override
    public boolean permissible() {
        return true;
    }

    @Override
    public String permission() {
        return "lunar.command.mutechat";
    }
}
