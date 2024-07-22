package net.pulsir.api.chat;

import org.bukkit.entity.Player;

public interface ChatManager {

    /**
     * Returns true/false whether chat is muted.
     */
    boolean isMuted();

    /**
     * Returns true/false whether chat is slowed.
     */
    boolean isSlowed();

    /**
     * Returns amount of seconds chat slowed is.
     */
    int getSlowedAmount();

    /**
     * Returns amount of seconds player is prohibited to type in chat.
     * @param player - Player which those methods will be invoked on.
     */
    int getPlayerCooldown(Player player);
}