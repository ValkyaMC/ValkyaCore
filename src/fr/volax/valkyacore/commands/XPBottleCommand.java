/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.ConfigType;
import fr.volax.volaxapi.tool.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class XPBottleCommand implements CommandExecutor {
    private final String help = ValkyaCore.getInstance().getConfigBuilder().getString("messages.xpbottle.help-message", ConfigType.MESSAGES.getConfigName());
    private final String helpGive = ValkyaCore.getInstance().getConfigBuilder().getString("messages.xpbottle.help-message-give", ConfigType.MESSAGES.getConfigName());
    private final String noXP = ValkyaCore.getInstance().getConfigBuilder().getString("messages.xpbottle.no-level", ConfigType.MESSAGES.getConfigName());
    private final String noNumber = ValkyaCore.getInstance().getConfigBuilder().getString("messages.xpbottle.no-number", ConfigType.MESSAGES.getConfigName());

    XPBottleCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player)sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().xpBottleUse)) return false;
        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.xpbottle")) return false;

        int xplevel = player.getLevel();
        Inventory inventaireP = player.getInventory();

        if(args.length == 0) {
            if (xplevel <= 0) {
                player.sendMessage(noXP);
                return false;
            }else{
                ItemStack xpbottle = new ItemBuilder(Material.EXP_BOTTLE, xplevel).setName("§aBouteille d'xp - §c1 niveau").setLore("§2Faites un clique droit pour vous regive l'xp.").toItemStack();
                inventaireP.addItem(xpbottle);
                player.setLevel(0);
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
            }
        }else if(args.length == 1){
            if(ValkyaCore.getInstance().isInt(args[0])){
                int xpd = Integer.parseInt(args[0]);
                if(xpd > xplevel){
                    player.sendMessage(noXP);
                    return false;
                }else{
                    ItemStack xpbottle = new ItemBuilder(Material.EXP_BOTTLE, xpd).setName("§aBouteille d'xp - §c1 niveau").setLore("§2Faites un clique droit pour vous regive l'xp.").toItemStack();
                    inventaireP.addItem(xpbottle);
                    player.setLevel(xplevel - xpd);
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
                }
            }else{
                if(args[0].equalsIgnoreCase("give")) {
                    if (player.hasPermission(ValkyaCore.getInstance().getConfigBuilder().getString("permissions.xp-give"))) {
                        player.sendMessage(helpGive);
                        return false;
                    }else{
                        player.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.no-perm"));
                        return false;
                    }
                }else if(args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("aide")){
                    player.sendMessage(help);
                    return false;
                }else{
                    player.sendMessage(help);
                    return false;
                }
            }
        }else if(args[0].equalsIgnoreCase("give") && args.length == 2){
            if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().xpBottleGive)) return false;
            if(!ValkyaCore.getInstance().isInt(args[1])){
                player.sendMessage(noNumber);
                return false;
            }else{
                ItemStack xpbottle = new ItemBuilder(Material.EXP_BOTTLE, Integer.parseInt(args[1])).setName("§aBouteille d'xp - §c1 niveau").setLore("§2Faites un clique droit pour vous regive l'xp.").toItemStack();
                inventaireP.addItem(xpbottle);
                player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
                return false;
            }
        }else if(args[0].equalsIgnoreCase("give") && args.length == 3){
            if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().xpBottleGive)) return false;
            if(!ValkyaCore.getInstance().isInt(args[1])){
                player.sendMessage(noNumber);
                return false;
            }else{
                Player target = Bukkit.getPlayer(args[2]);
                if(target == null){
                    player.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.xpbottle.not-online", ConfigType.MESSAGES.getConfigName()).replaceAll("%player%", args[2]));
                    return false;
                }else{
                    Inventory inventaireT = target.getInventory();
                    ItemStack xpbottle = new ItemBuilder(Material.EXP_BOTTLE, Integer.parseInt(args[1])).setName("§aBouteille d'xp - §c1 niveau").setLore("§2Faites un clique droit pour vous regive l'xp.").toItemStack();
                    inventaireT.addItem(xpbottle);
                    player.playSound(player.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);

                    player.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.xpbottle.give-message", ConfigType.MESSAGES.getConfigName()).replaceAll("%nombre%", args[1]).replaceAll("%player%", target.getName()));
                    target.sendMessage(ValkyaCore.getInstance().getConfigBuilder().getString("messages.xpbottle.receive-message", ConfigType.MESSAGES.getConfigName()).replaceAll("%nombre%", args[1]));
                    return false;
                }
            }
        }else{
            player.sendMessage(help);
            return false;
        }
        return false;
    }
}

