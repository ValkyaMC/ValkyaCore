package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.MobStackerConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class OnEntitySpawn implements Listener {
    @EventHandler
    public void onSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            if (MobStackerConfig.mobsToStack.contains(e.getEntityType())) {
                ValkyaCore.getInstance().getEntityStacker().getValidEntity().add(e.getEntity());
            }
        }
        if(e.getEntity().getType() == EntityType.SLIME){
            Slime slime = (Slime) e.getEntity();
            //valid display name
            if(ValkyaCore.getInstance().getStackEntity().parseAmount(slime.getCustomName()) != -1){
                slime.setSize(4);
            }
        }
    }
}
