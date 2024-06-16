package net.pulsir.lunar.utils.bungee.message;

import lombok.Getter;

@Getter
public enum ChannelType {

    STAFF("Staff"), ADMIN("Admin"), OWNER("Owner"), GLOBAL("Global"), FREEZE("Freeze"), UNFREEZE("UnFreeze");

    final String string;

    ChannelType(String string) {
        this.string = string;
    }
}
