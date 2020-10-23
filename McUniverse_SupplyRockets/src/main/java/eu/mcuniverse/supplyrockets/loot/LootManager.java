package eu.mcuniverse.supplyrockets.loot;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import eu.mcuniverse.supplyrockets.chances.Category;
import eu.mcuniverse.supplyrockets.chances.Rarity;
import eu.mcuniverse.supplyrockets.chances.Tier;
import eu.mcuniverse.supplyrockets.utils.RandomUtils;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LootManager {

	public ObjectSet<ItemStack> getLoot(Tier tier) {
		ObjectSet<ItemStack> content = new ObjectOpenHashSet<ItemStack>();

		ThreadLocalRandom rand = ThreadLocalRandom.current();
		for (Category category : Category.values()) {
			int amount = rand.nextInt(category.getMin(), category.getMax());
			for (int i = 0; i < amount; i++) {
				Rarity rarity = tier.getChances().next();
				try {
					content.add(RandomUtils.getRandomElementV2(LootItems.getAllItems(rarity, category)));
				} catch (Exception e) {
					Bukkit.getLogger().severe("LootManager.getLoot(): Error: " + e.toString());
				}
			}
		}

		return content;
	}
}