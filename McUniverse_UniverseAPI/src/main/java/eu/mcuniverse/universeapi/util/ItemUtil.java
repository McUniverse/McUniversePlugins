package eu.mcuniverse.universeapi.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.google.common.collect.Lists;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ItemUtil {

	/**
	 * Enchantment which will be added for "enchantment glow"
	 */
	private final static Enchantment GLOW = Enchantment.SILK_TOUCH;

	/**
	 * Createn an {@link ItemStack} with a customModelData
	 * 
	 * @param material The material of the {@link ItemStack}
	 * @param name     The displayname of the {@link ItemStack}
	 * @param data     The CustomModelData of the {@link ItemStack}
	 * @param lore     The lore of the {@link ItemStack}
	 * @return The {@link ItemStack} With the given traits
	 */
	public static ItemStack createSkinItem(@NonNull Material material, String name, int data, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(data);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		if (name != null)
			meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Createn an {@link ItemStack} with a customModelData
	 * 
	 * @param material The material of the {@link ItemStack}
	 * @param data     The CustomModelData of the {@link ItemStack}
	 * @param lore     The lore of the {@link ItemStack}
	 * @return The {@link ItemStack} With the given traits
	 */
	public ItemStack createSkinItem(Material material, int data, String... lore) {
		return createSkinItem(material, null, data, lore);
//		ItemStack item = new ItemStack(material);
//		ItemMeta meta = item.getItemMeta();
//		meta.setCustomModelData(data);
//		ArrayList<String> metaLore = new ArrayList<String>();
//
//		for (String loreComments : lore) {
//			metaLore.add(loreComments);
//		}
//
//		meta.setLore(metaLore);
//		item.setItemMeta(meta);
//		return item;
	}

//	/**
//	 * Createn an {@link ItemStack} with a customModelData
//	 * 
//	 * @param material    The material of the {@link ItemStack}
//	 * @param displayname The displayname of the {@link ItemStack}
//	 * @param data        The CustomModelData of the {@link ItemStack}
//	 * @return The {@link ItemStack} With the given traits
//	 */
//	public ItemStack createSkinItem(Material material, String displayname, int data) {
//		return createSkinItem(material, displayname, data);
//		ItemStack item = new ItemStack(material);
//		ItemMeta meta = item.getItemMeta();
//		meta.setCustomModelData(data);
//		meta.setDisplayName(displayname);
//		item.setItemMeta(meta);
//		return item;
//	}
	//

	public static ItemStack createItem(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack createEnchantedItem(@NonNull Material material, String name, String... lore) {
		ItemStack item = createItem(material, name, lore);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(GLOW, 32, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Add enchanting glow from an {@link ItemStack}
	 * 
	 * @param stack The ItemStack to modify
	 */
	public void addGlow(@NonNull ItemStack stack) {
		if (!stack.hasItemMeta()) {
			throw new IllegalArgumentException("Given stack has no itemmeta!");
		}
		ItemMeta meta = stack.getItemMeta();
		meta.addEnchant(GLOW, 32, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
	}

	/**
	 * Add enchanting glow to an {@link ItemStack}
	 * 
	 * @param stack The ItemStack to modify
	 */
	public void removeGlow(@NonNull ItemStack stack) {
		if (!stack.hasItemMeta()) {
			throw new IllegalArgumentException("Given stack has no itemmeta!");
		}
		ItemMeta meta = stack.getItemMeta();
		meta.removeEnchant(GLOW);
		meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
	}

	/**
	 * Check if an {@link ItemStack} is glowing
	 * 
	 * @param stack The ItemStack to check
	 * @return Wheter the <code>stack</code> is glowing
	 */
	public boolean isGlowing(@NonNull ItemStack stack) {
		if (!stack.hasItemMeta()) {
			throw new IllegalArgumentException("Given stack has no itemmeta!");
		}
		ItemMeta meta = stack.getItemMeta();
		return meta.hasEnchant(GLOW) && meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS);
	}

	/**
	 * Get a filler {@link ItemStack} to fill empty {@link Inventory} slots
	 * 
	 * @return THe filler
	 */
	public ItemStack getFiller() {
		ItemStack item = createItem(Material.BLACK_STAINED_GLASS_PANE, " ");
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE); // Add unusual flag to distinguis from regular glass pane
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Create a Player skull with texture
	 * <br>
	 * <b>IMPORTANT: ProtocolLib is required!</b>
	 * 
	 * @param displayname The name of the item
	 * @param value       The skin value
	 * @param lore        THe lore of the item
	 * @return The skull ItemStack
	 */
	public ItemStack createSkull(@NonNull String displayname, @NonNull String value, String... lore) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();

		WrappedGameProfile wrappedProfile = new WrappedGameProfile(UUID.randomUUID(), null);
		WrappedSignedProperty wrappedProperty = new WrappedSignedProperty("textures", value, "");

		wrappedProfile.getProperties().put("textures", wrappedProperty);

		Field profileField = null;
		try {
			profileField = meta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(meta, wrappedProfile.getHandle());
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		meta.setDisplayName(displayname);
		item.setItemMeta(meta);

		return item;
	}

	/**
	 * Create a FireWorkStar with Color
	 * 
	 * @param displayname Name of the star
	 * @param color       The {@linkplain Color} of the star
	 * @return The FireworkStar ItemStack
	 */
	public ItemStack createFireworkStar(String displayname, Color color) {
		ItemStack item = createItem(Material.FIREWORK_STAR, displayname);
		FireworkEffectMeta few = (FireworkEffectMeta) item.getItemMeta();
		FireworkEffect effect = FireworkEffect.builder().withColor(color).build();
		few.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		few.setEffect(effect);
		;
		item.setItemMeta(few);
		return item;
	}

	/**
	 * Create a FireworkStar with CustomModelData
	 * 
	 * @param displayname Displayname of the Item
	 * @param color       The {@linkplain Color} of the star
	 * @param data        CustomModelData
	 * @return The fireworkstar ItemStack
	 */
	public ItemStack createFireworkSkin(String displayname, Color color, int data) {
		ItemStack item = ItemUtil.createFireworkStar(displayname, color);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(data);
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Add one line to the lore
	 * 
	 * @param stack The {@link ItemStack} to edit
	 * @param lore  The String to add
	 */
	public void addLore(ItemStack stack, String lore) {
		ItemMeta meta = stack.getItemMeta();
		List<String> list = meta.getLore();
		if (list == null) {
			list = Lists.newArrayList();
		}
		list.add(lore);
		meta.setLore(list);
		stack.setItemMeta(meta);
	}

	/**
	 * Remove the lore of an item
	 * 
	 * @param stack The {@link ItemStack} to edit
	 */
	public void clearLore(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		meta.setLore(null);
		stack.setItemMeta(meta);
	}

	@UtilityClass
	public class Armor {

		public ItemStack createLeatherArmor(Material mat, int rgb, String displayname) {
			ItemStack item = createItem(mat, ChatColor.RESET + displayname);
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(Color.fromRGB(rgb));
			item.setItemMeta(meta);
			return item;
		}

		public ItemStack createLeatherArmorSkin(Material mat, int rgb, int customModelData, String displayname) {
			ItemStack item = createItem(mat, ChatColor.RESET + displayname);
			LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
			meta.setColor(Color.fromRGB(rgb));
			meta.setCustomModelData(customModelData);
			item.setItemMeta(meta);
			return item;
		}

		public ItemStack addArmorHead(ItemStack item, double value) {
			ItemMeta meta = item.getItemMeta();
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
					new AttributeModifier(UUID.randomUUID(), "mcuniverse", value, Operation.ADD_NUMBER, EquipmentSlot.HEAD));
			item.setItemMeta(meta);
			return item;
		}
		
		public ItemStack addArmorChest(ItemStack item, double value) {
			ItemMeta meta = item.getItemMeta();
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
					new AttributeModifier(UUID.randomUUID(), "mcuniverse", value, Operation.ADD_NUMBER, EquipmentSlot.CHEST));
			item.setItemMeta(meta);
			return item;
		}
		
		public ItemStack addArmorLegs(ItemStack item, double value) {
			ItemMeta meta = item.getItemMeta();
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
					new AttributeModifier(UUID.randomUUID(), "mcuniverse", value, Operation.ADD_NUMBER, EquipmentSlot.LEGS));
			item.setItemMeta(meta);
			return item;
		}
		
		public ItemStack addArmorFeet(ItemStack item, double value) {
			ItemMeta meta = item.getItemMeta();
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR,
					new AttributeModifier(UUID.randomUUID(), "mcuniverse", value, Operation.ADD_NUMBER, EquipmentSlot.FEET));
			item.setItemMeta(meta);
			return item;
		}
		
		public ItemStack addArmorToughnessHead(ItemStack item, double value) {
			ItemMeta meta = item.getItemMeta();
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
					new AttributeModifier(UUID.randomUUID(), "mcuniverse", value, Operation.ADD_NUMBER, EquipmentSlot.HEAD));
			item.setItemMeta(meta);
			return item;
		}
		
		public ItemStack addArmorToughnessChest(ItemStack item, double value) {
			ItemMeta meta = item.getItemMeta();
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
					new AttributeModifier(UUID.randomUUID(), "mcuniverse", value, Operation.ADD_NUMBER, EquipmentSlot.CHEST));
			item.setItemMeta(meta);
			return item;
		}
		
		public ItemStack addArmorToughnessLegs(ItemStack item, double value) {
			ItemMeta meta = item.getItemMeta();
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
					new AttributeModifier(UUID.randomUUID(), "mcuniverse", value, Operation.ADD_NUMBER, EquipmentSlot.LEGS));
			item.setItemMeta(meta);
			return item;
		}
		
		public ItemStack addArmorToughnessFeet(ItemStack item, double value) {
			ItemMeta meta = item.getItemMeta();
			meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS,
					new AttributeModifier(UUID.randomUUID(), "mcuniverse", value, Operation.ADD_NUMBER, EquipmentSlot.FEET));
			item.setItemMeta(meta);
			return item;
		}

	}

	/**
	 * A class to manage NBT-Tags on items with ProtocolLib </br>
	 * <b>IMPORTANT! This class requires ProtocolLib</b>
	 * 
	 * @author JayReturns
	 *
	 */
	@UtilityClass
	public class NBT {

		/**
		 * Add a NBT-Tag to the given {@link Itemstack}
		 * 
		 * @param item  The {@link ItemStack} to modify
		 * @param key   The Key of the NBT-Tag
		 * @param value The Value of the NBT-Tag
		 * @return Returns the modified version of <code>item</code>
		 */
		public ItemStack addNBTTag(ItemStack item, String key, String value) {
			item = MinecraftReflection.getBukkitItemStack(item);
			NbtCompound comp = (NbtCompound) NbtFactory.fromItemTag(item);
			comp.put(key, value);
			return item;
		}

		/**
		 * Get the value of a given key from an {@link ItemStack}
		 * 
		 * @param item The {@link Itemstack}
		 * @param key  The key of the NBT-Tag
		 * @return Returns the String from the value of the given key
		 */
		public String getValue(ItemStack item, String key) {
			item = MinecraftReflection.getBukkitItemStack(item);
			NbtCompound comp = (NbtCompound) NbtFactory.fromItemTag(item);
			return comp.getString(key);
		}

		/**
		 * Get all NBT-Keys of an ItemStack
		 * 
		 * @param item The ItemStack
		 * @return {@link Set<String>} with NBT-Keys
		 */
		protected Set<String> getKeys(ItemStack item) {
			item = MinecraftReflection.getBukkitItemStack(item);
			NbtCompound comp = (NbtCompound) NbtFactory.fromItemTag(item);
			return comp.getKeys();
		}

		/**
		 * Get all NBT-Tags of an ItemStack
		 * 
		 * @param item The ItemStack
		 * @return {@link Map<String, NbtBase<?>>} with Key and Value
		 */
		@SuppressWarnings("deprecation")
		protected Map<String, NbtBase<?>> getValues(ItemStack item) {
			item = MinecraftReflection.getBukkitItemStack(item);
			NbtCompound comp = (NbtCompound) NbtFactory.fromItemTag(item);
			return comp.getValue();
		}

	}

}