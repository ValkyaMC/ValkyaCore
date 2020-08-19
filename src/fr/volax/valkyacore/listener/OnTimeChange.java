package fr.volax.valkyacore.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class OnTimeChange implements Listener {
    @EventHandler
    public void onChange(WeatherChangeEvent event){
        if(event.toWeatherState()) event.setCancelled(true);
    }
}
