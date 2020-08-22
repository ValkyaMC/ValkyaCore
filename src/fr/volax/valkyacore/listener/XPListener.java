/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.PermissionsHelper;
import fr.volax.volaxapi.tool.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class XPListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(player.hasPermission(ValkyaCore.getInstance().getPermissionsHelper().xpDeath)){
            event.setKeepLevel(true);
            event.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR && !(player.isSneaking())|| event.getAction() == Action.RIGHT_CLICK_BLOCK && !(player.isSneaking())){
            if(player.getItemInHand().getType() == Material.EXP_BOTTLE){
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aBouteille d'xp - §c1 niveau")){
                    int numberitem = player.getItemInHand().getAmount();
                    ItemStack xpbottle = new ItemBuilder(Material.EXP_BOTTLE, numberitem - 1).setName("§aBouteille d'xp - §c1 niveau").setLore("§2Faites un clique droit pour vous regive l'xp.").toItemStack();
                    player.getInventory().removeItem(player.getItemInHand());
                    player.getInventory().addItem(xpbottle);
                    player.updateInventory();
                    player.giveExpLevels(1);
                    event.setCancelled(true);
                }
            }
        }else if(event.getAction() == Action.RIGHT_CLICK_AIR && player.isSneaking()|| event.getAction() == Action.RIGHT_CLICK_BLOCK && player.isSneaking()){
            if(player.getItemInHand().getType() == Material.EXP_BOTTLE){
                if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aBouteille d'xp - §c1 niveau")){
                    int numberitem = player.getItemInHand().getAmount();
                    player.getInventory().removeItem(player.getItemInHand());
                    player.updateInventory();
                    player.giveExpLevels(numberitem);
                    event.setCancelled(true);
                }
            }
        }
    }
}

