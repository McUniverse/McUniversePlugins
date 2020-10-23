package eu.mcuniverse.testing.Dimension;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.CraftChunk;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_15_R1.PacketPlayOutMapChunk;
import net.minecraft.server.v1_15_R1.PacketPlayOutUnloadChunk;

public class DimensionChanger implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Not a player");
			return true;
		}
		
		Player p = (Player) sender;
		
		try {
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("stoprain")) {
					
					p.getWorld().setBiome(p.getLocation().getBlockX(), p.getLocation().getBlockZ(), Biome.DESERT);
						
					Chunk chunk = p.getWorld().getChunkAt(p.getLocation());
					net.minecraft.server.v1_15_R1.Chunk nmsChunk = ((CraftChunk) chunk).getHandle();
					PacketPlayOutUnloadChunk unload = new PacketPlayOutUnloadChunk(chunk.getX(), chunk.getZ());
					PacketPlayOutMapChunk load = new PacketPlayOutMapChunk(nmsChunk, 65535);

					((CraftPlayer) p).getHandle().playerConnection.sendPacket(unload);
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(load);
					
					return true;
				}
				Float value = Float.valueOf(args[0]);
				PacketPlayOutGameStateChange changeValue = new PacketPlayOutGameStateChange(7, value);
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(changeValue);
				
				if (args.length == 2) {
					Float time = Float.valueOf(args[1]);
					PacketPlayOutGameStateChange changeTime = new PacketPlayOutGameStateChange(8, time);
					((CraftPlayer) p).getHandle().playerConnection.sendPacket(changeTime);;
				}

				p.sendMessage(ChatColor.GREEN + "Changed");
			} else {
				p.sendMessage(ChatColor.RED + "/dimension <fade_value> [fade_time] ");
			}
		} catch (Exception e) {
			p.sendMessage(ChatColor.RED + "Error: " + e.getMessage());
		}
		
		return true;
	}

	public void changeSky(Player player, float value, float time) {
		PacketPlayOutGameStateChange changeValue = new PacketPlayOutGameStateChange(7, value); // Set Fade value
		PacketPlayOutGameStateChange changeTime = new PacketPlayOutGameStateChange(8, time); // Set Fade time
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(changeValue);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(changeTime);
	}
	
}
