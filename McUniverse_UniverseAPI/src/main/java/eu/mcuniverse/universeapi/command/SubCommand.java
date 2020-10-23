package eu.mcuniverse.universeapi.command;

import org.bukkit.entity.Player;

import lombok.NonNull;

/**
 * Abstract class to make a CommandManager
 * @author jay
 *
 */
public abstract class SubCommand implements Comparable<SubCommand> {

	/**
	 * Get the name of the subcommand
	 * @return The name
	 */
	public abstract String getName();
	
	/**
	 * Get the description of the subcommand
	 * @return The description
	 */
	public abstract String getDescription();
	
	/**
	 * Get the syntax of the subcommand
	 * @return The syntax
	 */
	public abstract String getSyntax();
	
	// ode for the subcommand
	/**
	 * Code for the subcommand
	 * @param player The Player who performed the command
	 * @param args Arguments of the command (Same as <code>onCommand</code>
	 */
	public abstract void perform(@NonNull Player player, String args[]);
	
	@Override
	public int compareTo(SubCommand o) {
		return getName().compareTo(o.getName());
	}
	
}
