package net.pulsir.lunar.task;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.staffmod.StaffMod;
import com.lunarclient.apollo.module.staffmod.StaffModModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.session.SessionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class ServerTask implements Runnable {

    @Override
    public void run() {
        if (!Lunar.getInstance().getData().getStaffMembers().isEmpty()) {
            for (final UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                if (!Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission("lunar.staff")) {
                    Lunar.getInstance().getData().getStaffMode().remove(uuid);
                    Lunar.getInstance().getData().getStaffMembers().remove(uuid);
                }
                if (!Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission("lunar.spy")) {
                    Lunar.getInstance().getData().getSpy().remove(uuid);
                }
            }
        }

        if (!Lunar.getInstance().getData().getStaffMembers().isEmpty()) {
            for (final UUID uuid : Lunar.getInstance().getData().getStaffMembers()) {
                if (!Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission("apollo.staff")) {

                    Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(uuid);
                    apolloPlayerOpt.ifPresent(apolloPlayer -> Apollo.getModuleManager().getModule(StaffModModule.class)
                            .disableStaffMods(apolloPlayer, Collections.singletonList(StaffMod.XRAY)));
                    Bukkit.getConsoleSender().sendMessage("1");
                } else {
                    Bukkit.getConsoleSender().sendMessage("2");
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

        for (final UUID uuid : Lunar.getInstance().getData().getFightingPlayers().keySet()) {
            if (Lunar.getInstance().getData().getFightingPlayers().get(uuid) <= 1) {
                Lunar.getInstance().getData().getFightingPlayers().remove(uuid);
            }
        }
    }
}
