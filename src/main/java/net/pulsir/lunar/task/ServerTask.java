package net.pulsir.lunar.task;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.session.SessionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class ServerTask implements Runnable {

    @Override
    public void run() {
        if (!Lunar.getInstance().getData().getStaffMembers().isEmpty()) {
            for (final UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                if (!Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission("lunar.staff")) {
                    Lunar.getInstance().getData().getStaffMembers().remove(uuid);
                }
                if (!Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission("lunar.spy")) {
                    Lunar.getInstance().getData().getSpy().remove(uuid);
                }
            }
        }

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (onlinePlayers.hasPermission("lunar.staff")) {
                Lunar.getInstance().getData().getStaffMembers().add(onlinePlayers.getUniqueId());
            }

            if (!Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().containsKey(onlinePlayers.getUniqueId())) {
                Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().put(onlinePlayers.getUniqueId(),
                        new SessionPlayer(onlinePlayers.getUniqueId(), 0));
            }
        }

        for (UUID uuid : Lunar.getInstance().getData().getRequestCooldown().keySet()) {
            if (Lunar.getInstance().getData().getRequestCooldown().get(uuid) <= 1) {
                Lunar.getInstance().getData().getRequestCooldown().remove(uuid);
            } else {
                Lunar.getInstance().getData().getRequestCooldown().replace(uuid,
                        Lunar.getInstance().getData().getRequestCooldown().get(uuid),
                        Lunar.getInstance().getData().getRequestCooldown().get(uuid) - 1);
            }
        }

        for (UUID uuid : Lunar.getInstance().getData().getReportCooldown().keySet()) {
            if (Lunar.getInstance().getData().getReportCooldown().get(uuid) <= 1) {
                Lunar.getInstance().getData().getReportCooldown().remove(uuid);
            } else {
                Lunar.getInstance().getData().getReportCooldown().replace(uuid,
                        Lunar.getInstance().getData().getReportCooldown().get(uuid),
                        Lunar.getInstance().getData().getReportCooldown().get(uuid) - 1);
            }
        }
    }
}
