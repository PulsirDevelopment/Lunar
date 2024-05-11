package net.pulsir.lunar.command.chat;

import net.pulsir.lunar.Lunar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FrozenChatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.command.frozenchat"))) {
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (Lunar.getInstance().getData().getFreezeChat().contains(player.getUniqueId())) {
            Lunar.getInstance().getData().getFreezeChat().remove(player.getUniqueId());
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("FREEZE-CHAT.DISABLED")));
        } else {
            Lunar.getInstance().getData().getFreezeChat().add(player.getUniqueId());
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("FREEZE-CHAT.ENABLED")));
        }

        return true;
    }
}
