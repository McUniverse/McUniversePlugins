package eu.mcuniverse.essentials.tablist;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

public enum Rank {
	 
	OWNER("Owner", "&4", false),
	ADMIN("Admin", "&c", false),
	DEVELOPER("Developer", "&b", false),
	MODERATOR("Moderator", "&3", false),
	SUPPORTER("Supporter", "&9", false),
	ULTIMATE("Ultimate", "&d", true),
	MVPPLUS("MVP+", "&6", true),
	MVP("MVP", "&e", true),
	VIPPLUS("VIP+", "&2", true),
	VIP("VIP", "&a", true),
	DEFAULT("default", "&7", true);
	
	String name;
	String colorCode;
	@Getter
	boolean onlyColor;
	
	private Rank(String name, String colorCode, boolean onlyColor) {
		this.name = name;
		this.colorCode = colorCode;
		this.onlyColor = onlyColor;
	}
	
	public String getColorCode() {
		return ChatColor.translateAlternateColorCodes('&', colorCode);
	}
	
	public String getPrefix() {
//		return getColorCode() + getName();
		if (onlyColor) {
			return getColorCode() + getName();
		} else {
			return getColorCode() + getName() + ChatColor.DARK_GRAY + " | ";
		}
	}
	
	public String getName() {
		return name;
	}
	
}