/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.commands;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.handler.TeleportCooldownHandler;
import fr.volax.valkyacore.handler.TeleportHandler;
import fr.volax.valkyacore.tool.ConfigType;
import fr.volax.valkyacore.util.PlayerUtils;
import fr.volax.valkyacore.util.ValkyaUtils;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import fr.volax.volaxapi.tool.time.TimeUnit;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class RTPCommand implements CommandExecutor {
    public RTPCommand(String string) {
        ValkyaCore.getInstance().getCommand(string).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!ValkyaCore.getInstance().getPlayerUtils().isPlayer(sender)) return false;
        Player player = (Player)sender;

        if(!ValkyaCore.getInstance().getPlayerUtils().isAutoCommand(sender, "isActivated.commands.rtp")) return false;

        if(ConfigBuilder.getBoolean("rtp.cooldown-active") && (!TeleportCooldownHandler.areTherePlayersInTheMap())){
            TeleportCooldownHandler cooldown = TeleportCooldownHandler.getCooldown(player);
            if(cooldown != null){
                if(!cooldown.check(player) && (cooldown.getTimeLeft(player) * -1L >= 1L)){
                    ValkyaUtils.sendChat(player,ConfigBuilder.getCString("messages.rtp.error", ConfigType.MESSAGES.getConfigName()).replaceAll("&","§").replaceAll("%cooldown%", getTimeLeft(Math.toIntExact(cooldown.getTimeLeft(player) * -1L))));
                    return true;
                }else if(cooldown.getTimeLeft(player) * -1L == 0L){
                    cooldown.finalize();
                }else{
                    cooldown.finalize();
                }
            }
        }
        TeleportHandler tp = new TeleportHandler(player, Bukkit.getWorld(ConfigBuilder.getString("rtp.world")), ConfigBuilder.getInt("rtp.x"), ConfigBuilder.getInt("rtp.z"));
        tp.teleport();
        player.sendMessage(tp.getMessage());
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "/json {\"type\":\"title\",\"target\":\""+player.getName()+"\",\"title\":\"&eRandomTP\",\"subtitle\":\"§6"+tp.getX()+"X "+tp.getY()+"Y "+tp.getZ()+"Z\"}");
        if(ConfigBuilder.getBoolean("rtp.cooldown-active")){
            TeleportCooldownHandler cooldown = new TeleportCooldownHandler(player, ConfigBuilder.getInt("rtp.cooldown-temps"));
            cooldown.start();
        }
        return false;
    }

    public String getTimeLeft(int all_time){
        int heures = 0;
        int minutes = 0;
        int secondes = 0;


        while (all_time >= TimeUnit.HEURE.getToSecond()){
            heures++;
            all_time -= TimeUnit.HEURE.getToSecond();
        }

        while (all_time >= TimeUnit.MINUTE.getToSecond()){
            minutes++;
            all_time -= TimeUnit.MINUTE.getToSecond();
        }

        while (all_time >= TimeUnit.SECONDE.getToSecond()){
            secondes++;
            all_time -= TimeUnit.SECONDE.getToSecond();
        }

        if(heures == 0)
            if(minutes == 0) return secondes + "s";
            else return "§6" + minutes + "§em §6" + secondes + "§es";
        else return "§6" + heures + " §e" + TimeUnit.HEURE.getName() + " §6" + minutes + " §e" + TimeUnit.MINUTE.getName() + " §6" + secondes + " §e" + TimeUnit.SECONDE.getName();
    }
}
