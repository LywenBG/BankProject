package fr.lywen.bank.database;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.lywen.bank.player.BankPlayer;
import fr.lywen.bank.role.Role;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;


public class PlayerData {
    private DataBaseManager dataBaseManager;
    private MongoDatabase mongoDatabase;
    private MongoCollection mongoCollection;

    public PlayerData(DataBaseManager dataBaseManager){
        this.dataBaseManager = dataBaseManager;
        this.mongoDatabase = dataBaseManager.getMongoConnection().getMongoDatabase();
        this.mongoCollection = this.mongoDatabase.getCollection("MoneyCollection");
    }

    public Document toDocument(BankPlayer bankPlayer){
        return new Document("uuid",bankPlayer.getUuid().toString())
                .append("name", bankPlayer.getName())
                .append("coins", bankPlayer.getCoins())
                .append("role", bankPlayer.getRole().getId())
                .append("transaction", bankPlayer.getTransaction());

    }

    public void savePlayer(BankPlayer bankPlayer)
    {
        if(!this.hasAccount(bankPlayer))return;
        Document document = this.toDocument(bankPlayer);
        this.mongoCollection.updateOne(new Document("uuid", bankPlayer.getUuid().toString()), new Document("$set", document));

    }

    public boolean hasAccount(BankPlayer bankPlayer){
        Document document = this.getDocument(bankPlayer);
        return document != null;
    }

    public void createAccount(BankPlayer bankPlayer)
    {
        if(this.hasAccount(bankPlayer))return;
        mongoDatabase.getCollection("MoneyCollection").insertOne(toDocument(bankPlayer));
    }

    public Document getDocument(BankPlayer player)
    {
        return mongoDatabase.getCollection("MoneyCollection").find(new Document("uuid", player.getUuid().toString())).first();
    }

    public Document getDocument(String string){
        return mongoDatabase.getCollection("MoneyCollection").find(new Document("name", string)).first();
    }

    public ArrayList<Document> getArrayListDocumentReceiver(UUID uuid)
    {
        ArrayList<Document> documents = new ArrayList<>();


        for(Document docs : mongoDatabase.getCollection("TransfertCollection").find(new Document("receiver", uuid.toString())))
        {
            documents.add(docs);
        }


        return documents;
    }

    public ArrayList<Document> getArrayListDocumentGiver(UUID uuid)
    {
        ArrayList<Document> documents = new ArrayList<>();


        for(Document docs : mongoDatabase.getCollection("TransfertCollection").find(new Document("giver", uuid.toString())))
        {
            documents.add(docs);
        }


        return documents;
    }

    public void loadAccount(Player player){
        BankPlayer bankPlayer = new BankPlayer(player.getUniqueId(), Role.BASIC, player.getName());

        if(hasAccount(bankPlayer)){
            Document document = getDocument(bankPlayer);

            bankPlayer.setName(document.getString("name"));
            bankPlayer.setCoins(document.getInteger("coins"));
            bankPlayer.setRole(Role.getRole(document.getInteger("role")));
            bankPlayer.setTransaction(document.getBoolean("transaction"));


        }else{
            createAccount(bankPlayer);
        }

        dataBaseManager.getInstance().getPlayerManager().addBankPlayer(bankPlayer);

    }


}
