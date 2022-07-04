package org.andorvini;

import org.andorvini.Commands.GameStart;
import org.andorvini.Commands.SetPos;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Config Files
        FileConfiguration config = this.getConfig();
        config.addDefault("Administrator","Andorvini");
        config.options().copyDefaults(true);
        saveConfig();
        // Commands
        getServer().getPluginCommand("gamestart").setExecutor(new GameStart(this));
        getServer().getPluginCommand("setpos").setExecutor(new SetPos());
        // EventHandlers

        // SomeThings
        getServer().getWorld("world").setPVP(false);
    }
}
