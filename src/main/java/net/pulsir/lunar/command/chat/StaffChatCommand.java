package net.pulsir.lunar.command.chat;

import net.pulsir.lunar.Lunar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StaffChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.command.staffchat"))) {
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (Lunar.getInstance().getData().getStaffChat().contains(player.getUniqueId())) {
            Lunar.getInstance().getData().getStaffChat().remove(player.getUniqueId());

            if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("STAFF-CHAT.DISABLED")).isEmpty()) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("STAFF-CHAT.DISABLED")));
            }
        } else {
            Lunar.getInstance().getData().clearChat(player.getUniqueId());
            Lunar.getInstance().getData().getStaffChat().add(player.getUniqueId());

            if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("STAFF-CHAT.ENABLED")).isEmpty()) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("STAFF-CHAT.ENABLED")));
            }
        }

        return true;
    }
}
