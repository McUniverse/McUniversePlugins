package eu.mcuniverse.weapons.listener;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import eu.mcuniverse.weapons.main.Core;

public class DamageListener implements Listener {

	public DamageListener(Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
		
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() == null || e.getCause() != DamageCause.PROJECTILE) {
			return;
		}
		if (!(e.getDamager() instanceof Snowball)) {
			return;
		}
		Projectile snowball = (Projectile) e.getDamager();
		if (!(snowball.getShooter() instanceof Player)) {
			return;
		}
		double snowY = snowball.getLocation().getY();
		double damagedY = e.getEntity().getLocation().getY();
//		Bukkit.broadcastMessage("Delta: " + (snowY - damagedY));
		
	}
	
}
