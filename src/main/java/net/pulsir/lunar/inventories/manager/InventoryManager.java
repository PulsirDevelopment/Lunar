package net.pulsir.lunar.inventories.manager;

import lombok.Getter;
import net.pulsir.lunar.inventories.InventoryPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class InventoryManager {

    private final Map<UUID, InventoryPlayer> inventories = new HashMap<>();
}
