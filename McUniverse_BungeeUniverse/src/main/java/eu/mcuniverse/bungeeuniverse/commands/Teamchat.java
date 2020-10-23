package eu.mcuniverse.bungeeuniverse.commands;

import eu.mcuniverse.bungeeuniverse.Core;
import eu.mcuniverse.bungeeuniverse.data.Messages;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Teamchat extends Command {

	private final String TC_PREFIX = ChatColor.BLUE + "TeamChat" + ChatColor.DARK_GRAY + " \u25CF " + ChatColor.GRAY;

	public Teamchat() {
		super("adminchat", "mcuniverse.teamchat", new String[] { "tc" });
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(TextComponent.fromLegacyText("No Player", ChatColor.RED));
			return;
		}

		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (args.length == 0) {
			p.sendMessage(TextComponent.fromLegacyText(Messages.WARNING + "/teamchat <Message>"));
			return;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i] + " ");
		}

		String message = TC_PREFIX + p.getName() + " " + ChatColor.DARK_GRAY + " " + Messages.ARROW + " " + ChatColor.RED
				+ ChatColor.translateAlternateColorCodes('&', sb.toString());

		Core.getInstance()
				.getProxy()
				.getPlayers()
				.stream()
				.filter(players -> players.hasPermission(this.getPermission()))
				.forEach(players -> players.sendMessage(TextComponent.fromLegacyText(message)));
	}

}
