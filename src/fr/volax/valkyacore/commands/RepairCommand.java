package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.volaxapi.tool.time.TimeUnit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            return false;
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("hand")){
                ItemStack item = player.getItemInHand();

                if(item == null || item.getType() == Material.AIR || item.getType().isBlock() || item.getDurability() == 0 || item.getMaxStackSize() != 1 || item.getType().getMaxDurability() < 25)    {
                    player.sendMessage(ValkyaCore.PREFIX + " §eCet objet ne peut être réparé.");
                    return false;
                }

                if(ValkyaCore.getInstance().repair.containsKey(player.getUniqueId())){
                    float time = (System.currentTimeMillis() - ValkyaCore.getInstance().repair.get(player.getUniqueId())) / 1000;
                    if(time < 86400){
                        player.sendMessage(ValkyaCore.PREFIX + " §eVous ne pouvez pas repair votre item pour le moment, veillez attendre §6" + getTimeLeft((86400 - (int)time)) + " §e.");
                        return false;
                    }else { ValkyaCore.getInstance().repair.put(player.getUniqueId(), System.currentTimeMillis()); item.setDurability((short) 0); }
                } else{ ValkyaCore.getInstance().repair.put(player.getUniqueId(), System.currentTimeMillis()); item.setDurability((short) 0); }
                player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de réparer l'item que vous avez dans votre main !");
                return false;
            }else if(args[0].equalsIgnoreCase("all")){
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, ValkyaCore.getInstance().getPermissionsHelper().repairAll)) return false;

                if(ValkyaCore.getInstance().repair.containsKey(player.getUniqueId())){
                    float time = (System.currentTimeMillis() - ValkyaCore.getInstance().repair.get(player.getUniqueId())) / 1000;
                    if(time < 86400){
                        player.sendMessage(ValkyaCore.PREFIX + " §eVous ne pouvez pas repair vos items pour le moment, veillez attendre §6" + getTimeLeft((86400 - (int)time)) + " §e.");
                        return false;
                    }else { ValkyaCore.getInstance().repair.put(player.getUniqueId(), System.currentTimeMillis()); }
                } else{ ValkyaCore.getInstance().repair.put(player.getUniqueId(), System.currentTimeMillis()); }
                repairAll(player);
                return false;
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

    public void repairAll(Player player){
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
        }else{
            player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de réparer des items dans votre inventaire.");
        }
    }

    public String getTimeLeft(int timeLeft) {
        int heures = 0;
        int minutes = 0;
        int secondes = 0;

        while (timeLeft >= TimeUnit.HEURE.getToSecond()) {
            heures++;
            timeLeft -= TimeUnit.HEURE.getToSecond();
        }

        while (timeLeft >= TimeUnit.MINUTE.getToSecond()) {
            minutes++;
            timeLeft -= TimeUnit.MINUTE.getToSecond();
        }

        while (timeLeft >= TimeUnit.SECONDE.getToSecond()) {
            secondes++;
            timeLeft -= TimeUnit.SECONDE.getToSecond();
        }

        return "§6" + heures + " §e" + TimeUnit.HEURE.getName() + ", §6" + minutes + " §e" + TimeUnit.MINUTE.getName() + ", §6" + secondes + " 3§e" + TimeUnit.SECONDE.getName();
    }
}
