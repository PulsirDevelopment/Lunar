package net.pulsir.lunar.command.restore;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.discord.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InventoryRestoreCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.restore"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("RESTORE.USAGE")) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
            }
        } else {
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null || !target.isOnline()) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("PLAYER-NULL"))
                        .replace("{player}", args[0])));
                return false;
            }

            if (!Lunar.getInstance().getInventoryManager().getInventories().containsKey(target.getUniqueId())) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                .getConfiguration().getString("RESTORE.NO-DATABASE"))
                        .replace("{player}", target.getName())));
                return false;
            }

            target.getInventory().setContents(Lunar.getInstance().getInventoryManager().getInventories().get(target.getUniqueId()).getInventory());
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("delete-on-restore")) {
                Lunar.getInstance().getInventoryManager().getInventories().remove(target.getUniqueId());
            }
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                            .getString("RESTORE.RESTORED"))
                    .replace("{player}", target.getName())));

            if (Lunar.getInstance().getDiscord().getConfiguration().getBoolean("enabled") && Lunar.getInstance().getDiscord().getConfiguration().getBoolean("staff-refund.enabled")) {
                DiscordWebhook discordWebhook = new DiscordWebhook(Lunar.getInstance().getDiscord().getConfiguration().getString("webhook-url"));
                discordWebhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setDescription(Objects.requireNonNull(Lunar.getInstance().getDiscord().getConfiguration().getString("staff-refund.message"))
                                .replace("{player}", sender.getName())
                                .replace("{server}", Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("server-name")))
                                .replace("{target}", target.getName()))
                        .setAuthor(sender.getName(), "", ""));
                try {
                    discordWebhook.execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> players = new ArrayList<>();
            for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) players.add(onlinePlayer.getName());
            return players;
        }

        return new ArrayList<>();
    }
}
