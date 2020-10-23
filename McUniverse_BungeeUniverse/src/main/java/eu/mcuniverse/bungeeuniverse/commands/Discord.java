package eu.mcuniverse.bungeeuniverse.commands;

import eu.mcuniverse.bungeeuniverse.data.Data;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Discord extends Command {
	
	public Discord() {
		super("discord");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		sender.sendMessage(TextComponent.fromLegacyText(" "));
		sender.sendMessage(TextComponent.fromLegacyText(ChatColor.GRAY + "Discord Server: " + ChatColor.YELLOW + Data.DISCORD_URL));
		sender.sendMessage(TextComponent.fromLegacyText(" "));
	}

}
