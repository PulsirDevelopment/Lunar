package net.pulsir.lunar;

import lombok.Getter;
import net.pulsir.lunar.api.API;
import net.pulsir.lunar.chat.ChatMuteCommand;
import net.pulsir.lunar.chat.ChatSlowDownCommand;
import net.pulsir.lunar.chat.ChatUnMuteCommand;
import net.pulsir.lunar.command.chat.AdminChatCommand;
import net.pulsir.lunar.command.chat.FilterChatCommand;
import net.pulsir.lunar.command.chat.FrozenChatCommand;
import net.pulsir.lunar.command.chat.OwnerChatCommand;
import net.pulsir.lunar.command.chat.StaffChatCommand;
import net.pulsir.lunar.command.lunar.LunarCommand;
import net.pulsir.lunar.command.mod.ClearChatCommand;
import net.pulsir.lunar.command.player.ReportCommand;
import net.pulsir.lunar.command.player.RequestCommand;
import net.pulsir.lunar.command.restore.InventoryRestoreCommand;
import net.pulsir.lunar.command.restore.LastInventoryCommand;
import net.pulsir.lunar.command.session.SessionCommand;
import net.pulsir.lunar.command.staff.*;
import net.pulsir.lunar.data.Data;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.database.impl.FlatFile;
import net.pulsir.lunar.database.impl.Mongo;
import net.pulsir.lunar.filter.Filter;
import net.pulsir.lunar.hook.PlaceHolderHook;
import net.pulsir.lunar.inventories.manager.InventoryManager;
import net.pulsir.lunar.listener.*;
import net.pulsir.lunar.redis.RedisManager;
import net.pulsir.lunar.session.manager.SessionPlayerManager;
import net.pulsir.lunar.task.LunarTask;
import net.pulsir.lunar.task.MessagesTask;
import net.pulsir.lunar.task.ServerTask;
import net.pulsir.lunar.task.SessionTask;
import net.pulsir.lunar.utils.bungee.listener.BungeeListener;
import net.pulsir.lunar.utils.command.completer.type.CompleterType;
import net.pulsir.lunar.utils.command.manager.CommandManager;
import net.pulsir.lunar.utils.config.Config;
import net.pulsir.lunar.utils.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Getter
public final class Lunar extends JavaPlugin {

    @Getter
    private static Lunar instance;
    private Data data;
    private RedisManager redisManager;

    private Config configuration, language, inventory, messages, discord;

    private IDatabase database;
    @Getter
    private Message message;
    private Filter filter;

    private final InventoryManager inventoryManager = new InventoryManager();
    private final SessionPlayerManager sessionPlayerManager = new SessionPlayerManager();

    @Getter
    private final NamespacedKey namespacedKey = new NamespacedKey(this, "staff");
    @Getter
    private final NamespacedKey onlineStaffKey = new NamespacedKey(this, "player");

    private final API api = new API();
    private boolean lunarAPI = false;

    @Override
    public void onEnable() {
        instance = this;

        this.loadConfiguration();
        this.message = new Message();

        this.setupDatabase();

        this.registerCommands();
        Bukkit.getConsoleSender().sendMessage("[Lunar] Successfully loaded commands.");
        this.registerListeners(Bukkit.getPluginManager());
        Bukkit.getConsoleSender().sendMessage("[Lunar] Successfully loaded listeners.");

        this.data = new Data();

        this.setupSyncSystem();

        this.registerTasks();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceHolderHook().register();
        }
        if (Bukkit.getPluginManager().getPlugin("Apollo-Bukkit") != null) {
            lunarAPI = true;
        }

