package eu.mcuniverse.rocket.oil;

import java.util.Map;

import org.bukkit.Location;

import com.google.gson.Gson;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RefineryLocationSerializer {

	public String serialize(Location loc) {
		Gson gson = new Gson();
		return gson.toJson(loc.serialize());
	}
	
	@SuppressWarnings("unchecked")
	public Location deserialize(String str) {
		return Location.deserialize(new Gson().fromJson(str, Map.class));
	}
	
}
