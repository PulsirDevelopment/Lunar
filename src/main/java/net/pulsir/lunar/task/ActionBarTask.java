package net.pulsir.lunar.task;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class ActionBarTask implements Runnable {

    @Override
    public void run() {
        if (Lunar.getInstance().getData().getStaffMode().isEmpty()) return;
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
}
