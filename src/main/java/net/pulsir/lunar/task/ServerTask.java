package net.pulsir.lunar.task;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.session.SessionPlayer;
import net.pulsir.lunar.session.manager.SessionPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ServerTask implements Runnable {

    @Override
    public void run() {
        Map<UUID, Set<UUID>> spyMap = Lunar.getInstance().getData().getSpy();
        Set<UUID> staffModeSet = Lunar.getInstance().getData().getStaffMode();
        Set<UUID> staffMembersSet = Lunar.getInstance().getData().getStaffMembers();

        if (!staffMembersSet.isEmpty()) {
            for (final UUID uuid : staffMembersSet) {
                Player player = Bukkit.getPlayer(uuid);
                if (player != null) {
                    if (!player.hasPermission("lunar.staff")) {
                        staffModeSet.remove(uuid);
                        staffMembersSet.remove(uuid);
                    }
                    if (!player.hasPermission("lunar.spy")) {
                        spyMap.remove(uuid);
                    }
                }
            }
        }

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (onlinePlayers.hasPermission("lunar.staff")) {
                staffMembersSet.add(onlinePlayers.getUniqueId());

                SessionPlayerManager sessionManager = Lunar.getInstance().getSessionPlayerManager();
                sessionManager.getSessionPlayers().computeIfAbsent(
                        onlinePlayers.getUniqueId(),
                        uuid -> new SessionPlayer(onlinePlayers.getUniqueId(), 0));
            }
        }

        Map<UUID, Integer> requestCooldown = Lunar.getInstance().getData().getRequestCooldown();
        for (UUID uuid : requestCooldown.keySet()) {
            if (requestCooldown.get(uuid) <= 1) {
                requestCooldown.remove(uuid);
            } else {
                requestCooldown.replace(
                        uuid,
                        requestCooldown.get(uuid),
                        requestCooldown.get(uuid) - 1);
            }
        }

        Map<UUID, Integer> reportCooldown = Lunar.getInstance().getData().getReportCooldown();
        for (UUID uuid : reportCooldown.keySet()) {
            if (reportCooldown.get(uuid) <= 1) {
                reportCooldown.remove(uuid);
            } else {
                reportCooldown.replace(
                        uuid,
                        reportCooldown.get(uuid),
                        reportCooldown.get(uuid) - 1);
            }
        }

        Map<UUID, Integer> fightingMap = Lunar.getInstance().getData().getFightingPlayers();
        for (final UUID uuid : fightingMap.keySet()) {
            if (fightingMap.get(uuid) <= 1) {
                fightingMap.remove(uuid);
            }
        }

        Map<UUID, Integer> slowedMap = Lunar.getInstance().getData().getSlowdownedPlayers();
        if (!slowedMap.isEmpty()) {
            for (final UUID uuid : slowedMap.keySet()) {
                if (slowedMap.get(uuid) <= 0) {
                    slowedMap.remove(uuid);
                } else {
                    slowedMap.replace(
                            uuid,
                            slowedMap.get(uuid),
                            slowedMap.get(uuid) - 1);
                }
            }
        }
    }
}
