package eu.mcuniverse.system.listener;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinListener implements Listener {

	// Owner

	@EventHandler
	public void onJoinO(PostLoginEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.owner")) {
			BungeeCord.getInstance().broadcast("�4Owner �7| �4" + e.getPlayer().getName() + "�7 is now �aOnline");
		}
	}

	@EventHandler
	public void onLeaveO(PlayerDisconnectEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.owner")) {
			BungeeCord.getInstance().broadcast("�4Owner �7| �4" + e.getPlayer().getName() + "�7 is now �cOffline");
		}
	}

	// Admin

	@EventHandler
	public void onJoinA(PostLoginEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.admin")) {
			BungeeCord.getInstance().broadcast("�cAdmin �7| �c" + e.getPlayer().getName() + "�7 is now �aOnline");
		}
	}

	@EventHandler
	public void onLeaveA(PlayerDisconnectEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.admin")) {
			BungeeCord.getInstance().broadcast("�cAdmin �7| �c" + e.getPlayer().getName() + "�7 is now �cOffline");
		}
	}

	// Developer

	@EventHandler
	public void onJoinD(PostLoginEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.developer")) {
			BungeeCord.getInstance().broadcast("�bDeveloper �7| �b" + e.getPlayer().getName() + "�7 is now �aOnline");
		}
	}

	@EventHandler
	public void onLeaveD(PlayerDisconnectEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.developer")) {
			BungeeCord.getInstance().broadcast("§bDeveloper §7| §b" + e.getPlayer().getName() + "�7 is now �cOffline");
		}
	}

	// Developer

	@EventHandler
	public void onJoinM(PostLoginEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.moderator")) {
			BungeeCord.getInstance().broadcast("§9Moderator §7| §9" + e.getPlayer().getName() + "§7 is now §aOnline");
		}
	}

	@EventHandler
	public void onLeaveM(PlayerDisconnectEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.moderator")) {
			BungeeCord.getInstance().broadcast("§9Moderator §7| §9" + e.getPlayer().getName() + "§7 is now §cOffline");
		}
	}
	
	@EventHandler
	public void onJoinB(PostLoginEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.builder")) {
			BungeeCord.getInstance().broadcast("§eBuilder §7| §e" + e.getPlayer().getName() + "§7 is now §aOnline");
		}
	}

	@EventHandler
	public void onLeaveB(PlayerDisconnectEvent e) {
		final ProxiedPlayer p = e.getPlayer();
		if (p.hasPermission("mcuniverse.builder")) {
			BungeeCord.getInstance().broadcast("§eBuilder §7| §e" + e.getPlayer().getName() + "§7 is now §cOffline");
		}
	}
}
