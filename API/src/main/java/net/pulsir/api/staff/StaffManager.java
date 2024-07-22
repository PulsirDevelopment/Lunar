package net.pulsir.api.staff;

import org.bukkit.entity.Player;

public interface StaffManager {

    /**
     * Return's boolean value whether player is staff member.
     * @param player - Player which those methods will be invoked on.
     */
    boolean isStaff(Player player);

    /**
     * Return's boolean value whether player is admin member.
     * @param player - Player which those methods will be invoked on.
     */
    boolean isAdmin(Player player);

    /**
     * Return's boolean value whether player is owner member.
     * @param player - Player which those methods will be invoked on.
     */
    boolean isOwner(Player player);

    /**
     * Return's boolean value whether player is in vanish.
     * @param player - Player which those methods will be invoked on.
     */
    boolean isVanish(Player player);

    /**
     * Return's boolean value whether player is in staff chat.
     * @param player - Player which those methods will be invoked on.
     */
    boolean inStaffChat(Player player);

    /**
     * Return's boolean value whether player is in admin chat.
     * @param player - Player which those methods will be invoked on.
     */
    boolean inAdminChat(Player player);

    /**
     * Return's boolean value whether player is in owner chat.
     * @param player - Player which those methods will be invoked on.
     */
    boolean inOwnerChat(Player player);

    /**
     * Return's boolean value whether player is in staff mode.
     * @param player - Player which those methods will be invoked on.
     */
    boolean inStaffMode(Player player);

    /**
     * Return's boolean value whether player is frozen.
     * @param player - Player which those methods will be invoked on.
     */
    boolean isFrozen(Player player);

    /**
     * Put's player in staff mode.
     * @param player - Player which those methods will be invoked on.
     */
    void putStaffMode(Player player);

    /**
     * Remove's player from staff mode.
     * @param player - Player which those methods will be invoked on.
     */
    void removeStaffMode(Player player);
}
