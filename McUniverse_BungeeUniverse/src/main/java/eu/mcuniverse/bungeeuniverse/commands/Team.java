package eu.mcuniverse.bungeeuniverse.commands;

import java.util.ArrayList;
import java.util.List;

import eu.mcuniverse.bungeeuniverse.Core;
import eu.mcuniverse.bungeeuniverse.data.Messages;
import eu.mcuniverse.bungeeuniverse.ranks.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Team extends Command {

	public Team() {
		super("team", "", new String[] { "staff" });
	}

	private final String ONLINE_PREFIX = ChatColor.GREEN + "Online " + ChatColor.GRAY + "on " + ChatColor.YELLOW;

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(TextComponent.fromLegacyText("No Player"));
			return;
		}

		ProxiedPlayer pp = (ProxiedPlayer) sender;

		pp.sendMessage(new TextComponent(ChatColor.GRAY + "[]" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH
				+ "--------------" + ChatColor.GRAY + "[]" + ChatColor.GOLD + " Staff " + ChatColor.GREEN
				+ "(Online) " + ChatColor.GRAY + "[]" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH
				+ "--------------" + ChatColor.GRAY + "[]\n"));

		for (Rank r : Rank.values()) {
			if (!Core.getConfig().contains(r.getKey()))
				continue;
			if (!isTeamOnline(r))
				continue;
			List<String> members = Core.getConfig().getStringList(r.getKey());
			List<String> online = new ArrayList<String>();
			members.forEach(member -> {
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(member);
				if (p == null)
					return;
				if (p.isConnected())
					online.add(member);
			});

			TextComponent team = new TextComponent(
					r.getColorCode() + r.toString() + ChatColor.DARK_GRAY + " " + Messages.ARROW + " ");

			int i = 0;
			for (String player : online) {
				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(player);
				ComponentBuilder cb = new ComponentBuilder("");
				cb.append(new TextComponent(ChatColor.GREEN + player));
				cb.event(new HoverEvent(Action.SHOW_TEXT,
						new ComponentBuilder(ONLINE_PREFIX + p.getServer().getInfo().getName()).create()));

				for (BaseComponent bc : cb.create())
					team.addExtra(bc);
				if (i != online.size() - 1)
					team.addExtra(ChatColor.GRAY + " \u2503 " + ChatColor.GREEN);
				i++;
			}
			pp.sendMessage(team);
		}

		pp.sendMessage(new TextComponent("\n" + ChatColor.GRAY + "[]" + ChatColor.DARK_GRAY
				+ ChatColor.STRIKETHROUGH + "--------------" + ChatColor.GRAY + "[]" + ChatColor.GOLD + " Staff "
				+ ChatColor.GREEN + "(Online) " + ChatColor.GRAY + "[]" + ChatColor.DARK_GRAY
				+ ChatColor.STRIKETHROUGH + "--------------" + ChatColor.GRAY + "[]"));

	}

	public boolean isTeamOnline(Rank r) {
		List<String> members = Core.getConfig().getStringList(r.getKey());
		boolean online = false;
		for (String s : members) {
			ProxiedPlayer p = ProxyServer.getInstance().getPlayer(s);
			try {
				if (p.isConnected()) {
					online = true;
					break;
				}
			} catch (Exception e) {
				continue;
			}
		}
		return online;
	}

}
