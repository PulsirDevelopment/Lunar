package net.pulsir.lunar.command.chat;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AdminChatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.command.adminchat"))) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS"))));
            return false;
        }

        if (Lunar.getInstance().getData().getAdminChat().contains(player.getUniqueId())) {
            Lunar.getInstance().getData().getAdminChat().remove(player.getUniqueId());
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("ADMIN-CHAT.DISABLED"))));
        } else {
            Lunar.getInstance().getData().clearChat(player.getUniqueId());
            Lunar.getInstance().getData().getAdminChat().add(player.getUniqueId());
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("ADMIN-CHAT.ENABLED"))));
        }

        return true;
    }
}
