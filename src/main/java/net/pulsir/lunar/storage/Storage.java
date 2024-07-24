package net.pulsir.lunar.storage;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface Storage<K> {
    int resetTime();
    Runnable storageTask();
    void fetchUser(UUID uuid);
    void addFetchedUser(K k);
}
