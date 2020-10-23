package eu.mcuniverse.system.commands;

import eu.mcuniverse.system.Bungee;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class CMD_Adminchat extends Command {
	
	public CMD_Adminchat() {
		super("CMD_Adminchat", "", new String[] {"adminchat", "ac"});
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
	  if(sender instanceof ProxiedPlayer) {
		  ProxiedPlayer p = (ProxiedPlayer)sender;
		if(p.hasPermission("mcuniverse.adminchat")) {
		   if(args.length == 0) {
			   p.sendMessage(Bungee.adminchat + "§cUse: /ac <Message>");

		   } else {
	            StringBuilder sb = new StringBuilder();
	            int i = 0;
	            while (i < args.length) {
	                sb.append(String.valueOf(args[i]) + " ");
	                ++i;
	            }
	            String st = sb.toString();
				for(ProxiedPlayer all : Bungee.instance.getProxy().getPlayers()) {
					if(all.hasPermission("mcuniverse.adminchat")) {
					all.sendMessage(Bungee.adminchat + p.getName() + " §8» §9" + st.replace("&", "§"));
					}
					System.out.println(Bungee.adminchat + p.getName() + " §8» §9" + st.replace("&", "§"));
			}
	            
			}
		   } else {
			   p.sendMessage(Bungee.perms);
		   }
	  } else {
		  sender.sendMessage(Bungee.adminchat + "§cNo Player");
	  }
		
	}

}