package fr.volax.valkyacore.guis;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.ItemBuilder;
import fr.volax.valkyacore.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.bukkit.Material.*;

public class ModItemsInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent e){
        Player player = e.getPlayer();
        if(!(PlayerManager.isInModerationMod(player))) return;
        if(!(e.getRightClicked() instanceof Player)) return;
        Player target = (Player) e.getRightClicked();


        switch (player.getInventory().getItemInHand().getType()){
            case CHEST:
                Inventory inv = Bukkit.createInventory(null,5*9, target.getName() + " > Inventaire");
                for(int i = 0; i <36; i++){
                    if(target.getInventory().getItem(i) != null){
                        inv.setItem(i, target.getInventory().getItem(i));
                    }
                }
                inv.setItem(36, target.getInventory().getHelmet());
                inv.setItem(37, target.getInventory().getChestplate());
                inv.setItem(38, target.getInventory().getLeggings());
                inv.setItem(39, target.getInventory().getBoots());
                inv.setItem(43, new ItemBuilder(BREAD).setName("§6Nourriture " + target.getFoodLevel() + "/20.0").toItemStack());
                inv.setItem(44, new ItemBuilder(REDSTONE_BLOCK).setName("§cVie " + target.getHealth() + "/" + target.getMaxHealth()).toItemStack());
                if(target.getGameMode() == GameMode.CREATIVE){
                    inv.setItem(42, new ItemBuilder(COMMAND).setName("§6Gamemode Créatif").toItemStack());
                }
                if(target.getGameMode() == GameMode.SURVIVAL){
                    inv.setItem(42, new ItemBuilder(COMMAND).setName("§6Gamemode Survie").toItemStack());
                }
                if(target.getGameMode() == GameMode.ADVENTURE){
                    inv.setItem(42, new ItemBuilder(COMMAND).setName("§6Gamemode Aventure").toItemStack());
                }
                player.openInventory(inv);
                break;
            case BOOK:
                // A FAIRE
                break;
            case PACKED_ICE:
                // A FAIRE
                break;
            case BLAZE_ROD:
                target.damage(2000000);
                break;
            default:break;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(!PlayerManager.isInModerationMod(player)) return;
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_AIR) return;

        switch(player.getInventory().getItemInHand().getType()){
            case ARROW:
                List<Player> list = new ArrayList<>();
                for(Player players : Bukkit.getServer().getOnlinePlayers()){
                    list.add(players);
                    list.remove(player);
                }
                if(list.size() == 0){
                    player.sendMessage(ValkyaCore.PREFIX  + " §eIl n'y a aucun joueur sur lequel vous téléporter.");
                    return;
                }
                Player target = list.get(new Random().nextInt(list.size()));
                player.teleport(target.getLocation());
                player.sendMessage(ValkyaCore.PREFIX + " §eVous avez été téléporté à §6" + target.getName());
                break;
            case BLAZE_POWDER:
                PlayerManager mod = PlayerManager.getFromPlayer(player);
                mod.setVanished(!mod.isVanished());
                player.sendMessage(mod.isVanished() ?  ValkyaCore.PREFIX + " §eVous êtes à présent invisible !" : ValkyaCore.PREFIX + " §eVous êtes à présent visible !");
                break;

            default: break;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        for(Player players : Bukkit.getServer().getOnlinePlayers()){
            if(PlayerManager.isInModerationMod(players)){
                PlayerManager pm = PlayerManager.getFromPlayer(players);
                if(pm.isVanished()){
                    player.hidePlayer(players);
                }
            }
        }
    }
}

