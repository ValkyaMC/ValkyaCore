package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Repairable;

import java.util.ArrayList;
import java.util.List;

public class RepairCommand implements CommandExecutor {
    RepairCommand(String name) {
        ValkyaCore.getInstance().getCommand(name).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player) sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, ValkyaCore.getInstance().getPermissionsHelper().repairHand)) return false;

        if(args.length == 0){
            //TODO Cooldown
            ItemStack item = player.getItemInHand();

            if(item == null || item.getDurability() == 0 ||item.getType().isBlock()){
                player.sendMessage(ValkyaCore.PREFIX + " §eCet objet ne peut être réparé.");
                return false;
            }

            item.setDurability((short) 0);
            //TODO Cooldown
            player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de réparer l'item que vous avez dans votre main !");
            return false;
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("hand")){
                //TODO Cooldown
                ItemStack item = player.getItemInHand();

                if(item == null || item.getType() == Material.AIR || item.getType().isBlock() || item.getDurability() == 0 || item.getMaxStackSize() != 1 || item.getType().getMaxDurability() < 25)    {
                    player.sendMessage(ValkyaCore.PREFIX + " §eCet objet ne peut être réparé.");
                    return false;
                }

                item.setDurability((short) 0);
                //TODO Cooldown
                player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de réparer l'item que vous avez dans votre main !");
                return false;
            }else if(args[0].equalsIgnoreCase("all")){
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, ValkyaCore.getInstance().getPermissionsHelper().repairAll)) return false;
                List<String> repairItems = new ArrayList<String>();

                for(ItemStack item : player.getInventory().getContents()){
                    if(item == null || item.getType() == Material.AIR || item.getType().isBlock() || item.getDurability() == 0 || item.getMaxStackSize() != 1 || item.getType().getMaxDurability() < 25) continue;

                    item.setDurability((short) 0);
                    repairItems.add(item.getItemMeta().getDisplayName());
                }

                for(ItemStack item : player.getInventory().getArmorContents()){
                    if(item == null || item.getType() == Material.AIR || item.getType().isBlock() || item.getDurability() == 0 || item.getMaxStackSize() != 1 || item.getType().getMaxDurability() < 25) continue;

                    item.setDurability((short) 0);
                    repairItems.add(item.getItemMeta().getDisplayName());
                }

                if(repairItems.isEmpty()){
                    player.sendMessage(ValkyaCore.PREFIX + " §eAucun item ne peut être réparé dans votre inventaire.");
                    return false;
                }else{
                    player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de réparer des items dans votre inventaire.");
                    return false;
                }
            }else{
                helpMessage(player);
                return false;
            }
        }else{
            helpMessage(player);
            return false;
        }
    }

    private void helpMessage(CommandSender sender){
        sender.sendMessage(ValkyaCore.PREFIX + " §e/repair [hand|all]");
    }

    private boolean isRepairable(Item i){
        return i instanceof Repairable;
    }
}
