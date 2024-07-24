package net.pulsir.lunar.storage.impl;

import lombok.Getter;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.note.Note;
import net.pulsir.lunar.storage.Storage;

import java.util.*;

public class NoteStorage implements Storage<Note> {

    private final Map<UUID, Integer> expiryMap = new HashMap<>();
    @Getter private final Map<UUID, List<Note>> cacheMap = new HashMap<>();

    @Override
    public int resetTime() {
        return 60 * 10;
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
                        }
                    }
                }
            }
        };
    }

    @Override
    public void fetchUser(UUID uuid) {
        Lunar.getInstance().getDatabase().fetchAsynchronously(uuid);
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

        List<Note> cacheNotes = cacheMap.get(uuid);
        Set<Note> notes = Lunar.getInstance().getData().getPlayerNotes().get(uuid);

        allNotes.addAll(cacheNotes);
        allNotes.addAll(notes);

        allNotes.sort(Comparator.comparing(Note::getCreatedAt));

        return allNotes;
    }
}
