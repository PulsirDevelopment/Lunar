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

    /*
        [00:47:41 ERROR]: Could not pass event PlayerInteractAtEntityEvent to Lunar v1.0-SNAPSHOT
java.lang.NullPointerException: Cannot invoke "org.bukkit.inventory.meta.ItemMeta.getPersistentDataContainer()" because the return value of "org.bukkit.inventory.ItemStack.getItemMeta()" is null
        at net.pulsir.lunar.listener.StaffModeListener.onInspect(StaffModeListener.java:164) ~[Lunar-1.0-SNAPSHOT-all.jar:?]
        at com.destroystokyo.paper.event.executor.asm.generated.GeneratedEventExecutor5.execute(Unknown Source) ~[?:?]
        at org.bukkit.plugin.EventExecutor$2.execute(EventExecutor.java:77) ~[paper-api-1.20.4-R0.1-SNAPSHOT.jar:?]
        at co.aikar.timings.TimedEventExecutor.execute(TimedEventExecutor.java:81) ~[paper-api-1.20.4-R0.1-SNAPSHOT.jar:git-Paper-496]
        at org.bukkit.plugin.RegisteredListener.callEvent(RegisteredListener.java:70) ~[paper-api-1.20.4-R0.1-SNAPSHOT.jar:?]
        at io.papermc.paper.plugin.manager.PaperEventManager.callEvent(PaperEventManager.java:54) ~[paper-1.20.4.jar:git-Paper-496]
        at io.papermc.paper.plugin.manager.PaperPluginManagerImpl.callEvent(PaperPluginManagerImpl.java:126) ~[paper-1.20.4.jar:git-Paper-496]
        at org.bukkit.plugin.SimplePluginManager.callEvent(SimplePluginManager.java:615) ~[paper-api-1.20.4-R0.1-SNAPSHOT.jar:?]
        at net.minecraft.server.network.ServerGamePacketListenerImpl$3.performInteraction(ServerGamePacketListenerImpl.java:2722) ~[?:?]
        at net.minecraft.server.network.ServerGamePacketListenerImpl$3.a(ServerGamePacketListenerImpl.java:2775) ~[?:?]
        at net.minecraft.network.protocol.game.ServerboundInteractPacket$InteractionAtLocationAction.dispatch(ServerboundInteractPacket.java:159) ~[?:?]
        at net.minecraft.network.protocol.game.ServerboundInteractPacket.dispatch(ServerboundInteractPacket.java:80) ~[?:?]
        at net.minecraft.server.network.ServerGamePacketListenerImpl.handleInteract(ServerGamePacketListenerImpl.java:2711) ~[?:?]
        at net.minecraft.network.protocol.game.ServerboundInteractPacket.handle(ServerboundInteractPacket.java:67) ~[?:?]
        at net.minecraft.network.protocol.game.ServerboundInteractPacket.handle(ServerboundInteractPacket.java:12) ~[?:?]
        at net.minecraft.network.protocol.PacketUtils.lambda$ensureRunningOnSameThread$0(PacketUtils.java:54) ~[?:?]
        at net.minecraft.server.TickTask.run(TickTask.java:18) ~[paper-1.20.4.jar:git-Paper-496]
        at net.minecraft.util.thread.BlockableEventLoop.doRunTask(BlockableEventLoop.java:149) ~[?:?]
        at net.minecraft.util.thread.ReentrantBlockableEventLoop.doRunTask(ReentrantBlockableEventLoop.java:24) ~[?:?]
        at net.minecraft.server.MinecraftServer.doRunTask(MinecraftServer.java:1465) ~[paper-1.20.4.jar:git-Paper-496]
        at net.minecraft.server.MinecraftServer.d(MinecraftServer.java:194) ~[paper-1.20.4.jar:git-Paper-496]
        at net.minecraft.util.thread.BlockableEventLoop.pollTask(BlockableEventLoop.java:123) ~[?:?]
        at net.minecraft.server.MinecraftServer.pollTaskInternal(MinecraftServer.java:1442) ~[paper-1.20.4.jar:git-Paper-496]
        at net.minecraft.server.MinecraftServer.pollTask(MinecraftServer.java:1365) ~[paper-1.20.4.jar:git-Paper-496]
        at net.minecraft.util.thread.BlockableEventLoop.managedBlock(BlockableEventLoop.java:133) ~[?:?]
        at net.minecraft.server.MinecraftServer.tickServer(MinecraftServer.java:1516) ~[paper-1.20.4.jar:git-Paper-496]
        at net.minecraft.server.MinecraftServer.runServer(MinecraftServer.java:1226) ~[paper-1.20.4.jar:git-Paper-496]
        at net.minecraft.server.MinecraftServer.lambda$spin$0(MinecraftServer.java:319) ~[paper-1.20.4.jar:git-Paper-496]
        at java.lang.Thread.run(Thread.java:840) ~[?:?]
     */

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
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new VanishCommand());

        Objects.requireNonNull(getCommand("freeze")).setExecutor(new FreezeCommand());
        Objects.requireNonNull(getCommand("inspect")).setExecutor(new InvseeCommand());
    }

    private void registerListeners(PluginManager pluginManager){
        pluginManager.registerEvents(new ChatListener(), this);
        pluginManager.registerEvents(new PlayerListener(), this);
        pluginManager.registerEvents(new StaffModeListener(), this);
        pluginManager.registerEvents(new VanishListener(), this);
        pluginManager.registerEvents(new FreezeListener(), this);
    }

    private void registerTasks(){
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, new ActionBarTask(), 0L ,20L);
    }
}
