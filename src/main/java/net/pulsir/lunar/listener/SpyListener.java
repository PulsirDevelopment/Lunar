package net.pulsir.lunar.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Objects;
import java.util.Set;

public class SpyListener implements Listener {

    @EventHandler
    @Deprecated
    public void onMessage(AsyncPlayerChatEvent event) {
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("spy.messages")) {
            Set<Player> spy = Lunar.getInstance().getData().getSpyOf(event.getPlayer());

            for (final Player spyPlayer : spy) {
                spyPlayer.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                .getLanguage().getConfiguration().getString("SPY.FORMAT"))
                        .replace("{player}", event.getPlayer().getName())
                        .replace("{message}", event.getMessage())));
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("spy.commands")) {
            Set<Player> spy = Lunar.getInstance().getData().getSpyOf(event.getPlayer());

            for (final Player spyPlayer : spy) {
                spyPlayer.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                .getLanguage().getConfiguration().getString("SPY.FORMAT"))
                        .replace("{player}", event.getPlayer().getName())
                        .replace("{message}", event.getMessage())));
            }
        }
    }
}
