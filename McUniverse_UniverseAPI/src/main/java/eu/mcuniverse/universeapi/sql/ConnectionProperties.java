package eu.mcuniverse.universeapi.sql;

import lombok.Builder;
import lombok.Data;

@Data
@Builder()
public class ConnectionProperties {

	@Builder.Default
	private final String subprotocol = "mysql";
	@Builder.Default
	private String parameters = "autoReconnect=true&useSSL=false&verifyServerCertificate=false";
	private final String host, database, username, password;
	private final int port;
	
	public String getURL() {
		return "jdbc:" + subprotocol + "://" + host + ":" + port + "/" + database + "?" + parameters;
		
	}
	
}
