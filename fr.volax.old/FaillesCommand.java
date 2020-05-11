package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import fr.volax.valkyacore.tools.ItemBuilder;
import fr.volax.valkyacore.tools.ConfigBuilder;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Random;


public class FaillesCommand implements CommandExecutor {
    int count;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player)sender;
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, new PermissionsManager().faillesUse)) return false;
        if(args.length == 1) {
            if (args[0].equalsIgnoreCase("setupChest")) {
                int x = ((int) player.getLocation().getX());
                int y = (int) player.getLocation().getY();
                int z = (int) player.getLocation().getZ();
                String world = player.getWorld().getName();
                    ConfigBuilder.setCBool("chest.chest_" + count + ".SETUPED", true, "failles.yml");
                    ConfigBuilder.setCInt("chest.chest_" + count + ".X", x, "failles.yml");
                    ConfigBuilder.setCInt("chest.chest_" + count + ".Y", y, "failles.yml");
                    ConfigBuilder.setCInt("chest.chest_" + count + ".Z", z, "failles.yml");
                    ConfigBuilder.setCString("chest.chest_" + count + ".WORLD", world, "failles.yml");
                    ConfigBuilder.setCString("chest.chest_" + count + ".PLAYER", player.getName(), "failles.yml");
                    player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de setup le coffre n°" + count);
                    count++;
                    return false;
            }else if(args[0].equalsIgnoreCase("chest") || args[0].equalsIgnoreCase("chests")) {
                Inventory inventory = ValkyaCore.getInstance().getInventoriesManager().getChestFailles();
                for (int i = 0; i < 31; i++) {
                    int x = ConfigBuilder.getCInt("chest.chest_" + i + ".X", "failles.yml");
                    int y = ConfigBuilder.getCInt("chest.chest_" + i + ".Y", "failles.yml");
                    int z = ConfigBuilder.getCInt("chest.chest_" + i + ".Z", "failles.yml");

                    if (ValkyaCore.getInstance().isNull(x, y, z)) {
                        inventory.setItem(i, new ItemBuilder(Material.CHEST).setName("§eCoffre n°§6" + i).setLore("§eX: §6NON SETUP", "§eY: §6NON SETUP", "§eZ: §6NON SETUP").toItemStack());
                    } else {
                        inventory.setItem(i, new ItemBuilder(Material.CHEST).setName("§eCoffre n°§6" + i).setLore("§eX: §6" + x, "§eY: §6" + y, "§eZ: §6" + z, "§eClic droit: §6Prévisualise le coffre", "§eClic gauche: §6Téléporte à la position du coffre").toItemStack());
                    }
                    player.openInventory(inventory);
                }
                return false;
            }else if(args[0].equalsIgnoreCase("removeChest") || args[0].equalsIgnoreCase("help") ||args[0].equalsIgnoreCase("aide") || args[0].equalsIgnoreCase("setupItem")){
                helpMessages(player);
                return false;
            }
        }else if(args.length == 2){
            if(args[0].equalsIgnoreCase("setupChest")){
                if(!ValkyaCore.getInstance().isInt(args[1])){
                    player.sendMessage(ValkyaCore.PREFIX + " §e/faille setupChest [<Nombre valide>] \n"+ValkyaCore.PREFIX+" §e-> Supérieure ou égale à 0 et Inférieure ou égale à 30");
                    return false;
                }
                int countArgs = Integer.parseInt(args[1]);

                if(countArgs < 0 ||countArgs > 30){
                    player.sendMessage(ValkyaCore.PREFIX + " §e/faille setupChest [<Nombre valide>] \n"+ValkyaCore.PREFIX+" §e-> Supérieure ou égale à 0 et Inférieure ou égale à 30");
                    return false;
                }
                int x = ((int) player.getLocation().getX());
                int y = (int) player.getLocation().getY();
                int z = (int) player.getLocation().getZ();
                String world = player.getWorld().getName();

                ConfigBuilder.setCBool("chest.chest_" + countArgs + ".SETUPED", true, "failles.yml");
                ConfigBuilder.setCInt("chest.chest_" + countArgs + ".X", x, "failles.yml");
                ConfigBuilder.setCInt("chest.chest_" + countArgs + ".Y", y, "failles.yml");
                ConfigBuilder.setCInt("chest.chest_" + countArgs + ".Z", z, "failles.yml");
                ConfigBuilder.setCString("chest.chest_" + countArgs + ".WORLD", world, "failles.yml");
                ConfigBuilder.setCString("chest.chest_" + countArgs + ".PLAYER", player.getName(), "failles.yml");
                player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de setup le coffre n°" + countArgs);
            }else if(args[0].equalsIgnoreCase("removeChest")){
                if(!ValkyaCore.getInstance().isInt(args[1])){
                    player.sendMessage(ValkyaCore.PREFIX + " §e/faille removeChest [<Nombre valide>] \n"+ValkyaCore.PREFIX+" §e-> Supérieure ou égale à 0 et Inférieure ou égale à 30");
                    return false;
                }

                int chestNumber = Integer.parseInt(args[1]);

                if(chestNumber < 0 ||chestNumber > 30){
                    player.sendMessage(ValkyaCore.PREFIX + " §e/faille removeChest [<Nombre valide>] \n"+ValkyaCore.PREFIX+" §e-> Supérieure ou égale à 0 et Inférieure ou égale à 30");
                    return false;
                }
                int x = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".X", "failles.yml");
                int y = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".Y", "failles.yml");
                int z = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".Z", "failles.yml");


                if(ValkyaCore.getInstance().isNull(x, y, z)){
                    player.sendMessage(ValkyaCore.PREFIX + " §eVous ne pouvez pas remove un coffre non setup !");
                }else{
                    ConfigBuilder.setCBool("chest.chest_" + chestNumber + ".SETUPED", false, "failles.yml");
                    ConfigBuilder.setCInt("chest.chest_" + chestNumber + ".X", 0, "failles.yml");
                    ConfigBuilder.setCInt("chest.chest_" + chestNumber + ".Y", 0, "failles.yml");
                    ConfigBuilder.setCInt("chest.chest_" + chestNumber + ".Z", 0, "failles.yml");
                    ConfigBuilder.setCString("chest.chest_" + chestNumber + ".WORLD", "NON SETUP", "failles.yml");
                    ConfigBuilder.setCString("chest.chest_" + chestNumber + ".PLAYER", player.getName(), "failles.yml");
                    player.sendMessage(ValkyaCore.PREFIX + " §eVous venez de remove le coffre n°" + chestNumber);
                    return false;
                }
            }else if(args[0].equalsIgnoreCase("event")){
                if(ValkyaCore.getInstance().isInt(args[1])){
                    int chestNumber = Integer.parseInt(args[1]);
                    int x = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".X", "failles.yml");
                    int y = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".Y", "failles.yml");
                    int z = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".Z", "failles.yml");

                    if(ValkyaCore.getInstance().isNull(x, y, z)){
                        player.sendMessage(ValkyaCore.PREFIX + " §eVous ne pouvez pas lancer un event avec un coffre non setup !");
                        return false;
                    }

                }else if(args[1].equalsIgnoreCase("random")){
                    int chestNumber = new Random().nextInt(31);
                    int x = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".X", "failles.yml");
                    int y = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".Y", "failles.yml");
                    int z = ConfigBuilder.getCInt("chest.chest_" + chestNumber + ".Z", "failles.yml");

                    if(ValkyaCore.getInstance().isNull(x, y, z)){
                        player.performCommand("faille event random");
                        return false;
                    }

                    player.sendMessage(ValkyaCore.PREFIX + " §eCoffre en cours ! N°" + chestNumber);



                }else{
                    player.sendMessage(ValkyaCore.PREFIX + " §e/faille event <Nombre valide|random>");
                }
            }else if(args[0].equalsIgnoreCase("setupItem")){
                if(!ValkyaCore.getInstance().isInt(args[1])){
                    player.sendMessage(ValkyaCore.PREFIX + " §eVeillez fournir un nombre d'item max valide !");
                    return false;
                }
                int maxItem = Integer.parseInt(args[1]);

                if(maxItem <= 0 || maxItem > 64){
                    player.sendMessage(ValkyaCore.PREFIX + " §eVeillez fournir un nombre d'item max valide !");
                    return false;
                }


            }
        }
        return false;
    }

    private void helpMessages(Player player){
        player.sendMessage(ValkyaCore.PREFIX + " §e/faille aide");
        player.sendMessage(ValkyaCore.PREFIX + " §e/faille setupChest [<Nombre valide>]");
        player.sendMessage(ValkyaCore.PREFIX + " §e/faille removeChest [<Nombre valide>]");
        player.sendMessage(ValkyaCore.PREFIX + " §e/faille chests");
        player.sendMessage(ValkyaCore.PREFIX + " §e/faille event <Nombre valide|random>");
        player.sendMessage(ValkyaCore.PREFIX + " §e/faille setupItem <Nombre d'item max>");
        player.sendMessage(ValkyaCore.PREFIX + " §e/faille removeItem <ID de l'item>");
    }
}
