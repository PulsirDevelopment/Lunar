package net.pulsir.api.chat;

import org.bukkit.entity.Player;

public interface ChatManager {

    boolean isMuted();
    boolean isSlowed();
    int getSlowedAmount();
    int getPlayerCooldown(Player player);
}