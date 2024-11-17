package net.pulsir.lunar.manager;

import net.pulsir.api.cps.CPSManager;
import net.pulsir.lunar.Lunar;
import org.bukkit.entity.Player;

public class CPSManagerImpl implements CPSManager {

    @Override
    public int getPlayerCPS(Player player) {
        return Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().get(player.getUniqueId());
    }
}
