package net.pulsir.lunar.command.lunar;

import net.pulsir.lunar.Lunar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LunarCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender.hasPermission("lunar.command.lunar"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                    .getLanguage().getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            for (final String lines : Lunar.getInstance().getLanguage().getConfiguration().getStringList("LUNAR.USAGE")) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(lines));
            }
        } else if (args[0].equalsIgnoreCase("reload")) {
            Lunar.getInstance().reloadConfigs();

            if (!Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("LUNAR.RELOADED")).isEmpty()) {
                sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("LUNAR.RELOADED")));
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return new ArrayList<>(List.of("reload"));
        }

        return new ArrayList<>();
    }
}
