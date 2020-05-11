package fr.volax.valkyacore.listeners;

import fr.volax.valkyacore.ValkyaCore;
import fr.volax.valkyacore.tools.ConfigBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class FailleChestInteract implements Listener {
    private int task;
    private int timer = 3;

    @EventHandler
    public void onInteract(InventoryClickEvent event){
        Inventory inventory = event.getInventory();
        if(!(event.getWhoClicked() instanceof Player)) return;
        ItemStack item = event.getCurrentItem();
        Player player = (Player) event.getWhoClicked();

        if(inventory.getName() == ValkyaCore.getInstance().getInventoriesManager().getChestFailles().getName()){
            if(item.getType() == Material.CHEST){
                event.setCancelled(true);
                String currentChestLast = item.getItemMeta().getDisplayName().substring(12);
                int currentChest = Integer.parseInt(currentChestLast.substring(1));
                int x = ConfigBuilder.getCInt("chest.chest_" + currentChest + ".X", "failles.yml");
                int y = ConfigBuilder.getCInt("chest.chest_" + currentChest + ".Y", "failles.yml");
                int z = ConfigBuilder.getCInt("chest.chest_" + currentChest + ".Z", "failles.yml");
                if(event.getClick() == ClickType.LEFT){

                    if(ValkyaCore.getInstance().isNull(x, y, z)){
                        player.sendMessage(ValkyaCore.PREFIX + " §eVous ne pouvez pas vous téléporter sur un coffre non setup !");
                    }else{
                        World world = Bukkit.getWorld(ConfigBuilder.getCString("chest.chest_" + currentChest + ".WORLD", "failles.yml"));
                        Location location = new Location(world, x,y,z);
                        player.teleport(location);
                        player.sendMessage(ValkyaCore.PREFIX + " §eVous vous êtes téléporté au coffre n°" + currentChest);
                    }
                }else if(event.getClick() == ClickType.RIGHT){
                    if(ValkyaCore.getInstance().isNull(x, y, z)){
                        player.sendMessage(ValkyaCore.PREFIX + " §eVous ne pouvez pas prévisualiser un coffre non setup !");
                    }else{
                        World world = Bukkit.getWorld(ConfigBuilder.getCString("chest.chest_" + currentChest + ".WORLD", "failles.yml"));
                        Location location = new Location(world, x,y,z);
                        Material block = world.getBlockAt(location).getType();
                        Block block1 = world.getBlockAt(location);
                        ItemStack[] randomItems = new ItemStack[20];


                        //ItemStack randomItem = randomItems[maxInts()];

                        //world.getBlockAt(location).setType(Material.CHEST);

                        Chest chest = (Chest) block1.getState();

                        for(int i = 0; i <= 27; i++){
                            //chest.getInventory().setItem(i, );
                        }

                        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(ValkyaCore.getInstance(), () -> {
                            timer --;
                            if(timer == 0){
                                world.getBlockAt(location).setType(block);
                                timer = 3;
                                Bukkit.getScheduler().cancelTask(task);
                            }
                        }, 20, 20);
                    }
                }
            }
        }
    }
    /**
    private int maxInts(){
        for(int i = 0; i <= 20; i++){
            int testID = ConfigBuilder.getCInt("items.item_" + i + ".ID", "failles.yml");
            if(testID == 0){
                return i - 1;
            }
        }
        return 0;
    }


    private ItemStack[] registersItems(){
        ItemStack[] items = new ItemStack[20];
        for(int i = 0; i <= maxInts(); i++){
            ItemStack item;
        }

        return items;
    }
     */
}
