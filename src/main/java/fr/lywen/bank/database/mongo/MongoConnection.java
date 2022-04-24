package fr.lywen.bank.database.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import fr.lywen.bank.database.DataBaseManager;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoConnection {

    private DataBaseManager dataBaseManager;
    private MongoClient mongoClient;

    public MongoConnection(DataBaseManager dataBaseManager){
        this.dataBaseManager = dataBaseManager;
    }

    public void init(){
        MongoClientURI mongoClientURI = new MongoClientURI("mongodb://ConnexionJava:h1KHadeHjgvnAHYG@bank-shard-00-00.qokyx.mongodb.net:27017,bank-shard-00-01.qokyx.mongodb.net:27017,bank-shard-00-02.qokyx.mongodb.net:27017/Bank?ssl=true&replicaSet=atlas-y256lp-shard-0&authSource=admin&retryWrites=true&w=majority");

        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE);

        this.mongoClient = new MongoClient(mongoClientURI);
        dataBaseManager.getInstance().getLogger().log(Level.FINE, "Connected");

    }

    public MongoClient getMongoClient(){
        return mongoClient;
    }

    public DataBaseManager getDataBaseManager() {
        return dataBaseManager;
    }

    public MongoDatabase getMongoDatabase(){
        return mongoClient.getDatabase("Bank");
    }
}
