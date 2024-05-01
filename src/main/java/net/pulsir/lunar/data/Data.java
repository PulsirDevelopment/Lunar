package net.pulsir.lunar.data;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Data {

    private final Set<UUID> staffChat = new HashSet<>();
    private final Set<UUID> adminChat = new HashSet<>();
    private final Set<UUID> ownerChat = new HashSet<>();

    private final Set<UUID> staffMembers = new HashSet<>();
    private final Set<UUID> adminMembers = new HashSet<>();
    private final Set<UUID> ownerMembers = new HashSet<>();

    private final Set<UUID> staffMode = new HashSet<>();
    private final Set<UUID> vanish = new HashSet<>();
}
