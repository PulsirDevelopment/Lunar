package net.pulsir.lunar.utils.command;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

public abstract class Command {

    public abstract void execute(CommandSender sender, String[] args);
    public abstract String getName();
    public abstract boolean allow();
    public abstract Component getUsage();
    public abstract boolean permissible();
    public abstract String permission();
}