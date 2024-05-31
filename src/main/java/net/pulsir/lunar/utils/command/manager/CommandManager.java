package net.pulsir.lunar.utils.command.manager;

import net.pulsir.lunar.utils.command.Command;
import net.pulsir.lunar.utils.command.adapter.CommandAdapter;
import net.pulsir.lunar.utils.command.completer.ICompleter;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandManager {

    private final PluginCommand pluginCommand;
    private final List<Command> commands = new ArrayList<>();

    public CommandManager(PluginCommand pluginCommand) {
        this.pluginCommand = pluginCommand;
    }

    public void addSubCommand(Command command){
        commands.add(command);
    }

    public void setCommand(List<Command> commandList) {
        commands.addAll(commandList);
    }

    public void registerCommands(ICompleter completer) {
        pluginCommand.setExecutor(new CommandAdapter(commands, completer));
    }

    public List<Command> getRegisteredSubCommands() {
        return commands;
    }
}