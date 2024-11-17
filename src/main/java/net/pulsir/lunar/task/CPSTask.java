package net.pulsir.lunar.task;

import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class CPSTask implements Runnable {

    @Override
    public void run() {
        if (!Lunar.getInstance().getCpsPlayerManager().getFollowedPlayers().isEmpty())  {
            for (final UUID uuid : Lunar.getInstance().getCpsPlayerManager().getFollowedPlayers().keySet()) {
                Player player = Bukkit.getPlayer(uuid);

                if (player != null) {
                    Player target = Bukkit.getPlayer(Lunar.getInstance().getCpsPlayerManager().getFollowedPlayers().get(player.getUniqueId()));
                    if (target != null && target.isOnline() && Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().get(target.getUniqueId()) != null) {
                        int cps = Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().get(target.getUniqueId());
                        player.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance().getLanguage().getConfiguration()
                                        .getString("CPS.CHECK")).replace("{player}", target.getName())
                                .replace("{cps}", String.valueOf(cps))));
                    }
                }
            }
        }

        Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().clear();
    }
}
