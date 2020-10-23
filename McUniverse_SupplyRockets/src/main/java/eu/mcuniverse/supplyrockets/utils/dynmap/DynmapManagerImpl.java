package eu.mcuniverse.supplyrockets.utils.dynmap;

import org.bukkit.Bukkit;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.Marker;
import org.dynmap.markers.MarkerAPI;
import org.dynmap.markers.MarkerIcon;
import org.dynmap.markers.MarkerSet;

import eu.mcuniverse.supplyrockets.meteorite.IMeteorite;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lombok.Getter;

@Getter
public class DynmapManagerImpl implements DynmapManager {

	private static DynmapManagerImpl instance;

	private static final String MARKERSET = "meteorites";

	private DynmapAPI dynmapApi;
	private MarkerAPI markerApi;
	private MarkerSet markerSet;

	private Object2ObjectOpenHashMap<IMeteorite, Marker> markers;
	
	public DynmapManagerImpl() {
		this.dynmapApi = (DynmapAPI) Bukkit.getPluginManager().getPlugin("dynmap");
		this.markerApi = this.dynmapApi.getMarkerAPI();
		this.markerSet = this.markerApi.getMarkerSet(MARKERSET);
		if (markerSet == null) {
			markerSet = this.markerApi.createMarkerSet(MARKERSET, "Meteorite", null, false);
		}
		markers = new Object2ObjectOpenHashMap<>();
	}

	@Override
	public Marker createMarker(IMeteorite meteorite) {
		if (markers.containsKey(meteorite)) {
			throw new IllegalStateException("Meteorite is already in Map stored!");
		}
		
		MarkerIcon icon = this.markerApi.getMarkerIcon("redflag");
		
		String label = meteorite.getMeteoriteSize().toString() + " Meteorite";
		
		Marker marker = this.markerSet.createMarker(null, label, false, meteorite.getWorld().getName(),
				meteorite.getLocation().getBlockX(), meteorite.getLocation().getBlockY(), meteorite.getLocation().getBlockZ(),
				icon, false);
		
		markers.put(meteorite, marker);
		
		return marker;
	}
	
	@Override
	public Marker getMarker(IMeteorite meteorite) {
		return markers.get(meteorite);
	}
	
	@Override
	public void deleteMarker(IMeteorite meteorite) {
		markers.get(meteorite).deleteMarker();
		markers.remove(meteorite);
	}

	public static DynmapManagerImpl getInstance() {
		if (instance == null) {
			instance = new DynmapManagerImpl();
		}
		return instance;
	}

}
