package com.example.jrestart;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EmbeddedServerTest {

    private static final int DEFAULT_HTTP_PORT = 8090;
    private static final String WAR_LOCATION = "src/main/webapp";
    private static Logger logger = Logger.getLogger(EmbeddedServerTest.class);

    private static Server server;

    @BeforeClass
    private void startServer() {
        try {
            server = new Server(DEFAULT_HTTP_PORT);

            WebAppContext webAppContext = new WebAppContext();
            webAppContext.setContextPath("/");
            webAppContext.setWar(WAR_LOCATION);

            webAppContext.setServer(server);
            server.setHandler(webAppContext);
            server.start();
        } catch (Exception e) {
            logger.error("Error starting server", e);
        }
    }

    @AfterClass
    private void stopServer() {
        try {
            server.stop();
        } catch (Exception e) {
            logger.error("Error stopping server", e);
        }
    }

    @Test
    public void testGet() {
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest =
                    new HttpGet("http://localhost:8090/rest/employees/json/employee/1");
            getRequest.addHeader("accept", "application/json");

            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            logger.info("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            httpClient.getConnectionManager().shutdown();

        } catch (ClientProtocolException e) {
            logger.error("An exception has occurred", e);

        } catch (IOException e) {
            logger.error("An exception has occurred", e);
        }

    }
}
