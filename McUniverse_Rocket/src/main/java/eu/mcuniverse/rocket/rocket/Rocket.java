package eu.mcuniverse.rocket.rocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;

import com.google.gson.Gson;

import eu.mcuniverse.rocket.data.Variables;
import eu.mcuniverse.rocket.items.Items;
import eu.mcuniverse.rocket.rocket.RocketPart.AirTank;
import eu.mcuniverse.rocket.rocket.RocketPart.Boardcomputer;
import eu.mcuniverse.rocket.rocket.RocketPart.Frostshield;
import eu.mcuniverse.rocket.rocket.RocketPart.FuelTank;
import eu.mcuniverse.rocket.rocket.RocketPart.Heatshield;
import eu.mcuniverse.rocket.rocket.RocketPart.Hullarmor;
import eu.mcuniverse.rocket.rocket.RocketPart.Storage;
import eu.mcuniverse.rocket.rocket.RocketPart.WaterTank;
import eu.mcuniverse.rocket.sql.MySQL;
import eu.mcuniverse.rocket.util.ItemUtil;
import eu.mcuniverse.rocket.util.RocketUtil;
import eu.mcuniverse.universeapi.serialization.JsonItemStack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;

@Data
public class Rocket {

	@Setter(value = AccessLevel.NONE)
	private UUID ownerUUID;
	private int invSize;
	@Deprecated
	private int tier;
	private Map<String, Object> location;
	private double fuelLevel; // panes (max. 18)
	private int waterLevel;
	private int hullHits;
	@Setter(value = AccessLevel.PRIVATE)
	private UUID armorStandUUID;
	private ObjectArrayList<String> inventory;
	
	private Skin skin;

	private RocketPart.AirTank airTank;
	private RocketPart.WaterTank waterTank;
	private RocketPart.Hullarmor hullarmor;
	private RocketPart.Heatshield heatshield;
	private RocketPart.Frostshield frostshield;
	private RocketPart.Storage storage;
	private RocketPart.Boardcomputer boardcomputer;
	private RocketPart.FuelTank fuelTank;

	@Setter(value = AccessLevel.NONE)
	private HashMap<RocketPart.Parts, Object> map;

	// Constructor for a new rocket
	public Rocket(Player p, int rocketTier, Location spawnLoc) {
		ownerUUID = p.getUniqueId();
		invSize = RocketUtil.getInvSize(rocketTier);
		tier = rocketTier;
		location = spawnLoc.serialize();
		fuelLevel = 0;
		inventory = new ObjectArrayList<String>();
		waterLevel = 0;
		hullHits = 0;

		setAirTank(RocketPart.AirTank.NONE);
		setWaterTank(RocketPart.WaterTank.NONE);
		setHullarmor(RocketPart.Hullarmor.NONE);
		setHeatshield(RocketPart.Heatshield.NONE);
		setFrostshield(RocketPart.Frostshield.NONE);
		setStorage(RocketPart.Storage.NONE);
		setStorage(RocketPart.Storage.NONE);
		setBoardcomputer(RocketPart.Boardcomputer.NONE);
		setFuelTank(RocketPart.FuelTank.NONE);
		setSkin(Skin.ROCKET);
		
		
	}

//	public Rocket(Player p, Location spawnLoc, Skin skin, AirTank airTank, WaterTank waterTank, Hullarmor hullarmor,
//			Heatshield heatshield, Storage storage, Frostshield frostshield, Boardcomputer boardcomputer, FuelTank fuelTank) {
//		
//		ownerUUID = p.getUniqueId();
//		invSize = 0;
//		tier = 0;
//		location = spawnLoc.serialize();
//		fuelLevel = 0;
//		
//		this.skin = skin;
//		this.airTank = airTank;
//		this.waterTank = waterTank;
//		this.hullarmor = hullarmor;
//		this.heatshield = heatshield;
//		this.frostshield = frostshield;
//		this.boardcomputer = boardcomputer;
//		this.fuelTank = fuelTank;
//	}

	public int getPartTier(RocketPart.Parts part) {
		switch (part) {
		case AIR_TANK:
			return getAirTank().getTier();
		case WATER_TANK:
			return getWaterTank().getTier();
		case HULL_ARMOR:
			return getHullarmor().getTier();
		case HEAT_SHIELD:
			return getHeatshield().getTier();
		case FROST_SHIELD:
			return getFrostshield().getTier();
		case BOARDCOMPUTER:
			return getBoardcomputer().getTier();
		case FUEL_TANK:
			return getFuelTank().getTier();
		case STORAGE:
			return getStorage().getTier();
		default:
			throw new IllegalArgumentException("Unknown type: " + part);
		}
	}

