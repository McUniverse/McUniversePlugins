package eu.mcuniverse.supplyrockets.chances.random;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.supplyrockets.chances.Category;
import eu.mcuniverse.supplyrockets.chances.Rarity;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;

public class RandomTest {

//	public static void main(String[] args) {
////		RandomCollection<Rarity> map = new ChanceMapBuilder().putCommon(60).putUncommon(30).putRare(10).build();
//		RandomCollection<Rarity> map = chances(60, 30, 10);
//
//		map.map.entrySet().forEach(e -> {
//			System.out.println(e.getValue() + "=>" + e.getKey());
//		});
//		
//		Object2IntOpenHashMap<Rarity> result = new Object2IntOpenHashMap<>();
//		
//		for (int i = 0; i < 100000; i++) {
//			Rarity rarity = map.next();
//			result.putIfAbsent(rarity, 0);
//			result.compute(rarity, (key, value) -> value + 1);
//		}
//		
//		long total = result.values().stream().reduce(0, Integer::sum);
//		
//		System.out.println(total);
//		
//		System.out.println("------------------------------");
//		
//		result.forEach((k, v) -> {
//			System.out.println(k + "=>" + v + "=>" +((double) v / total) * 100);
//		});
//		
//		ObjectSet<ItemStack> items = new ObjectOpenHashSet<>();
//		
//		for (Category category : Category.values()) {
//			Rarity rarity = map.next();
//			items.add(getItems(rarity, category).iterator().next());
//		}
//		
//	}
//	
	public static ObjectSet<ItemStack> getTestItems() {
		ObjectSet<ItemStack> items = new ObjectOpenHashSet<>();
		
		RandomCollection<Rarity> map = chances(94, 3, 3); // Chances in % (should add up to 100%)

		
		for (Category category : Category.values()) {
			Rarity rarity = map.next();
			items.add(getItems(rarity, category).iterator().next());
		}
		return items;
	}
	
	private static RandomCollection<Rarity> chances(double common, double uncommon, double rare) {
		return new RandomCollection<Rarity>().add(common, Rarity.COMMON).add(uncommon, Rarity.UNCOMMON).add(rare,
				Rarity.RARE);
	}

	private static ObjectSet<ItemStack> getItems(Rarity rarity, Category category) {
		ObjectSet<ItemStack> items = new ObjectOpenHashSet<ItemStack>();
		
		items.add(toStack(rarity, category));
		
		/*		switch (category) {
		case BASIC:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}
			break;
		case NETHER:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}
			break;
		case PRISMARINE:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}
			break;

		case END:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}

		case PLANTS:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}
		case ORES_MOB:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}
		case EGGS:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}
		case FIREARM:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}
		case HEAVY:
			switch (rarity) {
			case COMMON:
				break;
			case UNCOMMON:
				break;
			case RARE:
				break;
			default:
				break;
			}

		default:
			break;
		}*/
		return items;
	}

	private static ItemStack toStack(Rarity rarity, Category category) {
		String name = rarity.name() + " -> " + category.name();
		ItemStack itemStack = new ItemStack(Material.AIR);
		switch (rarity) {
		case COMMON:
			itemStack.setType(Material.GRAY_WOOL);
			break;
		case UNCOMMON:
			itemStack.setType(Material.LIME_WOOL);
			break;
		case RARE:
			itemStack.setType(Material.PURPLE_WOOL);
			break;
		default:
			itemStack.setType(Material.RED_CONCRETE);;
		}
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		itemStack.setItemMeta(meta);
		
		return itemStack;
	}
	
}
