package fr.volax.valkyacore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AntiInsulteAsyncPlayerChat implements Listener {
    @EventHandler
    public void antiInsulte(AsyncPlayerChatEvent event){
        String messages = event.getMessage();
        if(contains(messages, "NTM") || contains(messages, "FDP") || contains(messages, "EZ") || contains(messages, "TG") || contains(messages, "NIQUE") || contains(messages, "BITE") || contains(messages, "CHATTE") || contains(messages, "ENCULE") || contains(messages, "BAISE") || contains(messages, "SUCEUR") || contains(messages, "PUTE") )
            event.setMessage(messages.replaceAll(messages, "§7***§r"));
    }

    private boolean contains(String messages, String insulte){
        return messages.toUpperCase().contains(insulte);
    }
}