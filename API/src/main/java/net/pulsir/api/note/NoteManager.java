package net.pulsir.api.note;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface NoteManager {

    void createNote(UUID playerUUID, UUID staffUUID, String note);
    void deleteNote(UUID playerUUID, String note);
    boolean noteExists(UUID playerUUID, String note);
    void listNotes(UUID playerUUID, Player staff, int queries);
}
