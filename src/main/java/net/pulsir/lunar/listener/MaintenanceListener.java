package net.pulsir.lunar.listener;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.maintenance.Maintenance;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class MaintenanceListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        if (Lunar.getInstance().getServerMaintenanceManager().isMaintenanceActive() &&
                !event.getPlayer().hasPermission("lunar.maintenance.bypass"))
            event.disallow(PlayerLoginEvent.Result.KICK_WHITELIST, Lunar.getInstance().getMessage().getMessage(generateMaintenanceMessage()));
    }

    private String generateMaintenanceMessage() {
        String message = "";
        Maintenance activeMaintenance = Lunar.getInstance().getServerMaintenanceManager().getFirstActivatedMaintenance();


        for (String line : Lunar.getInstance().getLanguage().getConfiguration().getStringList("MAINTENANCES.SERVER-ENTRY")) {
            if (message.isEmpty()) {
                message = line.replace("{name}", activeMaintenance.getName()).replace("{reason}", activeMaintenance.getReason())
                        .replace("{end_date}", activeMaintenance.getEndDate().toString());
            } else {
                message = message + "\n" + line.replace("{name}", activeMaintenance.getName()).replace("{reason}", activeMaintenance.getReason())
                        .replace("{end_date}", activeMaintenance.getEndDate().toString());
            }
        }

        return message;
    }
}
