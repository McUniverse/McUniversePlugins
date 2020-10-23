package eu.mcuniverse.weapons.commands.weaponadmin.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import eu.mcuniverse.weapons.commands.Subcommand;
import eu.mcuniverse.weapons.data.Messages;
import net.md_5.bungee.api.ChatColor;

public class SendResourcepackCommand extends Subcommand {

	@Override
	public String getName() {
		return "sendresourcepack";
	}

	@Override
	public String getDescription() {
		return "Send resourcepack via url to player";
	}

	@Override
	public String getSyntax() {
		return "/weaponadmin sendresourcepack <Player> <URL>";
	}

	@Override
	public void perform(Player player, String[] args) {
		if (args.length != 2) {
			player.sendMessage(Messages.WARNING + getDescription());
		}
		try {
			String name = args[1];
			String url = args[2];
			Player target = Bukkit.getPlayer(name);
			if (target == null) {
				player.sendMessage(Messages.WARNING + "The player " + ChatColor.YELLOW + name + ChatColor.RED + " is not online!");
				return;
			}
			target.setResourcePack(url);
			player.sendMessage(Messages.PREFIX + "Successfully send resourcepack to " + ChatColor.YELLOW + name);
		} catch (Exception e) {
			player.sendMessage(Messages.ERROR + "An error occured: " + e.getLocalizedMessage());
		}
		
	}

}
