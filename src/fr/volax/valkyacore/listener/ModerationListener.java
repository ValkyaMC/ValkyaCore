/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.utils.ValkyaUtils;
import fr.volax.volaxapi.tool.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModerationListener implements Listener {
    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (ValkyaCore.getInstance().getStaffMod().isFrozen(player) || ValkyaCore.getInstance().getStaffMod().isInStaffMode(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (ValkyaCore.getInstance().getStaffMod().isFrozen(player) || ValkyaCore.getInstance().getStaffMod().isInStaffMode(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void click(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        if (ValkyaCore.getInstance().getStaffMod().isInStaffMode(player) || ValkyaCore.getInstance().getStaffMod().isFrozen(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (ValkyaCore.getInstance().getStaffMod().isInStaffMode(player) || ValkyaCore.getInstance().getStaffMod().isFrozen(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void damage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (ValkyaCore.getInstance().getStaffMod().isInStaffMode(player) || ValkyaCore.getInstance().getStaffMod().isFrozen(player))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void damageByEntity(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
        if (entity instanceof Player && damager instanceof Player) {
            Player victim = (Player)entity;
            Player killer = (Player)damager;
            if (ValkyaCore.getInstance().getStaffMod().isFrozen(killer))
                event.setCancelled(true);
            if (ValkyaCore.getInstance().getStaffMod().isInStaffMode(killer) && !ValkyaCore.getInstance().getStaffMod().isInStaffMode(victim)) {
                ItemStack item = killer.getInventory().getItemInHand();
                if (!item.getType().equals(Material.STICK) || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName() || !item.getItemMeta().getDisplayName().equals("§a§lTest de recul"))
                    event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void food(FoodLevelChangeEvent event) {
        Player player = (Player)event.getEntity();
        if (ValkyaCore.getInstance().getStaffMod().isFrozen(player) || ValkyaCore.getInstance().getStaffMod().isInStaffMode(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void interact(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        Action action = event.getAction();
        if (item == null)
            return;
        if (isValid(item, Material.PACKED_ICE, "&b&lFreeze") && (action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK))) {
            event.setCancelled(true);
            String list = "§fPersonnes gelées : §a";
            for (int i = 0; i < ValkyaCore.getInstance().getStaffMod().getFrozen().size(); ) {
                list = list + Bukkit.getPlayer(ValkyaCore.getInstance().getStaffMod().getFrozen().get(i)).getName() + ((i == ValkyaCore.getInstance().getStaffMod().getFrozen().size() - 1) ? "§f." : "§f, §a");
                i++;
            }
            if (ValkyaCore.getInstance().getStaffMod().getFrozen().size() == 0)
                list = list + "§cAucun joueur§f.";
            player.sendMessage(ValkyaCore.getPREFIX() + list);
        } else if (isValid(item, Material.COMPASS, "§6§lTéléporation aléatoire") && (action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
            event.setCancelled(true);
            List<Player> pls = new ArrayList<>();
            for (Player p : ValkyaCore.getInstance().getServer().getOnlinePlayers()) {
                if (!p.equals(player))
                    pls.add(p);
            }
            if(pls.isEmpty()){
                ValkyaUtils.sendChat(player, "§eIl n'y aucun joueur sur lequel se téléporter !");
                event.setCancelled(true);
                return;
            }
            Player random = pls.get((new Random()).nextInt(pls.size()));
            player.teleport(random);
            ValkyaUtils.sendChat(player, "§eVous avez été téléporté à §6§l" + random.getName() + "§e !");
        }
    }

    @EventHandler
    public void interactWithEntity(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInHand();
        Entity entity = event.getRightClicked();
        if (entity instanceof Player && ValkyaCore.getInstance().getStaffMod().isInStaffMode(player)) {
            final Player target = (Player)entity;
            if (isValid(item, Material.PACKED_ICE, "&b&lFreeze")) {
                event.setCancelled(true);
                if (target.hasPermission("dev.moderation.freeze.bypass")) {
                    ValkyaUtils.sendChat(player, "§eVous ne pouvez pas geler ce joueur !");
                    return;
                }
                ValkyaCore.getInstance().getStaffMod().toggleFreeze(target, player);
                ValkyaUtils.sendChat(player, "§eLe joueur §6§l" + target.getName() + " §Ea été "+ (ValkyaCore.getInstance().getStaffMod().isFrozen(target) ? "gelé" : "dégelé") + ".");
            } else if (isValid(item, Material.CHEST, "&c&lInvsee")) {
                event.setCancelled(true);
                final Inventory inventory = Bukkit.createInventory(null, 54, "§6§l" + target.getName());
                player.openInventory(inventory);
                (new BukkitRunnable() {
                    public void run() {
                        if (!player.getOpenInventory().getTitle().equals(inventory.getTitle())) {
                            cancel();
                            return;
                        }
                        for (int i = 0; i < target.getInventory().getSize(); ) {
                            inventory.setItem(i, target.getInventory().getContents()[i]);
                            i++;
                        }
                        inventory.setItem(45, target.getInventory().getArmorContents()[3]);
                        inventory.setItem(46, target.getInventory().getArmorContents()[2]);
                        inventory.setItem(47, target.getInventory().getArmorContents()[1]);
                        inventory.setItem(48, target.getInventory().getArmorContents()[0]);
                        inventory.setItem(53, (new ItemBuilder(Material.COOKED_BEEF)).setName("§6§lFaim : §b§l" + target.getFoodLevel()).toItemStack());
                        inventory.setItem(52, (new ItemBuilder(Material.BEACON)).setName("§6§lVie : §b§l" + target.getHealth() + " PV").toItemStack());
                    }
                }).runTaskTimerAsynchronously(ValkyaCore.getInstance(), 0L, 5L);
            } else if (isValid(item, Material.WOOD_SWORD, "&c&lTueur")) {
                event.setCancelled(true);
                target.damage(target.getHealth());
                ValkyaUtils.sendChat(player, "§eVous avez anéantit le joueur §6§l" + target.getName() + " §e!");
            }
        }
    }

    private boolean isValid(ItemStack item, Material material, String name) {
        return (item.getType().equals(material) && item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals(name.replace("&", "§")));
    }

    @EventHandler
    public void move(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ValkyaCore.getInstance().getStaffMod().isFrozen(player))
            event.setTo(event.getFrom());
    }

    @EventHandler
    public void pickup(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (ValkyaCore.getInstance().getStaffMod().isInStaffMode(player) || ValkyaCore.getInstance().getStaffMod().isFrozen(player))
            event.setCancelled(true);
    }

    @EventHandler
    public void quit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (ValkyaCore.getInstance().getStaffMod().isInStaffMode(player)) {
            ValkyaCore.getInstance().getStaffMod().byPlayer(player).give();
            for (Player p : Bukkit.getServer().getOnlinePlayers()) { if (!p.equals(player)) p.showPlayer(player); }
            ValkyaCore.getInstance().getStaffMod().getMode().remove(player);
            player.setHealth(player.getMaxHealth());
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setFoodLevel(20);
            player.setHealth(player.getMaxHealth());
            player.setFoodLevel(20);
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent event){
        Player player = event.getPlayer();
        for (Player p : ValkyaCore.getInstance().getStaffMod().getMode().keySet()) {
            player.hidePlayer(p);
        }
    }
}
