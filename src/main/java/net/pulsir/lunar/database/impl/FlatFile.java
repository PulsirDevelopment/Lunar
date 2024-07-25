package net.pulsir.lunar.database.impl;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.inventories.InventoryPlayer;
import net.pulsir.lunar.note.Note;
import net.pulsir.lunar.utils.serializer.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.*;

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
        Lunar.getInstance().getNoteStorage().getExpiryMap().put(uuid, Lunar.getInstance().getNoteStorage().resetTime());
        Bukkit.getScheduler().runTaskAsynchronously(Lunar.getInstance(), () -> {
            if (Lunar.getInstance().getNotes().getConfiguration().getConfigurationSection("player") == null) return;
            for (final String players : Objects.requireNonNull(Lunar.getInstance().getNotes().getConfiguration().getConfigurationSection("player")).getKeys(false)) {
                if (Lunar.getInstance().getNotes().getConfiguration().getConfigurationSection("player." + players) == null) return;
                for (final String notes : Objects.requireNonNull(Lunar.getInstance().getNotes().getConfiguration().getConfigurationSection("player." + players + ".notes")).getKeys(false)) {
                    Note note = new Note(UUID.fromString(Objects.requireNonNull(Lunar.getInstance().getNotes().getConfiguration().getString("player." + players + ".notes." + notes + ".id"))),
                            UUID.fromString(Objects.requireNonNull(Lunar.getInstance().getNotes().getConfiguration().getString("player." + players + ".notes." + notes + ".uuid"))),
                            UUID.fromString(Objects.requireNonNull(Lunar.getInstance().getNotes().getConfiguration().getString("player." + players + ".notes." + notes + ".staffUUID"))),
                            new Date(Lunar.getInstance().getNotes().getConfiguration().getLong("player." + players + ".notes." + notes + ".createdAt")),
                            Lunar.getInstance().getNotes().getConfiguration().getString("player." + players + ".notes." + notes + ".note"));

                    Lunar.getInstance().getNoteStorage().addFetchedUser(note);
                }
            }
            Bukkit.broadcast(MiniMessage.miniMessage().deserialize("<green>fetched notes"));
        });
    }

    @Override
    public void saveNotes() {
        if (Lunar.getInstance().getData().getPlayerNotes().isEmpty()) return;
        for (final UUID uuid : Lunar.getInstance().getData().getPlayerNotes().keySet()) {
            List<Note> notes = Lunar.getInstance().getData().getPlayerNotes().get(uuid);

            for (final Note note : notes) {
                Lunar.getInstance().getNotes().getConfiguration()
                                .set("player." + uuid.toString() + ".notes." + note.getNoteID().toString() + ".id", note.getNoteID().toString());
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid + ".notes." + note.getNoteID().toString() + ".uuid", note.getUuid().toString());
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid + ".notes." + note.getNoteID().toString() + ".staffUUID", note.getStaffUUID().toString());
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid + ".notes." + note.getNoteID().toString() + ".note", note.getNote());
                Lunar.getInstance().getNotes().getConfiguration()
                        .set("player." + uuid + ".notes." + note.getNoteID().toString() + ".createdAt", note.getCreatedAt().getTime());

                Lunar.getInstance().getNotes().save();
            }
        }
    }
}
