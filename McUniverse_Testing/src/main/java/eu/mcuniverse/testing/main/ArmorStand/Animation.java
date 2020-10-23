package eu.mcuniverse.testing.main.ArmorStand;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import eu.mcuniverse.testing.main.Main;

public class Animation implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getType() == Material.EMERALD_BLOCK && e.getHand() == EquipmentSlot.OFF_HAND) {
			Block b = e.getBlock();
			final ArmorStand as = (ArmorStand) b.getWorld().spawnEntity(b.getLocation().add(0.5, 1, 0.5), EntityType.ARMOR_STAND);
			ItemStack item = new ItemStack(Material.STICK);
			ItemMeta meta = item.getItemMeta();
			meta.setCustomModelData(10000006);
			item.setItemMeta(meta);
			
			as.getEquipment().setHelmet(item);
			as.setHeadPose(new EulerAngle(Math.toRadians(0), 0, 0));
			// TODO: as.setRightArmPose();
			as.setVisible(false);
			
			new BukkitRunnable() {
				int count = 0;
				float x = 0;
				@Override
				public void run() {
					if (count > 90) {
						as.setVisible(true);
						cancel();
					} else {
						x = x += 1;
						count++;
						as.setHeadPose(new EulerAngle(Math.toRadians(x), 0, 0));
					}
				}
			}.runTaskTimer(Main.instance, 10L, 1L);
		}
	}

}
