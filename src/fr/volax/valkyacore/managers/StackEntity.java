package fr.volax.valkyacore.managers;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.util.MobStackerConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;

public class StackEntity {
    /** The stacked mob custom name format. */
    public static int INVALID_STACK = -1;


    /*
     * Methods used to Stack or Unstack mobs
     */
    public boolean attemptUnstackOne(LivingEntity livingEntity) {

        String displayName = livingEntity.getCustomName();
        int mobsAmount = parseAmount(displayName);

        // Kill this mob
        livingEntity.setHealth(0);

        if (mobsAmount <= 1) {
            // The stack is down to one mob; don't recreate it
            return false;
        }


        // Recreate the stack with one less mob
        mobsAmount--;
        String mobDisplayName = MobStackerConfig.stackMobsDispalyName.replace("%number%",String.valueOf(mobsAmount));

        LivingEntity dupEntity = (LivingEntity) livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), livingEntity.getType());
        dupEntity.setCustomName(mobDisplayName.replace("%type%", livingEntity.getType().name().toLowerCase().substring(0,1).toUpperCase()+ livingEntity.getType().name().substring(1).toLowerCase()));
        dupEntity.setCustomNameVisible(true);

        return true;
    }

    public boolean unstackAll(LivingEntity livingEntity){
        livingEntity.setCustomName("1 mob");
        //Hide name from users
        livingEntity.setCustomNameVisible(false);
        livingEntity.setHealth(0);
        ValkyaCore.getInstance().getEntityStacker().getValidEntity().remove(livingEntity);
        return true;
    }


    public boolean stack(LivingEntity target, LivingEntity stackee) {
        if (target.getType() != stackee.getType()) {
            return false; // The entities must be of the same type.
        }

        String displayName = target.getCustomName();
        int alreadyStacked = parseAmount(displayName);
        int stackedMobsAlready = 1;

        // Check if the stackee is already a stack
        if (isStacked(stackee)) {
            stackedMobsAlready = parseAmount(stackee.getCustomName());
        }
        if(stackedMobsAlready >= MobStackerConfig.maxAllowedInStack || alreadyStacked >= MobStackerConfig.maxAllowedInStack) return false;
        stackee.remove();
        ValkyaCore.getInstance().getEntityStacker().getValidEntity().remove(stackee);
        if (alreadyStacked == INVALID_STACK) {
            String newDisplayName = MobStackerConfig.stackMobsDispalyName.replace("%number%", String.valueOf(stackedMobsAlready + 1));
            target.setCustomName(newDisplayName.replace("%type%", target.getType().name().toLowerCase().substring(0,1).toUpperCase()+ target.getType().name().substring(1).toLowerCase()));
            target.setCustomNameVisible(true);
        } else {
            String newDisplayName = MobStackerConfig.stackMobsDispalyName.replace("%number%", String.valueOf(alreadyStacked + stackedMobsAlready));
            target.setCustomName(newDisplayName.replace("%type%", target.getType().name().toLowerCase().substring(0,1).toUpperCase()+ target.getType().name().substring(1).toLowerCase()));
        }
        return true;
    }

    public int parseAmount(String displayName) {
        if (displayName == null) {
            return INVALID_STACK;
        }

        String colourStrip = ChatColor.stripColor(displayName);
        String str = colourStrip.replaceAll("[^-?0-9]+", " ");

        try {
            return Integer.valueOf(str.replaceAll(" ",""));
        }catch(NumberFormatException e){
            return INVALID_STACK;
        }
    }

    private boolean isStacked(LivingEntity entity) {
        return parseAmount(entity.getCustomName()) != INVALID_STACK;
    }
}