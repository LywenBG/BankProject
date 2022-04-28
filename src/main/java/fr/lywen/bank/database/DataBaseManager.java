package fr.lywen.bank.database;

import fr.lywen.bank.Main;
import fr.lywen.bank.database.mongo.MongoConnection;
import fr.lywen.bank.database.mongo.TransactionData;
import org.bukkit.entity.Player;

public class DataBaseManager {

    private Main instance;
    private MongoConnection mongoConnection;
    private PlayerData playerDataManager;
    private TransactionData transactionData;



    public DataBaseManager(Main instance){
        this.instance = instance;
    }

    public void onEnable()
    {
        this.mongoConnection = new MongoConnection(this);
        this.mongoConnection.init();

        this.playerDataManager = new PlayerData(this);
        this.transactionData = new TransactionData(this);
    }

    public Main getInstance() {
        return instance;
    }

    public PlayerData getPlayerDataManager() {
        return playerDataManager;
    }

    public TransactionData getTransactionData() {
        return transactionData;
    }

    public MongoConnection getMongoConnection() {
        return mongoConnection;
    }
}
