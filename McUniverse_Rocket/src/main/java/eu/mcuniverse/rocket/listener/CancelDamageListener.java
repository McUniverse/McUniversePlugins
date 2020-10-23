package eu.mcuniverse.rocket.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import eu.mcuniverse.rocket.data.Variables;
import eu.mcuniverse.rocket.inventories.DeleteGUI;
import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.rocket.InRocketManager;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;

public class CancelDamageListener implements Listener {

	private Core plugin;
	
	public CancelDamageListener(Core main) {
		plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity().getCustomName() == null) {
			return;
		}
		if (e.getEntityType() != EntityType.ARMOR_STAND
				|| !(e.getDamager() instanceof Player || !e.getEntity().getCustomName().contains(Variables.ARMOR_STAND_NAME))) {
			return;
		}
		Player p = (Player) e.getDamager();
		if (!RocketManager.hasRocket(p)) {
			return;
		}
		Rocket r = RocketManager.getRocket(p);
		if (e.getEntity().getUniqueId().equals(r.getArmorStandUUID())) {
			p.openInventory(new DeleteGUI().getInventory());
			e.setCancelled(true);
		}
	}	
	
	// Cancel damage when in Rocket
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (InRocketManager.isInRocket(p)) {
			e.setCancelled(true);
		}
	}
	
}
