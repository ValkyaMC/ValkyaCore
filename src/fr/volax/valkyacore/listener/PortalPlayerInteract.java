/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;

/**
 * @author https://github.com/VolaxYT/PortalsCanceller/
 */
public class PortalPlayerInteract implements Listener {
    /**
     * When a player tries to create a Nether Portal
     * if in config the option: 'portals.create-nether' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onPortalCreate(PortalCreateEvent event){
        if(!ConfigBuilder.getCBool("portals.create-nether", ConfigType.PORTALS.getConfigName())){
            System.err.println(ValkyaCore.getLOGGER_PREFIX() + "A player tried to create an Nether portal (Don't ask where or who because the event does not allow it :D)");
            event.setCancelled(true);
        }
    }
    /**
     * When player tries to right click
     */
    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            /**
             * When player tries to right click with a eye of ender on ender portal frame
             * if in config the option: 'portals.create-end' is set to false -> the event is cancelled
             */
            if(player.getItemInHand().getType() == Material.EYE_OF_ENDER) {
                if (event.getClickedBlock().getType() == Material.ENDER_PORTAL_FRAME) {
                    if (!ConfigBuilder.getCBool("portals.create-end", ConfigType.PORTALS.getConfigName())) {
                        event.setCancelled(true);
                        System.err.println(ValkyaCore.getLOGGER_PREFIX() + player.getName() + " tried to create an End portal at (Player Location)");
                        System.err.println(ValkyaCore.getLOGGER_PREFIX() + "X: " + player.getLocation().getX() + " Y: " + player.getLocation().getY() + " Z: " + player.getLocation().getZ());
                    }
                }
            }
        }
    }
    /**
     * When player tries to use a dispenser for place fire on (all) blocks
     * if in config the option : 'portals.dispenser-can-use-flint' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onDipense(BlockDispenseEvent event){
        if(event.getItem().getType() == Material.FLINT_AND_STEEL){
            if(!ConfigBuilder.getCBool("portals.dispenser-can-use-flint", ConfigType.PORTALS.getConfigName())){
                event.setCancelled(true);
            }
        }
    }

    /**
     * When player try to enter in portal of Nether / End, he isn't teleport
     * if in config the option : 'portals.enter-nether' is set to false -> the event is cancelled
     * if in config the option : 'portals.enter-end' is set to false -> the event is cancelled
     */
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        PlayerTeleportEvent.TeleportCause cause = event.getCause();

        if(cause.equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)){
            if(!ConfigBuilder.getCBool("portals.enter-end", ConfigType.PORTALS.getConfigName())){
                System.err.println(ValkyaCore.getLOGGER_PREFIX() + player.getName() + " tried to enter in an End portal at (Player Location)");
                System.err.println(ValkyaCore.getLOGGER_PREFIX() + "X: " +player.getLocation().getX() + " Y: " +player.getLocation().getY() + " Z: " +player.getLocation().getZ());
                event.setCancelled(true);
                return;
            }
        }

        if(cause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)){
            if(!ConfigBuilder.getCBool("portals.enter-nether", ConfigType.PORTALS.getConfigName())) {
                System.err.println(ValkyaCore.getLOGGER_PREFIX() + player.getName() + " tried to enter in a Nether portal at (Player Location)");
                System.err.println(ValkyaCore.getLOGGER_PREFIX() + "X: " +player.getLocation().getX() + " Y: " +player.getLocation().getY() + " Z: " +player.getLocation().getZ());
                event.setCancelled(true);
                return;
            }
        }
    }
}
