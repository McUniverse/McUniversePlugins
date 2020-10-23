package eu.mcuniverse.audit.command;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import eu.mcuniverse.audit.storage.AuditStorageManager;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Data;
import net.md_5.bungee.api.ChatColor;

public class DeleteRecordCommand implements TabExecutor {

	private Object2ObjectOpenHashMap<String, DeleteObject> confirm = new Object2ObjectOpenHashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("mcuniverse.audit.delete")) {
			sender.sendMessage(UniverseAPI.getNoPermissions());
			return true;
		}

		if (args.length != 1) {
			sender.sendMessage(UniverseAPI.getWarning() + "Usage: /" + label + " <ID/confirm>");
			return true;
		}

		String option = args[0];
		if (option.equalsIgnoreCase("confirm")) {
			if (!confirm.containsKey(sender.getName())) {
				sender.sendMessage(UniverseAPI.getWarning() + "You don't have an open request to delete a record.");
				return true;
			}
			DeleteObject toDelete = confirm.get(sender.getName());
			if (toDelete.getInstant().isBefore(Instant.now())) {
				confirm.remove(sender.getName());
				sender.sendMessage(UniverseAPI.getWarning() + "Your request has expired!");
				return true;
			}
			sender.sendMessage(UniverseAPI.getPrefix() + "You deleted the death with ID " + ChatColor.YELLOW + toDelete.getId());
			AuditStorageManager.deleteRecord(toDelete.getId());
			confirm.remove(sender.getName());
			return true;
		}

		int id;
		try {
			id = Integer.parseInt(option);
		} catch (NumberFormatException e) {
			sender.sendMessage(UniverseAPI.getWarning() + "Invalid number: " + option);
			return true;
		}

		DeleteObject toDelete = new DeleteObject(id, Instant.now().plusSeconds(15));
		confirm.put(sender.getName(), toDelete);
		sender.sendMessage(UniverseAPI.getWarning() + "To delete the death record with ID " + ChatColor.YELLOW + option
				+ ChatColor.RED + " please type " + ChatColor.YELLOW + "/" + label + " confirm" + ChatColor.RED + ". This command will expire in 15 seconds.");

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length == 1) {
			List<String> ret = AuditStorageManager.getAllIDs().stream().map(i -> i + "").collect(Collectors.toList());
			ret.add(0, "confirm");
			return ret;
		}
		return new ArrayList<String>();
	}

	@Data
	private class DeleteObject {

		private final int id;
		private final Instant instant;

	}

}
