package net.pulsir.lunar.command.staff;

import com.google.common.collect.Lists;
import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.nametag.Nametag;
import com.lunarclient.apollo.module.nametag.NametagModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import com.lunarclient.apollo.recipients.Recipients;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.staff.Staff;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class StaffCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.staff"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        Set<UUID> staffSet = Lunar.getInstance().getData().getStaffMode();
        if (!staffSet.contains(player.getUniqueId())) {
            enableStaffMode(player);
        } else {
            disableStaffMode(player);
        }

        return true;
    }

    private void disableStaffMode(Player player) {
        Lunar.getInstance().getData().getStaffMode().remove(player.getUniqueId());

        player.setCollidable(true);
        player.setGameMode(GameMode.SURVIVAL);
        Bukkit.getOnlinePlayers().forEach(online -> online.showPlayer(Lunar.getInstance(), player));
        player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));

        player.getInventory().setContents(Lunar.getInstance().getData().getInventories().get(player.getUniqueId()));
        Lunar.getInstance().getData().getInventories().remove(player.getUniqueId());

        resetNametagsExample(player);

        player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                .getConfiguration().getString("STAFF-MODE.DISABLED")));
    }

    @Deprecated
    private void enableStaffMode(Player player) {
        Lunar.getInstance().getData().getStaffMode().add(player.getUniqueId());

        if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("force-vanish")) {
            Set<UUID> vanishSet = Lunar.getInstance().getData().getVanish();
            vanishSet.add(player.getUniqueId());

            Bukkit.getOnlinePlayers()
                    .stream()
                    .filter(online -> !vanishSet.contains(online.getUniqueId()))
                    .forEach(online -> online.hidePlayer(Lunar.getInstance(), player));
        }

        Lunar.getInstance().getData().getInventories().put(player.getUniqueId(), player.getInventory().getContents());
        player.getInventory().clear();
        player.setCollidable(false);
        player.setGameMode(GameMode.CREATIVE);
        Staff.items(player);

        for (final String staffEffect : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("staff-effects")) {
            player.addPotionEffect(new PotionEffect(Objects.requireNonNull(PotionEffectType.getByName(staffEffect)),
                    999999,
                    0)
                    .withParticles(false));
        }

        overrideNametagExample(player);

        player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                .getConfiguration().getString("STAFF-MODE.ENABLED")));
    }

    private void overrideNametagExample(Player target) {
        Apollo.getModuleManager().getModule(NametagModule.class).overrideNametag(Recipients.ofEveryone(), target.getUniqueId(), Nametag.builder()
                .lines(Lists.newArrayList(
                        Component.text()
                                .content("[StaffMode]")
                                .decorate(TextDecoration.ITALIC)
                                .color(NamedTextColor.GRAY)
                                .build(),
                        Component.text()
                                .content(target.getName())
                                .color(NamedTextColor.RED)
                                .build()
                ))
                .build()
        );
    }

    private void resetNametagsExample(Player viewer) {
        Optional<ApolloPlayer> apolloPlayerOpt = Apollo.getPlayerManager().getPlayer(viewer.getUniqueId());
        apolloPlayerOpt.ifPresent(Apollo.getModuleManager().getModule(NametagModule.class)::resetNametags);
    }
}
