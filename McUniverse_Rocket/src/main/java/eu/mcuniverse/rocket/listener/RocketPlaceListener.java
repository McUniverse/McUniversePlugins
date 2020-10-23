package eu.mcuniverse.rocket.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import com.google.gson.Gson;

import eu.mcuniverse.rocket.data.Messages;
import eu.mcuniverse.rocket.items.Items;
import eu.mcuniverse.rocket.main.Core;
import eu.mcuniverse.rocket.rocket.Rocket;
import eu.mcuniverse.rocket.rocket.RocketManager;
import eu.mcuniverse.rocket.util.ItemUtil;
import net.md_5.bungee.api.ChatColor;

public class RocketPlaceListener implements Listener {

	private Core plugin;
	
	private final String TITLE = "Confirm rocket placement";
	private final String KEY = "Rocket";

	public RocketPlaceListener(Core main) {
		plugin = main;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e) {
		if (e.getItem() == null || e.getClickedBlock() == null) {
			return;
		}
		
		try {
			Player p = e.getPlayer();
			if (e.getItem().getType() == Material.FIREWORK_ROCKET && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				ItemStack item = e.getItem();
				if (!item.hasItemMeta()) {
					return;
				}
				ItemMeta meta = item.getItemMeta();
				if (!meta.hasDisplayName()) {
					return;
				}
				if (meta.getDisplayName().equalsIgnoreCase(Items.getBaseRocketItem().getItemMeta().getDisplayName())) {
					if (RocketManager.hasRocket(p)) {
						p.sendMessage(Messages.WARNING + "You already own a rocket! Destroy it to place a new one!");
						e.setCancelled(true);
						return;
					}
					
					e.setCancelled(true);
					Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, TITLE);

					for (int i = 0; i < 5; i++) {
						inv.setItem(i, ItemUtil.getFiller());
					}

					Rocket r = new Rocket(p, 1, e.getClickedBlock().getLocation());
					
					inv.setItem(0, ItemUtil.createItem(Material.LIME_WOOL, ChatColor.GREEN + "Confirm"));
					inv.setItem(4, ItemUtil.createItem(Material.RED_WOOL, ChatColor.RED + "Cancel"));

					meta.getLore().add(ChatColor.RED + "Required space: " + ChatColor.GREEN + "0\u00D70\u00D70"); // \u00D7 =
																																																				// Multiplication
																																																				// sign
					ItemStack info = ItemUtil.createItem(Material.NAME_TAG, "Rocket Info", meta.getLore().toArray(new String[0]));
					try {
						//  Test if rocket has tag. if not -> catch block
						String rocket = ItemUtil.NBT.getValue(item, "rocketDestroy");
						info = ItemUtil.NBT.addNBTTag(info, KEY, rocket);
					} catch (Exception exe) {
						info = MinecraftReflection.getBukkitItemStack(info);
						NbtCompound com = (NbtCompound) NbtFactory.fromItemTag(info);
						com.put(KEY, new Gson().toJson(r));
					}

					inv.setItem(2, info);
					p.openInventory(inv);
				}
			}
		} catch (Exception exe) {
			exe.printStackTrace();
			e.getPlayer().sendMessage(Messages.ERROR + exe.getLocalizedMessage());
		}
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getView().getTitle().equalsIgnoreCase(TITLE)) {
			e.setCancelled(true);

			if (e.getCurrentItem().getType() == Material.LIME_WOOL) {
				ItemStack info = e.getInventory().getItem(2);
				info = MinecraftReflection.getBukkitItemStack(info);
				NbtCompound com = (NbtCompound) NbtFactory.fromItemTag(info);
				Rocket r = new Gson().fromJson(com.getString(KEY), Rocket.class);
				
				Location loc = p.getLocation();
				loc.setYaw(0);
				loc.setPitch(0);
				r.summonArmorStand(loc, true);
				r.saveRocket();

				// TODO: Floor testing + Can be placed testing (RocketUtil methods)
				// TODO: Spawn Barrier blocks!

				// Remove item
//				Map<Integer, ItemStack> map = p.getInventory().removeItem(new ItemStack(r.getItem().getType(), 1));
				p.getInventory().setItemInMainHand(null);
				
				p.sendMessage(Messages.PREFIX + ChatColor.GREEN + "You placed your rocket!");
				p.closeInventory();
				try {
					Entity as = Bukkit.getEntity(r.getArmorStandUUID());
					as.setGravity(true);
					
					new BukkitRunnable() {
						
						@Override
						public void run() {
							as.setGravity(false);
						}
					}.runTaskLater(Core.getInstance(), 20L* 10);
				} catch (Exception exe) {
					
				}
				
			} else if (e.getCurrentItem().getType() == Material.RED_WOOL) {
				p.closeInventory();
				p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, SoundCategory.MASTER, 1, 2);
			}

		}
	}

	
}
