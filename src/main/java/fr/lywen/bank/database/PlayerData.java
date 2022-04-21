package fr.lywen.bank.database;

import com.mongodb.MongoException;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import fr.lywen.bank.bank.Bank;
import fr.lywen.bank.player.BankPlayer;
import org.bson.Document;

import java.util.UUID;


public class PlayerData {
    private DataBaseManager dataBaseManager;
    private MongoDatabase mongoDatabase;

    public PlayerData(DataBaseManager dataBaseManager){
        this.dataBaseManager = dataBaseManager;
        this.mongoDatabase = dataBaseManager.getMongoConnection().getMongoDatabase();
    }

    public Document toDocument(BankPlayer bankPlayer){
        return new Document("uuid",bankPlayer.getUuid().toString())
                .append("coins", bankPlayer.getCoins());
    }

    public boolean hasAccount(BankPlayer bankPlayer){
        Document document = (Document) mongoDatabase.getCollection("MoneyCollection").find(new Document("uuid", bankPlayer.getUuid().toString()));
        return document != null;
    }

    public void createAccount(BankPlayer bankPlayer){
        mongoDatabase.getCollection("MoneyCollection").insertOne(toDocument(bankPlayer));
    }

    public Document getDocument(BankPlayer player)
    {
        return (Document) mongoDatabase.getCollection("MoneyCollection").find(new Document("uuid", player.getUuid().toString()));
    }

    public int getPlayerMoney(BankPlayer bankPlayer)
    {
        Document document = getDocument(bankPlayer);
        if(document != null)
        {
            return document.getInteger("coins");
        }

        return 0;
    }


    public void setPlayerMoney(BankPlayer bankPlayer){
      // mongoDatabase.getCollection("MoneyCollection").updateOne(new Document().append("coins", getPlayerMoney(bankPlayer)));



    }


    public void test()
    {
        Document document = new Document("test", "test");
        document.append("coins", 25);
        document.append("coins", 11);

        mongoDatabase.getCollection("MoneyCollection").insertOne(document);
    }
}
