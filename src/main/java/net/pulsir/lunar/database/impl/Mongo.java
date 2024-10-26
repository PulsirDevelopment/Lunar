package net.pulsir.lunar.database.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.maintenance.Maintenance;
import net.pulsir.lunar.mongo.MongoHandler;
import net.pulsir.lunar.offline.OfflinePlayerInventory;
import net.pulsir.lunar.utils.base64.Base64;
import net.pulsir.lunar.utils.wrapper.impl.InventoryWrapper;
import org.bson.Document;
import org.bukkit.inventory.Inventory;

import java.util.Date;
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

    public void saveMaintenance(Maintenance maintenance) {
        Document maintenanceDocument = new Document();
        maintenanceDocument.put("name", maintenance.getName());
        maintenanceDocument.put("reason", maintenance.getReason());
        maintenanceDocument.put("duration", maintenance.getDuration());
        maintenanceDocument.put("endDate", maintenance.getEndDate() != null ? maintenance.getEndDate().getTime() : -1);

        this.mongoHandler.getMaintenances().replaceOne(Filters.eq("name", maintenance.getName()),
                maintenanceDocument, new ReplaceOptions().upsert(true));
    }

    public void loadMaintenances() {
        FindIterable<Document> iterable = this.mongoHandler.getMaintenances().find();

        try (MongoCursor<Document> iterator = iterable.iterator()) {
            while (iterator.hasNext()) {
                Document document = iterator.next();

                String name = document.getString("name");
                String reason = document.getString("reason");
                int duration = document.getInteger("duration");
                Date endDate = document.getLong("endDate") == -1 ? null : new Date(document.getLong("endDate"));

                Lunar.getInstance().getServerMaintenanceManager().getMaintenances()
                        .add(new Maintenance(name, reason, duration, endDate));
            }
        }
    }

    @Override
    public void deleteMaintenance(String name) {

    }

    @Override
    public void saveOfflineInventory(UUID uuid, Inventory playerInventory, Inventory enderChestInventory) {
        Document document = new Document();
        document.put("uuid", uuid.toString());
        document.put("player", null);
        document.put("enderChest", null);

        this.mongoHandler.getOffline().replaceOne(Filters.eq("uuid", uuid.toString()),
                document, new ReplaceOptions().upsert(true));
    }

    @Override
    public void loadOfflineInventory(UUID uuid) {
        Document document = mongoHandler.getOffline().find(Filters.eq("uuid", uuid.toString())).first();
        if (document != null) {
            Lunar.getInstance().getOfflinePlayerManager().getOfflinePlayers()
                    .put(uuid, new OfflinePlayerInventory(Base64.toInventory(document.getString("player")),
                            Base64.toInventory(document.getString("enderChest"))));
        }
    }
}