        this.filter = new Filter();
    }

    @Override
    public void onDisable() {
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
        this.database.saveInventory();

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
        this.inventory = new Config(this, new File(getDataFolder(), "inventories.yml"),
                new YamlConfiguration(), "inventories.yml");
        this.messages = new Config(this, new File(getDataFolder(), "messages.yml"),
                new YamlConfiguration(), "messages.yml");
        this.discord = new Config(this, new File(getDataFolder(), "discord.yml"),
                new YamlConfiguration(), "discord.yml");

        this.configuration.create();
        this.language.create();
        this.inventory.create();
        this.messages.create();
        this.discord.create();
    }

    private void setupDatabase() {
        String databaseType = Objects.requireNonNull(getConfiguration().getConfiguration().getString("database")).toLowerCase();

        switch (databaseType) {
            case "mongo" -> {
                database = new Mongo();
                Bukkit.getConsoleSender().sendMessage("[Lunar] Successfully loaded MONGO as database.");
            }
            case "flatfile" -> {
                database = new FlatFile();
                Bukkit.getConsoleSender().sendMessage("[Lunar] Successfully loaded FLATFILE as database.");
            }
            default -> {
                database = new FlatFile();
                Bukkit.getConsoleSender().sendMessage("[Lunar] Unsupported database. Loading FLATFILE as default.");
            }
        }
    }

    private void setupSyncSystem() {
        if (!configuration.getConfiguration().getBoolean("allow-sync")) return;

        String syncSystem = Objects.requireNonNull(configuration.getConfiguration().getString("sync-system")).toLowerCase();
        switch (syncSystem) {
            case "redis" -> {
                this.redisManager = new RedisManager(configuration.getConfiguration().getBoolean("redis.auth"));
                this.redisManager.subscribe();
                Bukkit.getConsoleSender().sendMessage("[Lunar] Successfully loaded REDIS as sync system.");
            }
            case "bungee" -> {
                Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener());
                Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
                Bukkit.getConsoleSender().sendMessage("[Lunar] Successfully loaded BUNGEE as sync system.");
            }
            default -> {
                Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener());
                Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
                Bukkit.getConsoleSender().sendMessage("[Lunar] Unsupported sync system. Loading BUNGEE as default sync system.");
            }
        }
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("staffchat")).setExecutor(new StaffChatCommand());
        Objects.requireNonNull(getCommand("adminchat")).setExecutor(new AdminChatCommand());
        Objects.requireNonNull(getCommand("ownerchat")).setExecutor(new OwnerChatCommand());
        Objects.requireNonNull(getCommand("freezechat")).setExecutor(new FrozenChatCommand());
        Objects.requireNonNull(getCommand("filter")).setExecutor(new FilterChatCommand());
        Objects.requireNonNull(getCommand("clearchat")).setExecutor(new ClearChatCommand());

        Objects.requireNonNull(getCommand("staff")).setExecutor(new StaffCommand());
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new VanishCommand());

        Objects.requireNonNull(getCommand("freeze")).setExecutor(new FreezeCommand());
        Objects.requireNonNull(getCommand("inspect")).setExecutor(new InvseeCommand());

        Objects.requireNonNull(getCommand("broadcast")).setExecutor(new BroadcastCommand());

        Objects.requireNonNull(getCommand("report")).setExecutor(new ReportCommand());
        Objects.requireNonNull(getCommand("request")).setExecutor(new RequestCommand());

        Objects.requireNonNull(getCommand("restore")).setExecutor(new InventoryRestoreCommand());
        Objects.requireNonNull(getCommand("lastinventory")).setExecutor(new LastInventoryCommand());

        Objects.requireNonNull(getCommand("spy")).setExecutor(new SpyCommand());

        Objects.requireNonNull(getCommand("lunar")).setExecutor(new LunarCommand());

        Objects.requireNonNull(getCommand("tphere")).setExecutor(new TpHereCommand());

        Objects.requireNonNull(getCommand("hidestaff")).setExecutor(new HideStaffCommand());
        Objects.requireNonNull(getCommand("showstaff")).setExecutor(new ShowStaffCommand());

        Objects.requireNonNull(getCommand("minealerts")).setExecutor(new MineAlertCommand());

        Objects.requireNonNull(getCommand("session")).setExecutor(new SessionCommand());

        Objects.requireNonNull(getCommand("serverfreeze")).setExecutor(new ServerFreezeCommand());

        CommandManager chatManager = new CommandManager(getCommand("chat"));

        chatManager.addSubCommand(new ChatMuteCommand());
        chatManager.addSubCommand(new ChatUnMuteCommand());
        chatManager.addSubCommand(new ChatSlowDownCommand());

        chatManager.registerCommands(() -> CompleterType.CHAT);
    }

    private void registerListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new StaffModeListener(), this);
        pluginManager.registerEvents(new VanishListener(), this);
        pluginManager.registerEvents(new FreezeListener(), this);
        pluginManager.registerEvents(new InventoryListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new SpyListener(), this);
        pluginManager.registerEvents(new ChestListener(), this);
        pluginManager.registerEvents(new CommandListener(), this);
        pluginManager.registerEvents(new FilterListener(), this);
        pluginManager.registerEvents(new MineAlertListener(), this);
        pluginManager.registerEvents(new ServerFreezeListener(), this);
    }

    private void registerTasks() {
        boolean useStaffBar = getConfiguration().getConfiguration().getBoolean("staff-bar");

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, useStaffBar ? new LunarTask() : new ServerTask(), 0L, 20L);
        Bukkit.getScheduler().runTaskTimer(this, new MessagesTask(), 0L, 20L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new SessionTask(), 0L, 20L);
    }

    public API getAPI() {
        return api;
    }

    public void reloadConfigs() {
        this.configuration.reload();
        this.language.reload();
        this.inventory.reload();
        this.messages.reload();
    }
}
