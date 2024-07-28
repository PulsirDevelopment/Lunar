package net.pulsir.lunar.data;

import lombok.Getter;
import lombok.Setter;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class Data {

    private final Set<UUID> staffChat = new HashSet<>();
    private final Set<UUID> adminChat = new HashSet<>();
    private final Set<UUID> ownerChat = new HashSet<>();
    private final Set<UUID> freezeChat = new HashSet<>();
    private final Set<UUID> filterChat = new HashSet<>();

    private final Set<UUID> staffMembers = new HashSet<>();
    private final Set<UUID> adminMembers = new HashSet<>();
    private final Set<UUID> ownerMembers = new HashSet<>();

    private final Set<UUID> staffMode = new HashSet<>();
    private final Set<UUID> vanish = new HashSet<>();
    private final Set<UUID> frozenPlayers = new HashSet<>();

    private final Map<UUID, ItemStack[]> inventories = new HashMap<>();
    private final Map<UUID, UUID> inspect = new HashMap<>();

    private final Map<UUID, Integer> reportCooldown = new HashMap<>();
    private final Map<UUID, Integer> requestCooldown = new HashMap<>();

    private final Set<UUID> onlinePlayers = new HashSet<>();

    private final Map<UUID, Set<UUID>> spy = new HashMap<>();

    private final Set<UUID> staffTeam = new HashSet<>();

    private final Set<UUID> mineAlerts = new HashSet<>();

    @Setter
    private boolean isServerFrozen = false;

    @Setter
    private boolean isChatMuted = false;
    @Setter private int chatSlowdown = 0;

    private final Map<UUID, Integer> slowdownedPlayers = new HashMap<>();
    private final Map<UUID, Integer> fightingPlayers = new HashMap<>();

    public void clearChat(UUID uuid) {
        staffChat.remove(uuid);
        adminChat.remove(uuid);
        ownerChat.remove(uuid);
        freezeChat.remove(uuid);
    }

    public Set<Player> getSpyOf(Player player) {
        Set<Player> spyPlayers = new HashSet<>();
        for (final UUID uuid : spy.keySet()) {
            if (spy.get(uuid).contains(player.getUniqueId())) {
                if (player.hasPermission("lunar.staff")) {
                    if (Lunar.getInstance().getConfiguration().getConfiguration().getBoolean("allow-staff-spy")) {
                        spyPlayers.add(Bukkit.getPlayer(uuid));
                    } else {
                        spy.get(uuid).remove(player.getUniqueId());
                    }
                } else {
                    spyPlayers.add(Bukkit.getPlayer(uuid));
                }
            }
        }

        return spyPlayers;
    }
}
