package net.pulsir.lunar.task;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class PermissionTask implements Runnable{
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
    }
}
