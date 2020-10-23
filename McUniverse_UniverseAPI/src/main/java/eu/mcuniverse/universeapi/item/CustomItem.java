package eu.mcuniverse.universeapi.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

import eu.mcuniverse.universeapi.util.ItemUtil;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class CustomItem {

	public static ItemStack whiteleather = new ItemStack(Material.LEATHER);
	private static ItemMeta whiteMeta = whiteleather.getItemMeta();

	public static ItemStack enhancedcoal = new ItemStack(Material.COAL);
	private static ItemMeta menhancedcoal = enhancedcoal.getItemMeta();

	public static ItemStack enhancedredstone = new ItemStack(Material.REDSTONE);
	private static ItemMeta menhancedredstone = enhancedredstone.getItemMeta();

	public static ItemStack enhancedGlass = new ItemStack(Material.GLASS);
	private static ItemMeta menhancedGlass = enhancedGlass.getItemMeta();

	public static ItemStack lighter = new ItemStack(Material.FLINT_AND_STEEL);
	private static ItemMeta mlighter = lighter.getItemMeta();

	public static ItemStack airfilter = new ItemStack(Material.COBWEB);
	private static ItemMeta mairfilter = airfilter.getItemMeta();

	public static ItemStack enhancedCircutBoard = new ItemStack(Material.BLAZE_ROD);
	private static ItemMeta menhancedCircutBoard = enhancedCircutBoard.getItemMeta();

	public static ItemStack enhancedIron = new ItemStack(Material.IRON_INGOT);
	private static ItemMeta menhancedIron = enhancedIron.getItemMeta();

	public static ItemStack CircutBoard = new ItemStack(Material.BLAZE_ROD);
	private static ItemMeta mCircutBoard = CircutBoard.getItemMeta();

	public static ItemStack enhancedWood = new ItemStack(Material.OAK_LOG);
	private static ItemMeta menhancedWood = enhancedWood.getItemMeta();

	public static ItemStack goldWire = new ItemStack(Material.GOLD_INGOT);
	private static ItemMeta mgoldWire = goldWire.getItemMeta();

	public static ItemStack enhancedChest = new ItemStack(Material.ENDER_CHEST);
	private static ItemMeta menhancedChest = enhancedChest.getItemMeta();

	public static ItemStack ghastt = new ItemStack(Material.GHAST_TEAR);
	private static ItemMeta mghastt = ghastt.getItemMeta();

	public static ItemStack grenade = new ItemStack(Material.BLAZE_ROD);
	private static ItemMeta mgrenade = grenade.getItemMeta();

	public static ItemStack c4 = new ItemStack(Material.JUNGLE_BUTTON);
	private static ItemMeta mc4 = c4.getItemMeta();

	public static ItemStack igniter = new ItemStack(Material.BLAZE_ROD);
	private static ItemMeta migniter = igniter.getItemMeta();

	public static ItemStack ammu = new ItemStack(Material.BLAZE_ROD, 4);
	private static ItemMeta mammu = ammu.getItemMeta();

	public static ItemStack rocketammu = new ItemStack(Material.BLAZE_ROD, 1);
	private static ItemMeta mrocketammu = rocketammu.getItemMeta();

	public static ItemStack laserammu = new ItemStack(Material.BLAZE_ROD, 8);
	private static ItemMeta mlaserammu = laserammu.getItemMeta();

	public static ItemStack enhancedCompass = new ItemStack(Material.COMPASS);
	private static ItemMeta menhancedCompass = enhancedCompass.getItemMeta();

	public static ItemStack cobweb = new ItemStack(Material.COBWEB);
	private static ItemMeta mcobweb = cobweb.getItemMeta();

	static {
		mghastt.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Rocket Gear");
		mghastt.addEnchant(Enchantment.OXYGEN, 10, true);
		mghastt.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		mghastt.setCustomModelData(10000001);
		ghastt.setItemMeta(mghastt);

		whiteMeta.setCustomModelData(10000001);
		whiteMeta.setDisplayName(ChatColor.GRAY + "Reinforced Leather");
		whiteleather.setItemMeta(whiteMeta);

		menhancedcoal.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		menhancedcoal.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		menhancedcoal.setDisplayName("Enhanced Coal");
		enhancedcoal.setItemMeta(menhancedcoal);

		menhancedredstone.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		menhancedredstone.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		menhancedredstone.setDisplayName("Enhanced Redstone Dust");
		enhancedredstone.setItemMeta(menhancedredstone);

		menhancedGlass.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		menhancedGlass.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		menhancedGlass.setDisplayName("Enhanced Glass");
		enhancedGlass.setItemMeta(menhancedGlass);

		mairfilter.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		mairfilter.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		mairfilter.setDisplayName("Air-Filter");
		airfilter.setItemMeta(mairfilter);

		menhancedIron.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		menhancedIron.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		menhancedIron.setDisplayName("Enhanced Iron");
		enhancedIron.setItemMeta(menhancedIron);

		mCircutBoard.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		mCircutBoard.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		mCircutBoard.setDisplayName("Circuit Board");
		mCircutBoard.setCustomModelData(10000019);
		CircutBoard.setItemMeta(mCircutBoard);

		mgoldWire.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		mgoldWire.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		mgoldWire.setDisplayName("Gold Wire");
		mgoldWire.setCustomModelData(10000001);
		goldWire.setItemMeta(mgoldWire);

		menhancedWood.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		menhancedWood.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		menhancedWood.setDisplayName("Enhanced Oak Log");
		enhancedWood.setItemMeta(menhancedWood);

		mlighter.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		mlighter.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		mlighter.setDisplayName("Lighter");
		lighter.setItemMeta(mlighter);

		menhancedCircutBoard.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		menhancedCircutBoard.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		menhancedCircutBoard.setDisplayName("Enhanced Circuit Board");
		menhancedCircutBoard.setCustomModelData(10000018);
		enhancedCircutBoard.setItemMeta(menhancedCircutBoard);

		menhancedChest.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		menhancedChest.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		menhancedChest.setDisplayName("Enhanced Chest");
		enhancedChest.setItemMeta(menhancedChest);

		mgrenade.setDisplayName(ChatColor.DARK_RED + "Grenade");
		mgrenade.setCustomModelData(10000005);
		grenade.setItemMeta(mgrenade);

		mc4.setDisplayName("C4");
		mc4.setCustomModelData(10000001);
		c4.setItemMeta(mc4);

		migniter.setDisplayName("C4 Igniter");
		igniter.setItemMeta(migniter);

		mammu.setDisplayName("Ammunation");
		mammu.setCustomModelData(10000015);
		ammu.setItemMeta(mammu);

		mrocketammu.setDisplayName("Rocket Ammunation");
		mrocketammu.setCustomModelData(10000016);
		rocketammu.setItemMeta(mrocketammu);

		mlaserammu.setDisplayName("Laser Ammunation");
		mlaserammu.setCustomModelData(10000017);
		laserammu.setItemMeta(mlaserammu);

		menhancedCompass.setDisplayName("Enhanced Compass");
		menhancedCompass.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
		menhancedCompass.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		;
		enhancedCompass.setItemMeta(menhancedCompass);
	}

	@UtilityClass
	public class Rocket {
		public ItemStack getBaseRocketItem() {
			List<String> lore = new ArrayList<>();
			lore.add(ChatColor.AQUA + "Air tank: " + ChatColor.YELLOW + "None");
			lore.add(ChatColor.AQUA + "Water tank: " + ChatColor.YELLOW + "None");
			lore.add(ChatColor.AQUA + "Energy tank: " + ChatColor.YELLOW + "None");
			lore.add(ChatColor.AQUA + "Hull armor: " + ChatColor.YELLOW + "None");
			lore.add(ChatColor.AQUA + "Heat shield: " + ChatColor.YELLOW + "None");
			lore.add(ChatColor.AQUA + "Frost shield: " + ChatColor.YELLOW + "None");
			lore.add(ChatColor.AQUA + "Storage: " + ChatColor.YELLOW + "None");
			lore.add(ChatColor.AQUA + "Boardcomputer: " + ChatColor.YELLOW + "None");
			lore.add(ChatColor.AQUA + "Fuel tank: " + ChatColor.YELLOW + "None");
			ItemStack item = ItemUtil.createEnchantedItem(Material.FIREWORK_ROCKET, ChatColor.GREEN + "Rocket",
					lore.toArray(new String[0]));
			return item;
		}

		public ItemStack getRocketFuel() {
			ItemStack item = ItemUtil.createItem(Material.POTION, ChatColor.YELLOW + "" + ChatColor.ITALIC + "Rocketfuel",
					ChatColor.GRAY + "Use this to fill your rocket");
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			meta.setColor(Color.fromRGB(16443433));
			item.setItemMeta(meta);
			return item;
		}

		public ItemStack getOil() {
			ItemStack item = ItemUtil.createItem(Material.POTION, ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Oil",
					ChatColor.GRAY + "Use this to make rocketfuel");
			PotionMeta meta = (PotionMeta) item.getItemMeta();
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			meta.setColor(Color.fromBGR(0, 0, 0));
			item.setItemMeta(meta);
			return item;
		}

		public ItemStack getEnhancedCompass() {
			return ItemUtil.createEnchantedItem(Material.COMPASS, "Enhanced Compass");
		}
	}

	@UtilityClass
	public class Oil {

		public ItemStack getBlueprint() {
			ItemStack item = ItemUtil.createItem(Material.GLOBE_BANNER_PATTERN, ChatColor.BLUE + "Oil Rig Blueprint");
			ItemMeta meta = item.getItemMeta();
			meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			item.setItemMeta(meta);
			return item;
		}

	}

	@UtilityClass
	public class Armor {

		private static final int CUSTOM_MODEL_DATA = 10000001;

		public class Iron {
			private static final int COLOR = 16777215;

			public static ItemStack getHelmet() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_HELMET, COLOR, CUSTOM_MODEL_DATA,
						"Iron Helmet");
				ItemUtil.Armor.addArmorHead(item, 2);
				return item;
			}

			public static ItemStack getChestplate() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_CHESTPLATE, COLOR, CUSTOM_MODEL_DATA,
						"Iron Chestplate");
				ItemUtil.Armor.addArmorChest(item, 6);
				return item;
			}

			public static ItemStack getLeggings() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_LEGGINGS, COLOR, CUSTOM_MODEL_DATA,
						"Iron Leggings");
				ItemUtil.Armor.addArmorLegs(item, 5);
				return item;
			}

			public static ItemStack getBoots() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_BOOTS, COLOR, CUSTOM_MODEL_DATA,
						"Iron Boots");
				ItemUtil.Armor.addArmorFeet(item, 2);
				return item;
			}

		}

		public class Gold {
			private static final int COLOR = 16580372;

			public static ItemStack getHelmet() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_HELMET, COLOR, CUSTOM_MODEL_DATA,
						"Golden Helmet");
				ItemUtil.Armor.addArmorHead(item, 2);
				return item;
			}

			public static ItemStack getCHestplate() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_CHESTPLATE, COLOR, CUSTOM_MODEL_DATA,
						"Golden Chestplate");
				ItemUtil.Armor.addArmorChest(item, 5);
				return item;
			}

			public static ItemStack getLeggings() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_LEGGINGS, COLOR, CUSTOM_MODEL_DATA,
						"Golden Leggings");
				ItemUtil.Armor.addArmorLegs(item, 3);
				return item;
			}

			public static ItemStack getBoots() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_BOOTS, COLOR, CUSTOM_MODEL_DATA,
						"Golden Boots");
				ItemUtil.Armor.addArmorFeet(item, 1);
				return item;
			}
		}

		public class Diamond {
			private static final int COLOR = 65530;

			public static ItemStack getHelmet() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_HELMET, COLOR, CUSTOM_MODEL_DATA,
						"Diamond Helmet");
				ItemUtil.Armor.addArmorHead(item, 3);
				ItemUtil.Armor.addArmorToughnessHead(item, 2);
				return item;
			}

			public static ItemStack getChestplate() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_CHESTPLATE, COLOR, CUSTOM_MODEL_DATA,
						"Diamond Chestplate");
				ItemUtil.Armor.addArmorChest(item, 8);
				ItemUtil.Armor.addArmorToughnessChest(item, 2);
				return item;
			}

			public static ItemStack getLeggings() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_LEGGINGS, COLOR, CUSTOM_MODEL_DATA,
						"Diamond Leggings");
				ItemUtil.Armor.addArmorLegs(item, 6);
				ItemUtil.Armor.addArmorToughnessLegs(item, 2);
				return item;
			}

			public static ItemStack getBoots() {
				ItemStack item = ItemUtil.Armor.createLeatherArmorSkin(Material.LEATHER_BOOTS, COLOR, CUSTOM_MODEL_DATA,
						"Diamond Boots");
				ItemUtil.Armor.addArmorFeet(item, 3);
				ItemUtil.Armor.addArmorToughnessFeet(item, 2);
				return item;
			}

		}

		public class Leather {
			public static ItemStack getHelmet() {
				ItemStack item = ItemUtil.createSkinItem(Material.LEATHER_HELMET, "LeatherC Cap", CUSTOM_MODEL_DATA);
				ItemUtil.Armor.addArmorHead(item, 1);
				return item;
			}

			public static ItemStack getChestplate() {
				ItemStack item = ItemUtil.createSkinItem(Material.LEATHER_CHESTPLATE, "Leather Tunic", CUSTOM_MODEL_DATA);
				ItemUtil.Armor.addArmorChest(item, 3);
				return item;
			}

			public static ItemStack getLeggings() {
				ItemStack item = ItemUtil.createSkinItem(Material.LEATHER_LEGGINGS, "Leather Pants", CUSTOM_MODEL_DATA);
				ItemUtil.Armor.addArmorLegs(item, 2);
				return item;
			}

			public static ItemStack getBoots() {
				ItemStack item = ItemUtil.createSkinItem(Material.LEATHER_BOOTS, "Leather Boots", CUSTOM_MODEL_DATA);
				ItemUtil.Armor.addArmorFeet(item, 1);
				return item;
			}
		}
	}

}
