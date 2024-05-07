package net.pulsir.lunar.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import net.pulsir.lunar.Lunar;
import org.bson.Document;

import java.util.Objects;

@Getter
public class MongoHandler {

    private final MongoCollection<Document> inventories;

    public MongoHandler(){

        MongoClient mongoClient = MongoClients.create(
                new ConnectionString(Objects.requireNonNull(Lunar.getInstance().getConfiguration().getConfiguration().getString("mongo-uri")))
        );
        MongoDatabase mongoDatabase = mongoClient.getDatabase("lunar");

        this.inventories = mongoDatabase.getCollection("inventories");
    }
}
