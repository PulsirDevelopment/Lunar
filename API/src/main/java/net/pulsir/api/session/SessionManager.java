package net.pulsir.api.session;

import org.bukkit.entity.Player;

public interface SessionManager {

    /**
     * Returns time of staff member session time.
     * @param player - Player which those methods will be invoked on.
     */
    long getSessionTime(Player player);

    /**
     * Set's player session time.
     * @param player - Player which those methods will be invoked on.
     */
    void setSessionTime(Player player, long time);
}