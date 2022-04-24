package fr.lywen.bank.commands;

import fr.lywen.bank.Main;
import fr.lywen.bank.bank.Bank;
import fr.lywen.bank.player.BankPlayer;
import fr.lywen.bank.player.PlayerManager;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DonCommand implements CommandExecutor {

    private Main instance;

    public DonCommand(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)  {
        if (sender instanceof Player) {



            Player player = (Player) sender;
            BankPlayer bankPlayer = instance.getPlayerManager().getBankPlayer(player.getUniqueId());
            if(!bankPlayer.getTransaction()) {
                player.sendMessage("Votre compte est bloqué, vous ne pouvez pas envoyer d'argent");
                return true;
            }

            if (args.length >= 2) {
                if (args[0].equals("bank")) {

                    try {
                        int coins = Integer.parseInt(args[1]);

                        if (coins >= bankPlayer.getCoins()) { player.sendMessage("Vous ne possédez pas assez d'argents"); return true;}

                        if (coins < 0) { player.sendMessage("Vous ne pouvez pas envoyer en dessous de 0 euros"); return true;}

                            Bank bank = instance.getBankManager().getBank();
                            bank.addCoins(coins);

                            bankPlayer.removeCoins(coins);
                            player.sendMessage("Vous avez envoyé " + coins + " euros à la banque ! Merci :)");



                }catch(NumberFormatException e){
                    System.out.printf(e.getMessage());
                    player.sendMessage("Mauvais format : /don [destinataire] [montant]");
                }


            }else {
                    Player receiver = Bukkit.getPlayerExact(args[0]);

                    try {
                        int coins = Integer.parseInt(args[1]);


                        if (coins >= bankPlayer.getCoins()) { player.sendMessage("Vous ne possédez pas assez d'argents"); return true;}

                        if (coins < 0) { player.sendMessage("Vous ne pouvez pas envoyer en dessous de 0 euros"); return true;}


                        if(receiver ==null){

                            Document receiverDocument = instance.getDataBaseManager().getPlayerDataManager().getDocument(args[0]);
                            if(receiverDocument == null) {player.sendMessage("Ce Joueur n'existe pas"); return true; }
                            if(!receiverDocument.getBoolean("transaction")){ player.sendMessage("Ce joueur ne peut pas recevoir de l'argent, son compte est bloqué"); return true;}

                            receiverDocument.append("coins", receiverDocument.getInteger("coins")+coins);

                        } else{
                            BankPlayer bankPlayerReceiver = instance.getPlayerManager().getBankPlayer(receiver.getUniqueId());

                            if(!bankPlayerReceiver.getTransaction()) { player.sendMessage("Ce joueur ne peut pas recevoir de l'argent, son compte est bloqué"); return true;}

                            bankPlayerReceiver.addCoins(coins);
                            receiver.sendMessage("Vous venez de recevoir " + coins + " euros de la part de " + player.getName() + ". Dites lui merci !");
                        }

                        bankPlayer.removeCoins(coins);
                        player.sendMessage("Vous avez envoyé " + coins + " euros à " + receiver.getName() + " Il sera très content !");


                    } catch (NumberFormatException e) {
                        System.out.printf(e.getMessage());
                        player.sendMessage("Mauvais format : /don [destinataire] [montant]");
                    }

                }
        } else {
            player.sendMessage("Mauvais format : /don [destinataire] [montant]");
        }
    }



        return false;
}
}
