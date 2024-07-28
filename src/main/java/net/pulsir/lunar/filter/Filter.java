package net.pulsir.lunar.filter;

import lombok.Getter;
import net.pulsir.lunar.Lunar;

import java.util.*;

@Getter
public class Filter {

    private final Map<String, String> filterWords = new HashMap<>();

    public Filter() {
        if (Lunar.getInstance().getConfiguration().getConfiguration().getConfigurationSection("filter-messages") == null) return;
        for (final String filtered : Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getConfigurationSection("filter-messages")).getKeys(false)) {
            String message = Lunar.getInstance().getConfiguration().getConfiguration().getString("filter-messages." + filtered + ".message");
            String command = Lunar.getInstance().getConfiguration().getConfiguration().getString("filter-messages." + filtered + ".command");

            filterWords.putIfAbsent(message, command);
        }
    }
}
