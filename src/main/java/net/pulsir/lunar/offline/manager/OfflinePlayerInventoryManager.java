package net.pulsir.lunar.offline.manager;

import lombok.Getter;
import net.pulsir.lunar.offline.OfflinePlayerInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class OfflinePlayerInventoryManager {

    private final Map<UUID, OfflinePlayerInventory> offlinePlayersInventory = new HashMap<>();
}
