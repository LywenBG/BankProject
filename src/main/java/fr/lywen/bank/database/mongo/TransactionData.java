package fr.lywen.bank.database.mongo;

import com.mongodb.client.MongoCollection;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import fr.lywen.bank.database.DataBaseManager;
import org.bson.Document;

public class TransactionData {

    private DataBaseManager dataBaseManager;

    public TransactionData(DataBaseManager dataBaseManager){
        this.dataBaseManager = dataBaseManager;
    }

    public void sendTransaction(String giver, String receiver, int amount)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        dateFormat.format(date);


        MongoCollection mongoCollection = dataBaseManager.getMongoConnection().getMongoDatabase()
                .getCollection("TransfertCollection");

        Document document = new Document("giver", giver)
                .append("receiver", receiver)
                .append("amount", amount)
                .append("date", date.toString());


        mongoCollection.insertOne(document);
    }


}
