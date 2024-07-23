package net.pulsir.lunar.command.note;

import net.kyori.adventure.text.Component;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NoteCheckCommand extends Command {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) return;

        if (!(sender.hasPermission(permission()))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return;
        }

        if (args.length == 1) {
            sender.sendMessage(getUsage());
        } else {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);

            if (args.length == 2) {
                Lunar.getInstance().getNoteManager().listNotes(offlinePlayer.getUniqueId(), player, 10);
            } else {
                try {
                    int queries = Integer.parseInt(args[2]);
                    Lunar.getInstance().getNoteManager().listNotes(offlinePlayer.getUniqueId(), player, queries);
                } catch (NumberFormatException exception) {
                    player.sendMessage(ChatColor.RED + "Queries must be a number.");
                }
            }
        }
    }

    @Override
    public String getName() {
        return "check";
    }

    @Override
    public boolean allow() {
        return true;
    }

    @Override
    public Component getUsage() {
        return Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                .getLanguage().getConfiguration().getString("NOTES.USAGE.CHECK"));
    }

    @Override
    public boolean permissible() {
        return true;
    }

    @Override
    public String permission() {
        return "lunar.command.note.check";
    }
}
