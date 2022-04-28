package fr.lywen.bank.utils.inventories;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public interface IGuiManager
{
    /**
     * Open the given Abstract GUI to the player
     *
     * @param player The player to show the gui
     * @param gui The AbstractGui to show
     */
    void openGui(Player player, AbstractGui gui);

    /**
     * Close the opened GUI of the player
     *
     * @param player Player
     */
    void closeGui(Player player);

    /**
     * Remove the GUI data of the player from the save list
     * Automatically called in {@link IGuiManager##closeGui(Player)}
     *
     * @param player The Player for data to remove
     */
    void removeClosedGui(Player player);

    /**
     * Get the GUI object of a player
     *
     * @param player The HumanPlayer object to get the GUI
     *
     * @return The AbstractGui of the player
     */
    AbstractGui getPlayerGui(HumanEntity player);

    /**
     * Get the Gui object of a player
     *
     * @param player The UUID of the player to get the GUI
     *
     * @return The AbstractGui of the player
     */
    AbstractGui getPlayerGui(UUID player);

    /**
     * Get all players who have a GUI opened
     *
     * @return The gui list and uuid of players who opened the GUI
     */
    ConcurrentHashMap<UUID, AbstractGui> getPlayersGui();
}
