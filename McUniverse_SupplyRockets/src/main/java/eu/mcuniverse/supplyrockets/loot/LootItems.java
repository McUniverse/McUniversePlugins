package eu.mcuniverse.supplyrockets.loot;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

import eu.mcuniverse.supplyrockets.chances.Category;
import eu.mcuniverse.supplyrockets.chances.Rarity;
import eu.mcuniverse.universeapi.util.ItemUtil;
import eu.mcuniverse.weapons.weapon.Famas;
import eu.mcuniverse.weapons.weapon.Glock22;
import eu.mcuniverse.weapons.weapon.HKMP5;
import eu.mcuniverse.weapons.weapon.LaserLMG;
import eu.mcuniverse.weapons.weapon.LaserRifle;
import eu.mcuniverse.weapons.weapon.LaserSniper;
import eu.mcuniverse.weapons.weapon.RocketLauncher;
import eu.mcuniverse.weapons.weapon.SCAR;
import eu.mcuniverse.weapons.weapon.Shotgun;
import eu.mcuniverse.weapons.weapon.util.WeaponManager;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LootItems {

	private final boolean DEBUG = false;

	public ObjectSet<ItemStack> getAllItems(Rarity rarity, Category category) {
		ObjectSet<ItemStack> items = new ObjectOpenHashSet<ItemStack>();

		switch (category) {
		case BASIC:
			switch (rarity) {
			case COMMON:
				items.add(toStack(Material.STONE));
				items.add(toStack(Material.COBBLESTONE));
				items.add(toStack(Material.MOSSY_COBBLESTONE));
				items.add(toStack(Material.GRAVEL));
				items.add(toStack(Material.GRANITE));
				items.add(toStack(Material.DIORITE));
				items.add(toStack(Material.ANDESITE));
				items.add(toStack(Material.GRASS_BLOCK));
				items.add(toStack(Material.DIRT));
				items.add(toStack(Material.PODZOL));
				items.add(toStack(Material.MYCELIUM));
				items.add(toStack(Material.SAND));
				items.add(toStack(Material.SANDSTONE));
				items.add(toStack(Material.RED_SAND));
				items.add(toStack(Material.RED_SANDSTONE));
				items.add(toStack(Material.GLASS));
				items.add(toStack(Material.TERRACOTTA));
				items.add(toStack(Material.CLAY));
				items.add(toStack(Material.BRICKS));
				items.add(toStack(Material.ICE));
				items.add(toStack(Material.SNOW));
				items.add(toStack(Material.WHITE_WOOL));
				items.add(toStack(Material.SEA_LANTERN));
				items.add(toStack(Material.OAK_LOG));
				items.add(toStack(Material.SPRUCE_LOG));
				items.add(toStack(Material.BIRCH_LOG));
				items.add(toStack(Material.JUNGLE_LOG));
				items.add(toStack(Material.ACACIA_LOG));
				items.add(toStack(Material.DARK_OAK_LOG));
				break;
			case UNCOMMON:
				items.add(toStack(Material.FIRE_CORAL_BLOCK));
				items.add(toStack(Material.BUBBLE_CORAL_BLOCK));
				items.add(toStack(Material.BRAIN_CORAL_BLOCK));
				items.add(toStack(Material.TUBE_CORAL_BLOCK));
				items.add(toStack(Material.HORN_CORAL_BLOCK));
				items.add(toStack(Material.HONEYCOMB_BLOCK));
				break;
			case RARE:
				items.add(toStack(Material.BEE_NEST));
				break;
			default:
				break;
			}
			break;
		case NETHER:
			switch (rarity) {
			case COMMON:
				items.add(toStack(Material.MAGMA_BLOCK));
				items.add(toStack(Material.SOUL_SAND));
				items.add(toStack(Material.NETHER_WART_BLOCK));
				items.add(toStack(Material.NETHER_BRICKS));
				items.add(toStack(Material.NETHER_BRICK_STAIRS));
				items.add(toStack(Material.NETHER_BRICK_SLAB));
				items.add(toStack(Material.NETHER_BRICK_WALL));
				items.add(toStack(Material.NETHER_BRICK_FENCE));
				items.add(toStack(Material.RED_NETHER_BRICKS));
				items.add(toStack(Material.RED_NETHER_BRICK_STAIRS));
				items.add(toStack(Material.RED_NETHER_BRICK_SLAB));
				items.add(toStack(Material.RED_NETHER_BRICK_WALL));
				items.add(toStack(Material.NETHERRACK));
				items.add(toStack(Material.NETHER_BRICK));
				items.add(toStack(Material.GLOWSTONE));
				break;
			case UNCOMMON:
				items.add(toStack(Material.BLAZE_ROD));
				items.add(toStack(Material.QUARTZ));
				items.add(toStack(Material.BREWING_STAND));
				items.add(toStack(Material.NETHER_WART));
				break;
			case RARE:
				items.add(toStack(Material.WITHER_ROSE));
				break;
			default:
				break;
			}
			break;
		case PRISMARINE:
			switch (rarity) {
			case COMMON:
				items.add(toStack(Material.DARK_PRISMARINE));
				items.add(toStack(Material.DARK_PRISMARINE_STAIRS));
				items.add(toStack(Material.DARK_PRISMARINE_SLAB));
				items.add(toStack(Material.PRISMARINE));
				items.add(toStack(Material.PRISMARINE_STAIRS));
				items.add(toStack(Material.PRISMARINE_SLAB));
				items.add(toStack(Material.PRISMARINE_BRICKS));
				items.add(toStack(Material.PRISMARINE_BRICK_STAIRS));
				items.add(toStack(Material.PRISMARINE_BRICK_SLAB));
				items.add(toStack(Material.PRISMARINE_WALL));
				items.add(toStack(Material.PRISMARINE_SHARD));
				items.add(toStack(Material.PRISMARINE_CRYSTALS));
				break;
			case UNCOMMON:
				items.add(toStack(Material.SPONGE));
				items.add(toStack(Material.NAUTILUS_SHELL));
				break;
			case RARE:
				items.add(toStack(Material.TRIDENT));
				items.add(toStack(Material.HEART_OF_THE_SEA));
				break;
			default:
				break;
			}
			break;

		case END:
			switch (rarity) {
			case COMMON:
				items.add(toStack(Material.END_STONE));
				items.add(toStack(Material.END_STONE_BRICKS));
				items.add(toStack(Material.END_STONE_BRICK_STAIRS));
				items.add(toStack(Material.END_STONE_BRICK_SLAB));
				items.add(toStack(Material.END_STONE_BRICK_WALL));
				items.add(toStack(Material.PURPUR_PILLAR));
				items.add(toStack(Material.PURPUR_BLOCK));
				items.add(toStack(Material.PURPUR_STAIRS));
				items.add(toStack(Material.PURPUR_SLAB));
				break;
			case UNCOMMON:
//				items.add(toStack(Material.ENDER_PEARL));
				items.add(toStack(Material.END_ROD));
				items.add(toStack(Material.OBSIDIAN));
				break;
			case RARE:
				items.add(toStack(Material.DRAGON_BREATH));
				break;
			default:
				break;
			}

		case PLANTS:
			switch (rarity) {
			case COMMON:
				items.add(toStack(Material.DANDELION));
				items.add(toStack(Material.POPPY));
				items.add(toStack(Material.BLUE_ORCHID));
				items.add(toStack(Material.ALLIUM));
				items.add(toStack(Material.AZURE_BLUET));
				items.add(toStack(Material.RED_TULIP));
				items.add(toStack(Material.ORANGE_TULIP));
				items.add(toStack(Material.WHITE_TULIP));
				items.add(toStack(Material.PINK_TULIP));
				items.add(toStack(Material.OXEYE_DAISY));
				items.add(toStack(Material.CORNFLOWER));
				items.add(toStack(Material.LILY_OF_THE_VALLEY));
				items.add(toStack(Material.PUMPKIN));
				items.add(toStack(Material.MELON));
				items.add(toStack(Material.MELON_SLICE));
				items.add(toStack(Material.BROWN_MUSHROOM));
				items.add(toStack(Material.RED_MUSHROOM));
				items.add(toStack(Material.SUNFLOWER));
				items.add(toStack(Material.LILAC));
				items.add(toStack(Material.ROSE_BUSH));
				items.add(toStack(Material.PEONY));
				items.add(toStack(Material.LILY_PAD));
				items.add(toStack(Material.VINE));
				items.add(toStack(Material.KELP));
				items.add(toStack(Material.SUGAR_CANE));
				items.add(toStack(Material.BAMBOO));
				items.add(toStack(Material.CARROT));
				items.add(toStack(Material.POTATO));
				items.add(toStack(Material.APPLE));
				items.add(toStack(Material.SWEET_BERRIES));
				items.add(toStack(Material.BEETROOT_SEEDS));
				items.add(toStack(Material.PUMPKIN_SEEDS));
				items.add(toStack(Material.MELON_SEEDS));
				items.add(toStack(Material.COCOA_BEANS));
				items.add(toStack(Material.OAK_SAPLING));
				items.add(toStack(Material.SPRUCE_SAPLING));
				items.add(toStack(Material.BIRCH_SAPLING));
				items.add(toStack(Material.ACACIA_SAPLING));
				items.add(toStack(Material.JUNGLE_SAPLING));
				items.add(toStack(Material.DARK_OAK_SAPLING));
				items.add(toStack(Material.HONEYCOMB));
				items.add(toStack(Material.HONEY_BOTTLE));
				break;
			case UNCOMMON:
//				return getAllItems(Rarity.COMMON, category);
				break;
			case RARE:
//				return getAllItems(Rarity.UNCOMMON, category);
				break;
			default:
				break;
			}
		case ORES_MOB:
			switch (rarity) {
			case COMMON:
				items.add(toStack(Material.SCUTE));
				items.add(toStack(Material.STRING));
				items.add(toStack(Material.FEATHER));
				items.add(toStack(Material.GUNPOWDER));
				items.add(toStack(Material.FLINT));
				items.add(toStack(Material.LEATHER));
				items.add(toStack(Material.INK_SAC));
				items.add(toStack(Material.BONE));
				items.add(toStack(Material.PAPER));
				items.add(toStack(Material.RABBIT_HIDE));
				items.add(toStack(Material.SNOWBALL));
				items.add(toStack(Material.CHARCOAL));
				items.add(toStack(Material.COAL));
				items.add(toStack(Material.REDSTONE));
				items.add(toStack(Material.LAPIS_LAZULI));
				items.add(toStack(Material.IRON_INGOT));
				items.add(toStack(Material.BRICK));
				items.add(toStack(Material.GOLD_INGOT));
				break;
			case UNCOMMON:
				items.add(toStack(Material.FERMENTED_SPIDER_EYE));
				items.add(toStack(Material.GHAST_TEAR));
				items.add(toStack(Material.BLAZE_POWDER));
				items.add(toStack(Material.SPIDER_EYE));
				items.add(toStack(Material.PHANTOM_MEMBRANE));
				items.add(toStack(Material.RABBIT_FOOT));
				items.add(toStack(Material.MAGMA_CREAM));
				items.add(toStack(Material.SLIME_BALL));
				items.add(toStack(Material.DIAMOND));
				break;
			case RARE:
//				return getAllItems(Rarity.UNCOMMON, category);
				break;
			default:
				break;
			}
		case EGGS:
			switch (rarity) {
			case COMMON:
				items.add(toStack(Material.CAT_SPAWN_EGG));
				items.add(toStack(Material.BEE_SPAWN_EGG));
				items.add(toStack(Material.POLAR_BEAR_SPAWN_EGG));
				items.add(toStack(Material.COW_SPAWN_EGG));
				items.add(toStack(Material.TURTLE_SPAWN_EGG));
				items.add(toStack(Material.RABBIT_SPAWN_EGG));
				items.add(toStack(Material.FOX_SPAWN_EGG));
				items.add(toStack(Material.HORSE_SPAWN_EGG));
				items.add(toStack(Material.LLAMA_SPAWN_EGG));
				items.add(toStack(Material.WOLF_SPAWN_EGG));
				items.add(toStack(Material.SHEEP_SPAWN_EGG));
				items.add(toStack(Material.OCELOT_SPAWN_EGG));
				items.add(toStack(Material.PANDA_SPAWN_EGG));
				items.add(toStack(Material.PARROT_SPAWN_EGG));
				items.add(toStack(Material.PIG_SPAWN_EGG));
				items.add(toStack(Material.CHICKEN_SPAWN_EGG));
				items.add(toStack(Material.TURTLE_EGG));
				items.add(toStack(Material.SADDLE));
				items.add(toStack(Material.LEATHER_HORSE_ARMOR));
				items.add(toStack(Material.IRON_HORSE_ARMOR));
				items.add(toStack(Material.GOLDEN_HORSE_ARMOR));
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
				items.add(toStack(Material.VILLAGER_SPAWN_EGG));
				break;
			case UNCOMMON:
				break;
			case RARE:
				items.add(WeaponManager.getItem(new Shotgun()));
				items.add(WeaponManager.getItem(new Glock22()));
				items.add(WeaponManager.getItem(new HKMP5()));
				items.add(WeaponManager.getItem(new Famas()));
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
				items.add(WeaponManager.getItem(new LaserRifle()));
				items.add(WeaponManager.getItem(new LaserSniper()));
				items.add(WeaponManager.getItem(new LaserLMG()));
				items.add(WeaponManager.getItem(new RocketLauncher()));
				items.add(WeaponManager.getItem(new SCAR()));
				break;
			default:
				break;
			}

		default:
			break;
		}

		if (DEBUG) {
			if (items.size() == 0)
				items.add(ItemUtil.createItem(Material.BARRIER, category.name() + " --- " + rarity.name()));

			items.forEach(i -> {
				ItemMeta meta = i.getItemMeta();
				List<String> lore = meta.hasLore() ? meta.getLore() : Lists.newArrayList();
				lore.add("");
				lore.add("Rarity: " + rarity);
				lore.add("Category: " + category);
				meta.setLore(lore);
				i.setItemMeta(meta);
			});
		}

		return items;
	}

	private ItemStack toStack(Material material) {
		return new ItemStack(material);
	}

}