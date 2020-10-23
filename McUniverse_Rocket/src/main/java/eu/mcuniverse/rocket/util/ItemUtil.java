package eu.mcuniverse.rocket.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.comphenix.protocol.wrappers.nbt.NbtBase;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.google.common.collect.Lists;

import eu.mcuniverse.rocket.items.Items;
import eu.mcuniverse.rocket.rocket.RocketPart;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ItemUtil {

	/**
	 * Enchantment which will be added for "enchantment glow"
	 */
	private final Enchantment GLOW = Enchantment.SILK_TOUCH;
	
	/**
	 * Createn an {@link ItemStack} with a customModelData
	 * @param material The material of the {@link ItemStack}
	 * @param name The displayname of the {@link ItemStack}
	 * @param data The CustomModelData of the {@link ItemStack}
	 * @param lore The lore of the {@link ItemStack}
	 * @return The {@link ItemStack} With the given traits
	 */
	public ItemStack createSkinItem(Material material, String name, int data, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(data);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * Createn an {@link ItemStack} with a customModelData
	 * @param material The material of the {@link ItemStack}
	 * @param data The CustomModelData of the {@link ItemStack}
	 * @param lore The lore of the {@link ItemStack}
	 * @return The {@link ItemStack} With the given traits
	 */
	public ItemStack createSkinItem(Material material, int data, String... lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(data);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * Createn an {@link ItemStack} with a customModelData
	 * @param material The material of the {@link ItemStack}
	 * @param displayname The displayname of the {@link ItemStack}
	 * @param data The CustomModelData of the {@link ItemStack}
	 * @return The {@link ItemStack} With the given traits
	 */
	public ItemStack createSkinItem(Material material, String displayname, int data) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(data);
		meta.setDisplayName(displayname);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack createItem(Material material, String name, String... lore) {
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
	
	public ItemStack createItem(Material material, String name, List<String> lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack createEnchantedItem(Material material, String name, String... lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(GLOW, 32, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.setDisplayName(name);
		ArrayList<String> metaLore = new ArrayList<String>();

		for (String loreComments : lore) {
			metaLore.add(loreComments);
		}

		meta.setLore(metaLore);
		item.setItemMeta(meta);
		return item;
	}

	public void addGlow(@NonNull ItemStack stack) {
		if (!stack.hasItemMeta()) {
			throw new IllegalArgumentException("Given stack has no itemmeta!");
		}
		ItemMeta meta = stack.getItemMeta();
		meta.addEnchant(GLOW, 32, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
	}

	public void removeGlow(@NonNull ItemStack stack) {
		if (!stack.hasItemMeta()) {
			throw new IllegalArgumentException("Given stack has no itemmeta!");
		}
		ItemMeta meta = stack.getItemMeta();
		meta.removeEnchant(GLOW);
		meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
		stack.setItemMeta(meta);
	}
	
	public boolean isGlowing(@NonNull ItemStack stack) {
		if (!stack.hasItemMeta()) {
			throw new IllegalArgumentException("Given stack has no itemmeta!");
		}
		ItemMeta meta = stack.getItemMeta();
		return meta.hasEnchant(GLOW) && meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS);
	}

	public ItemStack getFiller() {
		ItemStack item = createItem(Material.BLACK_STAINED_GLASS_PANE, " ");
		ItemMeta meta = item.getItemMeta();
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE); // Add unusual flag to distinguis from regular glass pane
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack createSkull(@NonNull String displayname, String value, String... lore) {
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
	
	public ItemStack createWaterBottle(String displayname) {
		ItemStack item = Items.getWaterBottle();
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		item.setItemMeta(meta);
		return item;
	}
	
	/**
	 * Add one line to the lore
	 * @param stack The {@link ItemStack} to edit
	 * @param lore The String to add
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
	 * @param stack The {@link ItemStack} to edit
	 */
	public void clearLore(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();
		meta.setLore(null);
		stack.setItemMeta(meta);
	}

	/**
	 * A class to manage NBT-Tags on items with ProtocolLib
	 * @author JayReturns
	 *
	 */
	@UtilityClass
	public class NBT {
		
		/**
		 * Add a NBT-Tag to the given {@link Itemstack}
		 * @param item The {@link ItemStack} to modify
		 * @param key The Key of the NBT-Tag
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
		 * @param item The {@link Itemstack}
		 * @param key The key of the NBT-Tag
		 * @return Returns the String from the value of the given key
		 */
		public String getValue(ItemStack item, String key) {
			item = MinecraftReflection.getBukkitItemStack(item);
			NbtCompound comp = (NbtCompound) NbtFactory.fromItemTag(item);
			return comp.getString(key);
		}
		
		
		protected Set<String> getKeys(ItemStack item) {
			item = MinecraftReflection.getBukkitItemStack(item);
			NbtCompound comp = (NbtCompound) NbtFactory.fromItemTag(item);
			return comp.getKeys();
		}
		
		@SuppressWarnings("deprecation")
		protected Map<String, NbtBase<?>> getValues(ItemStack item) {
			item = MinecraftReflection.getBukkitItemStack(item);
			NbtCompound comp = (NbtCompound) NbtFactory.fromItemTag(item);
			return comp.getValue();
		}
		
	}

	@UtilityClass
	public class RocketItem {

		public int getLoreNumber(@NonNull RocketPart.Parts part) {
			return part.getIndex();
		}

		public String getType(@NonNull RocketPart.Parts part, @NonNull ItemStack item) {
			if (!item.hasItemMeta()) {
				throw new IllegalArgumentException("The given item has no ItemMeta!");
			}
			if (!item.getItemMeta().hasLore()) {
				throw new IllegalArgumentException("The given item has no lore!");
			}

			String lore = item.getItemMeta().getLore().get(getLoreNumber(part));
			lore = ChatColor.stripColor(lore);

			return lore.split(":")[1];
		}

	}

}