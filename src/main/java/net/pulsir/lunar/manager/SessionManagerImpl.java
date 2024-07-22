package net.pulsir.lunar.manager;

import net.pulsir.api.session.SessionManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;

public class SessionManagerImpl implements SessionManager {

    @Override
    public long getSessionTime(Player player) {
        return Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().get(player.getUniqueId()).getSessionTime();
    }

    @Override
    public void setSessionTime(Player player, long time) {
        Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().get(player.getUniqueId()).setSessionTime(time);
    }
}
