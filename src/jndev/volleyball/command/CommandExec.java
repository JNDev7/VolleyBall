package jndev.volleyball.command;

import jndev.volleyball.Message;
import jndev.volleyball.config.ConfigType;
import jndev.volleyball.config.Configs;
import jndev.volleyball.court.Court;
import jndev.volleyball.court.CourtManager;
import jndev.volleyball.court.Courts;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Zombie;

/**
 * Command class, listens for volleyball command
 */
public class CommandExec implements CommandExecutor {
    
    /**
     * method to make the volleyball commands work
     *
     * @param sender  command sender
     * @param command command
     * @param label   part of command immediately followed by /
     * @param args    command arguments
     * @return true
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            if (label.equalsIgnoreCase("volleyball")) {
                if (args.length > 0) {
                    switch (args[0].toLowerCase()) {
                        case "help":
                            if (player.hasPermission("volleyball.help")) player.sendMessage(Message.HELP_MAIN);
                            else player.sendMessage(Message.ERROR_NO_PERMS);
                            break;
                        case "reload":
                            if (player.hasPermission("volleyball.reload")) {
                                CourtManager.saveAll();
                                Configs.getConfig(ConfigType.COURT).reloadConfig();
                                Configs.getConfig(ConfigType.MESSAGE).reloadConfig();
                                CourtManager.loadAll();
                                for (World world : Bukkit.getWorlds()) {
                                    for (Entity entity : world.getEntities()) {
                                        if (entity.getName() != null && ((entity instanceof Slime &&
                                                entity.getName().equalsIgnoreCase(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "BALL")) ||
                                                (entity instanceof Zombie && entity.getName().equalsIgnoreCase(ChatColor.BLACK + "BALL")))) {
                                            entity.remove();
                                        }
                                    }
                                }
                                player.sendMessage(Message.SUCCESS_RELOADED);
                            } else player.sendMessage(Message.ERROR_NO_PERMS);
                            break;
                        case "court":
                            if (args.length > 1) {
                                switch (args[1].toLowerCase()) {
                                    case "help":
                                        if (player.hasPermission("volleyball.court.help"))
                                            player.sendMessage(Message.HELP_COURT);
                                        else player.sendMessage(Message.ERROR_NO_PERMS);
                                        break;
                                    case "list":
                                        if (player.hasPermission("volleyball.court.list"))
                                            player.sendMessage(Message.COURT_LIST.replace("$COURTS$",
                                                    Courts.getAll().keySet().toString()
                                                            .replace("[", "").replace("]", "")));
                                        else player.sendMessage(Message.ERROR_NO_PERMS);
                                        break;
                                    case "create":
                                        if (player.hasPermission("volleyball.court.create")) {
                                            if (args.length > 2) {
                                                if (Courts.get(args[2]) == null && !args[2].equalsIgnoreCase("create")
                                                        && !args[2].equalsIgnoreCase("help") && !args[2].equalsIgnoreCase("list")
                                                        && !args[2].equalsIgnoreCase("remove") && !args[2].equalsIgnoreCase("select") &&
                                                        !args[2].equalsIgnoreCase("info")) {
                                                    new Court(args[2]);
                                                    player.sendMessage(Message.SUCCESS_COURT_CREATED.replace("$COURT$", args[2]));
                                                } else {
                                                    player.sendMessage(Message.ERROR_COURT_EXISTS);
                                                }
                                            } else {
                                                player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                                            }
                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                        break;
                                    case "remove":
                                        if (player.hasPermission("volleyball.court.remove")) {
                                            if (args.length > 2 && Courts.get(args[2]) != null) {
                                                Courts.remove(args[1]);
                                                player.sendMessage(Message.SUCCESS_COURT_REMOVED
                                                        .replace("$COURT$", args[2]));
                                            } else {
                                                player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                                            }
                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                        break;
                                    case "select":
                                        if (player.hasPermission("volleyball.court.select")) {
                                            if (args.length > 2) {
                                                Courts.get(args[2]).select(player);
                                            } else {
                                                player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                                            }
                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                        break;
                                    case "info":
                                        if (player.hasPermission("volleyball.court.info")) {
                                            if (args.length > 2 && Courts.get(args[2]) != null) {
                                                player.sendMessage(Courts.get(args[2]).toString());
                                            } else {
                                                player.sendMessage(Message.ERROR_UNKNOWN_COURT);
                                            }
                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                        break;
                                    default:
                                        if (args.length > 4 || (args.length > 3 && (args[3].equalsIgnoreCase("net") ||
                                                args[3].equalsIgnoreCase("bounds")))) {
                                            Court court = Courts.get(args[1]);
                                            if (court != null && args[2].equalsIgnoreCase("set")) {
                                                switch (args[3].toLowerCase()) {
                                                    case "net":
                                                        if (player.hasPermission("volleyball.court.set.net"))
                                                            court.setNet(player);
                                                        else player.sendMessage(Message.ERROR_NO_PERMS);
                                                        break;
                                                    case "bounds":
                                                        if (player.hasPermission("volleyball.court.set.bounds"))
                                                            court.setBounds(player);
                                                        else player.sendMessage(Message.ERROR_NO_PERMS);
                                                        break;
                                                    case "speed":
                                                        if (player.hasPermission("volleyball.court.set.speed")) {
                                                            try {
                                                                double speed = Double.parseDouble(args[4]);
                                                                court.setSpeed(speed);
                                                                player.sendMessage(Message.SUCCESS_SET_COURT_SPEED
                                                                        .replace("$COURT$", args[1])
                                                                        .replace("$SPEED$", args[4]));
                                                            } catch (NumberFormatException e) {
                                                                player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                                            }
                                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                                        break;
                                                    case "texture":
                                                        if (player.hasPermission("volleyball.court.set.texture")) {
                                                            court.setTexture(args[4]);
                                                            player.sendMessage(Message.SUCCESS_SET_COURT_TEXTURE
                                                                    .replace("$COURT$", args[1])
                                                                    .replace("$URL$", args[4]));
                                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                                        break;
                                                    case "animations":
                                                        if (player.hasPermission("volleyball.court.set.animations")) {
                                                            boolean animations = Boolean.parseBoolean(args[4]);
                                                            court.setAnimations(animations);
                                                            player.sendMessage(Message.SUCCESS_SET_COURT_ANIMATIONS
                                                                    .replace("$COURT$", args[1])
                                                                    .replace("$BOOL$", Boolean.toString(animations)));
                                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                                        break;
                                                    case "enabled":
                                                        if (player.hasPermission("volleyball.court.set.enabled")) {
                                                            boolean enabled = Boolean.parseBoolean(args[4]);
                                                            court.setEnabled(enabled);
                                                            player.sendMessage(Message.SUCCESS_SET_COURT_ENABLED
                                                                    .replace("$COURT$", args[1])
                                                                    .replace("$BOOL$", Boolean.toString(enabled)));
                                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                                        break;
                                                    case "name":
                                                        if (player.hasPermission("volleyball.court.set.name")) {
                                                            court.setName(args[4]);
                                                            player.sendMessage(Message.SUCCESS_SET_COURT_NAME
                                                                    .replace("$OLD$", args[1])
                                                                    .replace("$NEW$", args[4]));
                                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                                        break;
                                                    case "restrictions":
                                                        if (player.hasPermission("volleyball.court.set.restrictions")) {
                                                            boolean restrictions = Boolean.parseBoolean(args[4]);
                                                            court.setRestrictions(restrictions);
                                                            player.sendMessage(Message.SUCCESS_SET_COURT_RESTRICTIONS
                                                                    .replace("$COURT$", args[1])
                                                                    .replace("$BOOL$", Boolean.toString(restrictions)));
                                                        } else player.sendMessage(Message.ERROR_NO_PERMS);
                                                        break;
                                                    default:
                                                        player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                                }
                                            } else player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                        } else player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                                }
                            } else player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                            break;
                        default:
                            player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
                    }
                } else player.sendMessage(Message.ERROR_UNKNOWN_ARGS);
            }
        } else sender.sendMessage("This command can only be executed by a player");
        return true;
    }
}
