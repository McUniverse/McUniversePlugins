package eu.mcuniverse.factionextension.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.md_5.bungee.api.ChatColor;

public class BlockedCommandsListener implements Listener {

	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (!e.getMessage().toLowerCase().startsWith("/f") || !e.getMessage().toLowerCase().startsWith("/factions")) {
			return;
		}
		Player p = e.getPlayer();
		
		String[] args = e.getMessage().split(" ");
		if (args.length == 1) { // Only command base
			return;
		}
		String subcommand = args[1];
		// Peaceful cmd
		if (subcommand.equalsIgnoreCase("peaceful")) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You can't make your faction peaceful!");
			return;
		}
		
//		if (Core.getConfigManager().getSettings().getBoolean("temporary.update.relation.on.command")) {
//			for (Faction other : Factions.getInstance().getAllFactions()) {
//				FPlayer fp = FPlayers.getInstance().getByPlayer(p);
//				if (!fp.hasFaction()) {
//					continue;
//				}
//				Faction faction = fp.getFaction();
//				if (faction.getRelationTo(other, true).isNeutral()) {
//				}
//			}
//		}
		
	}
	
}
