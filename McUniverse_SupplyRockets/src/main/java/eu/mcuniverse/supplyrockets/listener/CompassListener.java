package eu.mcuniverse.supplyrockets.listener;

import java.time.Duration;
import java.time.Instant;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.rockets.SRocketTier1;
import eu.mcuniverse.supplyrockets.rockets.SRocketTier2;
import eu.mcuniverse.universeapi.item.CustomItem;
import eu.mcuniverse.universeapi.rockets.Planet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.md_5.bungee.api.ChatColor;

public class CompassListener implements Listener {

	private Object2ObjectOpenHashMap<Player, Instant> cooldown = new Object2ObjectOpenHashMap<Player, Instant>();

	public CompassListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);

		cooldown.defaultReturnValue(Instant.now());
	}

	@EventHandler
	public void onCompassClick(PlayerInteractEvent e) {
		if (!e.hasItem()) {
			return;
		}

		if (e.getMaterial() == Material.COMPASS) {
			if (Instant.now().isBefore(cooldown.get(e.getPlayer()))) {
				long secs = Duration.between(Instant.now(), cooldown.get(e.getPlayer())).getSeconds();
				String s = secs == 0 ? "Second" : "Seconds";
				e.getPlayer().sendMessage(ChatColor.DARK_GREEN + "Please wait " + ChatColor.GREEN + (secs + 1) + " " + s + " "
						+ ChatColor.DARK_GREEN + "before doing that again");
				return;
			}

			Player p = e.getPlayer();
			Planet planet = Planet.getPlanet(p);
			
			if (e.getItem().isSimilar(CustomItem.Rocket.getEnhancedCompass())) {
				SRocketTier1 tier1 = Core.getCurrentTier1().get(planet);
				SRocketTier2 tier2 = Core.getCurrentTier2().get(planet);
				
				if (tier1 == null) {
					int distanceTier2 = (int) tier2.getLocation().distance(p.getLocation());
					p.sendMessage(ChatColor.YELLOW + "There is a Tier 2 Supply Rocket " + ChatColor.GREEN + "" + ChatColor.ITALIC
							+ distanceTier2 + ChatColor.YELLOW + " blocks away.");
					
					p.setCompassTarget(tier2.getLocation());
				} else if (tier2 == null) {

					int distanceTier1 = (int) tier1.getLocation().distance(p.getLocation());
					p.sendMessage(ChatColor.YELLOW + "There is a Tier 1 Supply Rocket " + ChatColor.GREEN + "" + ChatColor.ITALIC
							+ distanceTier1 + ChatColor.YELLOW + " blocks away.");
					p.setCompassTarget(tier1.getLocation());
				} else {
					int distanceTier1 = (int) tier1.getLocation().distance(p.getLocation());
					int distanceTier2 = (int) tier2.getLocation().distance(p.getLocation());
					p.sendMessage(ChatColor.YELLOW + "There is a Tier 1 Supply Rocket " + ChatColor.GREEN + "" + ChatColor.ITALIC
							+ distanceTier1 + ChatColor.YELLOW + " blocks away.");
					p.sendMessage(ChatColor.YELLOW + "There is a Tier 2 Supply Rocket " + ChatColor.GREEN + "" + ChatColor.ITALIC
							+ distanceTier2 + ChatColor.YELLOW + " blocks away.");
					if (distanceTier1 > distanceTier2) {
						p.setCompassTarget(tier2.getLocation());
					} else {
						p.setCompassTarget(tier1.getLocation());
					}
				}
			} else {
				SRocketTier1 tier1 = Core.getCurrentTier1().get(planet);
				if (tier1 == null) {
					p.sendMessage(ChatColor.RED + "Currently there is no rocket to be found.");
					return;
				}
				int distanceTier1 = (int) tier1.getLocation().distance(p.getLocation());
				p.setCompassTarget(tier1.getLocation());
				p.sendMessage(ChatColor.YELLOW + "There is a Supply Rocket " + ChatColor.GREEN + "" + ChatColor.ITALIC
						+ distanceTier1 + ChatColor.YELLOW + " blocks away.");
			}

			cooldown.put(e.getPlayer(), Instant.now().plusSeconds(5));
//			
//			SRocketTier1 tier1 = Core.getCurrentTier1().get(planet);
//			int distanceTier1 = (int) tier1.getLocation().distance(p.getLocation());
//
//			if (e.getItem().isSimilar(CustomItem.Rocket.getEnhancedCompass())) {
//				SRocketTier2 tier2 = Core.getCurrentTier2().get(planet);
//				int distanceTier2 = (int) tier2.getLocation().distance(p.getLocation());
//				p.sendMessage(ChatColor.YELLOW + "There is a Tier 1 Supply Rocket " + ChatColor.GREEN + "" + ChatColor.ITALIC
//						+ distanceTier1 + ChatColor.YELLOW + " blocks away.");
//				p.sendMessage(ChatColor.YELLOW + "There is a Tier 2 Supply Rocket " + ChatColor.GREEN + "" + ChatColor.ITALIC
//						+ distanceTier2 + ChatColor.YELLOW + " blocks away.");
//				if (distanceTier1 > distanceTier2) {
//					p.setCompassTarget(tier2.getLocation());
//				} else {
//					p.setCompassTarget(tier1.getLocation());
//				}
//			} else {
//				p.setCompassTarget(tier1.getLocation());
//				p.sendMessage(ChatColor.YELLOW + "There is a Supply Rocket " + ChatColor.GREEN + "" + ChatColor.ITALIC
//						+ distanceTier1 + ChatColor.YELLOW + " blocks away.");
//			}
//			cooldown.put(e.getPlayer(), Instant.now().plusSeconds(5));
		}
	}

}
