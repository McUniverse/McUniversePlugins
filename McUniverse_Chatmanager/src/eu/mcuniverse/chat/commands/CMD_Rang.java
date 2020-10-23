package eu.mcuniverse.chat.commands;

public enum CMD_Rang {
	
	DEFAULT("Player", "§7"),
	ULTIMATE("Ultimate", "§d"),
	VIP("VIP", "§a"),
	VIPPLUS("VIP+", "§2"),
	MVP("MVP", "§e"),
	MVPPLUS("MVP+", "§6"),
	SUPPORTER("Supporter", "§9"),
	MODERATOR("Moderator", "§3"),
	DEVELOPER("Developer", "§b"),
	ADMIN("Admin", "§c") ,
	OWNER("Owner", "§4");
	
	
	String name;
	String colorCode;
	
	private CMD_Rang(String name, String colorCode) {
		this.name = name;
		this.colorCode = colorCode;
	}
	
	public String getColorCode() {
		return colorCode;
	}
	
	public String getName() {
		return name;
	}
}