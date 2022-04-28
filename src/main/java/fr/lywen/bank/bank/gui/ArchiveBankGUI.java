package fr.lywen.bank.bank.gui;

import com.mongodb.client.MongoCollection;
import fr.lywen.bank.Main;
import fr.lywen.bank.utils.inventories.AbstractGui;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class ArchiveBankGUI extends AbstractGui {
    private Main instance;
    private Inventory inventory;
    private ArrayList<Document> receiverDocument;
    private ArrayList<Document> giverDocument;
    private ArrayList<Document> allDocument;
    private MongoCollection moneyCollection;
    private int pageIndex;
    private int pageNumber;


    public ArchiveBankGUI(Main instance, int pageIndex) {
        this.instance = instance;
        this.pageIndex = pageIndex;

    }

    @Override
    public void display(Player player) {
        moneyCollection = instance.getDataBaseManager().getMongoConnection().getMongoDatabase().getCollection("MoneyCollection");
        allDocument = new ArrayList<>();
        receiverDocument = instance.getDataBaseManager().getPlayerDataManager().getArrayListDocumentReceiver(player.getUniqueId());
        giverDocument = instance.getDataBaseManager().getPlayerDataManager().getArrayListDocumentGiver(player.getUniqueId());
        allDocument.addAll(receiverDocument);
        allDocument.addAll(giverDocument);

        this.inventory = Bukkit.createInventory(null, 54, "Archive" + pageIndex);
        this.update(player);
        player.openInventory(this.inventory);
    }

    @Override
    public void update(Player player) {
        int elementsSize = allDocument.size();
        int[] baseSlots = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        pageNumber = elementsSize % baseSlots.length == 0 ? elementsSize / baseSlots.length : elementsSize / baseSlots.length + 1;

        int limite = pageNumber == pageIndex && elementsSize % baseSlots.length != 0 ? elementsSize % baseSlots.length : baseSlots.length;

        this.inventory.clear();


        for (int i = 0; i < limite; i++) {
            int slot = i + (pageIndex - 1) * baseSlots.length;
            Document document = allDocument.get(slot);
            String[] words = toString(document).split(" ");

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("Transaction n°" + slot);
            meta.setLore(Arrays.asList("Donneur : " + words[0], "Receveur : " + words[1], "Montant : " + words[2] + "€", "Date : " + words[4]));
            item.setItemMeta(meta);

            this.setSlotData(inventory, item, baseSlots[i], "");

        }


        if (pageIndex != 1) {
            ItemStack arrowLeft = new ItemStack(Material.ARROW);
            ItemMeta arrowLeftMeta = arrowLeft.getItemMeta();
            arrowLeftMeta.setDisplayName("Page précédante");
            arrowLeft.setItemMeta(arrowLeftMeta);

            this.setSlotData(inventory, arrowLeft, 53 - 8, "Left");
        }


        if (pageIndex != pageNumber) {
            ItemStack arrowRight = new ItemStack(Material.ARROW);
            ItemMeta arrowRightMeta = arrowRight.getItemMeta();
            arrowRightMeta.setDisplayName("Page suivante");
            arrowRight.setItemMeta(arrowRightMeta);

            this.setSlotData(inventory, arrowRight, 53, "Right");
        }


    }

    @Override
    public void onClick(Player player, ItemStack stack, String action, ClickType clickType, int slot) {
        if (action.startsWith("Right")) {
            pageIndex++;
        }

        if (action.startsWith("Left")) {
            pageIndex--;
        }
        if (action.startsWith("Left") || action.startsWith("Right")) {
            instance.getGuiManager().openGui(player, new ArchiveBankGUI(instance, pageIndex));
        }

    }

    public String toString(Document document) {
        String giver = getName(document.getString("giver"));
        String receiver = Objects.equals(document.getString("receiver"), "bank") ? "bank" : getName(document.getString("receiver"));
        String amount = document.getInteger("amount").toString();
        String date = document.getString("date");

        return giver + " " + receiver + " " + amount + " " + date;
    }

    public String getName(String uuid) {
        Document documentName = (Document) moneyCollection.find(new Document("uuid", uuid)).first();

        return documentName.getString("name");
    }

}
