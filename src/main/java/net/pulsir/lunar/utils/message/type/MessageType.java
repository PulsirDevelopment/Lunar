package net.pulsir.lunar.utils.message.type;

import lombok.Getter;

@Getter
public enum MessageType {

    LEGACY("Legacy"), COMPONENT("Component");

    final String name;

    MessageType(String name) {
        this.name = name;
    }
}
