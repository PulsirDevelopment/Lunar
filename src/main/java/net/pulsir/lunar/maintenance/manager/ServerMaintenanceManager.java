package net.pulsir.lunar.maintenance.manager;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.maintenance.Maintenance;

@Getter
public class ServerMaintenanceManager {

    private final Set<Maintenance> maintenances = new HashSet<>();

    public boolean isMaintenanceActive() {
        return this.maintenances.stream()
                .anyMatch(Maintenance::isActive);
    }

    public Maintenance getFirstActivatedMaintenance() {
        return this.maintenances.stream()
                .filter(Maintenance::isActive)
                .findFirst().orElse(null);
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
        Lunar.getInstance().getDatabase().saveMaintenance(maintenance);
        return true;
    }

    public boolean deleteMaintenance(String name) {
        Maintenance maintenance = getMaintenanceByName(name);
        if (maintenance == null) return false;

        this.maintenances.remove(maintenance);
        Lunar.getInstance().getDatabase().removeMaintenance(maintenance);
        return true;
    }

    public boolean startMaintenance(String name) {
        Maintenance maintenance = getMaintenanceByName(name);
        if (maintenance == null) return false;

        maintenance.start();
        Lunar.getInstance().getDatabase().updateMaintenance(maintenance);
        return true;
    }

    public boolean stopMaintenance(String name) {
        Maintenance maintenance = getMaintenanceByName(name);
        if (maintenance == null) return false;

        maintenance.stop();
        Lunar.getInstance().getDatabase().updateMaintenance(maintenance);
        return true;
    }
}