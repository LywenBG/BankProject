package fr.lywen.bank.player;

import fr.lywen.bank.Main;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerManager {

    private Main instance;
    private ArrayList<BankPlayer> bankPlayers;

    public PlayerManager(Main instance){
        this.instance = instance;
    }

    public void addBankPlayer(BankPlayer bankPlayer){
        if(!bankPlayers.contains(bankPlayer)){
            bankPlayers.add(bankPlayer);
        }
    }

    public void removeBankPlayer(BankPlayer bankPlayer){
        bankPlayers.remove(bankPlayer);
    }

    public BankPlayer getBankPlayer(UUID uuid){
        for(BankPlayer bankPlayer : bankPlayers){
            if(bankPlayer.getUuid().equals(uuid)){
                return bankPlayer;
            }
        }
        return null;
    }

    public ArrayList<BankPlayer> getBankPlayers(){
        return bankPlayers;
    }

}
