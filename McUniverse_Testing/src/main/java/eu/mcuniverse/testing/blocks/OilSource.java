package eu.mcuniverse.testing.blocks;

import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Lockable;
import org.bukkit.block.ShulkerBox;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.mcuniverse.universeapi.util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class OilSource implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player p = (Player) sender;
		
		Location loc = p.getLocation();
		Block b = loc.getBlock();
		b.setType(Material.BLACK_SHULKER_BOX);
		BlockState state = b.getState();
		
		String key = UUID.randomUUID().toString();
		
		Lockable lock = (Lockable) state;
		lock.setLock(key);
		p.getInventory().addItem(ItemUtil.createItem(Material.STICK, key, ChatColor.GOLD + "Use me!"));
		
		ShulkerBox box = (ShulkerBox) state;
		box.getInventory().setItem(0, ItemUtil.createItem(Material.NETHER_STAR, ChatColor.DARK_RED + "Closed"));
		
//		EntityWither ew;
//		EntityWither.bossBattle.removePlayer(EntityPlayer);
//		EntityWither.bossBattle.visible = false;
//		EntityWither.bossBattle.b(); // removeAllPlayers()
		
		return true;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getClickedBlock() == null) {
			return;
		}
		if (e.getClickedBlock().getType() == Material.BLACK_SHULKER_BOX) {
			ShulkerBox box = (ShulkerBox) e.getClickedBlock().getState();
			if (box.getInventory().getItem(0) == null || box.getInventory().getItem(0).getType() == Material.AIR) {
				return;
			}
			ItemStack item = box.getInventory().getItem(0);
			if (!item.hasItemMeta()) {
				return;
			}
			ItemMeta meta = item.getItemMeta();
			if (!meta.hasDisplayName()) {
				return;
			}
			if (!meta.getDisplayName().equalsIgnoreCase(ChatColor.DARK_RED + "Closed")) {
				return;
			}
			e.getClickedBlock().getLocation().getBlock().setType(Material.AIR);
			Location loc = e.getClickedBlock().getLocation();
			e.getPlayer().playEffect(loc, Effect.ENDER_SIGNAL, null);
			e.getPlayer().sendMessage("You got oil");
		}
	}
	
}
