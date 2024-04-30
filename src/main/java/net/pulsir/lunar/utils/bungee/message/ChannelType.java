package net.pulsir.lunar.utils.bungee.message;

import lombok.Getter;

@Getter
public enum ChannelType {

    STAFF("Staff"), ADMIN("Admin"), OWNER("Owner");

    final String string;

    ChannelType(String string) {
        this.string = string;
    }

}
