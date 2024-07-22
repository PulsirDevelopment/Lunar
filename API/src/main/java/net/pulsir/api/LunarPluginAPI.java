package net.pulsir.api;

import net.pulsir.api.chat.ChatManager;
import net.pulsir.api.inventory.InventoryManager;
import net.pulsir.api.session.SessionManager;
import net.pulsir.api.staff.StaffManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface LunarPluginAPI extends Plugin {

    StaffManager staffManager();
    SessionManager sessionManager();
    InventoryManager inventoryManager();
    ChatManager chatManager();
}
