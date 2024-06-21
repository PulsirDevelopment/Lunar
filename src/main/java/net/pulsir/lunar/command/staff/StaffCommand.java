package net.pulsir.lunar.command.staff;

import com.google.common.collect.Lists;
import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.nametag.Nametag;
import com.lunarclient.apollo.module.nametag.NametagModule;
import com.lunarclient.apollo.recipients.Recipients;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.utils.staff.Staff;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StaffCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!(player.hasPermission("lunar.staff"))) {
            sender.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("NO-PERMISSIONS")));
            return false;
        }

        if (Lunar.getInstance().getData().getStaffMode().contains(player.getUniqueId())) {
            Lunar.getInstance().getData().getStaffMode().remove(player.getUniqueId());

            player.setGameMode(GameMode.SURVIVAL);
            player.getInventory().setContents(Lunar.getInstance().getData().getInventories().get(player.getUniqueId()));
            Lunar.getInstance().getData().getInventories().remove(player.getUniqueId());

            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("STAFF-MODE.DISABLED")));

            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("lunar-api")) {
                Apollo.getModuleManager().getModule(NametagModule.class).resetNametag(Recipients.ofEveryone(), player.getUniqueId());
            }
        } else {
            Lunar.getInstance().getData().getStaffMode().add(player.getUniqueId());
            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("force-vanish")) {
                Lunar.getInstance().getData().getVanish().add(player.getUniqueId());
            }

            Lunar.getInstance().getData().getInventories().put(player.getUniqueId(), player.getInventory().getContents());
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            Staff.items(player);

            player.sendMessage(Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getLanguage()
                    .getConfiguration().getString("STAFF-MODE.ENABLED")));

            if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("lunar-api")) {
                Component staffComponent =
                        Lunar.getInstance().getMessage().getMessage(Lunar.getInstance().getConfiguration().getConfiguration().getString("LUNAR-CLIENT.STAFF"));

                List<Component> staffNameTag = new ArrayList<>();
                for (final String lines : Lunar.getInstance().getConfiguration().getConfiguration().getStringList("lunar-client.staff-nametag")) {
                    staffNameTag.add(Lunar.getInstance().getMessage().getMessage(lines.replace("{player}", player.getName())));
                }

                Apollo.getModuleManager().getModule(NametagModule.class).overrideNametag(Recipients.ofEveryone(),
                        player.getUniqueId(), Nametag.builder()
                                .lines(staffNameTag).build());
            }
        }

        return true;
    }
}
