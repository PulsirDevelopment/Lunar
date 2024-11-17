package net.pulsir.api.cps;

import org.bukkit.entity.Player;

public interface CPSManager {

    /**
     * Returns the amount of player cps.
     */
    int getPlayerCPS(Player player);
}
