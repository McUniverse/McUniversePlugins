package eu.mcuniverse.weapons.runnables;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import eu.mcuniverse.weapons.data.Data;
import eu.mcuniverse.weapons.item.Items;
import eu.mcuniverse.weapons.main.Core;
import eu.mcuniverse.weapons.weapon.util.AmmunitionManager;
import eu.mcuniverse.weapons.weapon.util.AmmunitionType;
import eu.mcuniverse.weapons.weapon.util.Weapon;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class ReloadTimer extends BukkitRunnable {

	private Player p;
	private ItemStack item;
	private int count;
	private Weapon w;
	private AmmunitionType type;

	public ReloadTimer(Player p, ItemStack item, int count, Weapon w, AmmunitionType type) {
		this.p = p;
		this.item = item;
		this.count = count;
		this.w = w;
		this.type = type;
		this.runTaskLater(Core.getInstance(), w.getReloadingTime());
	}

	@Override
	public synchronized void cancel() throws IllegalStateException {
		p.getInventory().setItemInMainHand(AmmunitionManager.removeReloadTag(item));
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "Reloading canceled..."));
		super.cancel();
	}

	@Override
	public void run() {
		int toRemove = 0;
		int req = AmmunitionManager.getRequiredToFill(item, w);
		if (count >= req) {
			AmmunitionManager.reloadAmmunition(p, item, w, req);
			toRemove = req;
		} else {
			AmmunitionManager.reloadAmmunition(p, item, w, count);
			toRemove = count;
		}
		p.getInventory().removeItem(Items.getAmmunition(type, toRemove));
		p.getInventory().setItemInMainHand(AmmunitionManager.removeReloadTag(item));
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + "Reloaded"));
		if (toRemove > 0) {
			p.playSound(p.getLocation(), Sound.BLOCK_IRON_DOOR_OPEN, SoundCategory.MASTER, 1f, 2f);
		}
		Data.reloading.remove(p.getUniqueId());
	}

}
