package fr.lywen.bank.listeners;

import fr.lywen.bank.Main;
import fr.lywen.bank.player.BankPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    private Main instance;
    public PlayerListener(Main instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        BankPlayer bankPlayer = instance.getPlayerManager().getBankPlayer(player.getUniqueId());
        instance.getDataBaseManager().getPlayerDataManager().savePlayer(bankPlayer);
        instance.getPlayerManager().getBankPlayers().remove(bankPlayer);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent player){
        instance.getDataBaseManager().getPlayerDataManager().loadAccount(player.getPlayer());
    }

    @EventHandler
    public void onKillMob(EntityDeathEvent e){
        if(!(e.getEntity().getKiller() instanceof Player))return;

        BankPlayer bankPlayer = instance.getPlayerManager().getBankPlayer(e.getEntity().getKiller().getUniqueId());

        if (e.getEntity() instanceof Monster || e.getEntity() instanceof Wolf && ((Wolf) e).isAngry()) {

            bankPlayer.addCoins(instance.getHostileMobMoney());
        } else {

            bankPlayer.addCoins(instance.getPassiveMobMoney());
        }

    }

    @EventHandler
    public void onKillPlayer(PlayerDeathEvent e){

        if(e.getEntity().getKiller() instanceof Player){

            BankPlayer killed = instance.getPlayerManager().getBankPlayer(e.getEntity().getUniqueId());
            BankPlayer killer = instance.getPlayerManager().getBankPlayer(e.getEntity().getKiller().getUniqueId());

            int moneyLost = 10*killed.getCoins()/100 > 5000 ? 5000 : 10*killed.getCoins()/100;


            killed.removeCoins(moneyLost);
            killer.addCoins(moneyLost);
        } else{

            BankPlayer killed = instance.getPlayerManager().getBankPlayer(e.getEntity().getUniqueId());

            int moneyLost = 50*killed.getCoins()/100 > 5000 ? 5000 : 50*killed.getCoins()/100;
            killed.removeCoins(moneyLost);

        }
    }


}
