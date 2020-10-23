package eu.mcuniverse.bungeeuniverse.ranks;

import net.md_5.bungee.api.ChatColor;

public enum Rank {
	 
	OWNER("Team.Owner", ChatColor.DARK_RED.toString()),
	ADMIN("Team.Admin", ChatColor.RED.toString()),
	DEVELOPER("Team.Developer", ChatColor.AQUA.toString()),
	MODERATOR("Team.Moderator", ChatColor.DARK_AQUA.toString()),
	BUILDER("Team.Builder", ChatColor.YELLOW.toString()),
	SUPPORTER("Team.Supporter", ChatColor.BLUE.toString());
	
	String key;
	String colorCode;
	
	private Rank(String key, String colorCode) {
		this.key = key;
		this.colorCode = colorCode;
	}
	
	public String getColorCode() {
		return colorCode;
	}
	
	public String getKey() {
		return key;
	}
	
	@Override
	public String toString() {
		return super.toString().substring(0, 1) + super.toString().substring(1).toLowerCase();
	}
	
}