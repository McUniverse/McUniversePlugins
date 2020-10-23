package eu.mcuniverse.supplyrockets.rockets;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.chances.Tier;
import eu.mcuniverse.universeapi.rockets.Planet;
import eu.mcuniverse.universeapi.util.ItemUtil;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;

@Getter
public class SRocketTier2 implements IRocket {

	private World world;
	private Location location;
	private ObjectOpenHashSet<ItemStack> items;
	private Inventory inventory;
	private Tier tier = Tier.TIER_TWO;
	private ArmorStand armorStand;
	private Planet planet;

	public SRocketTier2(Planet planet) {
		this.planet = planet;
		this.world = Bukkit.getWorld(planet.getWorldName());
		if (this.world == null) {
			throw new IllegalArgumentException(world + " is not a valid world!");
		}
		location = planet.getRandomLocation();
	}

	@Override
	public void spawn() {
		if (!location.getChunk().isLoaded()) {
			if (!location.getChunk().load(false)) {
			}
		}
		if (armorStand == null) {
			armorStand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
			Core.getCurrentTier2().put(planet, this);
			Core.getTierTwoTimer().get(planet).getRocket().put(planet, this);
		}
//		armorStand.setRightArmPose(EulerAngle.ZERO);
		armorStand.setRightArmPose(new EulerAngle(Math.toRadians(-90), 0, 0));
		armorStand.getEquipment().setItemInMainHand(ItemUtil.createSkinItem(Material.STICK, "Stick", 10000090));
		armorStand.setInvulnerable(true);
	}

	@Override
	public Inventory getInventory() {
		if (inventory == null) {
			inventory = Bukkit.createInventory(this, InventoryType.CHEST, "Supply Rocket Tier 2");
			fillInventory();
		}
		return inventory;
	}

//	@Override
//	public void fillInventory() {
//		ObjectSet<ItemStack> loot = LootManager.getLoot(tier);
//		int[] slots = RandomUtils.getRandomSlot(getInventory().getSize());
//		int index = 0;
//		for (ItemStack item : loot) {
//			getInventory().setItem(slots[index], item);
//			index++;
//		}
//	}

}
