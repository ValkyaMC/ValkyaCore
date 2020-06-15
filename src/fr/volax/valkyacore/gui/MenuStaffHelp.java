package fr.volax.valkyacore.gui;

import fr.volax.valkyacore.tools.GuiBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuStaffHelp implements GuiBuilder {
    @Override
    public String name() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void contents(Player player, Inventory inv) {

    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {

    }
}
