package eu.mcuniverse.testing.nbt;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;

public class NBTCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("No player");
			return true;
		}

		Player p = (Player) sender;

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("get")) {
				ItemStack item = MinecraftReflection.getBukkitItemStack(p.getInventory().getItemInMainHand());
				NbtCompound com = (NbtCompound) NbtFactory.fromItemTag(item);
				p.sendMessage(com.getString("Testing"));
				return true;
			} else if (args[0].equalsIgnoreCase("skull")) {
				ItemStack item = new ItemStack(Material.PLAYER_HEAD);
				SkullMeta meta = (SkullMeta) item.getItemMeta();
					
				WrappedGameProfile profile = new WrappedGameProfile(UUID.randomUUID(), null);
				WrappedSignedProperty property = new WrappedSignedProperty("textures", "eyJ0ZXh0d"
						+ "XJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1c"
						+ "mUvYjFkZDRmZTRhNDI5YWJkNjY1ZGZkYjNlMjEzMjFkNmVmYTZhNmI1ZTdiOTU2ZGI5YzVkNTljOW"
						+ "VmYWIyNSJ9fX0", "");
				
				profile.getProperties().put("textures", property);
				
				Field profileField = null;
				try {
					profileField = meta.getClass().getDeclaredField("profile");
					profileField.setAccessible(true);
					profileField.set(meta, profile.getHandle());
				} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
				
				item.setItemMeta(meta);
				p.getInventory().addItem(item);
				return true;
			}
		}

		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		item = MinecraftReflection.getBukkitItemStack(item);

		NbtCompound com = (NbtCompound) NbtFactory.fromItemTag(item);
		com.put(NbtFactory.of("Testing", "{Class:{Name:\"Test\"}}"));

		p.getInventory().addItem(item);

		p.sendMessage("You got an item!");

		return true;
	}

}
