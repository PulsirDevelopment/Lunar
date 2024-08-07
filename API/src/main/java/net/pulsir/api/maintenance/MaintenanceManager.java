package net.pulsir.api.maintenance;

public interface MaintenanceManager {

    /**
     * Returns maintenance is active in server.
     */
    boolean isMaintenanceActivated();

    /**
     * Returns successful of action - maintenance with name don't exists
     * @param name - Name of maintenance
     * @param reason - Reason of maintenance
     * @param duration - Duration in minutes of maintenance
     */
    boolean createMaintenance(String name, String reason, int duration);

    /**
     * Returns successful of action - maintenance with name exists
     * @param name - Name of maintenance
     */
    boolean deleteMaintenance(String name);

    /**
     * Returns successful of action - maintenance with name exists
     * @param name - Name of maintenance
     */
    boolean startMaintenance(String name);

    /**
     * Returns successful of action - maintenance with name exists
     * @param name - Name of maintenance
     */
    boolean stopMaintenance(String name);
}