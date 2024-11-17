package net.pulsir.lunar.task;

import net.pulsir.lunar.Lunar;

public class CPSTask implements Runnable {

    @Override
    public void run() {
        Lunar.getInstance().getCpsPlayerManager().getCpsPlayer().clear();
    }
}
