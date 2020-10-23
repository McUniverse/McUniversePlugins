package eu.mcuniverse.weapons.explosives;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.weapons.item.Items;
import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.util.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class SmokeGrenade implements Listener {

	private Core plugin;

	public SmokeGrenade(final Core plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onThrow(PlayerInteractEvent e) {
		if (e.getItem() == null) {
			return;
		}

		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem().isSimilar(Items.Explosives.getSmokeGrenade())) {
				e.setCancelled(true);

				final Item i = p.getWorld().dropItem(p.getEyeLocation(), Items.Explosives.getSmokeGrenade());
				i.setGravity(true);
				i.setVelocity(p.getLocation().getDirection().multiply(1));
				i.setPickupDelay(Integer.MAX_VALUE);
				i.setInvulnerable(true);
//				i.setCustomName(true);

				new ExplosionTimer(i, 5);
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
						TextComponent.fromLegacyText("Grenade thrown", ChatColor.GREEN));

				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SNOWBALL_THROW, SoundCategory.MASTER, 1, 1);

				if (p.getGameMode() != GameMode.CREATIVE) {
					switch (Utils.getHand(p, Items.Explosives.getSmokeGrenade())) {
					case OFF:
						p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount() - 1);
						break;
					case MAIN:
						p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
					default:
						break;
					}
				}

			}
		}
	}

	private class ExplosionTimer extends BukkitRunnable {

		private Item item;
		private int count;

		public ExplosionTimer(Item item, int delay) {
			this.item = item;
			count = delay;

			runTaskTimer(plugin, 0L, 5L);
		}

		@Override
		public void run() {
			if (count == 0) {
				// Smoke
				item.remove();
				cancel();
			} else {
				item.setCustomName(ChatColor.RED + "" + count);
				item.getWorld().playSound(item.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, SoundCategory.MASTER, 1, 2);
			}
			count--;
		}
	}

	private class SmokeTimer extends BukkitRunnable {

		private Location location;
		private World world;
		
		public SmokeTimer(Location location) {
			this.location = location;
			this.world = location.getWorld();
			
			runTaskTimer(plugin, 0L, 5L);
		}
		
		public void run() {
		};

	}

}
