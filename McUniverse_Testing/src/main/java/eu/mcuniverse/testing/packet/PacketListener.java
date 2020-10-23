package eu.mcuniverse.testing.packet;

import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelPipeline;

public class PacketListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		injectPlayer(e.getPlayer());
	}
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		removePlayer(e.getPlayer());
	}

	private void removePlayer(Player player) {
		Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(player.getName());
			return null;
		});
	}
	
	private void injectPlayer(Player player) {
		ChannelDuplexHandler channelDuplexHandler = new CustomHandler();
		
		ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
		pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
	}
	
}
