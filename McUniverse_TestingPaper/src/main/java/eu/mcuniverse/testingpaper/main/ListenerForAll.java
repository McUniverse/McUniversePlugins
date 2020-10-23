package eu.mcuniverse.testingpaper.main;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;

public class ListenerForAll implements Listener {
	public void onEvent(Event event) {
//	if (event instanceof EntityEvent || event instanceof VehicleEvent || event instanceof PlayerStatisticIncrementEvent || event instanceof ServerEvent) {
//		return;
//	}
//	System.out.println(event.getEventName());
		if (event instanceof PlayerJumpEvent) {
			System.out.println(event.toString());
		}
	}
}
