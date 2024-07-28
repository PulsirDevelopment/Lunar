package net.pulsir.lunar.database.impl;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.mongo.MongoHandler;
import net.pulsir.lunar.note.Note;
import net.pulsir.lunar.utils.wrapper.impl.InventoryWrapper;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Mongo implements IDatabase {

    private final MongoHandler mongoHandler = new MongoHandler();

    @Override
    public void saveInventory() {
        Lunar.getInstance().getInventoryPlayerManager().getInventories().values().forEach(inventory ->
                mongoHandler.getInventories().replaceOne(Filters.eq("uuid", inventory.getUuid().toString()),
                new InventoryWrapper().wrap(inventory), new ReplaceOptions().upsert(true)));
    }

    @Override
    public void loadInventory(UUID uuid) {
        Document document = mongoHandler.getInventories().find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null) {
            Lunar.getInstance().getInventoryPlayerManager().getInventories()
                    .put(uuid, new InventoryWrapper().unwrap(document));
        }
    }
}
