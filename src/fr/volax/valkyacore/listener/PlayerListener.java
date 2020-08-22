/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.listener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.obsidianbreaker.BlockStatus;
import fr.volax.valkyacore.exception.UnknownBlockTypeException;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Player player = event.getPlayer();
      if(player.getItemInHand().getType() == Material.AIR) return;
      if (player.getItemInHand().getType() == Material.STICK && player.getItemInHand().getItemMeta().getDisplayName().equals("§eChecker"))
        try {
          float totalDurability, remainingDurability;
          Block block = event.getClickedBlock();
          BlockStatus status = ValkyaCore.getInstance().getStorage().getBlockStatus(block, false);
          if (status == null) {
            totalDurability = ValkyaCore.getInstance().getStorage().getTotalDurabilityFromConfig(block);
            remainingDurability = totalDurability;
          } else {
            totalDurability = status.getTotalDurability();
            remainingDurability = totalDurability - status.getDamage();
          } 
          if (block.getLocation().getBlockY() == 0 && ConfigBuilder.configs.getConfig(ConfigType.OBSIDIANBREAKER.getConfigName()).get().getBoolean("VoidProtector"))
              ValkyaUtils.sendChat(player, "§eDurabilité §6Illimité (Protection du void)");
          else if (totalDurability > 0.0F) {
            DecimalFormat format = new DecimalFormat("##.##");
            DecimalFormatSymbols symbol = new DecimalFormatSymbols();
            symbol.setDecimalSeparator('.');
            format.setDecimalFormatSymbols(symbol);
            String durability = format.format(totalDurability);
            String durabilityLeft = format.format(remainingDurability);
            ValkyaUtils.sendChat(player, "§eDurabilité §6" + durabilityLeft + " §esur §6" + durability);
          } else {
              ValkyaUtils.sendChat(player, "§eDurabilité §6Illimité");
          } 
        } catch (UnknownBlockTypeException ignored) {}
    } 
  }
}