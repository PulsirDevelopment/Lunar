package net.pulsir.lunar;

import lombok.Getter;
import net.pulsir.lunar.command.chat.AdminChatCommand;
import net.pulsir.lunar.command.chat.OwnerChatCommand;
import net.pulsir.lunar.command.chat.StaffChatCommand;
import net.pulsir.lunar.command.staff.FreezeCommand;
import net.pulsir.lunar.command.staff.InvseeCommand;
import net.pulsir.lunar.command.staff.StaffCommand;
import net.pulsir.lunar.command.staff.VanishCommand;
import net.pulsir.lunar.data.Data;
import net.pulsir.lunar.listener.*;
import net.pulsir.lunar.task.ActionBarTask;
import net.pulsir.lunar.utils.bungee.listener.BungeeListener;
import net.pulsir.lunar.utils.config.Config;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Getter
public final class Lunar extends JavaPlugin {

    @Getter private static Lunar instance;
    private Data data;

    private Config configuration;
    private Config language;

    @Getter private final NamespacedKey namespacedKey = new NamespacedKey(this, "staff");

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

        this.registerTasks();
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);

        if (getData().getInventories().isEmpty()) return;
        for (UUID uuid : getData().getInventories().keySet()) {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                player.setGameMode(GameMode.SURVIVAL);
                player.getInventory().clear();
                player.getInventory().setContents(getData().getInventories().get(player.getUniqueId()));
                getData().getStaffMode().clear();

                Location location = player.getLocation();

                if (location.getBlock().getType().equals(Material.AIR)) {

                    for (int i = location.getBlockY(); i > 1; i--) {
                        Location newLocation = new Location(location.getWorld(), location.getBlockX(), i, location.getBlockZ());

                        if (!newLocation.getBlock().getType().equals(Material.AIR)) {
                            player.teleport(new Location(location.getWorld(), location.getBlockX(), i + 1, location.getBlockZ()));
                            break;
                        }
                    }
                }
            }
        }

        getData().getInventories().clear();

        instance = null;
    }

    private void loadConfiguration() {
        this.configuration = new Config(this, new File(getDataFolder(), "configuration.yml"),
                new YamlConfiguration(), "configuration.yml");
        this.language = new Config(this, new File(getDataFolder(), "language.yml"),
                new YamlConfiguration(), "language.yml");

        this.configuration.create();
        this.language.create();
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new StaffChatCommand());
        Objects.requireNonNull(getCommand("adminchat")).setExecutor(new AdminChatCommand());
        Objects.requireNonNull(getCommand("ownerchat")).setExecutor(new OwnerChatCommand());

        Objects.requireNonNull(getCommand("staff")).setExecutor(new StaffCommand());
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new VanishCommand());

        Objects.requireNonNull(getCommand("freeze")).setExecutor(new FreezeCommand());
        Objects.requireNonNull(getCommand("inspect")).setExecutor(new InvseeCommand());
    }

    private void registerListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new StaffModeListener(), this);
        pluginManager.registerEvents(new VanishListener(), this);
        pluginManager.registerEvents(new FreezeListener(), this);
    }

    private void registerTasks() {
        if (getConfiguration().getConfiguration().getBoolean("staff-bar")) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ActionBarTask(), 0L, 20L);
        }
    }
}
