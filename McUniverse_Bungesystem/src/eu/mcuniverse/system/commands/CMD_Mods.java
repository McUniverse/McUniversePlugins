package eu.mcuniverse.system.commands;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CMD_Mods extends Command implements Listener {

	private List<String> bannedMods = Lists.newLinkedList();

	public CMD_Mods() {
		super("mods", "mcuniverse.mods", new String[] {});

		bannedMods.add("X-Ray");
		bannedMods.add("Ray");
		bannedMods.add("Auto");
		bannedMods.add("Anti");
		bannedMods.add("ESP");
		bannedMods.add("Click");
		bannedMods.add("Map");
		bannedMods.add("Hack");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 1) {
			sender.sendMessage(new TextComponent(Bungee.prefix + ChatColor.RED + "Usage: /mods <Player>"));
			return;
		}
		String name = args[0];
		if (ProxyServer.getInstance().getPlayer(name) != null) {
			ProxiedPlayer p = ProxyServer.getInstance().getPlayer(name);
			if (!p.isForgeUser()) {
				sender.sendMessage(new TextComponent(Bungee.prefix + ChatColor.RED + "The player " + ChatColor.YELLOW + name
						+ ChatColor.RED + " is not a forge user!"));
				return;
			}
			Map<String, String> mods = p.getModList();
			int size = mods.size();
			sender.sendMessage(new TextComponent(Bungee.prefix + "The player " + ChatColor.YELLOW + name + ChatColor.GRAY
					+ "has the following mods (" + size + "):"));
			mods.forEach(((mod, version) -> {
				sender.sendMessage(new TextComponent(ChatColor.YELLOW + " - " + mod + ":" + version));
			}));
		} else {
			sender.sendMessage(new TextComponent(
					Bungee.prefix + ChatColor.RED + "The player " + ChatColor.YELLOW + name + ChatColor.RED + " is not online!"));
		}
	}

	@EventHandler
	public void onJoin(PostLoginEvent e) {
		if (!e.getPlayer().isForgeUser()) {
			return;
		}
		ProxiedPlayer p = e.getPlayer();
		Map<String, String> mods = p.getModList();
		mods.forEach((name, version) -> {
			bannedMods.forEach(banned -> {
				if (name.toLowerCase().contains(banned.toLowerCase())) {
					ProxyServer.getInstance().getPlayers().forEach(team -> {
						if (team.hasPermission("mcuniverse.team")) {
							team.sendMessage(new TextComponent(Bungee.prefix + ChatColor.DARK_RED + "The player " + ChatColor.YELLOW
									+ p.getName() + ChatColor.DARK_RED + " meight be using banned modifications! "));
							team.sendMessage(new TextComponent(Bungee.prefix + ChatColor.DARK_RED + "Banned mod: " + ChatColor.YELLOW + name + ":" + version));
						}
					});
				}
			});
		});
	}

}
