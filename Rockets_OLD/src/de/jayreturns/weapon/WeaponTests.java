package de.jayreturns.weapon;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import de.jayreturns.main.Main;
import de.jayreturns.util.ItemUtil;
import de.jayreturns.util.Messages;

public class WeaponTests implements Listener, CommandExecutor {

	List<String> cooldown = new LinkedList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Messages.noPlayer);
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission("weapon.main")) {
			p.sendMessage(Messages.noPerms);
			return true;
		}
		p.getInventory().addItem(ItemUtil.createItem(Material.BLAZE_ROD, "§eSuperbow", "§6This is a §esuperbow!"));
		p.getInventory().addItem(ItemUtil.createItem(Material.BAMBOO, "§cFun-Gun", "Just have fun"));

		return true;
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getItem() == null)
			return;
		if (!e.getItem().hasItemMeta())
			return;
		if (e.getAction().toString().contains("RIGHT") && e.getItem() != null) {
			ItemMeta meta = e.getItem().getItemMeta();
			if (meta.getDisplayName().equalsIgnoreCase("§eSuperbow")) {
				e.setCancelled(true);
				if (cooldown.contains(p.getName()))
					return;
				Arrow a = (Arrow) p.launchProjectile(Arrow.class, p.getLocation().getDirection().multiply(2));
				a.setColor(Color.fromRGB(0, 255, 255));
				a.setPickupStatus(PickupStatus.DISALLOWED);
				a.setCustomName("BULLET");
				cooldown.add(p.getName());
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						if (a != null)
							a.remove();
					}
				}, 20L * 5);
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						cooldown.remove(p.getName());
					}
				}, 20L * 1);
			} else if (meta.getDisplayName().equalsIgnoreCase("§cFun-Gun")) {
				e.setCancelled(true);
				if (cooldown.contains(p.getName()))
					return;
				Egg egg = (Egg) p.launchProjectile(Egg.class, p.getLocation().getDirection().multiply(2));
				egg.setCustomName("FUN");
				cooldown.add(p.getName());
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						if (egg != null)
							egg.remove();
					}
				}, 20L * 5);
				Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
					@Override
					public void run() {
						cooldown.remove(p.getName());
					}
				}, 20L * 1);
			}
		}
	}

	@EventHandler
	public void on(ProjectileHitEvent e) {
		if (e.getEntity().getCustomName().equalsIgnoreCase("BULLET")) {
			if (e.getHitBlock() != null) {
				e.getEntity().remove();
			}
		} else if (e.getEntity().getCustomName().equalsIgnoreCase("FUN")) {
			if (e.getHitBlockFace() != null) {
				e.getEntity().remove();
				FallingBlock fb = (FallingBlock) e.getHitBlock().getWorld().spawnFallingBlock(e.getHitBlock().getLocation().add(0, 3, 0), Material.OBSIDIAN.createBlockData());
				fb.setDropItem(false);
				fb.setVelocity(new Vector(0, 2, 0));
			}
		}
	}

}
