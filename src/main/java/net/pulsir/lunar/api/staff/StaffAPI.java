package net.pulsir.lunar.api.staff;

import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;

public class StaffAPI {

    public boolean isStaff(Player player) {
        return Lunar.getInstance().getData().getStaffMembers().contains(player.getUniqueId());
    }

    public boolean isAdmin(Player player) {
        return Lunar.getInstance().getData().getAdminMembers().contains(player.getUniqueId());
    }

    public boolean isOwner(Player player) {
        return Lunar.getInstance().getData().getOwnerMembers().contains(player.getUniqueId());
    }

    public boolean isVanish(Player player) {
        return Lunar.getInstance().getData().getVanish().contains(player.getUniqueId());
    }
    public boolean inStaffChat(Player player) {
        return Lunar.getInstance().getData().getStaffMembers().contains(player.getUniqueId());
    }

    public boolean inAdminChat(Player player) {
        return Lunar.getInstance().getData().getAdminChat().contains(player.getUniqueId());
    }

    public boolean inOwnerChat(Player player) {
        return Lunar.getInstance().getData().getOwnerChat().contains(player.getUniqueId());
    }

    public boolean inStaffMode(Player player) {
        return Lunar.getInstance().getData().getStaffMode().contains(player.getUniqueId());
    }

    public boolean isFrozen(Player player) {
        return Lunar.getInstance().getData().getFrozenPlayers().contains(player.getUniqueId());
    }
}
