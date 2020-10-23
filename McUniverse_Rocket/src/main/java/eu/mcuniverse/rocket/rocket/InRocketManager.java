package eu.mcuniverse.rocket.rocket;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import eu.mcuniverse.rocket.data.Variables;
import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.util.ItemUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class InRocketManager {

//	@Setter @Getter
//	public volatile Location reference;
//	@Getter @Setter(value = AccessLevel.PRIVATE)
//	public volatile Location toLook;

	public void updateToLook(Player player) {
		Location l = Variables.toLook.get(player.getName());
		Variables.toLook.put(player.getName(), l.add(0, 0.5, 0));
	}

	public boolean isInRocket(Player player) {
		return Variables.inRocket.containsKey(player.getName());
	}

	public void setReference(Player player, Location loc) {
		Variables.references.put(player.getName(), loc);
	}

	public Location getReference(Player player) {
		return Variables.references.get(player.getName());
	}

	public Location getToLook(Player player) {
		return Variables.toLook.get(player.getName());
	}

	public void setToLook(Player player, Location loc) {
		Variables.toLook.put(player.getName(), loc.clone().add(0, 1, 0));
	}

	public void managePlayer(Player player) {
		if (isInRocket(player)) {
			removePlayer(player);
		} else {
			addToView(player);
		}
	}

	public void pause(Player player) {
		Variables.rocketTeleporter.get(player.getName()).setPause(true);
	}

	public void unpause(Player player) {
		Variables.rocketTeleporter.get(player.getName()).setPause(false);
	}

	private void addToView(Player player) {
		if (isInRocket(player)) {
			throw new UnsupportedOperationException("The player " + player.getName() + " is already in the inRocket list");
		}

		player.setAllowFlight(true);
		player.setFlying(true);

		setReference(player, RocketManager.getRocket(player).getLocation().clone().add(0, 1, 0));
		setToLook(player, RocketManager.getRocket(player).getLocation());

		// TODO: Quit event to remove from Map
		Variables.inRocket.put(player.getName(), player.getLocation());
		Variables.rocketTeleporter.put(player.getName(), new Teleporter(player));
		Variables.inventories.put(player.getName(), player.getInventory().getContents());

		player.getInventory().clear();

		PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, true, false);
		player.addPotionEffect(effect);

		setInventory(player);
	}

	private void removePlayer(Player player) {
		if (!isInRocket(player)) {
			throw new UnsupportedOperationException("The player " + player.getName() + " is not in the inRocket list");
		}
		player.teleport(Variables.inRocket.get(player.getName()));
		Variables.inRocket.remove(player.getName());

		player.getInventory().setContents(Variables.inventories.get(player.getName()));
		Variables.inventories.remove(player.getName());

		Variables.rocketTeleporter.get(player.getName()).cancel();
		Variables.rocketTeleporter.remove(player.getName());

		Variables.toLook.remove(player.getName());
		Variables.references.remove(player.getName());

		player.removePotionEffect(PotionEffectType.INVISIBILITY);

		PacketContainer packet = new PacketContainer(PacketType.Play.Server.REMOVE_ENTITY_EFFECT);
		packet.getIntegers().write(0, player.getEntityId());
		packet.getEffectTypes().write(0, PotionEffectType.INVISIBILITY);

		if (player.getGameMode() != GameMode.CREATIVE) {
			player.setAllowFlight(false);
			player.setFlying(false);
		}

		try {
			Core.getProtocolManager().sendServerPacket(player, packet);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("Cannot send packet " + packet, e);
		}

	}

	private void setInventory(Player p) {
		p.getInventory().setItem(8, ItemUtil.createItem(Material.BARRIER, ChatColor.DARK_RED + "Exit"));
		p.getInventory().setItem(0, ItemUtil.createItem(Material.SHULKER_SHELL, ChatColor.DARK_AQUA + "Launch menu"));
	}

	public class Teleporter extends BukkitRunnable {

		private Player p;
		private Location toLook;
		private Location tmp;
		private double angle = 0;
		Location loc;
		@Setter
		@Getter
		public boolean pause = false;

		public Teleporter(Player player) {
			p = player;
			loc = getReference(player);
			tmp = loc.clone();
			runTaskTimer(Core.getInstance(), 0, Variables.PERIOD);
		}

		@Override
		public void run() {
			if (!pause) {
				try {
					toLook = getToLook(p).clone();
					loc = getReference(p).clone();
					tmp = loc.clone();

					double x = Variables.RADIUS * Math.cos(angle);
					double z = Variables.RADIUS * Math.sin(angle);
					loc.setX(tmp.getX() + x);
					loc.setZ(tmp.getZ() + z);

					Vector v = new Vector();
					v.setX(toLook.getX() - loc.getX());
					v.setY(toLook.getY() - loc.getY());
					v.setZ(toLook.getZ() - loc.getZ());
					loc.setDirection(v);
					p.teleport(loc);
					angle += Variables.DELTA;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
