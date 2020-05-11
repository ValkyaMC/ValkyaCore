package fr.volax.valkyacore.listeners;

import fr.volax.valkyacore.managers.PlayerManager;
import fr.volax.valkyacore.tools.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class HeadOnDeath implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        if(event.getEntity().getKiller() == null) return;
        if(event.getEntity().getKiller() == event.getEntity().getPlayer()) return;
        if(PlayerManager.isInModerationMod(event.getEntity().getKiller())) return;
        ItemStack player_head = new ItemBuilder(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal()).setSkullOwner(event.getEntity().getPlayer().getName()).setName("§eTête de §6" + event.getEntity().getPlayer().getName()).toItemStack();
        Bukkit.getWorld(event.getEntity().getPlayer().getWorld().getName()).dropItemNaturally(event.getEntity().getPlayer().getLocation(), player_head);
    }
}