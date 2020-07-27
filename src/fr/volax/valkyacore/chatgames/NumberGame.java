package fr.volax.valkyacore.chatgames;

import fr.volax.valkyacore.ValkyaCore;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Random;

public class NumberGame implements Listener {
    private int currentNumber1, currentNumber2, operator, task, result;
    private double temps;
    private boolean isEnabled;

    public void newGame(){
        stopTimer();
        operator = new Random().nextInt(2);

        currentNumber1 = new Random().nextInt(2000);
        currentNumber2 = new Random().nextInt(2000);
        temps = 0;

        for(Player player : Bukkit.getOnlinePlayers()) {
            TextComponent mainComponent = new TextComponent(ValkyaCore.PREFIX + " ");
            TextComponent subComponent = new TextComponent("§eCalcul de rapidité");
            subComponent.setHoverEvent( new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§6" + getCalcul()).create()));
            mainComponent.addExtra(subComponent);
            mainComponent.addExtra( " §e! Vous avez §630 §esecondes !");
            player.spigot().sendMessage(mainComponent);
        }
        startTimer();
    }

    private String getOperator() {
        switch (operator){
            case 1:
                return "-";
            default:
                return "+";
        }
    }

    private int getResult(){
        switch (operator){
            case 1:
                result = (currentNumber1 - currentNumber2);
                return result;
            default:
                return (currentNumber1 + currentNumber2);
        }
    }

    private void setResult(){
        switch (operator){
            case 1:
                result = (currentNumber1 - currentNumber2);
            default:
                result = (currentNumber1 + currentNumber2);
        }
    }


    private String getCalcul(){ 
        switch (operator){
            case 1:
                return currentNumber1 + " - " + currentNumber2;
            default:
                return currentNumber1 + " + " + currentNumber2;
        }
    }

    private void startTimer(){
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(ValkyaCore.getInstance(), new Runnable() {
            public void run() {
                temps = temps + 0.1;
                setResult();
                isEnabled = true;

                if(temps >= 30){
                    Bukkit.broadcastMessage(ValkyaCore.PREFIX + " §eFin du temps ! Personne n'a trouvé la réponse ! Réponse: §6" + getResult());
                    stopTimer();
                    temps = 0;
                    isEnabled = false;
                }
            }
        },20, 2);
    }

    public void stopTimer(){
        Bukkit.getScheduler().cancelTask(task);
        temps = 0;
        isEnabled = false;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if(ValkyaCore.getInstance().getNumberGame().isEnabled){
            if(event.getMessage().contains(Integer.toString(ValkyaCore.getInstance().getNumberGame().getResult()))){
                Bukkit.broadcastMessage(ValkyaCore.PREFIX + " §eBravo à §6" + event.getPlayer().getName() + " §equi a trouvé §6" + ValkyaCore.getInstance().getNumberGame().getResult() + " §een §6" + ((int) ValkyaCore.getInstance().getNumberGame().temps) + "§es !");
                event.setCancelled(true);
                ValkyaCore.economy.depositPlayer(event.getPlayer(), 2500);
                event.getPlayer().sendMessage(ValkyaCore.PREFIX + " §eTu gagnes §62500$ §egrâce à l'event !");
                ValkyaCore.getInstance().getNumberGame().stopTimer();
            }
        }
    }
}
