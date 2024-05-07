package net.pulsir.lunar.task;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class ServerTask implements Runnable{
    @Override
    public void run() {
        if (!Lunar.getInstance().getData().getStaffMembers().isEmpty()) {
            Lunar.getInstance().getData().getStaffMembers().removeIf(uuid -> !Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission("lunar.staff"));
        }

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (onlinePlayers.hasPermission("lunar.staff")) {
                Lunar.getInstance().getData().getStaffMembers().add(onlinePlayers.getUniqueId());
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
