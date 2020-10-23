package eu.mcuniverse.audit.command;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.mcuniverse.audit.manager.AuditReport;
import eu.mcuniverse.audit.storage.AuditStorageManager;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.md_5.bungee.api.ChatColor;

public class ShowDeathsCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender.hasPermission("mcuniverse.audit.death"))) {
			sender.sendMessage(UniverseAPI.getNoPermissions());
			return true;
		}

		if (args.length != 1) {
			return showHelpMessage(sender, label);
		}

		String option = args[0];

		// TODO: Remove the following string (and the one after the try/catch when a player types the command and an inventory
		// opens. No priority because it's just a minor cosmetic "bug"
		
		sender.sendMessage(ChatColor.YELLOW + "[]" + ChatColor.GRAY + " ---------- " + ChatColor.RED + "Death Report"
				+ ChatColor.GRAY + " ---------- " + ChatColor.YELLOW + "[]");

		try {
			int id = Integer.parseInt(option);
			manageIdInput(sender, id);
		} catch (NumberFormatException e) {
			managePlayerInput(sender, option);
		}

		sender.sendMessage(ChatColor.YELLOW + "[]" + ChatColor.GRAY + " ---------- " + ChatColor.RED + "Death Report"
				+ ChatColor.GRAY + " ---------- " + ChatColor.YELLOW + "[]");

		return true;
	}

	private void managePlayerInput(CommandSender sender, String name) {
		if (!AuditStorageManager.hasAuditReport(name)) {
			sender.sendMessage(UniverseAPI.getWarning() + "This player does not have any deaths registered");
			return;
		}

		ObjectList<AuditReport> audits = AuditStorageManager.getAllAudits(name);
		audits.forEach(a -> {
			a.sendDate(sender);
		});
	}

	private void manageIdInput(CommandSender sender, int id) {
		if (!AuditStorageManager.doesReportExist(id)) {
			sender.sendMessage(UniverseAPI.getWarning() + "This death is not registered!");
			return;
		}
		AuditReport audit = AuditStorageManager.getAudit(id);
		if (sender instanceof Player) {
			((Player) sender).openInventory(audit.getFilledInventory());
		} else {
			audit.sendToSender(sender);
		}
	}

	private boolean showHelpMessage(CommandSender sender, String label) {
		sender.sendMessage(UniverseAPI.getWarning() + "Usage: /" + label + " <Player/ID>");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			return Bukkit.getOnlinePlayers().stream().map(p -> p.getName())
					.filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase()))
					.collect(Collectors.toCollection(ArrayList::new));
		}
		return new ArrayList<String>();
	}

}
