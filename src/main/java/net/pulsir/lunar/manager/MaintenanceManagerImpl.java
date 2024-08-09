package net.pulsir.lunar.manager;

import net.pulsir.api.maintenance.MaintenanceManager;
import net.pulsir.lunar.Lunar;

public class MaintenanceManagerImpl implements MaintenanceManager {

    public boolean isMaintenanceActivated() {
        return Lunar.getInstance().getServerMaintenanceManager().isMaintenanceActive();
    }

    public boolean createMaintenance(String name, String reason, int duration) {
        return Lunar.getInstance().getServerMaintenanceManager().createMaintenance(name, reason, duration);
    }

    public boolean deleteMaintenance(String name) {
        return Lunar.getInstance().getServerMaintenanceManager().deleteMaintenance(name);
    }

    public boolean startMaintenance(String name) {
        return Lunar.getInstance().getServerMaintenanceManager().startMaintenance(name);
    }

    public boolean stopMaintenance(String name) {
        return Lunar.getInstance().getServerMaintenanceManager().stopMaintenance(name);
    }
}