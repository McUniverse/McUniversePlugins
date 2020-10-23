package eu.mcuniverse.essentials.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import eu.mcuniverse.universeapi.api.UniverseAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class MapCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		sender.spigot().sendMessage(new ComponentBuilder()
				.append(UniverseAPI.getPrefix())
				.event(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("Click to open in the browser", ChatColor.YELLOW)))
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mcuniverse.eu:8123"))
				.append("Dynmap-Link: ")
				.color(ChatColor.GRAY)
				.event(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("Click to open in the browser", ChatColor.YELLOW)))
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mcuniverse.eu:8123"))
				.append("http://mcuniverse.eu:8123")
				.color(ChatColor.YELLOW)
				.underlined(true)
				.event(new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("Click to open in the browser", ChatColor.YELLOW)))
				.event(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mcuniverse.eu:8123"))
				.create());
		
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		return new ArrayList<String>();
	}

}
