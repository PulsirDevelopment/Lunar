package net.pulsir.lunar.task;

import net.kyori.adventure.text.Component;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.session.SessionPlayer;
import net.pulsir.lunar.session.manager.SessionPlayerManager;
import net.pulsir.lunar.utils.config.Config;
import net.pulsir.lunar.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class LunarTask implements Runnable {

    @Override
    public void run() {
        Config config = Lunar.getInstance().getConfiguration();

        Map<UUID, Set<UUID>> spyMap = Lunar.getInstance().getData().getSpy();
        Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();
        Set<UUID> staffModeSet = Lunar.getInstance().getData().getStaffMode();
        Set<UUID> staffMembersSet = Lunar.getInstance().getData().getStaffMembers();

        String barVisibility = Objects.requireNonNull(config.getConfiguration().getString("staff-bar-visibility")).toLowerCase();
        switch (barVisibility) {
            case "staff" -> {
                if (!staffModeSet.isEmpty()) {
                    for (UUID uuid : staffModeSet) {
                        handleBar(uuid);
                    }
                }
            }
            case "vanish" -> {
                if (!vanishSet.isEmpty()) {
                    for (UUID uuid : vanishSet) {
                        handleBar(uuid);
                    }
                }
            }
            case "both" -> {
                if (!staffModeSet.isEmpty()) {
                    for (UUID uuid : staffModeSet) {
                        handleBar(uuid);
                    }
                }

                if (!vanishSet.isEmpty()) {
                    for (UUID uuid : vanishSet) {
                        handleBar(uuid);
                    }
                }
            }
        }

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

    private Component getChatMessage(boolean inStaffChat, boolean inAdminChat, boolean inOwnerChat, Message message, Config language) {
        String messageKey;
        if (inStaffChat) {
            messageKey = "OPTIONS.STAFF-CHAT";
        } else if (inAdminChat) {
            messageKey = "OPTIONS.ADMIN-CHAT";
        } else if (inOwnerChat) {
            messageKey = "OPTIONS.OWNER-CHAT";
        } else {
            messageKey = "OPTIONS.PUBLIC-CHAT";
        }
        return message.getMessage(language.getConfiguration().getString(messageKey));
    }

    private void handleBar(UUID uuid) {
        Message message = Lunar.getInstance().getMessage();
        Config language = Lunar.getInstance().getLanguage();
        Config config = Lunar.getInstance().getConfiguration();

        Component vanishEnabled = message.getMessage(language.getConfiguration().getString("OPTIONS.VANISH-ENABLED"));
        Component vanishDisabled = message.getMessage(language.getConfiguration().getString("OPTIONS.VANISH-DISABLED"));
        Component vanish = Lunar.getInstance().getData().getVanish().contains(uuid) ? vanishEnabled : vanishDisabled;

        String onlineType = Objects.requireNonNull(config.getConfiguration().getString("online-players"));
        boolean useBukkit = onlineType.equalsIgnoreCase("bukkit");

        Component players = message
                .getMessage(Objects.requireNonNull(language
                                .getConfiguration()
                                .getString("OPTIONS.ONLINE"))
                        .replace("{players}",
                                useBukkit ?
                                        String.valueOf(Bukkit.getOnlinePlayers().size()) :
                                        String.valueOf(Lunar.getInstance().getData().getOnlinePlayers().size())));

        Component staffModeEnabled = message.getMessage(language.getConfiguration().getString("OPTIONS.STAFFMODE-ENABLED"));
        Component staffModeDisabled = message.getMessage(language.getConfiguration().getString("OPTIONS.STAFFMODE-DISABLED"));
        Component staffMode = Lunar.getInstance().getData().getStaffMode().contains(uuid) ? staffModeEnabled : staffModeDisabled;

        boolean inStaffChat = Lunar.getInstance().getData().getStaffChat().contains(uuid);
        boolean inAdminChat = Lunar.getInstance().getData().getAdminChat().contains(uuid);
        boolean inOwnerChat = Lunar.getInstance().getData().getOwnerChat().contains(uuid);

        Component staffChat = getChatMessage(inStaffChat, inAdminChat, inOwnerChat, message, language);

        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            player.sendActionBar(message.getMessage(Objects.requireNonNull(language
                            .getConfiguration().getString("STAFF-BAR"))
                    .replace("{vanish}", message.getComponent(vanish))
                    .replace("{online}", message.getComponent(players))
                    .replace("{chat}", message.getComponent(staffChat))
                    .replace("{staff}", message.getComponent(staffMode))));

        }
    }
}
