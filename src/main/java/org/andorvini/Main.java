package org.andorvini;

import org.andorvini.Commands.GameStart;
import org.andorvini.Commands.SetPos;
import org.andorvini.EventHandlers.SimpleEventHandler;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Variables
        Location PvPLocation = new Location(getServer().getWorld("world"),0,0,0);
        // Config Files
        FileConfiguration config = this.getConfig();
        config.addDefault("Administrator","Andorvini");
        config.addDefault("DamageEnabled",false);
        config.addDefault("PvPLocation", PvPLocation);
        config.addDefault("MatchDuration", 600);
        config.options().copyDefaults(true);
        saveConfig();
        // Commands
        getServer().getPluginCommand("gamestart").setExecutor(new GameStart(this));
        getServer().getPluginCommand("setpos").setExecutor(new SetPos(this));
        // EventHandlers
        getServer().getPluginManager().registerEvents(new SimpleEventHandler(this),this);
        // SomeThings
        getServer().getWorld("world").setPVP(false);
    }
}
