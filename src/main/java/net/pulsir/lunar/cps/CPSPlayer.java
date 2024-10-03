package net.pulsir.lunar.cps;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CPSPlayer {

    private int cps;

    public CPSPlayer(int cps) {
        this.cps = cps;
    }
}
