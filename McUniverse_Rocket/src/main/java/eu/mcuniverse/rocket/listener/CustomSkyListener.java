package eu.mcuniverse.rocket.listener;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

import eu.mcuniverse.rocket.main.Core;

public class CustomSkyListener implements Listener {

	public CustomSkyListener(final Core plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		setCustomSky(e.getPlayer());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		setCustomSky(e.getPlayer());
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onThunderChange(ThunderChangeEvent e) {
		e.setCancelled(true);
	}
	
	@EventHandler
	public void stopRain(PlayerMoveEvent e) {
		if (e.getFrom().getChunk() == e.getTo().getChunk()) {
			return;
		}
		removeRain(e.getPlayer());
	}

	private void removeRain(Player p) {
		Chunk c = p.getLocation().getChunk();
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				p.sendBlockChange(c.getBlock(x, 255, z).getLocation(), Material.BARRIER.createBlockData());
			}
		}
	}
	
	private void setCustomSky(Player p) {
		PacketContainer changeValue = Core.getProtocolManager().createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
		changeValue.getModifier().writeDefaults();
		changeValue.getIntegers().write(0, 7);
		changeValue.getFloat().write(0, 2f);
		
		PacketContainer changeTime = Core.getProtocolManager().createPacket(PacketType.Play.Server.GAME_STATE_CHANGE);
		changeTime.getModifier().writeDefaults();
		changeTime.getIntegers().write(0, 8);
		changeTime.getFloat().write(0, 0f);
		
		try {
			Core.getProtocolManager().sendServerPacket(p, changeValue);
			Core.getProtocolManager().sendServerPacket(p, changeTime);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
