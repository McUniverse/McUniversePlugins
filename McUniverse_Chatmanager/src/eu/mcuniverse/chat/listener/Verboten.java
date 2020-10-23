package eu.mcuniverse.chat.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import eu.mcuniverse.chat.main.main;


public class Verboten implements Listener {

	@EventHandler
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		if (p.getName().contains("Jay"))
			return;
		if (event.getMessage().equalsIgnoreCase("/pl") || event.getMessage().equalsIgnoreCase("/ver")
				|| event.getMessage().equalsIgnoreCase("/about") || event.getMessage().equalsIgnoreCase("/pl ?")
				|| event.getMessage().equalsIgnoreCase("/pl help") || event.getMessage().equalsIgnoreCase("/pl auto")
				|| event.getMessage().equalsIgnoreCase("/version") || event.getMessage().equalsIgnoreCase("/plugins")
				|| event.getMessage().startsWith("/?")) {
			event.setCancelled(true);
			p.sendMessage(main.noperm);
		}
		if (event.getMessage().startsWith("/pl ") || event.getMessage().startsWith("/plugins ")) {
			event.setCancelled(true);
		}
		if (event.getMessage().startsWith("//calc")) {
			event.setCancelled(true);
			p.sendMessage(main.noperm);
		}

	}

	@EventHandler
	public void Reload11(PlayerCommandPreprocessEvent event) {
		String[] cmd = event.getMessage().substring(1).split(" ");
		if (event.getPlayer().getName().contains("Jay"))
			return;
		if (cmd[0].startsWith("minecraft")) {
			event.setCancelled(true);
		}
		if (cmd[0].startsWith("minecraft:")) {
			event.setCancelled(true);
		}
		if (cmd[0].startsWith("minecraft:me")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void Reload1(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage();
		if (command.equalsIgnoreCase("/minecraft:me") || command.equalsIgnoreCase("/minecraft:me")) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerCommandPreproces(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		if (p.getName().contains("Jay"))
			return;
		if (event.getMessage().startsWith("/bukkit:") || event.getMessage().equalsIgnoreCase("/bukkit:weather")
				|| event.getMessage().equalsIgnoreCase("/bukkit:xp")
				|| event.getMessage().equalsIgnoreCase("/Bukkit:me")
				|| event.getMessage().equalsIgnoreCase("/bukkit:me") || event.getMessage().equalsIgnoreCase("help")
				|| event.getMessage().equalsIgnoreCase("/bukkit:?")
				|| event.getMessage().equalsIgnoreCase("/bukkit:help")
				|| event.getMessage().equalsIgnoreCase("/bukkit:plugins") || event.getMessage().startsWith("/bukkit")) {
			event.setCancelled(true);
			p.sendMessage(main.noperm);
		}
	}

	@EventHandler
	public void onMe(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("/me")) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerCommandPreproces1(PlayerCommandPreprocessEvent event) {
		Player p = event.getPlayer();
		if (event.getMessage().startsWith("/minecraft:") || event.getMessage().equalsIgnoreCase("/minecraft:weather")
				|| event.getMessage().equalsIgnoreCase("/help") || event.getMessage().equalsIgnoreCase("/minecraft:xp")
				|| event.getMessage().equalsIgnoreCase("/minecraft:me")
				|| event.getMessage().equalsIgnoreCase("/minecraft:me") || event.getMessage().equalsIgnoreCase("help")
				|| event.getMessage().equalsIgnoreCase("/minecraft:?")
				|| event.getMessage().equalsIgnoreCase("/minecraft:help")
				|| event.getMessage().equalsIgnoreCase("/minecraft:plugins")
				|| event.getMessage().startsWith("/minecraft")) {
			p.sendMessage(main.noperm);
			event.setCancelled(true);
			return;
		}
	}

	@EventHandler
	public void onCommandBlock2(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if ((e.getMessage().contains("/pex")) && !p.hasPermission("mcuniverse.pex")) {
			e.setCancelled(true);
			p.sendMessage(main.noperm);
		}
	}

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (!e.isCancelled()) {
			Player p = e.getPlayer();
			String msg = e.getMessage().split(" ")[0];
			HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(msg);
			if (topic == null) {
				p.sendMessage(main.prefix + "§7The Command §e" + msg + " §7does not exist!");
				e.setCancelled(true);
			}
		}
	}
}