package eu.mcuniverse.supplyrockets.command;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import eu.mcuniverse.supplyrockets.chances.MeteoriteSize;
import eu.mcuniverse.supplyrockets.meteorite.IMeteorite;
import eu.mcuniverse.universeapi.api.UniverseAPI;
import eu.mcuniverse.universeapi.rockets.Planet;

public class SummonMeteoriteCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;

		Player p = (Player) sender;

		if (args.length != 1) {
			p.sendMessage(UniverseAPI.getWarning() + "/" + label + " "
					+ Arrays.asList(MeteoriteSize.values()).stream().map(s -> s.toString()).collect(Collectors.toList()));
			return true;
		}
		
		MeteoriteSize size = null;
		try {
			size = MeteoriteSize.valueOf(args[0].toUpperCase());
		} catch (Exception e) {
			p.sendMessage(UniverseAPI.getWarning() + "Invalid size!");
			return true;
		}
		
		Planet planet = null;
		try {
			planet = Planet.getPlanet(p);
		} catch (Exception e) {
			p.sendMessage(UniverseAPI.getWarning() + "You're not on a planet!");
			return true;
		}
		
		IMeteorite meteorite = null;
		try {
			meteorite = size.getClazz().getConstructor(Planet.class).newInstance(planet);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			p.sendMessage(UniverseAPI.getWarning() + e.toString());
			e.printStackTrace();
			return true;
		}
		meteorite.spawn();
		p.sendMessage(UniverseAPI.getPrefix() + "Successfully spawned " + size.getName().toLowerCase());
		p.sendMessage("Loc: " + meteorite.getLocation().toString());
		p.sendMessage("Meteorite: " + meteorite.toString());
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (args.length != 1) {
			return null;
		}
		return Arrays.asList(MeteoriteSize.values()).stream().map(size -> size.toString())
				.filter(size -> size.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
	}

}
