package fr.volax.valkyacore.listener;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.List;

public class BanItemListener implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(ConfigBuilder.getCBool("activated", ConfigType.BANITEMS.getConfigName())){
            for(int id : (List<Integer>)ConfigBuilder.getCList("disabled-items", ConfigType.BANITEMS.getConfigName())){
                if(id == 0) return;
                Material banItem = Material.getMaterial(id);
                Player player = event.getPlayer();

                for(String regions : (List<String>)ConfigBuilder.getCList("regions", ConfigType.BANITEMS.getConfigName())){
                    if(regions.isEmpty()) return;
                    if(isInRegion(player, regions)){
                       if(event.getItem() == null) return;
                       if(event.getItem().getType() == Material.AIR) return;
                        if(event.getItem().getType() == banItem){
                            event.setCancelled(true);
                            ValkyaUtils.sendChat(player, "§eCet item est désactivé ici !");
                            return;
                        }
                    }
                }
            }
        }
    }

    protected boolean isInRegion(Player player, String region) {
        com.sk89q.worldedit.Vector v = new com.sk89q.worldedit.Vector(player.getLocation().getX(), player.getLocation().getBlockY(), player.getLocation().getZ());
        return WorldGuardPlugin.inst().getRegionManager(player.getLocation().getWorld()).getApplicableRegionsIDs(v).contains(region);
    }
}
