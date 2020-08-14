package fr.volax.valkyacore.listener;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DisableMoreTotems implements Listener {
    /**
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Inventory playerInventory = player.getInventory();
        int currentTotem = 0;
        for(ItemStack itemStack : playerInventory.getContents()){
            if(itemStack == null || itemStack.getType() == Material.AIR) return;
            if(itemStack.getType() == Material.getMaterial(276)){
                currentTotem++;
                System.out.println(currentTotem);
                if(currentTotem > 1){
                    player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                    playerInventory.remove(itemStack);
                    player.updateInventory();

                    currentTotem--;
                    if(currentTotem <= 1) return;
                }
            }
        }
    }
    */
}