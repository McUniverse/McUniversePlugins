package eu.mcuniverse.rocket.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

	public String capitalize(String str) {
		if (str == null || str.isEmpty()) {
			return str;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

}
