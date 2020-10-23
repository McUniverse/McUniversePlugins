package eu.mcuniverse.weapons.explosives;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
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

public class Grenade implements Listener {

	private Core plugin;

	public Grenade(final Core plugin) {
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onThrowGrenade(PlayerInteractEvent e) {
		if (e.getItem() == null) {
			return;
		}

		final Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (e.getItem().isSimilar(Items.Explosives.getGrenade())) {
				e.setCancelled(true);

				final Item i = p.getWorld().dropItem(p.getEyeLocation(), Items.Explosives.getGrenade());
				i.setGravity(true);
				i.setVelocity(p.getLocation().getDirection().multiply(1));
				i.setPickupDelay(Integer.MAX_VALUE);
				i.setCustomNameVisible(true);
				i.setInvulnerable(true);

				new ExplosionTimer(i, 5);
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Grenade thrown"));

				p.getWorld().playSound(p.getLocation(), Sound.ENTITY_SNOWBALL_THROW, SoundCategory.MASTER, 1, 1);

				if (p.getGameMode() != GameMode.CREATIVE) {
					switch (Utils.getHand(p, Items.Explosives.getGrenade())) {
					case OFF:
						p.getInventory().getItemInOffHand().setAmount(p.getInventory().getItemInOffHand().getAmount() - 1);
						break;
					case MAIN:
						p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
						break;
					default:
						break;
					}
				}
			}
		}
	}

	private class ExplosionTimer extends BukkitRunnable {

		private Item i;
		private int count;

		public ExplosionTimer(Item item, int delay) {
			i = item;
			count = delay;

			runTaskTimer(plugin, 0L, 20L);
		}

		@Override
		public void run() {
			if (count == 0) {
				i.getWorld().createExplosion(i.getLocation(), 4F, false, true);
				i.remove();
				cancel();
			} else {
				i.setCustomName(ChatColor.RED + "" + count);
				i.getWorld().playSound(i.getLocation(), Sound.BLOCK_COMPARATOR_CLICK, SoundCategory.MASTER, 1, 2);
			}
			count--;
		}

	}

}
