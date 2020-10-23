package eu.mcuniverse.factionextension.commands;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.perms.Role;

import eu.mcuniverse.factionextension.storage.FactionRocketManager;
import eu.mcuniverse.universeapi.rockets.Planet;
import net.md_5.bungee.api.ChatColor;

public class RocketLanding implements Listener {

	@EventHandler
	public void onPerform(PlayerCommandPreprocessEvent e) {
		if (!e.isCancelled() && e.getMessage().toLowerCase().startsWith("/f setrocketlanding")) {
			e.setCancelled(true);

			Player p = e.getPlayer();
			FPlayer fp = FPlayers.getInstance().getById(p.getUniqueId().toString());
			Faction faction = fp.getFaction();
			Planet planet = Planet.getPlanet(p);

			if (planet == null) {
				p.sendMessage(ChatColor.RED + "You are not on a planet.");
				return;
			}

			if (!fp.hasFaction()) {
				p.sendMessage(ChatColor.RED + "You are not a member of any faction.");
				return;
			}
			if (!fp.getRole().isAtLeast(Role.COLEADER)) {
				p.sendMessage(ChatColor.RED + "You do not have permission to set the rocket landing location.");
				return;
			}

			Faction f = fp.getFaction();
			FLocation floc = new FLocation(fp);
			if (Board.getInstance().getFactionAt(floc) != faction) {
				p.sendMessage(ChatColor.RED + "Sorry, your faction rocket landing location can only be set "
						+ "inside your own claimed territory.");
				return;
			}

//			MySQL.Rocketlanding.updateLocation(f.getTag(), p.getLocation());

			FactionRocketManager.setLandingLocation(f.getTag(), planet,
					p.getLocation().getBlock().getLocation().add(0.5, 0, 0.5));

			String pre = fp.getRole().getColor() + fp.getRole().getPrefix() + fp.getName();
			f.getFPlayers().forEach(member -> {
				member.sendMessage(pre + ChatColor.YELLOW + " set the rocket landing location for your"
						+ " faction on the planet " + ChatColor.DARK_AQUA + planet.getWorldName() + ChatColor.YELLOW
						+ ". You can now land in your faction's territory");
			});
		} else if (!e.isCancelled() && e.getMessage().toLowerCase().startsWith("/f delrocketlanding")) {
			e.setCancelled(true);
			Player p = e.getPlayer();
			FPlayer fp = FPlayers.getInstance().getById(p.getUniqueId().toString());
			Planet planet = Planet.getPlanet(p);

			if (planet == null) {
				p.sendMessage(ChatColor.RED + "You are not on a planet.");
				return;
			}
			if (!fp.hasFaction()) {
				p.sendMessage(ChatColor.RED + "You are not a member of any faction.");
				return;
			}
			if (!fp.getRole().isAtLeast(Role.COLEADER)) {
				p.sendMessage(ChatColor.RED + "You do not have permission to set the rocket landing location.");
				return;
			}

			Faction f = fp.getFaction();
//			MySQL.Rocketlanding.deleteLocation(f.getTag());
			FactionRocketManager.deleteLandingLocation(f.getTag(), planet);
			String pre = fp.getRole().getColor() + fp.getRole().getPrefix() + fp.getName();
			f.getFPlayers().forEach(member -> {
				member
						.sendMessage(pre + ChatColor.YELLOW + " deleted the rocket landing location for your faction on the planet "
								+ ChatColor.DARK_AQUA + planet.getWorldName() + ChatColor.YELLOW + ".");
			});
		}
	}
}
