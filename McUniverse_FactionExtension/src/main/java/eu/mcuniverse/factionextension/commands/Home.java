package eu.mcuniverse.factionextension.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.perms.Relation;

import eu.mcuniverse.factionextension.storage.HomeManager;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import net.md_5.bungee.api.ChatColor;

public class Home implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(UniverseAPI.getWarning() + "You're not a player!");
			return true;
		}

		Player p = (Player) sender;
		FPlayer fp = FPlayers.getInstance().getById(p.getUniqueId().toString());
		Faction faction = fp.getFaction();
		if (!fp.hasFaction()) {
			p.sendMessage(ChatColor.RED + "You are not a member of any faction.");
			return true;
		}
		
//		if (!MySQL.Home.hasHomeLocation(p.getUniqueId())) {
		if (!HomeManager.hasHomeLocation(p.getUniqueId())) {
			p.sendMessage(ChatColor.RED + "Sorry, you don't have a home.");
			return true;
		}

		try {
//			Location loc = MySQL.Home.getLocation(p.getUniqueId());
			Location loc = HomeManager.getHome(p.getUniqueId());
			FLocation floc = new FLocation(loc);
			if (!loc.getWorld().getName().equalsIgnoreCase(p.getWorld().getName())) {
				p.sendMessage(ChatColor.RED + "Sorry, you're not on the planet " + "where your home is! ("
						+ loc.getWorld().getName() + ")");
				return true;
			}
			Faction other = Board.getInstance().getFactionAt(floc);
			if (other != faction && other.getRelationTo(faction) != Relation.ALLY)  {
				p.sendMessage(ChatColor.RED + "Sorry, your home is not in your faction's territory anymore!");
				return true;
			}

			if (p.teleport(loc)) {
				p.sendMessage(ChatColor.YELLOW + "Teleported you to your home.");
			} else {
				p.sendMessage(
						UniverseAPI.getError() + "Couldn't teleport to your home. Please contact the server administration");
			}
		} catch (Exception e) {
			p.sendMessage(UniverseAPI.getError()
					+ "An internal error occured! Please report this to the server administration and provide them with the following message: "
					+ e.toString());
			e.printStackTrace();
		}

		return true;
	}

}
