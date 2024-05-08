package net.pulsir.lunar.task;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessagesTask implements Runnable {

    private final List<List<String>> globalMessages = new ArrayList<>();
    private int delay = 0;
    private int current = 0;
    private boolean enabled;

    public MessagesTask() {
        if (Lunar.getInstance().getMessages().getConfiguration().getConfigurationSection("messages") == null) return;
        this.delay = Lunar.getInstance().getMessages().getConfiguration().getInt("delay-between");
        this.enabled = Lunar.getInstance().getMessages().getConfiguration().getBoolean("enabled");

        for (final String messages : Objects.requireNonNull(Lunar.getInstance().getMessages().getConfiguration().getConfigurationSection("messages")).getKeys(false)) {
            globalMessages.add(Lunar.getInstance().getMessages().getConfiguration().getStringList("messages." + messages + ".message"));
        }
    }

    @Override
    public void run() {
        if (globalMessages.isEmpty() || !enabled) return;
        delay--;

        if (delay <= 1) {
            for (final String line : globalMessages.get(current)) {
                Bukkit.broadcast(MiniMessage.miniMessage().deserialize(line));
            }

            if (globalMessages.size() <= current + 1) {
                current = 0;
            } else {
                current += 1;
            }

            delay = Lunar.getInstance().getMessages().getConfiguration().getInt("delay-between");
        }
    }
}
