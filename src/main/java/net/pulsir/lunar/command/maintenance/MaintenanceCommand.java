package net.pulsir.lunar.command.maintenance;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.maintenance.Maintenance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.codehaus.plexus.util.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MaintenanceCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender.hasPermission("lunar.command.maintenance"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (args.length == 0) {
            sendHelpMessage(sender);
            return false;
        }

        switch (args[0]) {
            case "create" -> createMaintenance(sender, args);
            case "delete" -> deleteMaintenance(sender, args);
            case "list" -> showMaintenancesList(sender);
            case "start" -> startMaintenance(sender, args);
            case "stop" -> stopMaintenance(sender, args);
            default -> sendHelpMessage(sender);
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(List.of("create", "delete", "list", "start", "stop"));
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                return new ArrayList<>(List.of("<name>"));
            } else {
                List<String> maintenancesNames = new ArrayList<>();
                Lunar.getInstance().getServerMaintenanceManager().getMaintenances()
                        .forEach(maintenance -> maintenancesNames.add(maintenance.getName()));
                return maintenancesNames;
            }
        } else if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
            return new ArrayList<>(List.of("<duration>"));
        } else if (args.length >= 4 && args[0].equalsIgnoreCase("create")) {
            return new ArrayList<>(List.of("<reason>"));
        }

        return new ArrayList<>();
    }

    private void sendHelpMessage(CommandSender sender) {
        for (String line : Lunar.getInstance().getLanguage().getConfiguration().getStringList("MAINTENANCES.USAGE")) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(line));
        }
    }

    private void showMaintenancesList(CommandSender sender) {
        if (Lunar.getInstance().getServerMaintenanceManager().getMaintenances().isEmpty()) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                    .getLanguage().getConfiguration().getString("MAINTENANCES.LIST-EMPTY")));
        }

        for (Maintenance maintenance : Lunar.getInstance().getServerMaintenanceManager().getMaintenances()) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                    .getLanguage().getConfiguration().getString("MAINTENANCES.LIST").replace("{name}", maintenance.getName())));
        }
    }

    private void createMaintenance(CommandSender sender, String[] args) {
        if (args.length < 4) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                    .getInstance().getLanguage().getConfiguration().getString("MAINTENANCES.CREATE-USAGE"))));
            return;
        }

        String name = args[1];
        String duration = args[2];
        if (!StringUtils.isNumeric(duration)) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                    .getInstance().getLanguage().getConfiguration().getString("MAINTENANCES.CREATE-MUST-BE-A-NUMBER"))));
            return;
        }

        int intDuration = Integer.parseInt(duration);
        if (intDuration < 1) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                    .getInstance().getLanguage().getConfiguration().getString("MAINTENANCES.CREATE-DURATION-MUST-BE-POSITIVE"))));
            return;
        }

        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 3; i < args.length; i++) {
            reasonBuilder.append(args[i]);
            if (i + 1 < args.length) reasonBuilder.append(" ");
        }
        String reason = reasonBuilder.toString();

        String messagePath = Lunar.getInstance().getServerMaintenanceManager().createMaintenance(name, reason, intDuration)
                ? "MAINTENANCES.CREATED"
                : "MAINTENANCES.CREATED-EXISTS";
        sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                        .getInstance().getLanguage().getConfiguration().getString(messagePath))
                .replace("{name}", name)));
    }

    private void deleteMaintenance(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                    .getInstance().getLanguage().getConfiguration().getString("MAINTENANCES.DELETE-USAGE"))));
            return;
        }

        String name = args[1];
        String messagePath = Lunar.getInstance().getServerMaintenanceManager().deleteMaintenance(name)
                ? "MAINTENANCES.DELETED"
                : "MAINTENANCES.NOT-EXISTS";
        sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                        .getInstance().getLanguage().getConfiguration().getString(messagePath))
                .replace("{name}", name)));
    }

    private void startMaintenance(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                    .getInstance().getLanguage().getConfiguration().getString("MAINTENANCES.START-USAGE"))));
            return;
        }

        String name = args[1];

        if (Lunar.getInstance().getServerMaintenanceManager().getMaintenanceByName(name) != null
                && Lunar.getInstance().getServerMaintenanceManager().getMaintenanceByName(name).getEndDate() != null) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                    .getLanguage().getConfiguration().getString("MAINTENANCES.ACTIVE").replace("{name}", name)));
            return;
        }

        String messagePath = Lunar.getInstance().getServerMaintenanceManager().startMaintenance(name)
                ? "MAINTENANCES.START"
                : "MAINTENANCES.NOT-EXISTS";
        sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                        .getInstance().getLanguage().getConfiguration().getString(messagePath))
                .replace("{name}", name)));
    }

    private void stopMaintenance(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                    .getInstance().getLanguage().getConfiguration().getString("MAINTENANCES.STOP-USAGE"))));
            return;
        }

        String name = args[1];

        if (Lunar.getInstance().getServerMaintenanceManager().getMaintenanceByName(name) != null
        && Lunar.getInstance().getServerMaintenanceManager().getMaintenanceByName(name).getEndDate() == null) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance()
                    .getLanguage().getConfiguration().getString("MAINTENANCES.NOT-ACTIVE").replace("{name}", name)));
            return;
        }

        String messagePath = Lunar.getInstance().getServerMaintenanceManager().stopMaintenance(name)
                ? "MAINTENANCES.STOP"
                : "MAINTENANCES.NOT-EXISTS";
        sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar
                        .getInstance().getLanguage().getConfiguration().getString(messagePath))
                .replace("{name}", name)));
    }
}
