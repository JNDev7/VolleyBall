package jndev.volleyball;

import jndev.volleyball.ball.BallListener;
import jndev.volleyball.command.CommandExec;
import jndev.volleyball.command.CommandTabComplete;
import jndev.volleyball.config.ConfigManager;
import jndev.volleyball.config.ConfigType;
import jndev.volleyball.config.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the plugin. registers commands and listeners
 *
 * @author Jeremy Noesen
 */
public class VolleyBall extends JavaPlugin implements Listener {

    public static VolleyBall plugin;
    
    private final Permission admin = new Permission("volleyball.admin");
    
    ConfigManager courtConfig = Configs.getConfig(ConfigType.COURT);
    ConfigManager messageConfig = Configs.getConfig(ConfigType.MESSAGE);

    /**
     * runs when the plugin starts
     */
    public void onEnable() {
        plugin = this;
        
        messageConfig.saveDefaultConfig();
        courtConfig.saveDefaultConfig();
        
        Message.reloadMessages();

        Message msg = new Message();

        plugin.getServer().getConsoleSender().sendMessage(msg.STARTUP);

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new BallListener(), plugin);
        
        pm.addPermission(admin);

        getCommand("volleyball").setExecutor(new CommandExec());
        getCommand("volleyball").setTabCompleter(new CommandTabComplete());


        getConfig().options().copyDefaults(true);
        saveConfig();
    
        int count = 0;
    
        plugin.getServer().getConsoleSender().sendMessage(msg.CLEANING);
    
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getName() != null && ((entity instanceof Slime &&
                        entity.getName().equalsIgnoreCase(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL")) ||
                        (entity instanceof Zombie && entity.getName().equalsIgnoreCase(ChatColor.BLACK + "BALL")))) {
                    entity.remove();
                    count++;
                }
            }
        }
    
        plugin.getServer().getConsoleSender().sendMessage(msg.DONE_CLEANING.replace("$COUNT$",
                Integer.toString(count)));
        
    }

    /**
     * runs when the plugin stops
     */
    public void onDisable() {
        plugin = null;
    }
    
    public static VolleyBall getInstance() {
        return plugin;
    }
    
}


