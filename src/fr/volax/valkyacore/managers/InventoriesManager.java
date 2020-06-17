package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.tool.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@Deprecated
public class InventoriesManager {
    public static ItemStack valorion_help_1 = new ItemBuilder(Material.NAME_TAG, 1).setName("§cStaff Help").setLore("§6Les commandes qui sont dans le §l/staff§r§6 sont:"," ","§e-/staff                                            §6☆Modérateur","§e-/staff help                                    §6☆Modérateur","§e-/staff cooldownchat [<durée>]    §6☆Super-Modo"," ").toItemStack();
    public static ItemStack valorion_help_2 = new ItemBuilder(Material.COMMAND, 1).setName("§cSanctions Help").setLore("§6Les commandes de sanctions sont:"," ","§e-/ban <joueur> perm <raison>                         §6☆Modérateur","§e-/ban <joueur> <durée>:<unité> <raison>       §6☆Modérateur","§e-/unban <joueur>                                             §6☆Responsable","§e-/check <joueur>                                             §6☆Modérateur","§e-/kick <joueur> <raison>                                  §6☆Modérateur").toItemStack();

    private Inventory staffHelpInventory = Bukkit.createInventory(null, 9*3, "§bStaff Aide Menu");

    @Deprecated
    private Inventory setStaffHelpInventory(Inventory staffHelpInventory) {
        this.staffHelpInventory = staffHelpInventory;
        staffHelpInventory.setItem(10, valorion_help_1);
        staffHelpInventory.setItem(12, valorion_help_2);
        return staffHelpInventory;
    }

    @Deprecated
    public Inventory getStaffHelpInventory() {
        return setStaffHelpInventory(staffHelpInventory);
    }
}

