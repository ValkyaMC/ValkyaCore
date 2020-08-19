package fr.volax.valkyacore.tool;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StaffInventory {
    Player player;
    GameMode mode;
    ItemStack[] contents;
    ItemStack[] armor;

    public StaffInventory(Player player) {
        this.player = player;
    }

    public void save() {
        this.contents = this.player.getInventory().getContents();
        this.armor = this.player.getInventory().getArmorContents();
        this.mode = this.player.getGameMode();
    }

    public void give() {
        this.player.getInventory().clear();
        for (int i = 0; i < this.contents.length; ) {
            this.player.getInventory().setItem(i, this.contents[i]);
            i++;
        }
        this.player.getInventory().setHelmet(this.armor[3]);
        this.player.getInventory().setChestplate(this.armor[2]);
        this.player.getInventory().setLeggings(this.armor[1]);
        this.player.getInventory().setBoots(this.armor[0]);
        this.player.updateInventory();
        this.player.setGameMode(this.mode);
    }
}
