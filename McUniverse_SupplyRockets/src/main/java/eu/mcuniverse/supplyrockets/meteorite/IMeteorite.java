package eu.mcuniverse.supplyrockets.meteorite;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import eu.mcuniverse.supplyrockets.Core;
import eu.mcuniverse.supplyrockets.boss.BossManger;
import eu.mcuniverse.supplyrockets.chances.MeteoriteSize;
import eu.mcuniverse.supplyrockets.rockets.CommonUtils;
import eu.mcuniverse.supplyrockets.rockets.IRocket;

public interface IMeteorite extends IRocket {

	public MeteoriteSize getMeteoriteSize();
	
	public Entity[] getBosses();
	
	public void setBosses(Entity[] bosses);
	
	public void setArmorStand(ArmorStand armorStand);
	
	public String toString();
	
	public default void spawnCrater() {
		CommonUtils.makeSphere(getLocation(), getMeteoriteSize().getCraterSize());
	}
	
	public default boolean isLootAccessible() {
		for (Entity entity : getBosses()) {
			if (!entity.isDead() || !entity.isValid()) {
				Bukkit.broadcastMessage("Loot not accessible");
				return false;
			}
		}
		Bukkit.broadcastMessage("Loot accessible");
		return true;
	}
	
	public default void addToDynmap() {
		
	}
	
	@Override
	default void spawn() {
		if (!getLocation().getChunk().isLoaded()) {
			getLocation().getChunk().load();
		}
		if (getArmorStand() == null) {
			setArmorStand(CommonUtils.spawn(getLocation(), CommonUtils.getMeteoriteItem(getMeteoriteSize())));
			spawnCrater();
			Core.getMeteoriteTimer().get(getPlanet()).getMeteorites().put(getPlanet(), this);
			Core.getCurrentMeteorite().put(getPlanet(), this);
		}
		setBosses(BossManger.spawnMob(getLocation(), getMeteoriteSize()));
	}
	
}
