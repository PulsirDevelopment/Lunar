package net.pulsir.lunar.command.note;

import net.kyori.adventure.text.Component;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class NoteCreateCommand extends Command {

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
                sender.sendMessage(getUsage());
            } else {
                String note = "";

                for (int i = 2; i < args.length; i++) {
                    if (note.isEmpty()) {
                        note = args[i];
                    } else {
                        note = note + " " + args[i];
                    }
                }

                if (note.toCharArray().length >= 256) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                            .getLanguage().getConfiguration().getString("NOTES.CHARACTER-LIMIT")).replace("{current}",
                                    String.valueOf(note.toCharArray().length))));
                    return;
                }

                if (Lunar.getInstance().getNoteManager().noteExists(offlinePlayer.getUniqueId(), note)) {
                    player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("NOTES.EXISTS"))
                            .replace("{player}", args[1])
                            .replace("{note}", note)));
                    return;
                }

                Lunar.getInstance().getNoteManager().createNote(offlinePlayer.getUniqueId(),
                        player.getUniqueId(), note);
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                .getLanguage().getConfiguration().getString("NOTES.CREATED"))
                        .replace("{player}", args[1])));
            }
        }
    }

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public boolean allow() {
        return true;
    }

    @Override
    public Component getUsage() {
        return Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                .getLanguage().getConfiguration().getString("NOTES.USAGE.CREATE"));
    }

    @Override
    public boolean permissible() {
        return true;
    }

    @Override
    public String permission() {
        return "lunar.command.note.create";
    }
}
