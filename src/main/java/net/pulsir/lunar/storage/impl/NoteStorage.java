package net.pulsir.lunar.storage.impl;

import lombok.Getter;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.note.Note;
import net.pulsir.lunar.storage.Storage;
import org.bukkit.Bukkit;

import java.util.*;

public class NoteStorage implements Storage<Note> {

    @Getter private final Map<UUID, Integer> expiryMap = new HashMap<>();
    @Getter private final Map<UUID, List<Note>> cacheMap = new HashMap<>();

    @Override
    public int resetTime() {
        return 60 * 5;
    }

    @Override
    public Runnable storageTask() {
        return () -> {
            if (!expiryMap.isEmpty()) {
                for (final UUID uuid : expiryMap.keySet()) {
                    if (expiryMap.get(uuid) != null) {
                        expiryMap.replace(uuid, expiryMap.get(uuid), expiryMap.get(uuid) - 1);

                        if (expiryMap.get(uuid) <= 1) {
                            expiryMap.remove(uuid);
                            cacheMap.remove(uuid);

                            Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<red>Removed from cache"));
                        }
                    }
                }
            }
        };
    }

    @Override
    public void fetchUser(UUID uuid) {
        Lunar.getInstance().getDatabase().fetchNotesAsynchronously(uuid);
    }

    @Override
    public void addFetchedUser(Note note) {
        if (cacheMap.get(note.getUuid()) != null) {
            cacheMap.get(note.getUuid()).add(note);
        } else {
            cacheMap.put(note.getUuid(), new ArrayList<>(List.of(note)));
        }
    }

    public List<Note> getAllNotes(UUID uuid) {
        List<Note> allNotes = new ArrayList<>();

        if (cacheMap.get(uuid) == null && expiryMap.get(uuid) == null) {
            fetchUser(uuid);

            Bukkit.getConsoleSender().sendMessage(cacheMap.toString());
            Bukkit.getConsoleSender().sendMessage(expiryMap.toString());
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<green>fetchedddd"));
        } else {
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<green>No need to fetch"));
        }

        List<Note> notes = Lunar.getInstance().getData().getPlayerNotes().get(uuid);

        if (!cacheMap.isEmpty()) {
            allNotes.addAll(cacheMap.get(uuid));
        }
        if (notes != null && !notes.isEmpty()) {
            allNotes.addAll(notes);
        }

        allNotes.sort(Comparator.comparing(Note::getCreatedAt));

        return allNotes;
    }
}
