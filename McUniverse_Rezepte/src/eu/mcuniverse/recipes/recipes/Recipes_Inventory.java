package eu.mcuniverse.recipes.recipes;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;


public class Recipes_Inventory implements Listener {

	private final static String NAME = "           §8-» §dRecipes §8«-";
	static ItemStack grenade = new ItemStack(Material.FIREWORK_STAR, 1);
	static FireworkEffect fwe = FireworkEffect.builder().withColor(Color.LIME).build();
	static FireworkEffectMeta fwm = (FireworkEffectMeta) grenade.getItemMeta();
	static ArrayList<String> lore = new ArrayList<>();
	public static Inventory inv = Bukkit.createInventory(null, 54, NAME);

	public static void openGUI(Player p) {

		ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta blackmeta = black.getItemMeta();
		blackmeta.setDisplayName("§0");
		black.setItemMeta(blackmeta);

		ItemStack barrier = new ItemStack(Material.BARRIER);
		ItemMeta barriermeta = barrier.getItemMeta();
		barriermeta.setDisplayName("§cClose");
		barrier.setItemMeta(barriermeta);
		
		fwm.setEffect(fwe);
		fwm.setDisplayName("§4Grenade");
		fwm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		grenade.setItemMeta(fwm);
		
		ItemStack c4 = new ItemStack(Material.JUNGLE_BUTTON);
		ItemMeta c4meta = c4.getItemMeta();
		c4meta.setDisplayName("§cC4");
		c4.setItemMeta(c4meta);
		
		ItemStack armor = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
		ItemMeta armormeta = armor.getItemMeta();
		armormeta.setDisplayName("§cArmor");
		armor.setItemMeta(armormeta);
		
		ItemStack ressource = new ItemStack(Material.LAVA_BUCKET);
		ItemMeta ressourcemeta = ressource.getItemMeta();
		ressourcemeta.setDisplayName("§eRessourcen");
		ressource.setItemMeta(ressourcemeta);
		
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, black);
//			inv.setItem(21, c4);
//			inv.setItem(13, grenade);
			inv.setItem(23, ressource);
			inv.setItem(13, armor);
			inv.setItem(49, barrier);

			p.openInventory(inv);
		}
	
	}

}
