package fr.volax.valkyacore.gui;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.GuiBuilder;
import fr.volax.valkyacore.tools.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuStaffInventory implements GuiBuilder {
    public static ItemStack staff_1 = new ItemBuilder(Material.PAPER, 1).setName("§cStaff Aide Menu").setLore("§6Cela permet d'avoir la liste des commandes").toItemStack();

    @Override
    public String name() {
        return "§bStaff Menu";
    }

    @Override
    public int getSize() {
        return 3 * 9;
    }

    @Override
    public void contents(Player player, Inventory inv) {
        inv.setItem(10, staff_1);
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot) {
        if(current.getType() == Material.NAME_TAG && current.getItemMeta().getDisplayName().equals("§cStaff Help")){

        }else if(current.getType() == Material.PAPER && current.getItemMeta().getDisplayName().equals("§cStaff Aide Menu")){
            player.openInventory(ValkyaCore.getInstance().getInventoriesManager().getStaffHelpInventory());
        }
    }
}