	@Deprecated(forRemoval = true)
	public int getFuel() {
		double ret = (double) fuelLevel / (double) RocketUtil.getMaxFuel(tier);
		return (int) (ret * 100);
	}

	public void respawn() {
		if (getArmorStandUUID()!= null) {
			deleteRocket();
		}
		summonArmorstand();
	}
	
	public void setItemInHand(Skin skin) {
		if (Bukkit.getEntity(getArmorStandUUID()) != null) {
			ArmorStand as = (ArmorStand) Bukkit.getEntity(getArmorStandUUID());
			as.getEquipment().setItemInMainHand(skin.getItem());
		}
	}

	public void teleport(Location loc) {
		if (Bukkit.getEntity(getArmorStandUUID()) != null) {
			Bukkit.getEntity(getArmorStandUUID()).teleport(loc);
		}
		setLocation(loc.serialize());
		saveRocket();
	}
	
	public void setArmorStandPose(EulerAngle angle) {
		((ArmorStand) Bukkit.getEntity(getArmorStandUUID())).setRightArmPose(angle);
	}
	
	public void setArmorStandPose(double angle) {
		((ArmorStand) Bukkit.getEntity(getArmorStandUUID())).setRightArmPose(new EulerAngle(Math.toRadians(angle), 0, 0));
	}
	
	public void setFacingUp() {
		setArmorStandPose(-90);
	}
	
	public void setLayingDown() {
		setArmorStandPose(EulerAngle.ZERO);
	}
	
	public void saveRocket() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		OfflinePlayer owner = Bukkit.getOfflinePlayer(ownerUUID);
		if (MySQL.hasRocket(owner)) {
			MySQL.deleteRocket(owner);
		}
		MySQL.registerPlayer(owner, json);
	}
	
	public Location getLocation() {
		return Location.deserialize(location);
	}

	public ItemStack getItem() {
		ItemStack item = Items.getBaseRocketItem();
		ItemMeta meta = item.getItemMeta();
		meta.setLore(getInfos());
		item.setItemMeta(meta);
		
		return item;
	}

	public ObjectArrayList<ItemStack> getInventory() {
		ObjectArrayList<ItemStack> content = MySQL.getInventory(Bukkit.getOfflinePlayer(getOwnerUUID()));
		return content;
	}
	
	public void addItemToInventory(ItemStack item) {
		inventory.add(JsonItemStack.toJson(item));
		OfflinePlayer op = Bukkit.getOfflinePlayer(getOwnerUUID());
		ObjectArrayList<ItemStack> items = MySQL.getInventory(op);
		items.add(item);
		MySQL.updateInventory(Bukkit.getOfflinePlayer(ownerUUID), items);
	}
	
	public void summonArmorstand() {
		if (armorStandUUID != null) {
			return;
		}
		Location loc = Location.deserialize(location);
		ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.clone().add(0, 1, 0), EntityType.ARMOR_STAND);

		as.getEquipment().setItemInMainHand(getSkin().getItem());
