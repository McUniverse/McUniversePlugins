package eu.mcuniverse.recipes.main;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Recipes implements CommandExecutor {
	
	private final String NAME = "§8-» §dRecipes §8«-";

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player)sender;

		ItemStack Rezept = new ItemStack(Material.STRUCTURE_BLOCK);
		ItemMeta Rezeptmeta = Rezept.getItemMeta();
		Rezeptmeta.setDisplayName(NAME);
		Rezept.setItemMeta(Rezeptmeta);

		p.getInventory().setItemInHand(Rezept);
		p.updateInventory();
		
		
		return false;
	}
}
