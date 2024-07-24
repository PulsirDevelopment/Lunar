package net.pulsir.lunar.manager;

import net.pulsir.api.note.NoteManager;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.note.Note;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class NotesManagerImpl implements NoteManager {

    @Override
    public void createNote(UUID playerUUID, UUID staffUUID, String note) {
        Note playerNote = new Note(UUID.randomUUID(), playerUUID, staffUUID, new Date(), note);

        if (Lunar.getInstance().getData().getPlayerNotes().get(playerUUID) == null) {
            Lunar.getInstance().getData().getPlayerNotes().put(playerUUID, new HashSet<>(List.of(playerNote)));
        } else {
            Lunar.getInstance().getData().getPlayerNotes().get(playerUUID).add(playerNote);
        }
    }

    @Override
    public void deleteNote(UUID playerUUID, String note) {
        Note playerNote = null;

        for (final Note notes : Lunar.getInstance().getData().getPlayerNotes().get(playerUUID)) {
            if (notes.getNote().equalsIgnoreCase(note)) {
                playerNote = notes;
                break;
            }
        }

        Lunar.getInstance().getData().getPlayerNotes().get(playerUUID).remove(playerNote);
    }

    @Override
    public boolean noteExists(UUID playerUUID, String note) {
        if (Lunar.getInstance().getData().getPlayerNotes().isEmpty()) return false;
        if (Lunar.getInstance().getData().getPlayerNotes().get(playerUUID) == null
        || Lunar.getInstance().getData().getPlayerNotes().get(playerUUID).isEmpty()) return false;

        for (final Note notes : Lunar.getInstance().getData().getPlayerNotes().get(playerUUID)) {
            if (notes.getNote().equalsIgnoreCase(note)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void listNotes(UUID playerUUID, Player staff, int queries) {
        String playerName = Bukkit.getOfflinePlayer(playerUUID).getName();
        staff.sendMessage("Notes history of " + playerName);

        List<Note> playerNotes = Lunar.getInstance().getNoteStorage().getAllNotes(playerUUID);

        AtomicInteger currentQuery = new AtomicInteger(0);
        Bukkit.getScheduler().runTaskLater(Lunar.getInstance(), () -> {
            for (final Note notes : playerNotes) {
                staff.sendMessage(Lunar.getInstance().getMessage().getMessage(Objects.requireNonNull(Lunar.getInstance()
                                .getLanguage().getConfiguration().getString("NOTES.CHECK"))
                        .replace("{player}", Objects.requireNonNull(playerName))
                        .replace("{staff}", Objects.requireNonNull(Bukkit.getOfflinePlayer(notes.getStaffUUID()).getName()))
                        .replace("{date}", notes.getCreatedAt().toString())
                        .replace("{note}", notes.getNote())));
                currentQuery.incrementAndGet();

                if (currentQuery.get() >= queries) break;
            }
        }, 5L);
    }
}
