package eu.mcuniverse.recipes.inventory;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.recipes.recipes.Recipes_Armor;
import eu.mcuniverse.recipes.utils.ItemUtil;

public class Reinforced_Leather implements Listener {

	private final static String NAME = "             �8-� �cArmor �8�-";
	private final static String RESOURCEN = "         �8-� �eResourcen �8�-";
	private final static String Sleather = "    �8-� �7Reinforced Leather �8�-";
	static ArrayList<String> lore = new ArrayList<>();

	@EventHandler
	public void on(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equalsIgnoreCase(NAME) || e.getView().getTitle().equalsIgnoreCase(RESOURCEN)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�cClose")) {
				p.closeInventory();
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�7Reinforced Leather")) {
				p.closeInventory();

				Inventory inv = Bukkit.createInventory(null, 54, Sleather);

				ItemStack black = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
				ItemMeta blackmeta = black.getItemMeta();
				blackmeta.setDisplayName("�0");
				black.setItemMeta(blackmeta);

				ItemStack barrier = new ItemStack(Material.BARRIER);
				ItemMeta barriermeta = barrier.getItemMeta();
				barriermeta.setDisplayName("�4Close");
				barrier.setItemMeta(barriermeta);

				ItemStack back = new ItemStack(Material.ARROW);
				ItemMeta backmeta = back.getItemMeta();
				backmeta.setDisplayName("�cBack");
				back.setItemMeta(backmeta);

				ItemStack leather = new ItemStack(Material.LEATHER);
				ItemMeta leathermeta = leather.getItemMeta();
				leather.setItemMeta(leathermeta);

				ItemStack sleather = ItemUtil.createSkinItem(Material.LEATHER, "�7Reinforced Leather", 10000001);

				ItemStack wb = new ItemStack(Material.CRAFTING_TABLE);
				ItemMeta wbmeta = wb.getItemMeta();
				wbmeta.setDisplayName("�aCrafting Table");
				wbmeta.setLore(Arrays.asList("�7Craft this recipe by using", "�7a crafting table."));
				wb.setItemMeta(wbmeta);

				ItemStack string = new ItemStack(Material.STRING);
				ItemMeta stringmeta = string.getItemMeta();
				string.setItemMeta(stringmeta);

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

				inv.setItem(20, leather);
				inv.setItem(23, wb);
				inv.setItem(48, back);
				inv.setItem(49, barrier);
				inv.setItem(11, string);
				inv.setItem(19, string);
				inv.setItem(25, sleather);
				inv.setItem(21, string);
				inv.setItem(29, string);

				p.openInventory(inv);
			}
		}
	}

	@EventHandler
	public void Grenate(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equalsIgnoreCase(Sleather)) {
			e.setCancelled(true);
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�4Close")) {
				p.closeInventory();
			}
			if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("�cBack")) {
				p.openInventory(Recipes_Armor.inv);
			}
		}
	}

}
