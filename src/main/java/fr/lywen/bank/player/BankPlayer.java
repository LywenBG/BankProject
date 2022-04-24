package fr.lywen.bank.player;

import fr.lywen.bank.role.Role;

import java.util.UUID;

public class BankPlayer {

    private UUID uuid;
    private Role role;
    private String name;
    private int coins;

    private boolean transaction;

    public BankPlayer(UUID uuid, Role role, String name) {
        this.uuid = uuid;
        this.role = role;
        this.name = name;
        this.coins = 1000;
        transaction = true;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Role getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public boolean getTransaction() {
        return transaction;
    }

    public void addCoins(int coins)
    {
        this.coins += coins;
    }

    public void removeCoins(int coins)
    {
        this.coins -= coins;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTransaction(boolean transaction){
        this.transaction = transaction;
    }


}
