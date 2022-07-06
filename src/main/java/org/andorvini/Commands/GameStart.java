package org.andorvini.Commands;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GameStart implements CommandExecutor {

    private final JavaPlugin plugin;

    public GameStart(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Location PvPLocationToTP = plugin.getConfig().getLocation("PvPLocation");
        Timer gameTimer = new Timer();
        ItemStack woodenPickaxe = new ItemStack(Material.WOODEN_PICKAXE);
        ItemStack woodenAxe = new ItemStack(Material.WOODEN_AXE);
        BossBar timerBar = plugin.getServer().createBossBar("Осталось времени:",BarColor.YELLOW,BarStyle.SEGMENTED_10);
        int invisibilityDuration = plugin.getConfig().getInt("MatchDuration") * 20;
        PotionEffect invisibility = new PotionEffect(PotionEffectType.INVISIBILITY,invisibilityDuration,1,true,false);
        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        ArrayList<Player> pvpPlayers = new ArrayList<>();
        if (commandSender.isOp()) {
            for (Player onlinePlayer : players ) {
                while (true) {
                    double x = Math.random() * 2000 - 1000;
                    double y = new Random().nextInt(256);
                    double z = Math.random() * 2000 - 1000;
                    World world = Bukkit.getWorld("world");
                    Location locationRandom = new Location(world, x, y, z);
                    Block highestBlock = world.getHighestBlockAt(locationRandom);
                    int highestBlockY = highestBlock.getY();
                    Location checkLocation = new Location(world, x, highestBlockY, z);
                    Location locationToTp = new Location(world, x, highestBlockY + 1, z);
                    if (world.getBlockAt(checkLocation).getType() != Material.WATER) {
                        onlinePlayer.teleport(locationToTp);
                        break;
                    } else {
                        continue;
                    }
                }
                pvpPlayers.add(onlinePlayer);
                onlinePlayer.addPotionEffect(invisibility);
                onlinePlayer.getInventory().addItem(woodenAxe,woodenPickaxe);
                timerBar.addPlayer(onlinePlayer);
                timerBar.setProgress(1);
                gameTimer.scheduleAtFixedRate(new TimerTask() {
                    int i = plugin.getConfig().getInt("MatchDuration"); //ВЕРНУТЬ 600 И В 67 СТРОКЕ 300
                    double progressValue = 1;
                    public void run() {
                        timerBar.setTitle("Осталось времени: " + i);
                        timerBar.setProgress(progressValue);
                        progressValue = progressValue - 0.0016666666666667;
                        if (i == 80) {
                            onlinePlayer.sendTitle(ChatColor.RED + "Осталось 5 минут",null);
                        }
                        if (i == 60) {
                            onlinePlayer.sendTitle(ChatColor.RED + "Осталось 1 минута",null);
                        }
                        if (i == 10) {
                            onlinePlayer.sendTitle(ChatColor.RED + "Осталось 10 секунд",null);
                        }
                        if (i == 5) {
                            onlinePlayer.sendTitle(ChatColor.RED + "Осталось 5 секунд",null);
                        }
                        if (i == 4) {
                            onlinePlayer.sendTitle(ChatColor.RED + "Осталось 4 секунды",null);
                        }
                        if (i == 3) {
                            onlinePlayer.sendTitle(ChatColor.RED + "Осталось 3 секунды",null);
                        }
                        if (i == 2) {
                            onlinePlayer.sendTitle(ChatColor.RED + "Осталось 2 секунды",null);
                        }
                        if (i == 1) {
                            onlinePlayer.sendTitle(ChatColor.RED + "Осталось 1 секунда",null);
                        }
                        if (i == 0) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    onlinePlayer.teleport(PvPLocationToTP);
                                }
                            }.runTask(plugin);
                            timerBar.setVisible(false);
                        }
                        if (i == -3) {
                            onlinePlayer.sendTitle(ChatColor.RED + "PvP будет включено через 3",null);
                        }
                        if (i == -4) {
                            onlinePlayer.sendTitle(ChatColor.YELLOW + "2",null);
                        }
                        if (i == -5) {
                            onlinePlayer.sendTitle(ChatColor.GREEN + "1",null);
                        }
                        if (i == -6) {
                            plugin.getConfig().set("DamageEnabled",true);
                            plugin.getServer().getWorld("world").setPVP(true);
                            onlinePlayer.sendTitle(ChatColor.GREEN + "PvP включено",null);
                            gameTimer.cancel();
                            while (true) {
                                if (onlinePlayer.getGameMode() == GameMode.SPECTATOR) {
                                    pvpPlayers.remove(onlinePlayer);
                                    continue;
                                }
                                if (pvpPlayers.size() == 1) {
                                    onlinePlayer.sendTitle(pvpPlayers.get(0).getDisplayName() + "Победил",null);
                                    break;
                                } else {
                                    continue;
                                }
                            }
                        }
                        i--;
                    }
                },0,1000);
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "Вы не админ");
        }
        return true;

    }
}
