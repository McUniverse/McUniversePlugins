package eu.mcuniverse.testing.packet;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

public class CustomHandler extends ChannelDuplexHandler {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object packet) throws Exception {
//		if (packet instanceof PacketPlayInFlying) {
//		} else
//			Bukkit.getServer().getConsoleSender()
//					.sendMessage(ChatColor.YELLOW + "PACKET READ: " + ChatColor.RED + packet.toString());
		super.channelRead(ctx, packet);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object packet, ChannelPromise promise) throws Exception {
		if (!packet.toString().contains("play")) {
			
		}
//		if (packet.toString().toLowerCase().contains("entity") || packet instanceof PacketPlayOutUpdateTime
//				|| packet instanceof PacketPlayOutNamedSoundEffect) {
//		} else
//			Bukkit.getServer().getConsoleSender()
//					.sendMessage(ChatColor.AQUA + "PACKET WRITE: " + ChatColor.GREEN + packet.toString());
//		if (packet instanceof PacketPlayOutGameStateChange) {
//			PacketPlayOutGameStateChange change = (PacketPlayOutGameStateChange) packet;
//			Field[] fields = change.getClass().getDeclaredFields();
//			for (Field field : fields) {
//				if (!field.getName().equals("a"))
//					continue;
//				field.setAccessible(true);
//				Object value = field.get(change);
//				System.out.println(field.getName() + " --- " + value);
//			}
//		}
		super.write(ctx, packet, promise);
	}

}
