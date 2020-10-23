package eu.mcuniverse.rocket.rocket;

import org.bukkit.entity.Player;

import com.google.gson.Gson;

import eu.mcuniverse.rocket.sql.MySQL;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RocketManager {

	/**
	 * Get the rocket object of <code>player</code>
	 * @param player The player whom you want to get the rocket
	 * @return The Rocket object of <code>player</code>
	 */
	public Rocket getRocket(@NonNull Player player) {
		testRocket(player);
		
		String json = MySQL.getRocket(player);
		Gson gson = new Gson();
		Rocket r = gson.fromJson(json, Rocket.class);
		return r;
	}
	
	/**
	 * Get the rocket object of <code>player</code>
	 * @param name The player name whom you want to get the rocket
	 * @return The rocket object of <code>player</code>
	 */
	public Rocket getRocket(@NonNull String name) {
		testRocket(name);
		
		String json = MySQL.getRocket(name);
		Gson gson = new Gson();
		Rocket r = gson.fromJson(json, Rocket.class);
		return r;
	}

	/**
	 * Deletes the rocket of <code>player</code>
	 * @param player
	 */
	public void deleteRocket(@NonNull Player player) {
		testRocket(player);
		
		if (InRocketManager.isInRocket(player)) {
			InRocketManager.managePlayer(player);
		}
		
		Rocket r = getRocket(player);
		r.deleteRocket();
		MySQL.deleteRocket(player);
	}
	
	/**
	 * Deletes the rocket of <code>player</code>
	 * @param player
	 */
	public void deleteRocket(@NonNull String name) {
		testRocket(name);
		
		Rocket r = getRocket(name);
		r.deleteRocket();
		MySQL.deleteRocket(name);
	}

	/**
	 * Test if the fuel of a rocket is full
	 * @param player The player whom you want to get tested
	 * @return True if the fuel is full, false when it's not
	 */
	public boolean isFuelFull(@NonNull Player player) {
		testRocket(player);
		Rocket r = getRocket(player);
		return r.getFuelLevel() >= 18;
//		return r.getFuelLevel() >= RocketUtil.getMaxFuel(r.getTier());
	}
	
	/**
	 * Add fuel to <code>player<code>'s rocket
	 * @param player The player whom you want to add fuel
	 */
	public void addFuel(Player player) {
		testRocket(player);
		
		Rocket r = getRocket(player);
//		r.setFuelLevel(r.getFuelLevel() + 3);
		r.setFuelLevel(r.getFuelLevel() + r.getFuelTank().getSlotsPerFuel());
		MySQL.updateRocket(player, r);
	}

	/**
	 * Test wheter a player has a rocket
	 * @param player The player who you want to test
	 * @return Returns wheter the player has a rocket
	 */
	public boolean hasRocket(Player player) {
		return MySQL.hasRocket(player);
	}
	
	public boolean hasRocket(String name) {
		return MySQL.hasRocket(name);
	}
	
	private void testRocket(Player player) {
		if (!MySQL.hasRocket(player)) {
			throw new IllegalArgumentException("Player " + player.getName() + " has no rocket");
		}
	}
	
	private void testRocket(String name) {
		if (!MySQL.hasRocket(name)) {
			throw new IllegalArgumentException("Player " + name + " has no rocket");
		}
	}

}
