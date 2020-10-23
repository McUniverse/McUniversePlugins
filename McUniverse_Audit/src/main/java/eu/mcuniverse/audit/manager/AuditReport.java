package eu.mcuniverse.audit.manager;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;

import eu.mcuniverse.audit.listener.DeathListener;
import eu.mcuniverse.universeapi.serialization.JsonItemStack;
import eu.mcuniverse.universeapi.util.ItemUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AuditReport {

	private int id = -1;
	private final Timestamp timestamp;
	private final UUID UUID;
	private final String name;
	private final String inventoryContent;
	private final String location;
	private final String deathMessage;
	private final int exp;

	@SuppressWarnings("unchecked")
	public Location getRealLocation() {
		return Location.deserialize(new Gson().fromJson(getLocation(), Map.class));
	}

	public void sendToSender(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + "ID: " + ChatColor.GREEN + getId());
		sender.sendMessage(ChatColor.YELLOW + "Player Name: " + ChatColor.GREEN + getName());
		sender.sendMessage(ChatColor.YELLOW + "UUID: " + ChatColor.GREEN + getUUID());
		sender.sendMessage(ChatColor.YELLOW + "Inventory: " + ChatColor.GREEN + getInventoryContent());
		sender.sendMessage(ChatColor.YELLOW + "Location: " + ChatColor.GREEN + getLocation());
		sender.sendMessage(ChatColor.YELLOW + "Death message: " + ChatColor.GREEN + getDeathMessage());
		sender.sendMessage(ChatColor.YELLOW + "Exp: " + ChatColor.GREEN + getExp());
	}

	public void sendDate(CommandSender sender) {
		HoverEvent he = new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Click to view this death", ChatColor.GREEN));
		ClickEvent ce = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/showdeaths " + getId());
		
		BaseComponent[] baseComponent = new ComponentBuilder()
				.append(" - ")
				.color(ChatColor.YELLOW)
				.event(he)
				.event(ce)
				.append("ID: ")
				.color(ChatColor.RED)
				.event(he)
				.event(ce)
				.append(getId() + "")
				.color(ChatColor.YELLOW)
				.event(he)
				.event(ce)
				.append(", ")
				.color(ChatColor.GRAY)
				.event(he)
				.event(ce)
				.append("Time: ")
				.color(ChatColor.RED)
				.event(he)
				.event(ce)
				.append(getTimestamp().toString())
				.color(ChatColor.YELLOW)
				.event(he)
				.event(ce)
				.create();
		
		sender.spigot().sendMessage(baseComponent);
	}

	public Inventory getFilledInventory() {
		String modifiedContent = getInventoryContent();
		if (modifiedContent.startsWith(DeathListener.SEPERATOR)) {
			modifiedContent = modifiedContent.substring(DeathListener.SEPERATOR.length());
		}
		String[] serialized = modifiedContent.split(DeathListener.SEPERATOR);
		
		Inventory inv = Bukkit.createInventory(null, 9 * 6, "Inventory of " + getName() + ". ID: " + getId());
		
		int index = 0;
		for (String serializedItem : serialized) {
			ItemStack item; 
			try {
				item = JsonItemStack.fromJson(serializedItem);
			} catch (Exception e) {
				item = ItemUtil.createItem(Material.BARRIER, ChatColor.RED + "Serialization error", "Error: " + e.getLocalizedMessage(),
						ChatColor.RED + "Please contact an administrator. " + ChatColor.BLUE + "(JayReturns)");
			}
			
			inv.setItem(index, item);
			index++;
			if (index > inv.getSize()) {
				break;
			}
		}
		return inv;
	}

}
