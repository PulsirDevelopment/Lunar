package net.pulsir.lunar.task;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.session.manager.SessionPlayerManager;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class SessionTask implements Runnable {

    @Override
    public void run() {
        SessionPlayerManager sessionManager = Lunar.getInstance().getSessionPlayerManager();
        if (sessionManager.getSessionPlayers().isEmpty()) return;

        for (final UUID sessionUUID : sessionManager.getSessionPlayers().keySet()) {
            if (!Objects.requireNonNull(Bukkit.getPlayer(sessionUUID)).hasPermission("lunar.staff")) {
                sessionManager.getSessionPlayers().remove(sessionUUID);
            } else {
                sessionManager.getSessionPlayers().get(sessionUUID)
                        .setSessionTime(sessionManager.getSessionPlayers().get(sessionUUID)
                                .getSessionTime() + 1);
            }
        }
    }
}
