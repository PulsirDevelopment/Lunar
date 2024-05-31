package net.pulsir.lunar.api.chat;

import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;

public class ChatAPI {

    public boolean isMuted(){
        return Lunar.getInstance().getData().isChatMuted();
    }
    public boolean isSlowed(){
        return Lunar.getInstance().getData().getChatSlowdown() <= 0;
    }
    public int getSlowedAmount(){
        return Lunar.getInstance().getData().getChatSlowdown();
    }
    public int getPlayerCooldown(Player player) {
        return Lunar.getInstance().getData().getSlowdownedPlayers().get(player.getUniqueId());
    }
}
