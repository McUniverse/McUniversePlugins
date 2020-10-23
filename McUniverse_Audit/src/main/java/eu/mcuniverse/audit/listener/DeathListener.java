package eu.mcuniverse.audit.listener;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;

import eu.mcuniverse.audit.manager.AuditReport;
import eu.mcuniverse.audit.storage.AuditStorageManager;
import eu.mcuniverse.universeapi.serialization.JsonItemStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.md_5.bungee.api.ChatColor;

public class DeathListener implements Listener {

	public static final String SEPERATOR = "\u2022\u2022\u2022";
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();

		Timestamp timestamp = Timestamp.from(Instant.now());
		UUID uuid = p.getUniqueId();
		String name = p.getName();
//		String inventoryContent = arrayToString(p.getInventory().getContents());
		String inventoryContent = serializeInventory(p.getInventory().getContents());
		String location = new Gson().toJson(p.getLocation().getBlock().getLocation().serialize());
		String deathMessage = e.getDeathMessage();
		int exp = e.getDroppedExp();

		AuditReport ao = new AuditReport(timestamp, uuid, name, inventoryContent, location, deathMessage, exp);

		AuditStorageManager.add(ao);

	}

	private String serializeInventory(ItemStack[] content) {
		ObjectList<ItemStack> list = new ObjectArrayList<>(content);
		
//		char seperator = '\u2022'; // •
		
		AtomicReference<String> str = new AtomicReference<String>("");
		list.stream().filter(i -> i != null).map(JsonItemStack::toJson).forEach(item -> {
			str.set(str.get() + SEPERATOR + item);
		});
		
		return str.get();
	}
	
	private String arrayToString(ItemStack[] array) {
		ObjectList<ItemStack> list = new ObjectArrayList<ItemStack>(array);

		char seperatorChar = '\u2022'; // •
		String seperator = ChatColor.BLUE + "" + seperatorChar + "" + ChatColor.GREEN;
		AtomicReference<String> str = new AtomicReference<String>("");

		list.stream().filter(i -> i != null).map(this::itemStackToString).forEach(item -> {
			String newValue = str.get() + seperator + item;
			str.set(newValue);
		});
		;

		return str.get();
	}

	private String itemStackToString(ItemStack item) {
		if (item == null) {
			return "null";
		}

		char seperatorChar = '\uFF0C'; // ，
		String seperator = ChatColor.DARK_GRAY + "" + seperatorChar + "" + ChatColor.GREEN;

		String str = "";
		str += "Type=" + item.getType().toString() + seperator + "Amount=" + item.getAmount();
		if (item.hasItemMeta()) {
			ItemMeta meta = item.getItemMeta();
			str += seperator + "MetaType=" + meta.getClass().getName();
			if (meta.hasDisplayName()) {
				str += seperator + "Name=" + meta.getDisplayName();
			}
			if (meta.hasCustomModelData()) {
				str += seperator + "CustomModelData=" + meta.getCustomModelData();
			}
			if (meta.getItemFlags().size() > 0) {
				str += seperator + "ItemFlags=" + meta.getItemFlags().toString();
			}
			if (meta.hasLore()) {
				str += seperator + "Lore=" + meta.getLore().toString();
			}
			if (meta.isUnbreakable()) {
				str += seperator + "Unbreakable=true";
			}
			if (meta.hasEnchants()) {
				str += seperator + "Enchants=" + meta.getEnchants();
			}
		}

		return str;
	}

}
