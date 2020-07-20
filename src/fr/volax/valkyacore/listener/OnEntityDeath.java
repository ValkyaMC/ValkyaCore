package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.MobStackerConfig;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class OnEntityDeath implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onEntityDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof LivingEntity)) {
            return; // Not a living entity.
        }

        LivingEntity entity =  e.getEntity();

        if(entity.getType() == EntityType.SLIME){
            return;
        }


        if (entity.getType() != EntityType.PLAYER) {
            if(ValkyaCore.getInstance().getEntityStacker().getEntitiesToMultiplyOnDeath().contains(entity) || (entity.getKiller() != null && ValkyaCore.getInstance().getEntityStacker().getInstantKillPlayers().contains(entity.getKiller().getName()))){
                ValkyaCore.getInstance().getEntityStacker().getEntitiesToMultiplyOnDeath().remove(entity);
                e.setDroppedExp(e.getDroppedExp() * multiplyDropsReturnExp(entity,e.getDrops()));
                return;

            }
            ValkyaCore.getInstance().getStackEntity().attemptUnstackOne(entity);
        }

        if(MobStackerConfig.stackOnlySpawnerMobs){
            ValkyaCore.getInstance().getEntityStacker().getValidEntity().remove(entity);
        }
    }


    public int multiplyDropsReturnExp(LivingEntity dead, List<ItemStack> drops){
        int amountToMultiply = ValkyaCore.getInstance().getStackEntity().parseAmount(dead.getCustomName());
        if(amountToMultiply <=1) return 1;
        for(ItemStack i : drops){
            ItemStack item = new ItemStack(i);
            item.setAmount(amountToMultiply);
            dead.getWorld().dropItem(dead.getLocation(),item);

        }
        return amountToMultiply;

    }
}
