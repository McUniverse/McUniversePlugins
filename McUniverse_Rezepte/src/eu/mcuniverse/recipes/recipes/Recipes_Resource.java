package eu.mcuniverse.recipes.recipes;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.recipes.utils.ItemUtil;

public class Recipes_Resource implements Listener {
	
	private final static String NAME = "           §8-» §dRecipes §8«-";
	private final static String Armor = "         §8-» §eResourcen §8«-";
	static ArrayList<String> lore = new ArrayList<>();
	public static Inventory inv = Bukkit.createInventory(null, 54, Armor);
	
	@EventHandler
	public void on(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equalsIgnoreCase(NAME)) {
			e.setCancelled(true);
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")) {
					p.closeInventory();
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eRessourcen")) {
					p.closeInventory();
					
					ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					ItemMeta blackmeta = black.getItemMeta();
					blackmeta.setDisplayName("§0");
					black.setItemMeta(blackmeta);
					
					ItemStack barrier = new ItemStack(Material.BARRIER);
					ItemMeta barriermeta = barrier.getItemMeta();
					barriermeta.setDisplayName("§4Close");
					barrier.setItemMeta(barriermeta);
					
					ItemStack back = new ItemStack(Material.ARROW);
					ItemMeta backmeta = back.getItemMeta();
					backmeta.setDisplayName("§cBack");
					back.setItemMeta(backmeta);
					
					
					ItemStack Leather = ItemUtil.createSkinItem(Material.LEATHER, "§7Reinforced Leather", 10000001);
					

					for (int i = 0; i < inv.getSize(); i++) {
						inv.setItem(i, black);
						
						inv.setItem(10, Leather);
						inv.setItem(48, back);
						inv.setItem(49, barrier);
					
					p.openInventory(inv);
				}
				}
		}
		}
		
		@EventHandler
		public void Grenate(InventoryClickEvent e) {
			Player p = (Player) e.getWhoClicked();
			if (e.getView().getTitle().equalsIgnoreCase(Armor)) {
				e.setCancelled(true);
					if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Close")) {
						p.closeInventory();
					}
					if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cBack")) {
						p.openInventory(Recipes_Inventory.inv);
					}
			}
	}

}
