package net.pulsir.lunar.chat;

import net.kyori.adventure.text.Component;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.Objects;

public class ChatSlowDownCommand extends Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender.hasPermission(permission()))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return;
        }

        if (args.length == 1) {
            sender.sendMessage(getUsage());
        } else {
            int seconds = Integer.parseInt(args[1]);

            Lunar.getInstance().getData().setChatSlowdown(seconds);

            if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("CHAT-SLOWDOWN.SENDER")).isEmpty()) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                        .getLanguage().getConfiguration().getString("CHAT-SLOWDOWN.SENDER")));
            }
            if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("CHAT-SLOWDOWN.GLOBAL")).isEmpty()) {
                Bukkit.broadcast(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                .getLanguage().getConfiguration().getString("CHAT-SLOWDOWN.GLOBAL"))
                        .replace("{sender}", sender.getName())
                        .replace("{time}", String.valueOf(seconds))));
            }
        }
    }

    @Override
    public String getName() {
        return "slowdown";
    }

    @Override
    public boolean allow() {
        return true;
    }

    @Override
    public Component getUsage() {
        return Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                .getLanguage().getConfiguration().getString("CHAT-SLOWDOWN.USAGE"));
    }

    @Override
    public boolean permissible() {
        return true;
    }

    @Override
    public String permission() {
        return "lunar.command.slowdown";
    }
}
