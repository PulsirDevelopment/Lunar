package net.pulsir.api;

import net.pulsir.api.bungee.BungeeManager;
import net.pulsir.api.chat.ChatManager;
import net.pulsir.api.cps.CPSManager;
import net.pulsir.api.inventory.InventoryManager;
import net.pulsir.api.maintenance.MaintenanceManager;
import net.pulsir.api.redis.RedisManager;
import net.pulsir.api.session.SessionManager;
import net.pulsir.api.staff.StaffManager;
import org.bukkit.plugin.Plugin;

public interface LunarPluginAPI extends Plugin {

    StaffManager getStaffManager();
    SessionManager getSessionManager();
    InventoryManager getInventoryManager();
    ChatManager getChatManager();
    RedisManager getRedisManager();
    BungeeManager getBungeeManager();
    MaintenanceManager getMaintenanceManager();
    CPSManager getCPSManager();
}
