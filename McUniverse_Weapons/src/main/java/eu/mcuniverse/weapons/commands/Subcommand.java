package eu.mcuniverse.weapons.commands;

import org.bukkit.entity.Player;

public abstract class Subcommand {

	// Get the name of the subcommand
	public abstract String getName();
	
	// Get the description of the subcommand
	public abstract String getDescription();
	
	// Get the syntax of the subcommand
	public abstract String getSyntax();
	
	// ode for the subcommand
	public abstract void perform(Player player, String args[]);
	
	
}
