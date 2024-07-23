package net.pulsir.lunar.note;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class Note {

    private UUID noteID, uuid, staffUUID;
    private Date createdAt;
    private String note;

    public Note(UUID noteID, UUID uuid, UUID staffUUID, Date createdAt, String note) {
        this.noteID = noteID;
        this.uuid = uuid;
        this.staffUUID = staffUUID;
        this.createdAt = createdAt;
        this.note = note;
    }
}
