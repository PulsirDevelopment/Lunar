package net.pulsir.lunar.task;

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
            String vanish = Lunar.getInstance().getData().getVanish().contains(uuid) ? "Enabled" : "Disabled";
            int players = Bukkit.getOnlinePlayers().size();
            String staffChat =
                    Lunar.getInstance().getData().getStaffChat().contains(uuid) ? "Staff" :
                    Lunar.getInstance().getData().getAdminChat().contains(uuid) ? "Admin" :
                    Lunar.getInstance().getData().getOwnerChat().contains(uuid) ? "Owner" :
                            "Public";

            Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendActionBar(MiniMessage.miniMessage().deserialize(Objects.requireNonNull(Lunar
                            .getInstance().getLanguage().getConfiguration().getString("STAFF-BAR"))
                    .replace("{vanish}", vanish)
                    .replace("{online}", String.valueOf(players))
                    .replace("{chat}", staffChat)));
        }
    }
}
