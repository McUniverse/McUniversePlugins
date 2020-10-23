package eu.mcuniverse.system.commands;

import java.io.IOException;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class CMD_Status extends Command {

	public CMD_Status() {
		super("status", "mcuniverse.status", new String[] {});
	}

	public void execute(CommandSender sender, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("get")) {
				if (!Bungee.config.contains("status")) {
					sender.sendMessage(new TextComponent(ChatColor.DARK_RED + "Status not set!"));
					return;
				}
				int status = Bungee.config.getInt("status");
				sender.sendMessage(new TextComponent(
						Bungee.prefix + ChatColor.GRAY + "The current status is " + ChatColor.GOLD + status + "%"));
			} else {
				try {
					int i = Integer.valueOf(args[0]);
					if (i > 100 || i < 0) {
						sender.sendMessage(new TextComponent(ChatColor.RED + "Your number must be between 0 and 100!"));
						return;
					}
	 				Bungee.config.set("status", i);
					sender.sendMessage(new TextComponent(
							Bungee.prefix + ChatColor.GRAY + "Updated the status to " + ChatColor.GOLD + i + "%"));
					
					try {
						ConfigurationProvider.getProvider(YamlConfiguration.class).save(Bungee.config, Bungee.file);
					} catch (IOException e) {
						sender.sendMessage(new TextComponent(ChatColor.DARK_RED + "Could not save the file!"));
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(new TextComponent(ChatColor.RED + "Please insert a number!"));
				}
			}
		} else {
			sender.sendMessage(new TextComponent(ChatColor.RED + "Usage: /status <get/#>"));
		}
	}

}
