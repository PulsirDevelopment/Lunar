package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (Lunar.getInstance().getData().getFrozenPlayers().contains(event.getPlayer().getUniqueId())) {
            List<String> commands = Lunar.getInstance().getConfiguration()
                    .getConfiguration().getStringList("freeze-blocked-commands");

            if (commands.isEmpty()) return;
            commands.forEach(command -> {
                if (event.getMessage().toLowerCase().startsWith(command)) {
                    event.getPlayer().sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar
                            .getInstance().getLanguage().getConfiguration().getString("FREEZE.COMMAND")));
                    event.setCancelled(true);
                }
            });
        }
    }
}
