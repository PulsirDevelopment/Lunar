package net.pulsir.lunar.database.impl;

import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.note.Note;
import net.pulsir.lunar.utils.serializer.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FlatFile implements IDatabase {

    @Override
    public void saveInventory() {
        Lunar.getInstance().getInventoryPlayerManager().getInventories().values().forEach(inventory -> {
            List<String> string = new ArrayList<>();
            for (final ItemStack itemStack : inventory.getInventory()) {
                string.add(ItemStackSerializer.serialize(itemStack));
            }

            Lunar.getInstance().getInventory().getConfiguration().set("inventory." + inventory.getUuid().toString() + ".uuid", inventory.getUuid().toString());
            Lunar.getInstance().getInventory().getConfiguration().set("inventory." + inventory.getUuid().toString() + ".inventory", inventory.getInventoryString());
            Lunar.getInstance().getInventory().save();
        });
    }

    @Override
    public void loadInventory(UUID uuid) {
        if (Lunar.getInstance().getInventory().getConfiguration().getConfigurationSection("inventory") == null) return;
        for (final String inventory : Objects.requireNonNull(Lunar.getInstance().getInventory().getConfiguration().getConfigurationSection("inventory")).getKeys(false)) {
            List<String> strings = Lunar.getInstance().getInventory().getConfiguration().getStringList("inventory." + inventory + ".inventory");
            ItemStack[] itemStacks = new ItemStack[strings.size()];

            for (int i = 0; i < strings.size(); i++) {
                itemStacks[i] = ItemStackSerializer.deSerialize(strings.get(i));
            }

            Lunar.getInstance().getInventoryPlayerManager().getInventories()
                    .put(uuid, new InventoryPlayer(uuid, itemStacks));
        }
    }

    @Override
    public void fetchNotesAsynchronously(UUID uuid) {

    }

    @Override
    public void saveNotes() {
        for (final UUID uuid : Lunar.getInstance().getData().getPlayerNotes().keySet()) {
            List<Note> notes = Lunar.getInstance().getData().getPlayerNotes().get(uuid);

            for (final Note note : notes) {
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid.toString() + "noteID", note.getNoteID().toString());
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid + "uuid", note.getUuid().toString());
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid + "staffUUID", note.getStaffUUID().toString());
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid + "note", note.getNote());
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid + "createdAt", note.getCreatedAt().getTime());

                Lunar.getInstance().getNotes().save();
            }
        }
    }
}
