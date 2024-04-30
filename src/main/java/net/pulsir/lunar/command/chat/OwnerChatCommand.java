package net.pulsir.lunar.command.chat;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class OwnerChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.command.ownerchat"))) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS"))));
            return false;
        }

        if (Lunar.getInstance().getData().getOwnerChat().contains(player.getUniqueId())) {
            Lunar.getInstance().getData().getOwnerChat().remove(player.getUniqueId());
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("OWNER-CHAT.DISABLED"))));
        } else {
            Lunar.getInstance().getData().getOwnerChat().add(player.getUniqueId());
            player.sendMessage(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("OWNER-CHAT.ENABLED"))));
        }

        return true;
    }
}
