package eu.mcuniverse.universeapi.luckperms;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import eu.mcuniverse.universeapi.java.exception.DependencyNotFoundException;
import eu.mcuniverse.universeapi.main.APIMain;
import lombok.Getter;
import lombok.NonNull;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;

public class LuckPermsUtil {

	private static LuckPermsUtil instance;

	@Getter
	private static LuckPerms luckPermsApi;

	private LuckPermsUtil() {
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

		luckPermsApi = provider.getProvider();
	}

	public static LuckPermsUtil getInstance() {
		if (!APIMain.getPlugins().contains("LuckPerms")) {
			throw new DependencyNotFoundException("LuckPerms");
		}
		if (instance == null) {
			instance = new LuckPermsUtil();
		}
		return instance;
	}

	/**
	 * Get the prefix of a player
	 * 
	 * @param uuid The UUID of the palyer
	 * @return The prefix
	 */
	public String getPrefix(@NonNull UUID uuid) {
		Player player = Bukkit.getPlayer(uuid);
		Validate.isTrue(player != null, "Player is offline! UUID: " + uuid.toString());
		User user = getLuckPermsApi().getUserManager().getUser(uuid);
		QueryOptions queryOptions = getLuckPermsApi().getContextManager().getQueryOptions(player);
		CachedMetaData metaData = user.getCachedData().getMetaData(queryOptions);
		return metaData.getPrefix();
	}

	/**
	 * Get the group of a player.<br>
	 * <b>Important</b> it only returns <b>one</b> group or default!
	 * 
	 * @param uuid UUID of the player
	 * @return The group of the player
	 */
	public String getGroup(@NonNull UUID uuid) {
		User user = getLuckPermsApi().getUserManager().getUser(uuid);
		List<String> groups = user.getNodes().stream().filter(NodeType.INHERITANCE::matches).map(NodeType.INHERITANCE::cast)
				.map(InheritanceNode::getGroupName).collect(Collectors.toList());

		if (groups.size() == 0) {
			throw new UnsupportedOperationException("This user has no inherited groups! UUID: " + uuid.toString());
		} else if (groups.size() == 1) {
			return groups.get(0);
		} else if (groups.size() == 2) {
			if (groups.contains("default")) {
				groups.remove("default");
				return groups.get(0);
			} else {
				throw new UnsupportedOperationException("This user has more than 1 group! UUID " + uuid.toString());
			}
		}
		return null;
	}

}
