package net.pulsir.lunar.session.manager;

import lombok.Getter;
import net.pulsir.lunar.session.SessionPlayer;

import java.util.*;

@Getter
public class SessionPlayerManager {

    private final Map<UUID, SessionPlayer> sessionPlayers = new HashMap<>();
}
