package eu.mcuniverse.testing.main;

import static java.lang.Math.*;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

import eu.mcuniverse.testing.brewing.BrewingRecipe;
import eu.mcuniverse.testing.listener.BrewListener;

//import com.comphenix.protocol.PacketType;
//import com.comphenix.protocol.events.PacketContainer;

public class Main extends JavaPlugin implements Listener {

	public static ProtocolManager protocolManager;
	public static Main instance;

	@Override
	public void onEnable() {
		System.out.println("[Testing] Enabled! Version " + getDescription().getVersion());
		protocolManager = ProtocolLibrary.getProtocolManager();
		instance = this;

//		CraftingTest.init(this);

//		protocolManager
//				.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.WORLD_EVENT) {
//					@Override
//					public void onPacketSending(PacketEvent event) {
//							ProtocolUtil.sendPacketInformation(event.getPacket(), event.getPlayer());
//					}
//				});

//		getServer().getPluginManager().registerEvents(this, this);

//		getServer().getPluginManager().registerEvents(new Animation(), this);
//		getServer().getPluginManager().registerEvents(new MouseMover(), this);
//		getServer().getPluginManager().registerEvents(new ThirdPerson(), this);
//		getServer().getPluginManager().registerEvents(new CommandListener(), this);
//		getServer().getPluginManager().registerEvents(new RoomListener(), this);

//		getServer().getPluginManager().registerEvents(new PacketListener(), this);
//		getServer().getPluginManager().registerEvents(new GravityListener(), this);
//		getServer().getPluginManager().registerEvents(new OilSource(), this);

		getServer().getPluginManager().registerEvents(new BrewListener(), this);

		getServer().getPluginManager().registerEvents(new Listener() {
			@EventHandler
			public void on(PlayerCommandPreprocessEvent e) {
				if (e.getMessage().startsWith("//world")) {
					Player p = e.getPlayer();
					Bukkit.getWorlds().forEach(world -> {
						p.sendMessage(world.getName() + " --- " + world);
					});
					e.setCancelled(true);
				}
			}
		}, this);

//		getCommand("dimension").setExecutor(new DimensionChanger());
//		getCommand("oil").setExecutor(new OilSource());
//		getCommand("nbt").setExecutor(new NBTCommand());

		new BrewingRecipe(Material.STONE, (inventory, item, ingridient) -> {
			if (item.getType() != Material.GLASS_BOTTLE)
				return;
			item.setType(Material.CHISELED_RED_SANDSTONE);
		});
	}

