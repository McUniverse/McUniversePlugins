package de.vlogdev.team;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class Team extends Plugin {

	public static List<String> admins = new ArrayList<>();
	public static List<String> devs = new ArrayList<>();
	public static List<String> srdevs = new ArrayList<>();
	public static List<String> srmods = new ArrayList<>();
	public static List<String> mods = new ArrayList<>();
	public static List<String> archis = new ArrayList<>();
	public static List<String> harchis = new ArrayList<>();
	public static List<String> sups = new ArrayList<>();
	public static List<String> rekrut = new ArrayList<>();

	Configuration configuration;
	public static Team team;

	@Override
	public void onEnable() {
		team = this;
		getProxy().getPluginManager().registerCommand(this, new CMD_Team());
		loadConfig();
	}

	public void loadConfig() {
		for (String s : Arrays.asList("VlogDev", "KohleBlock", "rod_Vunning"))
			admins.add(s);
		
		for (String s : Arrays.asList("JayReturns"))
			srdevs.add(s);

		for (String s : Arrays.asList("EasyAceGames", "ClientBots"))
			devs.add(s);

		for (String s : Arrays.asList("Krote"))
			srmods.add(s);

		for (String s : Arrays.asList("IZeErZ"))
			mods.add(s);

		for (String s : Arrays.asList("Darf_Er_Das"))
			harchis.add(s);
		
		for (String s : Arrays.asList("A1ver", "Obstiii", "ReqaxYT", "Eley"))
			archis.add(s);

		for (String s : Arrays.asList("ZooZockt"))
			sups.add(s);
		
		for (String s : Arrays.asList("LeSchwitzerchen"))
			rekrut.add(s);

	}
}
