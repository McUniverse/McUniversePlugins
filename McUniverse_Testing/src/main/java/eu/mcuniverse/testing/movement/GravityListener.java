package eu.mcuniverse.testing.movement;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GravityListener implements Listener {

	@EventHandler
	public void onChangeVel(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().startsWith("//gravity")) {
			e.setCancelled(true);
			Player p = e.getPlayer();
			if (p.hasPotionEffect(PotionEffectType.JUMP) && p.hasPotionEffect(PotionEffectType.SLOW_FALLING)) {
				p.removePotionEffect(PotionEffectType.JUMP);
				p.removePotionEffect(PotionEffectType.SLOW_FALLING);
			} else {
				p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 3, false, false, false));
				p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, Integer.MAX_VALUE, 0, false, false, false));
			}
		}
	}

//	private Vector velocity = new Vector(0, 1, 0);
//	private ObjectSet<UUID> jumped = new ObjectOpenHashSet<>();
//	
//	@EventHandler
//	public void onJump(PlayerMoveEvent e) {
//		Player p = e.getPlayer();
//		boolean isLadder = p.getLocation().getBlock().getType() == Material.LADDER;
//		if (jumped.contains(p.getUniqueId())) {
//			return;
//		}
//		if (e.getTo().getY() > e.getFrom().getY() && !p.isSwimming() && !p.isFlying() && !isLadder) {
//			p.setVelocity(velocity);
//			jumped.add(p.getUniqueId());
//			new BukkitRunnable() {
//				
//				@Override
//				public void run() {
//					jumped.remove(p.getUniqueId());
//					p.sendMessage("Jump again");
//				}
//			}.runTaskLater(Main.instance, 20L * 10);
//		}
//	}
//
//	@EventHandler
//	public void onChangeVel(PlayerCommandPreprocessEvent e) {
//		if (e.getMessage().startsWith("//velocity")) {
//			e.setCancelled(true);
//			Player p = e.getPlayer();
//			String[] cmd = e.getMessage().split(" ");
//			if (cmd.length != 4) {
//				TextComponent txt = new TextComponent("//velocity <x> <y> <z>");
//				txt.setColor(ChatColor.RED);
//				txt.setHoverEvent(
//						new HoverEvent(Action.SHOW_TEXT, TextComponent.fromLegacyText("Click to suggest", ChatColor.GOLD)));
//				txt.setClickEvent(
//						new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "//velocity <x> <y> <z>"));
//				p.spigot().sendMessage(txt);
//				return;
//			}
//			try {
//				double x = Double.parseDouble(cmd[1]);
//				double y = Double.parseDouble(cmd[2]);
//				double z = Double.parseDouble(cmd[3]);
//				
//				velocity = new Vector(x, y, z);
//				p.sendMessage(ChatColor.GREEN + "Velocity set!");
//			} catch (NumberFormatException exe) {
//				p.sendMessage(ChatColor.RED + "Invalid arguments!");
//			}
//		}
//	}

}
