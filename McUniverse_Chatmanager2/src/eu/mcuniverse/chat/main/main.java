package eu.mcuniverse.chat.main;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import eu.mcuniverse.chat.commands.CMD_CC;
import eu.mcuniverse.chat.commands.CMD_Gamemode;
import eu.mcuniverse.chat.commands.CMD_Rang;
import eu.mcuniverse.chat.commands.CMD_Reload;
import eu.mcuniverse.chat.listener.ChatEvent;
import eu.mcuniverse.chat.listener.JoinListener;
import eu.mcuniverse.chat.listener.Motd_String;
import eu.mcuniverse.chat.listener.Verboten;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class main extends JavaPlugin implements Listener {

	public static Scoreboard sb;
	PluginManager pm = Bukkit.getServer().getPluginManager();
	public static main instance;
	public static boolean first = false;

	public static String prefix = "§6McUniverse §7» ";
	public static String noperm = "§cYou don't have the permission to use that command.";

	public static File WhitelistMessagecfg = new File("plugins/System/", "WhitelistMessage.yml");
	public static YamlConfiguration WhitelistMessage = YamlConfiguration.loadConfiguration(WhitelistMessagecfg);

	public void onEnable() {

		Bukkit.getConsoleSender().sendMessage("§6McUniverse §8» §aMcUniverse_Grund activated.");
		Bukkit.getConsoleSender().sendMessage("§6McUniverse §8» §7Authors: §eVlogDev, JayReturns");

		pm.registerEvents(this, this);

		pm.registerEvents(new ChatEvent(), this);
		pm.registerEvents(new JoinListener(), this);
		pm.registerEvents(new Motd_String(), this);
		pm.registerEvents(new Verboten(), this);
		pm.registerEvents(new CMD_Reload(this), this);

		getCommand("chatclear").setExecutor(new CMD_CC());
		getCommand("cc").setExecutor(new CMD_CC());
		getCommand("gm").setExecutor(new CMD_Gamemode());

		sb = Bukkit.getScoreboardManager().getNewScoreboard();

		sb.registerNewTeam("00000Owner");
		sb.registerNewTeam("00001Admin");
		sb.registerNewTeam("00002Dev");
		sb.registerNewTeam("00003Mod");
		sb.registerNewTeam("00004Supp");
		sb.registerNewTeam("00005Build");
		sb.registerNewTeam("00006Ultimate");
		sb.registerNewTeam("00007MVPPlus");
		sb.registerNewTeam("00008MVP");
		sb.registerNewTeam("00009VIPplus");
		sb.registerNewTeam("00010VIP");
		sb.registerNewTeam("00011Player");

		sb.getTeam("00000Owner").setPrefix("§4Owner §8┃ §7");
		sb.getTeam("00001Admin").setPrefix("§cAdmin §8┃ §7");
		sb.getTeam("00002Dev").setPrefix("§bDev §8┃ §7");
		sb.getTeam("00003Mod").setPrefix("§3Mod §8┃ §7");
		sb.getTeam("00004Supp").setPrefix("§9Sup §8┃ §7");
		sb.getTeam("00005Build").setPrefix("§aBuilder §8┃ §7");
		sb.getTeam("00009VIPplus").setPrefix("§2");
		sb.getTeam("00010VIP").setPrefix("§a");
		sb.getTeam("00007MVPPlus").setPrefix("§6");
		sb.getTeam("00008MVP").setPrefix("§e");
		sb.getTeam("00006Ultimate").setPrefix("§d");
		sb.getTeam("00011Player").setPrefix("§7");

		for (Player p : Bukkit.getOnlinePlayers())
			setGroup(p);
	}

	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§6McUniverse §8» §cMcUniverse_Grund deactivated.");
		Bukkit.getConsoleSender().sendMessage("§6McUniverse §8» §7Authors: §eVlogDev, JayReturns");
	}

	private void setGroup(Player p) {
		String group = "";
		PermissionUser user = PermissionsEx.getUser(p);
		String userG = user.getParentIdentifiers().get(0);
		boolean onlyColor = false;
		switch (userG.toLowerCase()) {
		case "owner":
			group = "00000Owner";
			break;
		case "admin":
			group = "00001Admin";
			break;
		case "developer":
			group = "00002Dev";
			break;
		case "moderator":
			group = "00003Mod";
			break;
		case "supporter":
			group = "00004Supp";
			break;
		case "builder":
			group = "00005Build";
			break;
		case "vipplus":
			group = "00009VIPplus";
			onlyColor = true;
			break;
		case "vip":
			group = "00010VIP";
			onlyColor = true;
			break;
		case "mvpplus":
			group = "00007MVPPlus";
			onlyColor = true;
			break;
		case "mvp":
			group = "00008MVP";
			onlyColor = true;
			break;
		case "ultimate":
			group = "00006Ultimate";
			onlyColor = true;
			break;
		case "default":
			group = "00011Player";
			onlyColor = true;
			break;
		default:
			group = "00011Player";
			onlyColor = true;
			break;
		}
		
		sb.getTeam(group).addEntry(p.getName());
		if (onlyColor) {
			try {
				CMD_Rang rank = CMD_Rang.valueOf(userG.toUpperCase());
				p.setPlayerListName(rank.getColorCode() + p.getName());
				p.setDisplayName(rank.getColorCode() + p.getName());
			} catch (Exception e) {
				p.sendMessage("§4An internal error occured: Tab prefix rank");
			}
		} else {
			p.setPlayerListName(sb.getTeam(group).getPrefix() + "§7" +  p.getName());
			p.setDisplayName(sb.getTeam(group).getPrefix() + "§7" + p.getName());
		}
		for (Player players : Bukkit.getOnlinePlayers()) {
			players.setScoreboard(sb);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e) {
		setGroup(e.getPlayer());
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return true;

		Player p = (Player) sender;

		
		
		if (command.getName().equalsIgnoreCase("rank")) {
			if (p.hasPermission("mcuniverse.rank")) {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("list")) {
						p.sendMessage(prefix + "§aList of Ranks");
						String msg = "";
						for (CMD_Rang r : CMD_Rang.values()) {
							msg = msg + r.getColorCode() + r.getName() + "§7, ";
						}
						p.sendMessage(prefix + msg);
						return true;
					}
				} else if (args.length == 2) {
					String rank = args[1];
					Player target = Bukkit.getPlayer(args[0]);
					if (target != null) {
						try {
							CMD_Rang r = CMD_Rang.valueOf(rank.toUpperCase());
							p.performCommand("pex user " + target.getName() + " group set " + r.toString());
							Bukkit.broadcastMessage(
									"§7[]§8§m------------------§7[] §6McUniverse §7[]§8§m------------------§7[]");
							Bukkit.broadcastMessage(
									prefix + "§7The Player §e" + target.getName() + " §7has a new Rank.   ");
							Bukkit.broadcastMessage(prefix + "§cRank§7: " + r.getColorCode() + r.getName());
							Bukkit.broadcastMessage(
									"§7[]§8§m------------------§7[] §6McUniverse §7[]§8§m------------------§7[]");
						} catch (Exception e) {
							p.sendMessage(prefix + "§cThis rank does not exist!");
							p.sendMessage(prefix + "§aList of Rank:");
							String msg = "";
							for (CMD_Rang r : CMD_Rang.values()) {
								msg = msg + r.getColorCode() + r.getName() + "§7, ";
							}
							p.sendMessage(prefix + msg);
						}
					} else {
						p.sendMessage(prefix + "§cThe Player is not online!");
					}
				} else {
					p.sendMessage(prefix + "§c/" + label + " <Name> <Rank>");
				}
			} else {
				p.sendMessage(noperm);
			}
		}

		return true;
	}

}