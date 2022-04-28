package fr.lywen.bank.utils.inventories;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.PlayerInventory;

public class GuiListener implements Listener
{
    private final GuiManager manager;

    public GuiListener(GuiManager manager)
    {
        this.manager = manager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getWhoClicked() instanceof Player)
        {
            Player player = (Player) event.getWhoClicked();
            AbstractGui gui = manager.getPlayerGui(player);

            if (gui != null)
            {
                if (event.getClickedInventory() instanceof PlayerInventory)
                    return;

                String action = gui.getAction(event.getSlot());

                if (action != null)
                    gui.onClick(player, event.getCurrentItem(), action, event.getClick(), event.getSlot());

                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event)
    {
        Player player = (Player) event.getPlayer();
        if (manager.getPlayerGui(player) != null)
        {
            manager.removeClosedGui(player);
        }
    }
}
