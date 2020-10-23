package eu.mcuniverse.testing.main;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ThirdPerson implements Listener {

	Map<String, Location> inSim = new LinkedHashMap<String, Location>();
//	List<String> inSim = new ArrayList<String>();
	final int RADIUS = 5;
	Map<String, double[]> angles = new LinkedHashMap<String, double[]>(); // name, [Theta, Phi]

	@EventHandler
	public void onThirdPersonMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (inSim.containsKey(p.getName())) {
			// Get every location
			Location loc = inSim.get(p.getName()).clone();
			Location tmp = loc.clone();
			Location block = loc.clone().subtract(0, 1, 0);
			Location from = e.getFrom();
			Location to = e.getTo();

			// Get angles
			double theta = angles.get(p.getName())[0];
			double phi = angles.get(p.getName())[1];

			// Convert mouse movement to angle change
			if (from.getYaw() > to.getYaw()) {
				phi += 0.07;
				p.sendMessage("LEFT");
			} else if (from.getYaw() < to.getYaw()) {
				phi -= 0.07;
				p.sendMessage("RIGHT");
			}
			if (from.getPitch() > to.getPitch()) {
				theta += 0.07;
				p.sendMessage("UP");
			} else if (from.getPitch() < to.getPitch()) {
				theta -= 0.07;
				p.sendMessage("DOWN");
			}

			double deltaYaw = to.getYaw() - from.getYaw();
			double deltaPitch = to.getPitch() - from.getPitch();
			DecimalFormat df = new DecimalFormat("#.#");
			
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					new TextComponent("§eYaw: §f" + df.format(deltaYaw) + " §ePitch: §f" + df.format(deltaPitch)));

			// Calculate x,y,z
			double x = RADIUS * sin(theta) * cos(phi);
			double y = RADIUS * sin(theta) * sin(phi);
			double z = RADIUS * cos(theta);
			loc.setX(tmp.getX() + x);
			loc.setY(tmp.getY() + y);
			loc.setZ(tmp.getZ() + z);

			
			// Calculate Vector
			Vector v = new Vector();
			v.setX(block.getX() - loc.getX());
			v.setY(block.getY() - loc.getY());
			v.setZ(block.getZ() - loc.getZ());
			loc.setDirection(v);

			p.teleport(loc);

			angles.remove(p.getName());
			angles.put(p.getName(), new double[] { theta, phi });
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null)
			return;

		if (e.getClickedBlock().getType() == Material.DIAMOND_BLOCK && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			e.setCancelled(true);

			Player p = e.getPlayer();
			if (!inSim.containsKey(p.getName())) {
				Location loc = e.getClickedBlock().getLocation().add(0, 1, 0);
				inSim.put(p.getName(), loc);
				angles.put(p.getName(), new double[] { 0, 0 });
				p.sendMessage("Controlling now!");
			}
		}
	}

	@EventHandler
	public void onInteractQuit(PlayerInteractEvent e) {
		if (e.getItem() == null) {
			return;
		}

		if (e.getItem().getType() == Material.BARRIER) {
			Player p = e.getPlayer();
			if (inSim.containsKey(p.getName())) {
				inSim.remove(p.getName());
				angles.remove(p.getName());
				p.sendMessage("Not controlling anymore!");
			}
		}
	}

	private String locToString(Location loc) {
		DecimalFormat df = new DecimalFormat("#.#");
		return df.format(loc.getX()) + ", " + df.format(loc.getY()) + ", " + df.format(loc.getZ());
	}

}
