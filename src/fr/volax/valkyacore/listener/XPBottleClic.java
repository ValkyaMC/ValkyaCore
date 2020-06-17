package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.tool.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class XPBottleClic implements Listener {
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

