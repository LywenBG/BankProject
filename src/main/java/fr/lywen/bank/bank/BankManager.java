package fr.lywen.bank.bank;

import fr.lywen.bank.Main;

public class BankManager {

    private Main instance;
    private Bank bank;

    public BankManager(Main instance) {
        this.instance = instance;
        this.bank = new Bank(instance);
    }

    public Bank getBank() {
        return bank;
    }
}
