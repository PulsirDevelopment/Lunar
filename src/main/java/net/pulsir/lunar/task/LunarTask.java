package net.pulsir.lunar.task;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class LunarTask implements Runnable {

    @Override
    public void run() {
        if (!Lunar.getInstance().getData().getStaffMode().isEmpty()) {
            for (UUID uuid : Lunar.getInstance().getData().getStaffMode()) {
                Component vanish = Lunar.getInstance().getData().getVanish().contains(uuid)
                        ? MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-ENABLED"))) :
                        MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-DISABLED")));

                Component players = MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                        .getConfiguration().getString("OPTIONS.ONLINE")).replace("{players}", String.valueOf(Bukkit.getOnlinePlayers().size()))));

                Component staffChat =
                        Lunar.getInstance().getData().getStaffChat().contains(uuid) ? MiniMessage.miniMessage()
                                .deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFF-CHAT"))) :
                                Lunar.getInstance().getData().getAdminChat().contains(uuid) ? MiniMessage.miniMessage()
                                        .deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.ADMIN-CHAT"))) :
                                        Lunar.getInstance().getData().getOwnerChat().contains(uuid) ? MiniMessage.miniMessage()
                                                .deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.OWNER-CHAT"))) :
                                                MiniMessage.miniMessage()
                                                        .deserialize(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.PUBLIC-CHAT")));

                if (Bukkit.getPlayer(uuid) != null && Objects.requireNonNull(Bukkit.getPlayer(uuid)).isOnline()) {
                    Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendActionBar(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar
                                    .getInstance().getLanguage().getConfiguration().getString("STAFF-BAR"))
                            .replace("{vanish}", MiniMessage.miniMessage().serialize(vanish))
                            .replace("{online}", MiniMessage.miniMessage().serialize(players))
                            .replace("{chat}", MiniMessage.miniMessage().serialize(staffChat))));
                }
            }
        }

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
