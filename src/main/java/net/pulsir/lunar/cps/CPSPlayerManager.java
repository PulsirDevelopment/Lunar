package net.pulsir.lunar.cps;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class CPSPlayerManager {

    private final Map<UUID, Integer> cpsPlayer = new HashMap<>();
    private final Map<UUID, UUID> followedPlayers = new HashMap<>();
}
