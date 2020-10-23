package eu.mcuniverse.supplyrockets.utils.dynmap;

import org.dynmap.markers.Marker;

import eu.mcuniverse.supplyrockets.meteorite.IMeteorite;

public interface DynmapManager {

	/**
	 * Create a Marker on the map
	 * @param meteorite The meteorite to display
	 * @return Returns the created Marker
	 */
	public Marker createMarker(IMeteorite meteorite);
	
	/**
	 * Get a certain Marker for a meteorite
	 * @param meteorite The meteorite to get
	 * @return Returns the Marker according to the meteorite
	 */
	public Marker getMarker(IMeteorite meteorite);
	
	/**
	 * Delete a marker on the dynmap
	 * @param meteorite The meteorite to delete
	 */
	public void deleteMarker(IMeteorite meteorite);
	
	/**
	 * Get the Instance of this Interface
	 * @return The Instance {@link DynmapManagerImpl}
	 */
	public static DynmapManager getInstance() {
		return DynmapManagerImpl.getInstance();
	}
	
}
