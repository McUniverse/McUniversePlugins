package eu.mcuniverse.testing.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.mojang.authlib.GameProfile;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.EnumMoveType;
import net.minecraft.server.v1_15_R1.MinecraftServer;
import net.minecraft.server.v1_15_R1.PacketPlayOutEntity.PacketPlayOutRelEntityMove;
import net.minecraft.server.v1_15_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_15_R1.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_15_R1.PlayerConnection;
import net.minecraft.server.v1_15_R1.PlayerInteractManager;
import net.minecraft.server.v1_15_R1.Vec3D;
import net.minecraft.server.v1_15_R1.WorldServer;

public class NPCManager {

	public EntityPlayer createNPC(Player player, String npcName) {
		Location loc = player.getLocation();
		
		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld) player.getWorld()).getHandle();
		GameProfile gameProfile = new GameProfile(player.getUniqueId(), ChatColor.RED + npcName);
		
		EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile, new PlayerInteractManager(nmsWorld));
		Player npcPlayer = npc.getBukkitEntity().getPlayer();
		npcPlayer.setPlayerListName("");
		
		npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		
		Bukkit.getOnlinePlayers().forEach(p -> {
			PlayerConnection con = ((CraftPlayer) p).getHandle().playerConnection;
			con.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
			con.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
			con.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, npc));
		});
		
		return npc;
	}
	
	public void moveNPC(EntityPlayer npc, Location before, Location after) {
		double dX = before.getX() - after.getX();
		double dY = before.getY() - after.getY();
		double dZ = before.getZ() - after.getZ();
		double yaw = Math.atan2(dZ, dX);
		double pitch = Math.atan2(Math.sqrt(dZ * dZ * dX * dX), dY) * Math.PI;
		double x = Math.sin(pitch) * Math.cos(yaw);
		double y = Math.sin(pitch) * Math.sin(yaw);
		double z = Math.cos(pitch);
		
		Vector vector = new Vector(x, y, z);
		double speed = vector.length();
		npc.move(EnumMoveType.SELF, new Vec3D(x, y, z));
		
		PacketPlayOutRelEntityMove movePacket = new PacketPlayOutRelEntityMove(npc.getId(), (short) x, (short) y, (short) z, false);
		Bukkit.getOnlinePlayers().forEach(p -> {
			PlayerConnection con = ((CraftPlayer) p).getHandle().playerConnection;
			con.sendPacket(movePacket);
		});
	}
	
}
