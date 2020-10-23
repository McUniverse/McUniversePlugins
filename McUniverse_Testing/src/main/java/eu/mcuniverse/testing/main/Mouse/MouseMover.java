package eu.mcuniverse.testing.main.Mouse;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class MouseMover implements Listener {

	List<String> players = new ArrayList<String>();

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().equalsIgnoreCase("/mouse")) {
			if (!players.contains(e.getPlayer().getName())) {
				players.add(e.getPlayer().getName());
			} else {
				players.remove(e.getPlayer().getName());
			}
			e.setCancelled(true);
		}
	}

	// yaw -> x
	// pitch -> y

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (players.contains(p.getName())) {
//			p.sendMessage(ChatColor.RED + "" + ((int) e.getFrom().getYaw()) + " --> " + (int) e.getTo().getYaw());
//			p.sendMessage(ChatColor.GREEN + "" + ((int) e.getFrom().getPitch()) + " --> " + (int) e.getTo().getPitch());
//			if ((int) e.getFrom().getYaw() == (int) e.getTo().getYaw()) {
//				p.sendMessage("CANCEL");
//				return;
//			}
//			if ((int) e.getFrom().getPitch() == (int) e.getTo().getPitch()) {
//				p.sendMessage("CANCEL");
//				return;
//			}
			if (e.getFrom().getYaw() > e.getTo().getYaw()) {
				p.sendMessage("LEFT");
			} else if (e.getFrom().getYaw() < e.getTo().getYaw()) {
				p.sendMessage("RIGHT");
			}
			if (e.getFrom().getPitch() > e.getTo().getPitch()) {
				p.sendMessage("UP");
			} else if (e.getFrom().getPitch() < e.getTo().getPitch()) {
				p.sendMessage("DOWN");
			}
		}
	}

}
