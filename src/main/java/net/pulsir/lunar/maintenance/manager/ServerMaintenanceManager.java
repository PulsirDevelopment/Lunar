package net.pulsir.lunar.maintenance.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.maintenance.Maintenance;
import org.bukkit.Bukkit;

@Getter
public class ServerMaintenanceManager {

    private final List<Maintenance> maintenances = new ArrayList<>();

    public boolean isMaintenanceActive() {
        for (final Maintenance maintenance : this.maintenances) {
            if (maintenance.getEndDate() != null) return true;
        }

        return false;
    }

    public Maintenance getFirstActivatedMaintenance() {
        for (final Maintenance maintenance : this.maintenances) {
            if (maintenance.getEndDate() != null) return maintenance;
        }

        return null;
    }

    public Maintenance getMaintenanceByName(String name) {
        return this.maintenances.stream()
                .filter(maintenance -> maintenance.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    public boolean createMaintenance(String name, String reason, int duration) {
        if (getMaintenanceByName(name) != null) return false;

        Maintenance maintenance = new Maintenance(name, reason, duration);
        this.maintenances.add(maintenance);
        return true;
    }

    public boolean deleteMaintenance(String name) {
        Maintenance maintenance = getMaintenanceByName(name);
        if (maintenance == null) return false;

        this.maintenances.remove(maintenance);

        Bukkit.getScheduler().runTaskAsynchronously(Lunar.getInstance(), () -> Lunar.getInstance().getDatabase().deleteMaintenance(name));
        return true;
    }

    public boolean startMaintenance(String name) {
        Maintenance maintenance = getMaintenanceByName(name);
        if (maintenance == null) return false;

        maintenance.start();
        return true;
    }

    public boolean stopMaintenance(String name) {
        Maintenance maintenance = getMaintenanceByName(name);
        if (maintenance == null) return false;

        maintenance.stop();
        return true;
    }
}