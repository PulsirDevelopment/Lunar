package net.pulsir.lunar.filter;

import lombok.Getter;
import net.pulsir.lunar.Lunar;

import java.util.HashSet;
import java.util.Set;

@Getter
public class Filter {

    private final Set<String> filterWords = new HashSet<>();

    public Filter() {
        filterWords.addAll(Lunar.getInstance().getConfiguration().getConfiguration().getStringList("filter-messages"));
    }
}
