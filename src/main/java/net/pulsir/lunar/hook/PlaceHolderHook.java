package net.pulsir.lunar.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.data.Data;
import net.pulsir.lunar.utils.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PlaceHolderHook extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "lunar";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Pulsir";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        String enabled = getPlaceholderAPI().getPlaceholderAPIConfig().booleanTrue();
        String disabled = getPlaceholderAPI().getPlaceholderAPIConfig().booleanFalse();

        Data data = Lunar.getInstance().getData();

        switch (identifier) {
            case "muted":
                return data.isChatMuted() ? enabled : disabled;

            case "slowed_amount":
                return String.valueOf(data.getChatSlowdown());

            case "slowed":
                return data.getChatSlowdown() > 0 ? enabled : disabled;

            case "vanish":
                return data.getVanish().contains(player.getUniqueId()) ? enabled : disabled;

            case "staff":
                return data.getStaffMode().contains(player.getUniqueId()) ? enabled : disabled;

            case "staff_chat":
                return data.getStaffChat().contains(player.getUniqueId()) ? enabled : disabled;

            case "admin_chat":
                return data.getAdminChat().contains(player.getUniqueId()) ? enabled : disabled;

            case "owner_chat":
                return data.getOwnerChat().contains(player.getUniqueId()) ? enabled : disabled;

            case "frozen":
                return data.getFrozenPlayers().contains(player.getUniqueId()) ? enabled : disabled;

            case "online":
                Config config = Lunar.getInstance().getConfiguration();
                boolean isShowTotalPlayers = Objects.requireNonNull(config.getConfiguration().getString("online-players")).equalsIgnoreCase("bukkit");

                return isShowTotalPlayers ? String.valueOf(Bukkit.getOnlinePlayers().size()) : String.valueOf(data.getOnlinePlayers().size());
            default:
                return null;
        }
    }
}