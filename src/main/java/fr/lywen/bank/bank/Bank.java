package fr.lywen.bank.bank;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.lywen.bank.Main;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.lang.management.BufferPoolMXBean;


public class Bank {

    private Main instance;
    private MongoDatabase mongoDatabase;
    private MongoCollection mongoCollection;


    public Bank(Main instance) {
        this.instance = instance;
        this.mongoDatabase = instance.getDataBaseManager().getMongoConnection().getMongoDatabase();
        this.mongoCollection = mongoDatabase.getCollection("BankCollection");
    }

    public void addCoins(int coins){
        Document document =  mongoDatabase.getCollection("BankCollection").find(new Document("name","bank")).first();
        int money = document.getInteger("coins")+coins;

        document.append("coins", money);

        this.mongoCollection.updateOne(new Document("name", "bank"), new Document("$set", document));


    }

    public int getCoins(){
        return mongoDatabase.getCollection("BankCollection").find(new Document("name","bank")).first().getInteger("coins");
    }





}
