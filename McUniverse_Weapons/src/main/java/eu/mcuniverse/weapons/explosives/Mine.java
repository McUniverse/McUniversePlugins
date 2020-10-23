package eu.mcuniverse.weapons.explosives;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitTask;

import eu.mcuniverse.weapons.item.Items;
import eu.mcuniverse.weapons.main.Core;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Mine implements Listener {

	private Set<UUID> activated = new HashSet<>();
	private Set<UUID> almostExplosion = new HashSet<>();
	private Map<UUID, BukkitTask> tasks = new HashMap<>();

	private Object2ObjectOpenHashMap<UUID, ObjectArrayList<Location>> mines2 = new Object2ObjectOpenHashMap<UUID, ObjectArrayList<Location>>();

	private final int MAX_MINES = 10;

	private Core plugin;

	public Mine(final Core plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock() == null || e.getItemInHand() == null) {
			return;
		}

		if (e.getItemInHand().isSimilar(Items.Explosives.getC4())) {
			if (e.getBlock().getType() == Items.Explosives.getC4().getType()) {
				Player p = e.getPlayer();
				UUID uuid = p.getUniqueId();

				if (!mines2.containsKey(uuid)) {
					mines2.put(uuid, new ObjectArrayList<Location>());
				}
				if (mines2.get(uuid).size() > MAX_MINES) {
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							new TextComponent(ChatColor.RED + "You placed the maximum of mines!"));
					p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, SoundCategory.MASTER, 1, 2);
					e.setCancelled(true);
				} else {
					mines2.get(uuid).add(e.getBlock().getLocation());
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							new TextComponent(ChatColor.GREEN + "You placed a " + ChatColor.DARK_RED + "C4"));
				}
			}
		} else if (e.getItemInHand().isSimilar(Items.Explosives.getIgniter())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!(mines2.getOrDefault(e.getPlayer().getUniqueId(), new ObjectArrayList<Location>()).size() > 0)
				|| e.getBlock() == null) {
			return;
		}

		Player p = e.getPlayer();
		if (e.getBlock().getType() == Material.JUNGLE_BUTTON) {

			for (Entry<UUID, ObjectArrayList<Location>> entry : mines2.object2ObjectEntrySet()) {
				UUID uuid = entry.getKey();
				ObjectArrayList<Location> list = entry.getValue();
				if (list.contains(e.getBlock().getLocation())) {
					if (uuid != p.getUniqueId()) {
						e.setCancelled(true);
						p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
								new TextComponent(ChatColor.RED + "You can't break the mine of another player"));
						break;
					}
				}
			}

			if (mines2.get(p.getUniqueId()).contains(e.getBlock().getLocation())) {
				mines2.get(p.getUniqueId()).remove(e.getBlock().getLocation());
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						new TextComponent(ChatColor.GRAY + "You removed the " + ChatColor.DARK_RED + "C4"));
				if (p.getGameMode() != GameMode.CREATIVE) {
					e.setDropItems(false);
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), Items.Explosives.getC4());
				}
			}
		}
	}

	@EventHandler
	public void onActivate(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (e.getItem() == null) {
			return;
		}

		if (e.getItem().isSimilar(Items.Explosives.getIgniter())) {
			if (e.getClickedBlock() != null) {
				if (e.getClickedBlock().getType() == Material.JUNGLE_BUTTON) {

					if (mines2.get(p.getUniqueId()).contains(e.getClickedBlock().getLocation())) {
						e.setCancelled(true);
						if (activated.contains(p.getUniqueId())) {
							p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
									new TextComponent(ChatColor.GRAY + "You deactivated the " + ChatColor.DARK_RED + "C4"));
							activated.remove(p.getUniqueId());
						} else {
							p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
									new TextComponent(ChatColor.GRAY + "You activated the " + ChatColor.DARK_RED + "C4"));
							activated.add(p.getUniqueId());
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onIgnite(PlayerInteractEvent e) {
		final Player p = e.getPlayer();

		if (e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.JUNGLE_BUTTON || e.getItem() == null) {
			return;
		}

		if (e.getItem().isSimilar(Items.Explosives.getIgniter())) {

			if (!(mines2.get(p.getUniqueId()).size() > 0)) {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
						ChatColor.RED + "You have to place " + ChatColor.DARK_RED + "C4 " + ChatColor.RED + "first"));
				p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, SoundCategory.MASTER, 1, 2);
				return;
			}

			if (!activated.contains(p.getUniqueId())) {
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(
						ChatColor.RED + "You have to activate the " + ChatColor.DARK_RED + "C4 " + ChatColor.RED + "first"));
				p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, SoundCategory.MASTER, 1, 2);
				return;
			}

			if (!almostExplosion.contains(p.getUniqueId())) {

				if (!(mines2.get(p.getUniqueId()).size() > 0)) {
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
							new TextComponent(ChatColor.RED + "You didn't place " + ChatColor.DARK_RED + "C4"));
					return;
				}

				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						new TextComponent(ChatColor.YELLOW + "Are you sure you want to detonate the " + ChatColor.DARK_RED + "C4"));
				almostExplosion.add(p.getUniqueId());
				tasks.put(p.getUniqueId(), Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						if (almostExplosion.contains(p.getUniqueId())) {
							p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
									new TextComponent(ChatColor.RED + "You didn't confirm the detonation"));
							p.playSound(p.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, SoundCategory.MASTER, 1, 2);
							Bukkit.getScheduler().cancelTask(tasks.get(p.getUniqueId()).getTaskId());
							almostExplosion.remove(p.getUniqueId());
						}
					}
				}, 20L * 5));
			} else {
				Bukkit.getScheduler().cancelTask(tasks.get(p.getUniqueId()).getTaskId());

				for (Location loc : mines2.get(p.getUniqueId())) {
					loc.getWorld().createExplosion(loc, 5F, false, true);
					loc.getBlock().setType(Material.AIR);

					mines2.get(p.getUniqueId()).remove(loc);
					activated.remove(p.getUniqueId());
					almostExplosion.remove(p.getUniqueId());
					tasks.remove(p.getUniqueId());
				}
			}
		}
	}

}
