package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.managers.PermissionsManager;
import fr.volax.valkyacore.managers.PlayerManager;
import fr.volax.valkyacore.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ModCommand implements CommandExecutor {
    public static ItemStack invSee = new ItemBuilder(Material.CHEST).setName("§eVoir inventaire").setLore("§7Clique droit sur un joueur", "§7pour voir sont inventaire.").toItemStack();
    public static ItemStack reports = new ItemBuilder(Material.BOOK).setName("§6Voir les signalements").setLore("§7Clique droit sur un joueur", "§7pour voir ses signalements.").toItemStack();
    public static ItemStack freeze = new ItemBuilder(Material.PACKED_ICE).setName("§bFreeze").setLore("§7Clique droit sur un joueur", "§7pour le freeze.").toItemStack();
    public static ItemStack kbTesteur = new ItemBuilder(Material.STICK).setName("§dTest de knockback").setLore("§7Clique gauche sur un joueur", "§7pour tester son recul.").addUnsafeEnchantment(Enchantment.KNOCKBACK, 5).toItemStack();
    public static ItemStack killer = new ItemBuilder(Material.BLAZE_ROD).setName("§cTueur de joueur").setLore("§7Clique droit sur un joueur", "§7pour le tuer.").toItemStack();
    public static ItemStack tpRandom = new ItemBuilder(Material.ARROW).setName("§aTp alatoire").setLore("§7Clique droit pour se téléporter", "§7aléatoirement sur un joueur.").toItemStack();
    public static ItemStack vanish = new ItemBuilder(Material.BLAZE_POWDER).setName("§2Vanish").setLore("§7Clique droit pour activer/désactiver", "§7le vanish.").toItemStack();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player) sender;
        if(!ValkyaCore.getInstance().getPlayerUtils().hasPerm(player, new PermissionsManager().staffModeUse)) return false;

        if (args.length == 0) {
            if (PlayerManager.isInModerationMod(player)) {
                PlayerManager pm = PlayerManager.getFromPlayer(player);
                ValkyaCore.getInstance().staff.remove(player.getUniqueId());
                player.getInventory().clear();
                player.sendMessage(ValkyaCore.PREFIX + " §eVous n'êtes plus en mode Modération.");
                pm.giveInventory();
                pm.destroy();
                if (player.getGameMode() != GameMode.CREATIVE) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    return false;
                }
                    return false;
            }

            PlayerManager pm = new PlayerManager(player);
            pm.init();
            ValkyaCore.getInstance().staff.add(player.getUniqueId());
            player.sendMessage(ValkyaCore.PREFIX + " §eVous êtes maintenant en mode Modération.");
            pm.saveInventory();
            player.setAllowFlight(true);
            player.setFlying(true);

            player.getInventory().setItem(0, invSee);
            player.getInventory().setItem(1, reports);
            player.getInventory().setItem(4, vanish);
            player.getInventory().setItem(5, freeze);
            player.getInventory().setItem(6, kbTesteur);
            player.getInventory().setItem(7, killer);
            player.getInventory().setItem(8, tpRandom);

        }
        if (args.length == 1) {
            if(!ValkyaCore.getInstance().getPlayerUtils().isOnlinePlayer(player, args[0])){ helpMessage(player); return false; }
            Player target = Bukkit.getPlayer(args[0]);
        }
        helpMessage(player);
        return false;
    }

    private void helpMessage(Player player){
        player.sendMessage(ValkyaCore.PREFIX + " §e/mod [<Modérateur>]");
    }
}
