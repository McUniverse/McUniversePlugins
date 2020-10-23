package eu.mcuniverse.chat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import eu.mcuniverse.chat.main.main;


public class Motd_String implements Listener {
	
	@EventHandler
	public void Motd(ServerListPingEvent e) {
		e.setMotd(main.prefix + "§cMaintenance Work");
		e.setMaxPlayers(0);
	}

}
