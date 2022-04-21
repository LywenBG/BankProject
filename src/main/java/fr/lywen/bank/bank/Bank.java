package fr.lywen.bank.bank;

import fr.lywen.bank.Main;

public class Bank {

    private Main instance;
    private int coins;

    public Bank(Main instance) {
        this.instance = instance;
        this.coins = 5000;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void addCoins(int coins){
        this.coins+=coins;
    }

    public void removeCoins(int coins){
        this.coins-=coins;
    }

}
