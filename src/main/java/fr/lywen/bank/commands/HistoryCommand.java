package fr.lywen.bank.commands;

import com.mongodb.client.MongoCollection;
import fr.lywen.bank.Main;
import fr.lywen.bank.bank.gui.ArchiveBankGUI;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class HistoryCommand implements CommandExecutor {

    private Main instance;
    private MongoCollection mongoCollection;
    private MongoCollection moneyCollection;


    public HistoryCommand(Main instance){
        this.instance = instance;
        mongoCollection = instance.getDataBaseManager().getMongoConnection().getMongoDatabase()
                .getCollection("TransfertCollection");

        moneyCollection = instance.getDataBaseManager().getMongoConnection().getMongoDatabase()
                .getCollection("MoneyCollection");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        instance.getGuiManager().openGui(player, new ArchiveBankGUI(instance, 1));

      /*

       ArrayList<Document> history = instance.getDataBaseManager().getPlayerDataManager().getArrayListDocumentGiver(player.getUniqueId());



        if(history == null || history.isEmpty()) {
            player.sendMessage("Vous n'avez jamais fait de transaction :(");

            return true;
        }

        for(Document document : history){

            String giver = getName(document.getString("giver"));
            String receiver = Objects.equals(document.getString("receiver"), "bank") ? "bank" : getName(document.getString("receiver"));
            String amount = document.getInteger("amount").toString();


            player.sendMessage(giver + " -> " + receiver + " : " + amount);
        }




       */
        return false;
    }

    public String getName(String uuid){
        Document documentName = (Document) moneyCollection.find(new Document("uuid", uuid)).first();

        return documentName.getString("name");
    }

}
