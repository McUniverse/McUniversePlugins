package eu.mcuniverse.weapons.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.weapons.main.Core;

public class ResourceCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No Perms!");
			return true;
		}

		Player p = (Player) sender;

		if (p.hasPermission("weapons.reload")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					Core.getInstance().reloadConfig();
					p.sendMessage(UniverseAPI.getPrefix() + "Reloaded the config for the resourcepacks");
					return true;
				}
			}
		}
		
		try {
			String url = Core.getInstance().getConfig().getString("url");
			String hash = Core.getInstance().getConfig().getString("hash");

//			PacketContainer packet = Core.getProtocolManager().createPacket(PacketType.Play.Server.RESOURCE_PACK_SEND);
//			packet.getModifier().writeDefaults();
//			packet.getStrings().write(0, url).write(1, hash);
//			
//			Core.getProtocolManager().sendServerPacket(p, packet);

			p.setResourcePack(url);
			
		} catch (Exception e) {
			p.sendMessage(UniverseAPI.getError()
					+ "An internal error occured. Please report the following to the server team: " + e.getLocalizedMessage());
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return new ArrayList<String>();
	}

}
