package net.pulsir.lunar.task;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class SessionTask implements Runnable {

    @Override
    public void run() {
        if (!Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().isEmpty()) {
            for (final UUID sessionUUID : Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().keySet()) {
                if (!Objects.requireNonNull(Bukkit.getPlayer(sessionUUID)).hasPermission("lunar.staff")) {
                    Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().remove(sessionUUID);
                } else {
                    Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().get(sessionUUID)
                            .setSessionTime(Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().get(sessionUUID)
                                    .getSessionTime() + 1);
                }
            }
        }
    }
}
