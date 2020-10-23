package eu.mcuniverse.factionextension.commands;

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

public class SetHome implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(UniverseAPI.getWarning() + "You're not a player");
			return true;
		}

		Player p = (Player) sender;
		FPlayer fp = FPlayers.getInstance().getById(p.getUniqueId().toString());
		Faction faction = fp.getFaction();
		FLocation floc = new FLocation(fp);
		if (!fp.hasFaction()) {
			p.sendMessage(ChatColor.RED + "You are not a member of any faction.");
			return true;
		}
		
		Faction other = Board.getInstance().getFactionAt(floc);
		if (other != faction && other.getRelationTo(faction) != Relation.ALLY) {
			p.sendMessage(ChatColor.RED + "Sorry, you cannot set a home outside of your faction's territory!");
			return true;
		}

//		MySQL.Home.updateLocation(p.getUniqueId(), p.getLocation());
		HomeManager.updateHome(p.getUniqueId(), p.getLocation());

		p.sendMessage(ChatColor.YELLOW + "You set your home!");

		return true;
	}

}
