package net.pulsir.lunar.listener;

import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.staffmod.StaffMod;
import com.lunarclient.apollo.module.staffmod.StaffModModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import net.pulsir.lunar.Lunar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collections;
import java.util.Optional;

public class ApolloListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("apollo.staff")) {
            Lunar.getInstance().getData().getStaffTeam().add(event.getPlayer().getUniqueId());

            Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            apolloPlayerOpt.ifPresent(apolloPlayer -> Apollo.getModuleManager().getModule(StaffModModule.class)
                    .enableStaffMods(apolloPlayer, Collections.singletonList(StaffMod.XRAY)));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Lunar.getInstance().getData().getStaffTeam().remove(event.getPlayer().getUniqueId());
    }
}
