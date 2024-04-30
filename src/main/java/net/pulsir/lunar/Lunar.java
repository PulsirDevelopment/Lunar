package net.pulsir.lunar;

import lombok.Getter;
import net.pulsir.lunar.listener.PlayerListener;
import net.pulsir.lunar.utils.bungee.listener.BungeeListener;
import net.pulsir.lunar.utils.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

@Getter
public final class Lunar extends JavaPlugin {

    @Getter private static Lunar instance;

    private Config configuration;
    private Config language;

    @Override
    public void onEnable() {
        instance = this;

        this.loadConfiguration();

        if (configuration.getConfiguration().getBoolean("bungee")) {
            Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener());
            Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        }

        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
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
}
