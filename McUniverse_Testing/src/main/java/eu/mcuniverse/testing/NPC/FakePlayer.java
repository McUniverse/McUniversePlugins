package eu.mcuniverse.testing.NPC;

import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_15_R1.ChatComponentText;
import net.minecraft.server.v1_15_R1.EntityPig;
import net.minecraft.server.v1_15_R1.EntityZombie;
import net.minecraft.server.v1_15_R1.PathfinderGoalNearestAttackableTarget;

public class FakePlayer extends EntityZombie {

	public FakePlayer(org.bukkit.World world) {
		super(((CraftWorld)world).getHandle());
		
		Zombie zombie = (Zombie) this.getBukkitEntity();
		
		this.setBaby(true);
		
		zombie.setHealth(50);
		
		this.setHealth(50);
		this.setCustomName(new ChatComponentText(ChatColor.GOLD + "Jay"));
		this.setCustomNameVisible(true);
		
		this.targetSelector.a(new PathfinderGoalNearestAttackableTarget<EntityPig>(this, EntityPig.class, true));
		
		this.getWorld().addEntity(this, SpawnReason.DEFAULT);
	}
	
	public static Player getPlayer() {
		return null;
	}
	
}
