package eu.mcuniverse.system.commands;

import java.util.ArrayList;
import java.util.List;

import eu.mcuniverse.system.Bungee;
import eu.mcuniverse.system.ranks.Rank;
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

public class CMD_Team extends Command {

	public CMD_Team() {
		super("CMD_Team", "", new String[] { "staff", "team" });
	}

	private final String onlinePrefix = ChatColor.GREEN + "Online " + ChatColor.GRAY + "on " + ChatColor.YELLOW;

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (sender instanceof ProxiedPlayer) {
			ProxiedPlayer pp = (ProxiedPlayer) sender;

			pp.sendMessage(new TextComponent(ChatColor.GRAY + "[]" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH
					+ "--------------" + ChatColor.GRAY + "[]" + ChatColor.GOLD + " Staff " + ChatColor.GREEN
					+ "(Online) " + ChatColor.GRAY + "[]" + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH
					+ "--------------" + ChatColor.GRAY + "[]\n"));

			for (Rank r : Rank.values()) {
				if (!Bungee.config.contains(r.getKey()))
					continue;
				if (!isTeamOnline(r))
					continue;
				List<String> members = Bungee.config.getStringList(r.getKey());
				List<String> online = new ArrayList<String>();
				members.forEach(member -> {
					ProxiedPlayer p = ProxyServer.getInstance().getPlayer(member);
					if (p == null)
						return;
					if (p.isConnected())
						online.add(member);
				});

				TextComponent team = new TextComponent(
						r.getColorCode() + r.toString() + ChatColor.DARK_GRAY + " " + Bungee.ARROW + " ");
				
				int i = 0;
				for (String player : online) {
					ProxiedPlayer p = ProxyServer.getInstance().getPlayer(player);
					ComponentBuilder cb = new ComponentBuilder("");
					cb.append(new TextComponent(ChatColor.GREEN + player));
					cb.event(new HoverEvent(Action.SHOW_TEXT,
							new ComponentBuilder(onlinePrefix + p.getServer().getInfo().getName()).create()));

					for (BaseComponent bc : cb.create())
						team.addExtra(bc);
					if (i != online.size() - 1)
						team.addExtra(ChatColor.GRAY + " \u2503 " + ChatColor.GREEN);
					i++;
				}
				pp.sendMessage(team);
//				pp.sendMessage(new TextComponent(team.toPlainText().substring(0, team.toPlainText().length() - 5)));
			}

			pp.sendMessage(new TextComponent("\n" + ChatColor.GRAY + "[]" + ChatColor.DARK_GRAY
					+ ChatColor.STRIKETHROUGH + "--------------" + ChatColor.GRAY + "[]" + ChatColor.GOLD + " Staff "
					+ ChatColor.GREEN + "(Online) " + ChatColor.GRAY + "[]" + ChatColor.DARK_GRAY
					+ ChatColor.STRIKETHROUGH + "--------------" + ChatColor.GRAY + "[]"));

//			pp.sendMessage(new ComponentBuilder("�7[]�8�m--------------�7[] �6McUniverse �7[]�8�m--------------�7[]").create());
//			pp.sendMessage(new ComponentBuilder("").create());
//			
//			sendTeamMembers(pp, "�4Admin �8� ", Team.admins);
//			sendTeamMembers(pp, "�bSrDev �8� ", Team.srdevs);
//			sendTeamMembers(pp, "�bDev �8� ", Team.devs);
//			sendTeamMembers(pp, "�cSrMod �8� ", Team.srmods);
//			sendTeamMembers(pp, "�cMod �8� ", Team.mods);
//			sendTeamMembers(pp, "�aHBuild �8� ", Team.harchis);
//			sendTeamMembers(pp, "�aBuild �8� ", Team.archis);
//			sendTeamMembers(pp, "�eSup �8� ", Team.sups);
//			sendTeamMembers(pp, "�dRekrut �8� ", Team.rekrut);
//			
//			pp.sendMessage(new ComponentBuilder("").create());
//			pp.sendMessage(new ComponentBuilder("�7[]�8�m--------------�7[] �6McUniverse �7[]�8�m--------------�7[]").create());

		} else {
			sender.sendMessage(new TextComponent("No Player"));
		}
	}

	public boolean isTeamOnline(Rank r) {
		List<String> members = Bungee.config.getStringList(r.getKey());
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

//	public void sendTeamMembers(ProxiedPlayer pp, String prefix, List<String> list) {
//		TextComponent comp = new TextComponent(prefix);
//		if (!list.isEmpty()) {
//			for (String s : list) {
//				ComponentBuilder cb = new ComponentBuilder("");
//				cb.append((Team.team.getProxy().getPlayer(s) != null ? "§a" : "§7") + s);
//				if (Team.team.getProxy().getPlayer(s) != null) {
//					cb.event(
//							new HoverEvent(Action.SHOW_TEXT,
//									new ComponentBuilder("§aOnline §7of §e"
//											+ Team.team.getProxy().getPlayer(s).getServer().getInfo().getName())
//													.create()));
//				}
//
//				for (BaseComponent bc : cb.create())
//					comp.addExtra(bc);
//				comp.addExtra(" §8? ");
//			}
//			pp.sendMessage(comp);
//		} else {
//			ComponentBuilder cb = new ComponentBuilder("§eGesucht!");
//			for (BaseComponent base : cb.create())
//				comp.addExtra(base);
//			pp.sendMessage(comp);
//		}
//
//	}
}
