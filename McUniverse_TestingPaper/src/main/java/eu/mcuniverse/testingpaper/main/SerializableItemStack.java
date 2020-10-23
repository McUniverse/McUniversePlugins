package eu.mcuniverse.testingpaper.main;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 * Made by Scarsz http://github.com/Scarsz
 *
 * @in /dev/hell
 * @on 1/13/2017
 */
public class SerializableItemStack {

	private Map<String, Map<String, Object>> serializableMap = new HashMap<>();
	private static Yaml yaml;
	static {
		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setPrettyFlow(true);
		yaml = new Yaml(dumperOptions);
	}

	public SerializableItemStack(ItemStack itemStackToMakeSerializable) {
		ItemStack itemStack = itemStackToMakeSerializable.clone();

		if (itemStack == null)
			itemStack = new ItemStack(Material.AIR);
		serializableMap.put("itemMeta", itemStack.hasItemMeta() ? itemStack.getItemMeta().serialize() : null);
		itemStack.setItemMeta(null);
		serializableMap.put("itemStack", itemStack.serialize());
	}

	public SerializableItemStack(Map<String, Map<String, Object>> serializableMap) {
		this.serializableMap = serializableMap;
	}

	public SerializableItemStack(String yamlSerializedMap) {
		this.serializableMap = yaml.loadAs(yamlSerializedMap, Map.class);
	}

	public ItemStack toItemStack() {
		ItemStack itemStack = ItemStack.deserialize(serializableMap.get("itemStack"));
		if (serializableMap.get("itemMeta") != null)
			itemStack.setItemMeta((ItemMeta) ConfigurationSerialization.deserializeObject(serializableMap.get("itemMeta"),
					ConfigurationSerialization.getClassByAlias("ItemMeta")));
		return itemStack;
	}

	public Map<String, Map> toSerializableMap() {
		return new HashMap<>(serializableMap);
	}

	public String toYaml() {
		return yaml.dump(serializableMap);
	}

}