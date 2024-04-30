package net.pulsir.lunar.utils.bungee.message;

public enum ChannelType {

    STAFF("Staff"), ADMIN("Admin"), OWNER("Owner");

    final String string;

    ChannelType(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }
}
