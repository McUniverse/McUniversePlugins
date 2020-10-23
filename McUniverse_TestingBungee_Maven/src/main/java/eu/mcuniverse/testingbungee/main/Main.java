package eu.mcuniverse.testingbungee.main;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin {

	@Override
	public void onEnable() {
		getLogger().info("[Testing] Plugin enabled!");

		getProxy().getPluginManager().registerListener(this, new MessagingListener());
	}

}
