package net.pulsir.lunar.command.staff;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorldCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.command.world"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            usage(player);
        } else {
            World world = Bukkit.getWorld(args[0]);

            List<String> availableWorlds = new ArrayList<>();
            for (final World serverWorld : Bukkit.getWorlds()) {
                availableWorlds.add(serverWorld.getName());
            }

            if (world == null) {
                player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                        .getLanguage().getConfiguration().getString("WORLD.NULL")).replace("{world}", args[0])
                        .replace("{existing_worlds}", String.valueOf(availableWorlds))));
                return false;
            }

            player.teleport(new Location(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance()
                    .getLanguage().getConfiguration().getString("WORLD.TELEPORTED")).replace("{world}", world.getName()))));
        }

        return true;
    }

    private void usage(Player player) {
        player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                .getConfiguration().getString("WORLD.USAGE")));
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("world", "world_nether", "world_the_end"));
        }

        return new ArrayList<>();
    }
}
