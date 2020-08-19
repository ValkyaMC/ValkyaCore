package fr.volax.valkyacore.util;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.StaffInventory;
import fr.volax.volaxapi.tool.item.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StaffMod {
    private final ValkyaCore main;

    public StaffMod(ValkyaCore main) {
        this.main = main;
    }

    public void toggle(Player player) {
        if (isInStaffMode(player)) {
            byPlayer(player).give();
            main.mode.remove(player);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
        } else {
            StaffInventory inv = new StaffInventory(player);
            inv.save();
            player.setGameMode(GameMode.SURVIVAL);
            player.setAllowFlight(true);
            player.teleport(player.getLocation().clone().add(0.0D, 0.2D, 0.0D));
            player.setFlying(true);
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000,3,false, false));
            player.getInventory().clear();
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
            ItemBuilder freeze = (new ItemBuilder(Material.PACKED_ICE)).setName("§b§lFreeze").setLore("§7Clic gauche > Afficher les joueurs gelés, §7Clic droit > Geler / Dégeler un joueur.");
            ItemBuilder invsee = (new ItemBuilder(Material.CHEST)).setName("§c§lInvsee").setLore("§7Clic gauche > /.", "§7Clic droit > Afficher l'inventaire d'un joueur.");
            ItemBuilder knockback = (new ItemBuilder(Material.STICK)).setName("§a§lTest de recul").setLore("§7Clic gauche > Tester le recul d'un joueur.", "§7Clic droit > /." ).addUnsafeEnchantment(Enchantment.KNOCKBACK, 10);
            ItemBuilder killer = (new ItemBuilder(Material.WOOD_SWORD)).setName("§c§lTueur").setLore("§7Clic gauche > /.", "§7Clic droit > Tuer le joueur." );
            ItemBuilder random = (new ItemBuilder(Material.COMPASS)).setName("§6§lTéléporation aléatoire").setLore("§7Clic gauche > /.", "§7Clic droit > Se téléporter à un joueur aléatoire.");
            ItemBuilder soon = (new ItemBuilder(Material.REDSTONE_BLOCK)).setName("§e§k§l*** ***").setLore("§7Clic gauche > §k***§7.", "§7Clic droit > §k***§7." );
            player.getInventory().setItem(0, freeze.toItemStack());
            player.getInventory().setItem(1, invsee.toItemStack());
            player.getInventory().setItem(3, knockback.toItemStack());
            player.getInventory().setItem(4, killer.toItemStack());
            player.getInventory().setItem(5, random.toItemStack());
            player.getInventory().setItem(6, soon.toItemStack());
            player.getInventory().setItem(2, soon.toItemStack());
            player.getInventory().setItem(7, soon.toItemStack());
            player.getInventory().setItem(8, soon.toItemStack());
            player.updateInventory();
            main.mode.put(player, inv);
        }
    }

    public void toggleFreeze(Player player, Player source) {
        if (isFrozen(player)) {
            main.frozen.remove(player.getUniqueId());
            player.setAllowFlight(false);
            ValkyaUtils.sendChat(player, "§eVous avez été dégelé par §6§l" + source.getName() + " §e!");
        } else {
            main.frozen.add(player.getUniqueId());
            player.setAllowFlight(true);
            ValkyaUtils.sendChat(player, "§eVous avez été gelé par §6§l" + source.getName() + " §e!");
        }
    }

    public boolean isInStaffMode(Player player) {
        return main.mode.containsKey(player);
    }
    public boolean isFrozen(Player player) {
        return main.frozen.contains(player.getUniqueId());
    }
    public StaffInventory byPlayer(Player player) {
        return main.mode.get(player);
    }
    public List<UUID> getFrozen() {
        return main.frozen;
    }
    public HashMap<Player, StaffInventory> getMode() {
        return (HashMap<Player, StaffInventory>)main.mode;
    }
}
