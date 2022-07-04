package org.andorvini.EventHandlers;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleEventHandler implements Listener {

    private final JavaPlugin plugin;

    public SimpleEventHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent e) {
        if (!plugin.getConfig().getBoolean("DamageEnabled") && e.getEntityType() == EntityType.PLAYER) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent e) {
        Player deadPlayer = e.getEntity();
        String deadPlayerName = deadPlayer.getName();
        plugin.getServer().getPlayer(deadPlayerName).setGameMode(GameMode.SPECTATOR);
    }

}
