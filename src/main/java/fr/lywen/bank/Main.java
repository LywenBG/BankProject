package fr.lywen.bank;

import fr.lywen.bank.bank.BankManager;
import fr.lywen.bank.commands.DonCommand;
import fr.lywen.bank.database.DataBaseManager;
import fr.lywen.bank.listeners.PlayerListener;
import fr.lywen.bank.player.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private Main instance;
    private PlayerManager playerManager;
    private BankManager bankManager;
    private DataBaseManager dataBaseManager;

    @Override
    public void onLoad() {
        instance = this;
    }
    @Override
    public void onEnable() {
        instance = this;

        dataBaseManager = new DataBaseManager(this);
        dataBaseManager.onEnable();
        playerManager = new PlayerManager(this);
        bankManager = new BankManager(this);


        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(instance), instance);
        getCommand("don").setExecutor(new DonCommand(this));

        this.dataBaseManager.getPlayerDataManager().test();

        super.onEnable();

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public BankManager getBankManager() {
        return bankManager;
    }
}
