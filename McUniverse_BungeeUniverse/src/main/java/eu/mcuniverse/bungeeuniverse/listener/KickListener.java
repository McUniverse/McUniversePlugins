package eu.mcuniverse.bungeeuniverse.listener;

import eu.mcuniverse.bungeeuniverse.Core;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class KickListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onKick(ServerKickEvent e) {
		e.setCancelServer(ProxyServer.getInstance().getServerInfo("Lobby"));
		e.setCancelled(true);

		Core.getInstance().getLogger()
				.info("The palyer " + e.getPlayer().getName() + " was send to the lobby because he was kicked from "
						+ e.getKickedFrom().getName() + "! Kick reason: " + e.getKickReason());
		
		e.getPlayer().sendMessage(new ComponentBuilder()
				.append("McUniverse")
				.color(ChatColor.GOLD)
				.append(" » ")
				.color(ChatColor.DARK_GRAY)
				.append("It looks like your previous server went down. Sending you to the Lobby . . .")
				.color(ChatColor.RED)
				.event(new HoverEvent(Action.SHOW_TEXT, new ComponentBuilder("I'll give my best. It won't hurt, I promise!").color(ChatColor.GREEN).create()))
				.create());

	}

}
