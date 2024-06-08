package net.pulsir.lunar.session;

import lombok.Getter;
import lombok.Setter;
import net.pulsir.lunar.Lunar;

import java.util.UUID;

@Getter
@Setter
public class SessionPlayer {

    private UUID uuid;
    private long sessionTime;

    public SessionPlayer(UUID uuid, long sessionTime) {
        this.uuid = uuid;
        this.sessionTime = sessionTime;
    }
}
