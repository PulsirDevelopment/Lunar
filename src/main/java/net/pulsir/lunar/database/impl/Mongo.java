package net.pulsir.lunar.database.impl;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import net.pulsir.lunar.Lunar;
import net.pulsir.lunar.database.IDatabase;
import net.pulsir.lunar.maintenance.Maintenance;
import net.pulsir.lunar.mongo.MongoHandler;
import net.pulsir.lunar.utils.wrapper.impl.InventoryWrapper;
import org.bson.Document;

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
        this.mongoHandler.getMaintenances().insertOne(maintenanceDocument);
    }

    public void updateMaintenance(Maintenance maintenance) {
        this.removeMaintenance(maintenance);
        this.saveMaintenance(maintenance);
    }

    public void removeMaintenance(Maintenance maintenance) {
        this.mongoHandler.getMaintenances().findOneAndDelete(new Document("name", maintenance.getName()));
    }

    public void loadMaintenances() {
        for (MongoCursor<Document> cursor = this.mongoHandler.getMaintenances().find().cursor(); cursor.hasNext(); ) {
            Document document = cursor.next();
            String name = document.getString("name");
            String reason = document.getString("reason");
            int duration = document.getInteger("duration");
            Date endDate = document.getLong("endDate") == -1 ? null : new Date(document.getLong("endDate"));

            Lunar.getInstance().getServerMaintenanceManager().getMaintenances()
                    .add(new Maintenance(name, reason, duration, endDate));
            if (!cursor.hasNext()) cursor.close();
        }
    }
}
