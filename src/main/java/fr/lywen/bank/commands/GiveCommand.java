package fr.lywen.bank.commands;

import fr.lywen.bank.Main;
import fr.lywen.bank.bank.Bank;
import fr.lywen.bank.player.BankPlayer;
import fr.lywen.bank.role.Role;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveCommand implements CommandExecutor {

    private Main instance;

    public GiveCommand(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            BankPlayer bankPlayer = instance.getPlayerManager().getBankPlayer(player.getUniqueId());

            if (bankPlayer.getRole().equals(Role.BASIC)) {
                player.sendMessage("Vous n'avez pas les droits pour cette commande");
                return true;
            }

            if (args.length >= 3) {
                if (args[1].equals("bank")) {

                    Document giverDocument = instance.getDataBaseManager().getPlayerDataManager().getDocument(args[0]);
                    if(!giverDocument.getBoolean("transaction")){ player.sendMessage("Ce joueur ne peut pas recevoir de l'argent, son compte est bloqué"); return true;}

                    if (giverDocument == null) {
                        player.sendMessage("Le donneur n'existe pas");
                        return true;
                    }

                    int giverMoney = giverDocument.getInteger("coins");
                    String giverName = giverDocument.getString("name");

                    try {
                        int coins = Integer.parseInt(args[2]);


                        if (coins >= giverMoney) {
                            player.sendMessage(giverName + " ne possède pas assez d'argents");
                            return true;
                        }

                        if (coins < 0) {
                            player.sendMessage("Vous ne pouvez pas envoyer en dessous de 0 euros");
                            return true;
                        }

                        Bank bank = instance.getBankManager().getBank();
                        bank.addCoins(coins);

                        giverDocument.append("coins", giverDocument.getInteger("coins") - coins);

                        player.sendMessage("Vous avez envoyé " + coins + " euros à la banque ! Merci :)");


                    } catch (NumberFormatException e) {
                        System.out.printf(e.getMessage());
                        player.sendMessage("Mauvais format : /don [destinataire] [montant]");
                    }


                } else {
                    Document giverDocument = instance.getDataBaseManager().getPlayerDataManager().getDocument(args[0]);
                    Document receiverDocument = instance.getDataBaseManager().getPlayerDataManager().getDocument(args[1]);

                    if(!giverDocument.getBoolean("transaction") || !receiverDocument.getBoolean("transaction")){
                        player.sendMessage("Les ou l'un des joueurs ne peut pas recevoir de l'argent, un des deux compte est bloqué");
                        return true;
                    }


                    if (giverDocument == null || receiverDocument == null) {
                        player.sendMessage("Le donneur ou le receveur n'existe pas");
                        return true;
                    }

                    int giverMoney = giverDocument.getInteger("coins");
                    int receiverMoney = receiverDocument.getInteger("coins");
                    String giverName = giverDocument.getString("name");
                    String receiverName = receiverDocument.getString("name");

                    try{
                        int coins = Integer.parseInt(args[2]);

                        if (coins >= giverMoney) {
                            player.sendMessage(giverName + " ne possède pas assez d'argents");
                            return true;
                        }

                        if (coins < 0) {
                            player.sendMessage("Vous ne pouvez pas envoyer en dessous de 0 euros");
                            return true;
                        }

                        giverDocument.append("coins", giverMoney-coins);
                        receiverDocument.append("coins", receiverMoney+coins);

                        player.sendMessage("Vous avez envoyé " + coins + " euros à " + receiverName + " depuis " + giverName);



                    }catch (NumberFormatException e){
                        System.out.printf(e.getMessage());
                        player.sendMessage("Mauvais format : /don [destinataire] [montant]");
                    }


                }


            } else {
                player.sendMessage("Mauvais format : /give [Source] [Destinaire] [Montant]");
            }

        }
        return false;
    }
}