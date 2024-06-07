package net.pulsir.lunar.api.session;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.session.SessionPlayer;
import org.bukkit.entity.Player;

public class SessionAPI {

    public long getSessionTime(Player player) {
        return Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().get(player.getUniqueId()).getSessionTime();
    }

    public void setSessionTime(Player player, long time) {
        Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().get(player.getUniqueId()).setSessionTime(time);
    }

    public SessionPlayer getPlayer(Player player) {
        return Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().get(player.getUniqueId());
    }
}
