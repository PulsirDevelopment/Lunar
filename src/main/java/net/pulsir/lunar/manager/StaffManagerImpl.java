package net.pulsir.lunar.manager;

import net.pulsir.api.staff.StaffManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;

public class StaffManagerImpl implements StaffManager {

    @Override
    public boolean isStaff(Player player) {
        return Lunar.getInstance().getData().getStaffMembers().contains(player.getUniqueId());
    }

    @Override
    public boolean isAdmin(Player player) {
        return Lunar.getInstance().getData().getAdminMembers().contains(player.getUniqueId());
    }

    @Override
    public boolean isOwner(Player player) {
        return Lunar.getInstance().getData().getOwnerMembers().contains(player.getUniqueId());
    }

    @Override
    public boolean isVanish(Player player) {
        return Lunar.getInstance().getData().getVanish().contains(player.getUniqueId());
    }

    @Override
    public boolean inStaffChat(Player player) {
        return Lunar.getInstance().getData().getStaffChat().contains(player.getUniqueId());
    }

    @Override
    public boolean inAdminChat(Player player) {
        return Lunar.getInstance().getData().getAdminChat().contains(player.getUniqueId());
    }

    @Override
    public boolean inOwnerChat(Player player) {
        return Lunar.getInstance().getData().getOwnerChat().contains(player.getUniqueId());
    }

    @Override
    public boolean inStaffMode(Player player) {
        return Lunar.getInstance().getData().getStaffMode().contains(player.getUniqueId());
    }

    @Override
    public boolean isFrozen(Player player) {
        return Lunar.getInstance().getData().getFrozenPlayers().contains(player.getUniqueId());
    }

    @Override
    public void putStaffMode(Player player) {
        Lunar.getInstance().getData().getStaffMode().add(player.getUniqueId());
    }

    @Override
    public void removeStaffMode(Player player) {
        Lunar.getInstance().getData().getStaffMode().remove(player.getUniqueId());
    }
}
