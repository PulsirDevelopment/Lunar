package net.pulsir.lunar.database.impl;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.mongo.MongoHandler;
import net.pulsir.lunar.utils.wrapper.impl.InventoryWrapper;
import org.bson.Document;

import java.util.UUID;

public class Mongo implements IDatabase {

    private final MongoHandler mongoHandler = new MongoHandler();

    @Override
    public void saveInventory() {
        Lunar.getInstance().getInventoryManager().getInventories().values().forEach(inventory ->
                mongoHandler.getInventories().replaceOne(Filters.eq("uuid", inventory.getUuid().toString()),
                new Document(), new ReplaceOptions().upsert(true)));
    }

    @Override
    public void loadInventory(UUID uuid) {
        Document document = mongoHandler.getInventories().find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null) {
            Lunar.getInstance().getInventoryManager().getInventories()
                    .put(uuid, new InventoryWrapper().unwrap(document));
        }
    }
}