//		as.setRightArmPose(EulerAngle.ZERO);
		switch (getSkin()) {
		case SHUTTLE:
			as.setRightArmPose(EulerAngle.ZERO);
			break;
		case SATURN_V:
			as.setRightArmPose(EulerAngle.ZERO);
			break;
		case ROCKET:
			as.setRightArmPose(new EulerAngle(Math.toRadians(-90), 0, 0));
			break;
		default:
			break;
		}

		as.getLocation().setYaw(0);
		as.getLocation().setPitch(0);
		as.setGravity(false);
		as.setCustomName(Variables.ARMOR_STAND_NAME);
		as.setInvulnerable(true);
		as.setVisible(false);
		
		this.armorStandUUID = as.getUniqueId();
	}
	
	public void summonArmorStand(Location loc, boolean fromGson) {
		if (!fromGson) {
			summonArmorstand();
		}
		setArmorStandUUID(null);
		setLocation(loc.serialize());
		summonArmorstand();
	}

	public List<String> getInfos() {
		List<String> list = new ArrayList<>();
		list.add(ChatColor.AQUA + "Air tank: " + ChatColor.YELLOW + airTank.toString());
		list.add(ChatColor.AQUA + "Water tank: " + ChatColor.YELLOW + waterTank.toString());
		list.add(ChatColor.AQUA + "Hull armor: " + ChatColor.YELLOW + hullarmor.toString());
		list.add(ChatColor.AQUA + "Heat shield: " + ChatColor.YELLOW + heatshield.toString());
		list.add(ChatColor.AQUA + "Frost shield: " + ChatColor.YELLOW + frostshield.toString());
		list.add(ChatColor.AQUA + "Storage: " + ChatColor.YELLOW + storage.toString());
		list.add(ChatColor.AQUA + "Boardcomputer: " + ChatColor.YELLOW + boardcomputer.toString());
		list.add(ChatColor.AQUA + "Fuel tank: " + ChatColor.YELLOW + fuelTank.toString());
//		list.add(ChatColor.AQUA + "Thruster: " + ChatColor.YELLOW + thruster.toString());
//		list.add(ChatColor.AQUA + "Solar panel: " + ChatColor.YELLOW + solarpanel.toString());
		return list;
	}

	public void deleteRocket() {
		if (Bukkit.getEntity(getArmorStandUUID()) != null) {
			Bukkit.getEntity(getArmorStandUUID()).remove();
		}
		if (Bukkit.getPlayer(getOwnerUUID()) != null) {
//			Bukkit.getPlayer(getOwnerUUID()).getInventory().addItem(this.getItem());
			ItemStack item = this.getItem();
			item = ItemUtil.NBT.addNBTTag(item, "rocketDestroy", new Gson().toJson(this));
			Bukkit.getPlayer(getOwnerUUID()).getInventory().addItem(item);
		}
	}

	public boolean upgrade(RocketPart.Parts part) {
		switch (part) {
		case AIR_TANK:
			if (getAirTank().getTier() >= getAirTank().getMaxTier()) {
				return false;
			} else {
				setAirTank(AirTank.getByTier(getAirTank().getTier() + 1));
				return true;
			}
		case WATER_TANK:
			if (getWaterTank().getTier() >= getWaterTank().getMaxTier()) {
				return false;
			} else {
				setWaterTank(WaterTank.getByTier(getWaterTank().getTier() + 1));
				return true;
			}
		case HULL_ARMOR:
			if (getHullarmor().getTier() >= getHullarmor().getMaxTier()) {
				return false;
			} else {
				setHullarmor(Hullarmor.getByTier(getHullarmor().getTier() + 1));
				return true;
			}
		case HEAT_SHIELD:
			if (getHeatshield().getTier() >= getHeatshield().getMaxTier()) {
				return false;
			} else {
				setHeatshield(Heatshield.getByTier(getHeatshield().getTier() + 1));
				return true;
			}
		case FROST_SHIELD:
			if (getFrostshield().getTier() >= getFrostshield().getMaxTier()) {
				return false;
			} else {
				setFrostshield(Frostshield.getByTier(getFrostshield().getTier() + 1));
				return true;
			}
		case BOARDCOMPUTER:
			if (getBoardcomputer().getTier() >= getBoardcomputer().getMaxTier()) {
				return false;
			} else {
				setBoardcomputer(Boardcomputer.getByTier(getBoardcomputer().getTier() + 1));
				return true;
			}
		case FUEL_TANK:
			if (getFuelTank().getTier() >= getFuelTank().getMaxTier()) {
				return false;
			} else {
				setFuelTank(FuelTank.getByTier(getFuelTank().getTier() + 1));
				return true;
			}
		case STORAGE:
			if (getStorage().getTier() >= getStorage().getMaxTier()) {
				return false;
			} else {
				setStorage(Storage.getByTier(getStorage().getTier() + 1));
				return true;
			}
		default:
			return false;
		}
	}

	public boolean isMaxTier(RocketPart.Parts part) {
		switch (part) {
		case AIR_TANK:
			return getAirTank().getTier() >= getAirTank().getMaxTier();
		case WATER_TANK:
			return getWaterTank().getTier() >= getWaterTank().getMaxTier();
		case HULL_ARMOR:
			return getHullarmor().getTier() >= getHullarmor().getMaxTier();
		case FROST_SHIELD:
			return getFrostshield().getTier() >= getFrostshield().getMaxTier();
		case HEAT_SHIELD:
			return getHeatshield().getTier() >= getHeatshield().getMaxTier();
		case BOARDCOMPUTER:
			return getBoardcomputer().getTier() >= getBoardcomputer().getMaxTier();
		case FUEL_TANK:
			return getFuelTank().getTier() >= getFuelTank().getMaxTier();
		case STORAGE:
			return getStorage().getTier() >= getStorage().getMaxTier();
		default:
			return false;
		}
	}

}
