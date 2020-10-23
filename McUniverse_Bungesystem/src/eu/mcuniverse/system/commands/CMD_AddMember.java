package eu.mcuniverse.system.commands;

import java.io.IOException;
import java.util.List;

import eu.mcuniverse.system.Bungee;
import eu.mcuniverse.system.ranks.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class CMD_AddMember extends Command {

	public CMD_AddMember() {
		super("addmember", "mcuniverse.addmember", new String[] { "" });
	}

	// /addmember <Player> <Rank>
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("list")) {
				String s = "";
				for (Rank r : Rank.values()) {
					s += r.getColorCode() + r.toString() + ", ";
				}
				s = s.substring(0, s.length()-2);
				sender.sendMessage(new TextComponent(Bungee.prefix + s));
				return;
			} else 
				sender.sendMessage(new TextComponent(ChatColor.RED + "Usage: /addmember <Player> <Rank> or /addmember list [rank]"));
		} else if (args.length == 2) {
			String rank = args[1];
			Rank r = null;
			try {
				r = Rank.valueOf(rank.toUpperCase());
			} catch (Exception e) {
				sender.sendMessage(new TextComponent(ChatColor.RED + "The rank " + rank + " doesn't exist!"));
				return;
			}
			if (args[0].equalsIgnoreCase("list")) {
				List<String> members = Bungee.config.getStringList(r.getKey());
				String s = r.getColorCode() + r.toString() + ChatColor.GRAY;
				for (String member : members) {
					s += member + ", ";
				}
				sender.sendMessage(new TextComponent(s));
				return;
			}
			String player = args[0];
			ProxiedPlayer p = ProxyServer.getInstance().getPlayer(player);
			List<String> current = Bungee.config.getStringList(r.getKey());
			current.add(player);
			Bungee.config.set(r.getKey(), current);
			sender.sendMessage(new TextComponent(Bungee.prefix + ChatColor.GRAY + "You added " + ChatColor.YELLOW + p.getName() + ChatColor.GRAY + " to the group " + r.getColorCode() + r.toString()));
			try {
				ConfigurationProvider.getProvider(YamlConfiguration.class).save(Bungee.config, Bungee.file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else 
			sender.sendMessage(new TextComponent(ChatColor.RED + "Usage: /addmember <Player> <Rank> or /addmember list [rank]"));
	}

}
