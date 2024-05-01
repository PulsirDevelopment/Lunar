package net.pulsir.lunar;

import lombok.Getter;
import net.pulsir.lunar.command.chat.AdminChatCommand;
import net.pulsir.lunar.command.chat.OwnerChatCommand;
import net.pulsir.lunar.command.chat.StaffChatCommand;
import net.pulsir.lunar.command.staff.StaffCommand;
import net.pulsir.lunar.data.Data;
import net.pulsir.lunar.listener.ChatListener;
import net.pulsir.lunar.utils.bungee.listener.BungeeListener;
import net.pulsir.lunar.utils.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

@Getter
public final class Lunar extends JavaPlugin {

    @Getter private static Lunar instance;
    private Data data;

    private Config configuration;
    private Config language;

    @Override
    public void onEnable() {
        instance = this;

        this.loadConfiguration();
        this.registerCommands();
        this.registerListeners(Bukkit.getPluginManager());

        this.data = new Data();

        if (configuration.getConfiguration().getBoolean("bungee")) {
            Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener());
            Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        }
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    private void loadConfiguration(){
        this.configuration = new Config(this, new File(getDataFolder(), "configuration.yml"),
                new YamlConfiguration(), "configuration.yml");
        this.language = new Config(this, new File(getDataFolder(), "language.yml"),
                new YamlConfiguration(), "language.yml");

        this.configuration.create();
        this.language.create();
    }

    private void registerCommands(){
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new StaffChatCommand());
        Objects.requireNonNull(getCommand("adminchat")).setExecutor(new AdminChatCommand());
        Objects.requireNonNull(getCommand("ownerchat")).setExecutor(new OwnerChatCommand());

        Objects.requireNonNull(getCommand("staff")).setExecutor(new StaffCommand());
    }

    private void registerListeners(PluginManager pluginManager){
        pluginManager.registerEvents(new ChatListener(), this);
    }
}
