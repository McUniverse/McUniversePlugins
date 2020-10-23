package eu.mcuniverse.testing.NPC;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.testing.main.Main;

public class CommandListener implements Listener {

	@EventHandler
	public void onCMD(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (e.getMessage().startsWith("/recipeshow")) {
			e.setCancelled(true);

			ItemStack hand = p.getInventory().getItemInMainHand();
			if (hand == null || hand.getType() == Material.AIR) {
				p.sendMessage("Du hast kein Item in der Hand!");
				return;
			}

			List<Recipe> recipes = Bukkit.getRecipesFor(hand);
			p.sendMessage("Found recipes: " + recipes.size());
			
//			recipes.forEach(r -> {
//				if (r instanceof ComplexRecipe) {
//					ComplexRecipe cr = (ComplexRecipe) r;
//					cr.
//				}
//			});
			
			List<Recipe> toDisplay = recipes.stream().filter(r -> r instanceof ShapedRecipe || r instanceof ShapelessRecipe)
					.collect(Collectors.toList());
			p.sendMessage("Displaying: " + toDisplay.size());

			if (toDisplay.size() <= 0) {
				p.sendMessage("No recipes found!");
				if (toDisplay.size() != recipes.size()) {
					recipes.stream().map(r -> r.toString()).forEach(p::sendMessage);
				}
				return;
			}

			final Inventory inv = openInventory(p, toDisplay.get(0));

			new BukkitRunnable() {
				int index = 0;
				int count = 0;

				@Override
				public void run() {
					if (index >= toDisplay.size()) {
						index = 0;
					}
					Recipe r = toDisplay.get(index);
					showRecipe(p, r, inv);
					index++;
					count++;
					if (count > 20) {
						cancel();
					}
				}
			}.runTaskTimer(Main.instance, 0L, 20L);
		}
	}

	private Inventory openInventory(Player p, Recipe r) {
		Inventory inv = Bukkit.createInventory(null, InventoryType.CHEST,
				"Recipe for §1" + r.getResult().getType().toString());
		
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE));
		}
		
		Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20).forEach(slot -> {
			inv.setItem(slot, null);
		});

		inv.setItem(13, new ItemStack(Material.CRAFTING_TABLE));
		inv.setItem(15, r.getResult());

		p.openInventory(inv);
		return inv;
	}

	private void showRecipe(Player p, Recipe r, Inventory inv) {

		Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20).forEach(slot -> {
			inv.setItem(slot, null);
		});
		
		if (r instanceof ShapedRecipe) {
			ShapedRecipe sr = (ShapedRecipe) r;
			Map<Character, ItemStack> map = sr.getIngredientMap();

			for (int i = 0; i < 3; i++) {
				try {
					String s = sr.getShape()[i];
					char[] chars = s.toCharArray();
					for (int j = 0; j < 3; j++) {
						try {
							char c = chars[j];
							ItemStack item = map.get(c);
							inv.setItem((9 * i) + j, item);
						} catch (IndexOutOfBoundsException exe) {
							continue;
						}
					}
				} catch (IndexOutOfBoundsException exe) {
					continue;
				}
			}
		} else if (r instanceof ShapelessRecipe) {
			ShapelessRecipe slr = (ShapelessRecipe) r;

			int row = 0;

			for (int i = 0; i < slr.getIngredientList().size(); i++) {
				ItemStack item = slr.getIngredientList().get(i);
				if (i > 3 || i > 6)
					row++;
				inv.setItem((9 * row) + (i % 3), item);
			}
		}
	}

	private Location loc = null;
//	@EventHandler
//	public void onCMD2(PlayerCommandPreprocessEvent e) {
//		if (e.getMessage().contains("fakeplayer")) {
//			e.setCancelled(true);
//			String msg = e.getMessage().split(" ")[1];
//			NPCManager npcm = new NPCManager();
//			EntityPlayer npc = npcm.createNPC(e.getPlayer(), msg);
//
//			npcm.moveNPC(npc, e.getPlayer().getLocation(), loc);
//		} else if (e.getMessage().contains("loc")) {
//			e.setCancelled(true);
//			loc = e.getPlayer().getLocation();
//			e.getPlayer().sendMessage("Loc set!");
//		}
//	}

}
