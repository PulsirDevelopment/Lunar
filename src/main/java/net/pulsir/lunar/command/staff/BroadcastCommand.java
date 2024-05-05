package net.pulsir.lunar.command.staff;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.staff"))) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS"))));
            return false;
        }

        if (args.length == 0) {
            for (final String usage : Lunar.getInstance().getLanguage().getConfiguration().getStringList("USAGE.BROADCAST")) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize(usage));
            }
        } else {
            String message = "";
            for (String arg : args) {
                if (message.isEmpty()) {
                    message = arg;
                } else {
                    message = message + " " + arg;
                }
            }

            Bukkit.broadcast(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("BROADCAST-MESSAGE")).replace("{message}", message))));
        }

        return true;
    }
}
