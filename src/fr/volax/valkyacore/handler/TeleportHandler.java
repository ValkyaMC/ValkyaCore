/*
 * Copyright (c) 2020. Class by Valkya
 * https://dev.volax.fr
 * https://github.com/VolaxYT
 * https://www.youtube.com/c/Volax
 */

package fr.volax.valkyacore.handler;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.volaxapi.tool.config.ConfigBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Random;

public class TeleportHandler {
    Player player = null;
    World world;
    int xCoord = -1;
    int zCoord = -1;
    int xF = 0;
    int yF = 0;
    int zF = 0;
    String uuid;

    public TeleportHandler(Player player, World world, int xCoord, int zCoord) {
        this.player = player;
        this.world = world;
        this.uuid = player.getUniqueId().toString();
        this.xCoord = xCoord;
        this.zCoord = zCoord;
    }

    public void teleport() {
        Location location = getLocation();
        boolean onLand = false;

        while (!onLand) {
            if (location.getY() == 0) {
                location = getLocation();
                onLand = false;
            } else {
                onLand = true;
            }
        }
        this.player.teleport(location);
    }

    public int getX() {
        return this.xF;
    }

    public int getY() {
        return this.yF;
    }

    public int getZ() {
        return this.zF;
    }

    private void set(double x, double y, double z) {
        this.xF = ((int) x);
        this.yF = ((int) y);
        this.zF = ((int) z);
    }

    public String getMessage() {
        String msg1 = "§eTéléportation à la location:";
        String msg2 = "§eX: §6" + getX();
        String msg3 = "§eY: §6" + getY();
        String msg4 = "§eZ: §6" + getZ();
        String espace = "\n";
        return ValkyaCore.getPREFIX() + msg1 + espace + ValkyaCore.getPREFIX() + msg2 + espace + ValkyaCore.getPREFIX() + msg3 + espace + ValkyaCore.getPREFIX() + msg4 + espace;
    }

    public Location getLocation() {
        Random random = new Random();
        int x = random.nextInt(this.xCoord);
        int z = random.nextInt(this.zCoord);

        x = rdm(x);
        z = rdm(z);

        Location loc = safeY(new Location(Bukkit.getWorld(ValkyaCore.getInstance().getConfigBuilder().getString("rtp.world")), x, 63, z));
        set(loc.getX(), loc.getY(), loc.getZ());

        return loc;
    }

    public Location safeY(Location location) {
        location.setY(location.getWorld().getHighestBlockYAt(location));
        return location;
    }

    public int rdm(int i) {
        Random random = new Random();
        int j = random.nextInt(2);
        switch (j) {
            case 0:
                return i *= -1;
            case 1:
                return i;
        }
        return i *= -1;
    }
}