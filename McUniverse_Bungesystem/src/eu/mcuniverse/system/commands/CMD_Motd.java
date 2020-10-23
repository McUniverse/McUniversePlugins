package eu.mcuniverse.system.commands;

import java.io.IOException;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class CMD_Motd extends Command {

	public CMD_Motd() {
		super("motd", "mcuniverse.motd", new String[] {});
	}

	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + "Current MOTD:"));
			String motd = Bungee.config.getString("motd");
			sender.sendMessage(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', motd).replace("\\n", "\n")));
			sender.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + ChatColor.GRAY
					+ "To change the current MOTD use " + ChatColor.YELLOW + "/motd <new motd>"));
			sender.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + "To reload the config use " +ChatColor.YELLOW + " /motd reload"));
		} else {
			if (args[0].equalsIgnoreCase("reload")) {
				try {
					Bungee.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(Bungee.file);
					sender.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + "Successfully reloaded the configuration file!"));
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
			Bungee.config.set("motd", motd);
			sender.sendMessage(TextComponent.fromLegacyText(Bungee.prefix + "Successfully changed the motd!"));
			Bungee.saveConfig();
		}
	}
	

}
