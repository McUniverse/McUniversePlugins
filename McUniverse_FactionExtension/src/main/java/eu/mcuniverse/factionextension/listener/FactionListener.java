package eu.mcuniverse.factionextension.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.massivecraft.factions.Factions;
import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FPlayerLeaveEvent;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.FactionRenameEvent;
import com.massivecraft.factions.perms.Relation;

import eu.mcuniverse.factionextension.storage.FactionRocketManager;
import eu.mcuniverse.factionextension.storage.FactionStorageManager;

public class FactionListener implements Listener {

	@EventHandler
	public void onFactionJoin(FPlayerJoinEvent e) {
		FactionStorageManager.registerPlayer(e.getfPlayer().getPlayer().getUniqueId(), e.getFaction().getTag());
	}
	
	@EventHandler
	public void onFactionLeave(FPlayerLeaveEvent e) {
		FactionStorageManager.deletePlayer(e.getfPlayer().getPlayer().getUniqueId());
	}
	
	@EventHandler
	public void onFactionCreate(FactionCreateEvent e) {
		FactionStorageManager.registerPlayer(e.getFPlayer().getPlayer().getUniqueId(), e.getFaction().getTag());
		e.getFaction().setPeaceful(false);
		Factions.getInstance().getAllFactions().forEach(other -> {
			e.getFaction().setRelationWish(other, Relation.ENEMY);
			other.setRelationWish(e.getFaction(), Relation.ENEMY);
		});
	}
	
	@EventHandler
	public void onFactionDisband(FactionDisbandEvent e) {
		e.getFaction().getFPlayers().stream().map(fp -> fp.getPlayer().getUniqueId()).forEach(FactionStorageManager::deletePlayer);
		FactionRocketManager.deleteAllLandingLocations(e.getFaction().getTag());
	}
	
	@EventHandler
	public void onFactionRename(FactionRenameEvent e) {
		e.getFaction().getFPlayers().stream().map(fp -> fp.getPlayer().getUniqueId()).forEach(uuid -> FactionStorageManager.updatePlayer(uuid, e.getFactionTag()));
	}
	
}
