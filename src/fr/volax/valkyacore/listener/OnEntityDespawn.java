package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

public class OnEntityDespawn implements Listener {
    @EventHandler
    public void onDespawnEvent(ItemDespawnEvent e){
        if(e.getEntity() instanceof LivingEntity){
            if (ValkyaCore.getInstance().getEntityStacker().getValidEntity().contains(e.getEntity())) {
                ValkyaCore.getInstance().getEntityStacker().getValidEntity().remove(e.getEntity());
            }
        }
    }
}
