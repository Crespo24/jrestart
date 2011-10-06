package com.example.jrestart;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

@Ignore
public class EmbeddedServerTest {

	private static final int DEFAULT_HTTP_PORT = 8090;
	private static final String WAR_LOCATION = "src/main/webapp";
	private static Logger logger = Logger.getLogger(EmbeddedServerTest.class);

	public static void main(String[] args) {
		try {
			Server server = new Server(DEFAULT_HTTP_PORT);

			WebAppContext webAppContext = new WebAppContext();
			webAppContext.setContextPath("/");
			webAppContext.setWar(WAR_LOCATION);

			webAppContext.setServer(server);
			server.setHandler(webAppContext);
			server.start();
		}
        catch (Exception e) {
			logger.error("Error when starting", e);
		}
	}
}
