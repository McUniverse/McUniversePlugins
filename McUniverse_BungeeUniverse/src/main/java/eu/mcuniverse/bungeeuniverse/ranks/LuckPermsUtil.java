package eu.mcuniverse.bungeeuniverse.ranks;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NonNull;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.query.QueryOptions;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class LuckPermsUtil {

	private static LuckPermsUtil instance;

	@Getter
	private static LuckPerms luckPermsApi;

	private LuckPermsUtil() {
//		ProxyServer.getInstance().getScheduler().schedule(Core.getInstance(), new Runnable() {
//			
//			@Override
//			public void run() {
				luckPermsApi = LuckPermsProvider.get();
//			}
//		}, 1, TimeUnit.SECONDS);
	}

	public static LuckPermsUtil getInstance() {
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
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uuid);
		User user = getLuckPermsApi().getUserManager().getUser(uuid);
		QueryOptions queryOptions = getLuckPermsApi().getContextManager().getQueryOptions(player);
		CachedMetaData metaData = user.getCachedData().getMetaData(queryOptions);
		return metaData.getPrefix();
	}

	/**
	 * Get the group of a player.</br>
	 * <b>Important</b> it only returns <b>one</b> group or default!
	 * 
	 * @param uuid
	 * @return
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
