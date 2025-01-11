package edu.badpals.proyectoad3.DAO;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import edu.badpals.proyectoad3.controller.Encriptado;
import org.bson.Document;

public class MongoDbDAO {
    private final MongoDatabase database;

    public MongoDbDAO() {
        String connectionString = "mongodb+srv://accesobd:259hpvMbH0VgemMK@cluster0.unvuy.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(connectionString)).serverApi(serverApi).build();

        MongoClient mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("EsportsDbLogin");
    }

    public boolean registerUser(String username, String password) {
        MongoCollection<Document> collection = database.getCollection("users");

        Document query = new Document("username", username);
        if (collection.find(query).first() != null) {
            return false;
        }
        String hashedPassword = Encriptado.encriptar(password);
        Document user = new Document("username", username).append("password", hashedPassword)
                .append("createdAt", System.currentTimeMillis());

        try {
            collection.insertOne(user);
            return true;
        } catch (MongoException e) {
            return false;
        }
    }

    public boolean loginUser(String username, String password) {
        MongoCollection<Document> collection = database.getCollection("users");

        try {
            Document query = new Document("username", username);
            Document user = collection.find(query).first();

            if (user != null) {
                String storedPassword = user.getString("password");
                if (Encriptado.verificar(password, storedPassword)) {
                    return true;
                }
            }
        } catch (MongoException e) {
            return false;
        }

        return false;
    }
}
