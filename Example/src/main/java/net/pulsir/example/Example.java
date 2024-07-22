package net.pulsir.example;

import net.pulsir.api.LunarAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Example extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        boolean inStaffMode = LunarAPI.getPlugin().getStaffManager().inStaffMode(event.getPlayer());
        long sessionTime = LunarAPI.getPlugin().getSessionManager().getSessionTime(event.getPlayer());

        event.getPlayer().sendMessage("StaffMode ? " + inStaffMode);
        event.getPlayer().sendMessage("SessionTime : " + sessionTime);
    }
}
