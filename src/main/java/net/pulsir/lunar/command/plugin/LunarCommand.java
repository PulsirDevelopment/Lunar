package net.pulsir.lunar.command.plugin;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class LunarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        sender.sendMessage(MiniMessage.miniMessage().deserialize("<purple><bold>---<white>---------<purple><bold>---"));
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<purple>Lunar made by: <white>Fankyyy"));
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<purple><bold>---<white>---------<purple><bold>---"));

        return true;
    }
}
