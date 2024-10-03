package net.pulsir.lunar.cps.manager;

import lombok.Getter;
import net.pulsir.lunar.cps.CPSPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class CPSPlayerManager {

    private final Map<UUID, CPSPlayer> cpsPlayers = new HashMap<>();
}
