package eu.mcuniverse.audit.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import eu.mcuniverse.audit.storage.AuditStorageManager;

public class JoinListener implements Listener {

	public void onSqlJoin(PlayerJoinEvent e) {
		if (!AuditStorageManager.isConnected()) {
			return;
		}
		if (!e.getPlayer().hasPlayedBefore()) {
			return;
		}
		if (!AuditStorageManager.hasAuditReport(e.getPlayer().getUniqueId())) {
			return;
		}
		AuditStorageManager.updateName(e.getPlayer().getUniqueId(), e.getPlayer().getName());
	}
	
}
