package eu.mcuniverse.ktesting

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

object DungeonCommand : TabExecutor {
	
	override fun onCommand(sender: CommandSender?, command: Command?, label: String?, args: Array<out String?>): Boolean {
		
		if (sender !is Player) {
			sender!!.sendMessage("Not a player");
			return true;
		}
		
		val p: Player = sender;
		
		if (!p.isOp()) {
			p.sendMessage("You're not op!");
			return true;
		}
		
//		StructureManager.loadSchematic();
		
		return true;
	}
	
	override fun onTabComplete(sender: CommandSender?, command: Command?, alias: String?, args: Array<out String?>): List<String> {
		return listOf("");
	}
	
}