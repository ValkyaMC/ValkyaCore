/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.ValkyaUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ReportLogCommand implements CommandExecutor, Listener {
    public ReportLogCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player) sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, ValkyaCore.getInstance().getPermissionsHelper().reportUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.reportlog")) return false;

        if(args.length != 1) {
            ValkyaUtils.sendChat(player, "§c/reportlog <Joueur>");
            return false;
        }

        if(!ValkyaCore.getInstance().getPlayerUtils().exist(args[0])){
            ValkyaUtils.sendChat(player, "§c/reportlog <Joueur>");
            return false;
        }

        List<ItemStack> reports = ValkyaCore.getInstance().getReportManager().getAllReport(args[0]);
        Inventory inventory = Bukkit.createInventory(null, 5*9, "§eLog " + args[0]);
        int i = 0;
        for(ItemStack itemStack : reports){
            inventory.setItem(i, itemStack);
            i++;
        }

        player.openInventory(inventory);
        return false;
    }
    @EventHandler
    public void onInteract(InventoryClickEvent event){
        Player player = (Player)event.getWhoClicked();
        Inventory inv = event.getInventory();
        ItemStack current = event.getCurrentItem();
        if (event.getCurrentItem() != null) {
            if(inv.getTitle().startsWith("§eLog")){
                event.setCancelled(true);
                if(event.getClick() == ClickType.RIGHT){
                    ValkyaCore.getInstance().getReportManager().deleteReport(Integer.parseInt(current.getItemMeta().getDisplayName().substring(13)));
                    player.performCommand("reportlog " + inv.getTitle().substring(6));
                }
            }
        }
    }
}
