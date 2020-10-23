package eu.mcuniverse.bungeeuniverse.commands;

import java.io.IOException;

import eu.mcuniverse.bungeeuniverse.Core;
import eu.mcuniverse.bungeeuniverse.data.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Motd extends Command {

	public Motd() {
		super("motd", "mcuniverse.motd", new String[] {});
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(TextComponent.fromLegacyText(Messages.PREFIX + "Current MOTD:"));
			String motd = Core.getConfig().getString("motd");
			sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', motd).replace("\\n", "\n")));
			sender.sendMessage(TextComponent.fromLegacyText(Messages.PREFIX + ChatColor.GRAY
					+ "To change the current MOTD use " + ChatColor.YELLOW + "/motd <new motd>"));
			sender.sendMessage(TextComponent.fromLegacyText(Messages.PREFIX + "To reload the config use " +ChatColor.YELLOW + " /motd reload"));
		} else {
			if (args[0].equalsIgnoreCase("reload")) {
				try {
					Core.setConfig(ConfigurationProvider.getProvider(YamlConfiguration.class).load(Core.getConfigFile()));
					sender.sendMessage(TextComponent.fromLegacyText(Messages.PREFIX + "Successfully reloaded the configuration file!"));
				} catch (IOException e) {
					e.printStackTrace();
					sender.sendMessage(TextComponent.fromLegacyText(ChatColor.RED + "Something went wrong!"));
				}
				return;
			}
			String motd = "";
			for (String s : args) {
				motd += " " + s;
			}
			Core.getConfig().set("motd", motd);
			sender.sendMessage(TextComponent.fromLegacyText(Messages.PREFIX + "Successfully changed the motd!"));
//			Bungee.saveConfig();
			Core.saveConfig();
		}
	}
	
}
