package net.pulsir.lunar.task;

import net.kyori.adventure.text.Component;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.session.SessionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class LunarTask implements Runnable {

    @Override
    public void run() {
        if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-bar-visibility")).equalsIgnoreCase("staff")) {
            if (!Lunar.getInstance().getData().getStaffMode().isEmpty()) {
                for (UUID uuid : Lunar.getInstance().getData().getStaffMode()) {
                    Component vanish = Lunar.getInstance().getData().getVanish().contains(uuid)
                            ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-ENABLED")) :
                            Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-DISABLED"));

                    Component players;

                    if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("online-players")).equalsIgnoreCase("bukkit")) {
                        players = Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("OPTIONS.ONLINE"))
                                .replace("{players}", String.valueOf(Bukkit.getOnlinePlayers().size())));
                    } else {
                        players = Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("OPTIONS.ONLINE"))
                                .replace("{players}", String.valueOf(Lunar.getInstance().getData().getOnlinePlayers().size())));
                    }

                    Component staffMode = Lunar.getInstance().getData().getStaffMode().contains(uuid)
                            ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFFMODE-ENABLED"))
                            : Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFFMODE-DISABLED"));

                    Component staffChat =
                            Lunar.getInstance().getData().getStaffChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("OPTIONS.STAFF-CHAT")) :
                                    Lunar.getInstance().getData().getAdminChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                            .getLanguage().getConfiguration().getString("OPTIONS.ADMIN-CHAT")) :
                                            Lunar.getInstance().getData().getOwnerChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                                    .getLanguage().getConfiguration().getString("OPTIONS.OWNER-CHAT")) :
                                                    Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.PUBLIC-CHAT"));

                    if (Bukkit.getPlayer(uuid) != null && Objects.requireNonNull(Bukkit.getPlayer(uuid)).isOnline()) {
                        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendActionBar(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                        .getConfiguration().getString("STAFF-BAR"))
                                .replace("{vanish}", Lunar.getInstance().getMessage().getComponent(vanish))
                                .replace("{online}", Lunar.getInstance().getMessage().getComponent(players))
                                .replace("{chat}", Lunar.getInstance().getMessage().getComponent(staffChat))
                                .replace("{staff}", Lunar.getInstance().getMessage().getComponent(staffMode))));
                    }
                }
            }
        } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-bar-visibility")).equalsIgnoreCase("vanish")) {
            if (!Lunar.getInstance().getData().getVanish().isEmpty()) {
                for (UUID uuid : Lunar.getInstance().getData().getVanish()) {
                    Component vanish = Lunar.getInstance().getData().getVanish().contains(uuid)
                            ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-ENABLED")) :
                            Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-DISABLED"));

                    Component players;

                    if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("online-players")).equalsIgnoreCase("bukkit")) {
                        players = Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("OPTIONS.ONLINE"))
                                .replace("{players}", String.valueOf(Bukkit.getOnlinePlayers().size())));
                    } else {
                        players = Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("OPTIONS.ONLINE"))
                                .replace("{players}", String.valueOf(Lunar.getInstance().getData().getOnlinePlayers().size())));
                    }

                    Component staffMode = Lunar.getInstance().getData().getStaffMode().contains(uuid)
                            ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFFMODE-ENABLED"))
                            : Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFFMODE-DISABLED"));

                    Component staffChat =
                            Lunar.getInstance().getData().getStaffChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("OPTIONS.STAFF-CHAT")) :
                                    Lunar.getInstance().getData().getAdminChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                            .getLanguage().getConfiguration().getString("OPTIONS.ADMIN-CHAT")) :
                                            Lunar.getInstance().getData().getOwnerChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                                    .getLanguage().getConfiguration().getString("OPTIONS.OWNER-CHAT")) :
                                                    Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.PUBLIC-CHAT"));

                    if (Bukkit.getPlayer(uuid) != null && Objects.requireNonNull(Bukkit.getPlayer(uuid)).isOnline()) {
                        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendActionBar(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                        .getConfiguration().getString("STAFF-BAR"))
                                .replace("{vanish}", Lunar.getInstance().getMessage().getComponent(vanish))
                                .replace("{online}", Lunar.getInstance().getMessage().getComponent(players))
                                .replace("{chat}", Lunar.getInstance().getMessage().getComponent(staffChat))
                                .replace("{staff}", Lunar.getInstance().getMessage().getComponent(staffMode))));
                    }
                }
            }
        } else if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("staff-bar-visibility")).equalsIgnoreCase("both")) {
            if (!Lunar.getInstance().getData().getStaffMode().isEmpty()) {
                for (UUID uuid : Lunar.getInstance().getData().getStaffMode()) {
                    Component vanish = Lunar.getInstance().getData().getVanish().contains(uuid)
                            ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-ENABLED")) :
                            Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-DISABLED"));

                    Component players;

                    if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("online-players")).equalsIgnoreCase("bukkit")) {
                        players = Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("OPTIONS.ONLINE"))
                                .replace("{players}", String.valueOf(Bukkit.getOnlinePlayers().size())));
                    } else {
                        players = Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("OPTIONS.ONLINE"))
                                .replace("{players}", String.valueOf(Lunar.getInstance().getData().getOnlinePlayers().size())));
                    }

                    Component staffMode = Lunar.getInstance().getData().getStaffMode().contains(uuid)
                            ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFFMODE-ENABLED"))
                            : Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFFMODE-DISABLED"));

                    Component staffChat =
                            Lunar.getInstance().getData().getStaffChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("OPTIONS.STAFF-CHAT")) :
                                    Lunar.getInstance().getData().getAdminChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                            .getLanguage().getConfiguration().getString("OPTIONS.ADMIN-CHAT")) :
                                            Lunar.getInstance().getData().getOwnerChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                                    .getLanguage().getConfiguration().getString("OPTIONS.OWNER-CHAT")) :
                                                    Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.PUBLIC-CHAT"));

                    if (Bukkit.getPlayer(uuid) != null && Objects.requireNonNull(Bukkit.getPlayer(uuid)).isOnline()) {
                        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendActionBar(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                        .getConfiguration().getString("STAFF-BAR"))
                                .replace("{vanish}", Lunar.getInstance().getMessage().getComponent(vanish))
                                .replace("{online}", Lunar.getInstance().getMessage().getComponent(players))
                                .replace("{chat}", Lunar.getInstance().getMessage().getComponent(staffChat))
                                .replace("{staff}", Lunar.getInstance().getMessage().getComponent(staffMode))));
                    }
                }
            }

            if (!Lunar.getInstance().getData().getVanish().isEmpty()) {
                for (UUID uuid : Lunar.getInstance().getData().getVanish()) {
                    Component vanish = Lunar.getInstance().getData().getVanish().contains(uuid)
                            ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-ENABLED")) :
                            Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.VANISH-DISABLED"));

                    Component players;

                    if (Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("online-players")).equalsIgnoreCase("bukkit")) {
                        players = Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("OPTIONS.ONLINE"))
                                .replace("{players}", String.valueOf(Bukkit.getOnlinePlayers().size())));
                    } else {
                        players = Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("OPTIONS.ONLINE"))
                                .replace("{players}", String.valueOf(Lunar.getInstance().getData().getOnlinePlayers().size())));
                    }

                    Component staffMode = Lunar.getInstance().getData().getStaffMode().contains(uuid)
                            ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFFMODE-ENABLED"))
                            : Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.STAFFMODE-DISABLED"));

                    Component staffChat =
                            Lunar.getInstance().getData().getStaffChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                    .getLanguage().getConfiguration().getString("OPTIONS.STAFF-CHAT")) :
                                    Lunar.getInstance().getData().getAdminChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                            .getLanguage().getConfiguration().getString("OPTIONS.ADMIN-CHAT")) :
                                            Lunar.getInstance().getData().getOwnerChat().contains(uuid) ? Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                                                    .getLanguage().getConfiguration().getString("OPTIONS.OWNER-CHAT")) :
                                                    Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage().getConfiguration().getString("OPTIONS.PUBLIC-CHAT"));

                    if (Bukkit.getPlayer(uuid) != null && Objects.requireNonNull(Bukkit.getPlayer(uuid)).isOnline()) {
                        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendActionBar(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage()
                                        .getConfiguration().getString("STAFF-BAR"))
                                .replace("{vanish}", Lunar.getInstance().getMessage().getComponent(vanish))
                                .replace("{online}", Lunar.getInstance().getMessage().getComponent(players))
                                .replace("{chat}", Lunar.getInstance().getMessage().getComponent(staffChat))
                                .replace("{staff}", Lunar.getInstance().getMessage().getComponent(staffMode))));
                    }
                }
            }
        }

        if (!Lunar.getInstance().getData().getStaffMembers().isEmpty()) {
            Lunar.getInstance().getData().getStaffMembers().removeIf(uuid -> !Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission("lunar.staff"));
            Lunar.getInstance().getData().getStaffMode().removeIf(uuid -> !Objects.requireNonNull(Bukkit.getPlayer(uuid)).hasPermission("lunar.staff"));
        }

        for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
            if (onlinePlayers.hasPermission("lunar.staff")) {
                Lunar.getInstance().getData().getStaffMembers().add(onlinePlayers.getUniqueId());

                if (!Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().containsKey(onlinePlayers.getUniqueId())) {
                    Lunar.getInstance().getSessionPlayerManager().getSessionPlayers().put(onlinePlayers.getUniqueId(),
                            new SessionPlayer(onlinePlayers.getUniqueId(), 0));
                }
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
