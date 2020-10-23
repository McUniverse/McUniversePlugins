package eu.mcuniverse.testing.luckperms;

import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;

public class PermTestLuck {

	public void perms() {

		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

		LuckPerms api = provider.getProvider();

		Player p = Bukkit.getPlayer("JayReturns");
		User user = api.getUserManager().getUser(p.getUniqueId());

		
		List<String> groups = user.getNodes().stream()
        .filter(NodeType.INHERITANCE::matches)
        .map(NodeType.INHERITANCE::cast)
        .map(InheritanceNode::getGroupName)
        .collect(Collectors.toList());
		
		String userG = groups.get(0);
		
		
	}

	
	
}
