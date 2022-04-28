package fr.lywen.bank.utils.inventories;

import fr.lywen.bank.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GuiManager
{
    private Main plugin;
    private final ConcurrentHashMap<UUID, AbstractGui> currentGUIs;

    public GuiManager(Main plugin)
    {
        this.plugin = plugin;
        this.currentGUIs = new ConcurrentHashMap<>();
        Bukkit.getPluginManager().registerEvents(new GuiListener(this), plugin);
    }

    public void openGui(Player player, AbstractGui gui)
    {
        if (this.currentGUIs.containsKey(player.getUniqueId())) {
            this.removeClosedGui(player);
        }

        gui.display(player);
        this.currentGUIs.put(player.getUniqueId(), gui);
    }

    public void closeGui(Player player)
    {
        this.removeClosedGui(player);
    }

    public void removeClosedGui(Player player)
    {
        if (this.currentGUIs.containsKey(player.getUniqueId()))
        {
            this.getPlayerGui(player).onClose(player);
            this.currentGUIs.remove(player.getUniqueId());
        }
    }

    public AbstractGui getPlayerGui(HumanEntity player)
    {
        return getPlayerGui(player.getUniqueId());
    }

    public AbstractGui getPlayerGui(UUID player)
    {
        if (this.currentGUIs.containsKey(player))
            return this.currentGUIs.get(player);

        return null;
    }

    public ConcurrentHashMap<UUID, AbstractGui> getPlayersGui()
    {
        return this.currentGUIs;
    }
}
