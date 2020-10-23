package eu.mcuniverse.recipes.inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.recipes.recipes.Recipes_Inventory;

public class Item_Grenade implements Listener {
	
	private final static String NAME = "           §8-» §dRecipes §8«-";
	private final static String GRENADE = "          §8-» §4Grenade §8«-";
	static ItemStack grenade = new ItemStack(Material.FIREWORK_STAR, 1);
	static FireworkEffect fwe = FireworkEffect.builder().withColor(Color.LIME).build();
	static FireworkEffectMeta fwm = (FireworkEffectMeta) grenade.getItemMeta();
	static ArrayList<String> lore = new ArrayList<>();
	
	@EventHandler
	public void on(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equalsIgnoreCase(NAME)) {
			e.setCancelled(true);
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cClose")) {
					p.closeInventory();
				}
				if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§4Grenade")) {
					p.closeInventory();
					
					Inventory inv = Bukkit.createInventory(null, 54, GRENADE);
					
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
					
					ItemStack wb = new ItemStack(Material.CRAFTING_TABLE);
					ItemMeta wbmeta = wb.getItemMeta();
					wbmeta.setDisplayName("§aCrafting Table");
					wbmeta.setLore(Arrays.asList("§7Craft this recipe by using", "§7a crafting table."));
					wb.setItemMeta(wbmeta);
					
					fwm.setEffect(fwe);
					fwm.setDisplayName("§4Grenade");
					fwm.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
					grenade.setItemMeta(fwm);

					    inv.setItem(0, black);
						inv.setItem(1, black);
						inv.setItem(2, black);
						inv.setItem(3, black);
						inv.setItem(4, black);
						inv.setItem(5, black);
						inv.setItem(6, black);
						inv.setItem(7, black);
						inv.setItem(8, black);
						inv.setItem(9, black);
						inv.setItem(13, black);
						inv.setItem(14, black);
						inv.setItem(14, black);
						inv.setItem(15, black);
						inv.setItem(16, black);
						inv.setItem(17, black);
						inv.setItem(18, black);
						inv.setItem(22, black);
						inv.setItem(24, black);
						inv.setItem(26, black);
						inv.setItem(27, black);
						inv.setItem(31, black);
						inv.setItem(32, black);
						inv.setItem(33, black);
						inv.setItem(34, black);
						inv.setItem(35, black);
						inv.setItem(36, black);
						inv.setItem(37, black);
						inv.setItem(38, black);
						inv.setItem(39, black);
						inv.setItem(40, black);
						inv.setItem(41, black);
						inv.setItem(42, black);
						inv.setItem(43, black);
						inv.setItem(44, black);
						inv.setItem(45, black);
						inv.setItem(46, black);
						inv.setItem(47, black);
						inv.setItem(50, black);
						inv.setItem(51, black);
						inv.setItem(52, black);
						inv.setItem(53, black);
						
						
						
						inv.setItem(25, grenade);
						inv.setItem(23, wb);
						inv.setItem(48, back);
						inv.setItem(49, barrier);
					
					p.openInventory(inv);
				}
				}
		}
		
		@EventHandler
		public void Grenate(InventoryClickEvent e) {
			Player p = (Player) e.getWhoClicked();
			if (e.getView().getTitle().equalsIgnoreCase(GRENADE)) {
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