	@EventHandler
	public void onClick(final PlayerInteractEvent e) {
		if (e.getClickedBlock() == null)
			return;

		if (e.getClickedBlock().getType() == Material.DIAMOND_BLOCK) {
			final Player p = e.getPlayer();
			final Location loc = e.getClickedBlock().getLocation().add(0, 1, 0);
			final Location tmp = loc.clone();
			final Pig c = (Pig) p.getWorld().spawnEntity(loc, EntityType.PIG);
			c.setInvulnerable(true);
			c.setSilent(true);
			c.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
			c.setGravity(false);
			c.setAI(false);

//			Player player = (Player) p.getWorld().spawnEntity(loc, EntityType.PLAYER);
//			player.setCustomName(p.getCustomName() + " Custom");
//			player.setDisplayName(p.getDisplayName() + " Display");

			PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CAMERA);
			packet.getIntegers().write(0, c.getEntityId());

			try {
				protocolManager.sendServerPacket(p, packet);
			} catch (InvocationTargetException exe) {
				throw new RuntimeException("Cannot send packet " + packet, exe);
			}

			// With 3D:
			new BukkitRunnable() {
				final int radius = 10;
				double theta = 1;
				double phi = 0;
				Location block = e.getClickedBlock().getLocation();

				@Override
				public void run() {
					double x = radius * sin(theta) * cos(phi);
					double y = radius * sin(theta) * sin(phi);
					double z = radius * cos(theta);
					loc.setX(tmp.getX() + x);
					loc.setY(tmp.getY() + y);
					loc.setZ(tmp.getZ() + z);

					Vector v = new Vector();
					v.setX(block.getX() - loc.getX());
					v.setY(block.getY() - loc.getY());
					v.setZ(block.getZ() - loc.getZ());
					loc.setDirection(v);
					c.teleport(loc);
//				p.teleport(loc);

//					
					if (theta > PI * 2) {
						phi += 0.05;
						p.sendMessage("\u03C6 erhöht -> " + phi);
					} else {
						theta += 0.05;
						p.sendMessage("\u03B8 erhöht -> " + theta);
					}
					if (phi >= PI * 2 && theta >= PI * 2) {
						resetView(p);
						c.remove();
						cancel();
					}
				}
			}.runTaskTimer(this, 5L, 1L);

//			new BukkitRunnable() {
//				final int radius = 10;
//				double angle = 0;
//				Location block = e.getClickedBlock().getLocation();
//				@Override
//				public void run() {
//					double x = radius * Math.cos(angle);
//					double z = radius * Math.sin(angle);
//					loc.setX(tmp.getX() + x);
//					loc.setZ(tmp.getZ() + z);
//
//					Vector v = new Vector();
//					v.setX(block.getX() - loc.getX());
//					v.setY(block.getY() - loc.getY());
//					v.setZ(block.getZ() - loc.getZ());
//					loc.setDirection(v);
//					c.teleport(loc);
////					p.teleport(loc);
//					
//					if (angle > Math.PI * 2) {
//						resetView(p);
//						c.remove();
//						cancel();
//					}
//					angle += 0.05;
//				}
//			}.runTaskTimer(this, 5L, 1L);

		}
	}

	private static void resetView(Player p) {
		PacketContainer packet2 = protocolManager.createPacket(PacketType.Play.Server.CAMERA);
		packet2.getIntegers().write(0, p.getEntityId());

		try {
			protocolManager.sendServerPacket(p, packet2);
		} catch (InvocationTargetException exe) {
			throw new RuntimeException("Cannot send packet " + packet2, exe);
		}
	}

//	@EventHandler
//	public void onProjectileThrow(ProjectileLaunchEvent e) {
//		final Projectile projectile = e.getEntity();
//		if (projectile.getShooter() instanceof Player) {
//			PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CAMERA);
//			packet.getIntegers().write(0, projectile.getEntityId());
//			
//			final Player p = (Player) projectile.getShooter();
//			
//			try {
//				protocolManager.sendServerPacket(p, packet);
//				Bukkit.broadcastMessage("Packet sent!");
//			} catch (InvocationTargetException exe) {
//				throw new RuntimeException("Cannot send packet " + packet, exe);
//			}		
//			
//			new BukkitRunnable() {
//				
//				@Override
//				public void run() {
//					if (projectile.isOnGround() || projectile.isDead()) {
//						PacketContainer packet2 = protocolManager.createPacket(PacketType.Play.Server.CAMERA);
//						packet2.getIntegers().write(0, p.getEntityId());
//
//						try {
//							protocolManager.sendServerPacket(p, packet2);
//							Bukkit.broadcastMessage("Removed camera");
//						} catch (InvocationTargetException exe) {
//							throw new RuntimeException("Cannot send packet " + packet2, exe);
//						}
//						cancel();
//					}
//				}
//			}.runTaskTimer(this, 0L, 1L);
//		}
//	}

	@EventHandler
	public void onTest(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().contains("testing")) {
			final Player p = e.getPlayer();
			final Snowball s = (Snowball) p.launchProjectile(Snowball.class);
			s.setVelocity(new Vector(0, 5, 0));

			PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.CAMERA);
			packet.getIntegers().write(0, s.getEntityId());

			try {
				protocolManager.sendServerPacket(p, packet);
			} catch (InvocationTargetException exe) {
				throw new RuntimeException("Cannot send packet " + packet, exe);
			}

			Bukkit.getScheduler().runTaskLater(this, new Runnable() {

				@Override
				public void run() {
					PacketContainer packet2 = protocolManager.createPacket(PacketType.Play.Server.CAMERA);
					packet2.getIntegers().write(0, p.getEntityId());

					try {
						protocolManager.sendServerPacket(p, packet2);
					} catch (InvocationTargetException exe) {
						throw new RuntimeException("Cannot send packet " + packet2, exe);
					}
				}
			}, 20L * 10);

			e.setCancelled(true);
		}
	}

}
