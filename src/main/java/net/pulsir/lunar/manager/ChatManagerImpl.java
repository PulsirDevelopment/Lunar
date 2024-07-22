package net.pulsir.lunar.manager;

import net.pulsir.api.LunarAPI;
import net.pulsir.api.chat.ChatManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;

public class ChatManagerImpl implements ChatManager {

    @Override
    public boolean isMuted() {
        return Lunar.getInstance().getData().isChatMuted();
    }

    @Override
    public boolean isSlowed() {
        return Lunar.getInstance().getData().getChatSlowdown() > 0;
    }

    @Override
    public int getSlowedAmount() {
        return Lunar.getInstance().getData().getChatSlowdown();
    }

    @Override
    public int getPlayerCooldown(Player player) {
        return Lunar.getInstance().getData().getSlowdownedPlayers().get(player.getUniqueId());
    }
}
