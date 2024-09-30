package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class BlockedCommands implements Listener {

    /*
        Credit goes to BGHDDevelopment (https://github.com/BGHDDevelopment/NoPluginCommand)
     */

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (event.getPlayer().hasPermission("lunar.command.bypass")) return;
        List<String> commands = Lunar.getInstance().getConfiguration().getConfiguration().getStringList("blocked-commands");
        commands.forEach(all -> {
            String[] arrCommand = event.getMessage().toLowerCase().split(" ", 2);
            if (arrCommand[0].equalsIgnoreCase("/" + all.toLowerCase())) {
                event.setCancelled(true);
            }
        });
    }
}
