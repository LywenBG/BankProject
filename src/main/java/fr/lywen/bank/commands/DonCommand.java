package fr.lywen.bank.commands;

import fr.lywen.bank.Main;
import fr.lywen.bank.bank.Bank;
import fr.lywen.bank.player.BankPlayer;
import fr.lywen.bank.player.PlayerManager;
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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){

            Player player = (Player) sender;
            BankPlayer bankPlayer = instance.getPlayerManager().getBankPlayer(player.getUniqueId());

            if(args.length >= 2){
               if(args[0].equals("bank")){
                   Bank bank = instance.getBankManager().getBank();

                   try{
                        int coins = Integer.parseInt(args[1]);

                        if(coins <= bankPlayer.getCoins()){
                        }



                   }catch (NumberFormatException e){

                   }

               }

               int coins = 10;
                if(coins < 0){
                    sender.sendMessage("Vous ne pouvez pas envoyer un montant en dessous de 0 !");
                } else if(coins > 10)
                {

                }


            }
        }



        return false;
    }
}
