package fr.lywen.bank.commands;

import fr.lywen.bank.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BankCommand implements CommandExecutor {

    private Main instance;

    public BankCommand(Main instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){

            sender.sendMessage("La banque possède  : " + instance.getBankManager().getBank().getCoins() + " euros !");

        }

        return false;

    }
}
