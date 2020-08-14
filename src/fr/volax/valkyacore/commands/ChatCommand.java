package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChatCommand implements CommandExecutor {
    public ChatCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0){
            if(sender instanceof Player)
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().chatChangeSet)) return false;
            helpMessage(sender);
            return false;
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("toggle")){
                if(sender instanceof Player)
                    if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().chatChangeSet)) return false;
                if(ConfigBuilder.getString("chat").equalsIgnoreCase("false")) {
                    ConfigBuilder.set("chat", "true");
                    ValkyaUtils.broadcast("§eLe chat vient d'être désactivé par §6§l" + sender.getName() + "§e.");
                    return false;
                }else if(ConfigBuilder.getString("chat").equalsIgnoreCase("true")){
                    ConfigBuilder.set("chat", "false");
                    ValkyaUtils.broadcast("§eLe chat vient d'être désactivé par §6§l" + sender.getName() + "§e.");
                    return false;
                }
            }else if(args[0].equalsIgnoreCase("clear")){
                if(sender instanceof Player)
                    if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().chatClear)) return false;
                for(int i=0;i<100;i++)
                    Bukkit.broadcastMessage("");
                ValkyaUtils.broadcast("§eLe chat vient d'être clear par §6§l" + sender.getName() + "§e.");
                return false;
            }else{
                helpMessage(sender);
                return false;
            }
        }else{
            if(sender instanceof Player)
                if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(sender, ValkyaCore.getInstance().getPermissionsHelper().chatChangeSet)) return false;
            helpMessage(sender);
            return false;
        }
        return false;
    }

    private void helpMessage(CommandSender sender){
        ValkyaUtils.sendChat(sender,"§e/chat <toggle|clear>");
    }
}
