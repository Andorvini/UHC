package org.andorvini.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SetPos implements CommandExecutor {

    private final JavaPlugin plugin;
    Location setPositionLocation = new Location(Bukkit.getWorld("world"),0,0,0);

    public SetPos(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender.isOp()){
            Player player = (Player) commandSender;
            setPositionLocation = player.getLocation();
            player.sendMessage(ChatColor.GREEN + "Место PvP установлено на координатах" + setPositionLocation);
            plugin.getConfig().set("PvPLocation",setPositionLocation);
        } else {
            commandSender.sendMessage(ChatColor.RED + "Вы не админ");
        }
        return true;
    }
}
