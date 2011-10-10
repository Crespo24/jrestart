package com.example.jrestart;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.testng.Assert;
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

    public static final String URL = "http://localhost:8090/rest/employees";

    public static final String HELLO_URL = URL + "/hello";
    public static final String ECHO_URL = URL + "/echo/HelloWorld";
    public static final String LIST_EMPLOYEES_URL = URL + "/employees";
    public static final String GET_EMPLOYEE_URL = URL + "/employee/1";
    public static final String LIST_EMPLOYEES_JSON_URL = URL + "/json/employees";
    public static final String GET_EMPLOYEE_JSON_URL = URL + "/json/employee/1";


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

    private void getRequest(String url, String mimeType) {
        DefaultHttpClient httpClient = null;
        try {
            httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);
            getRequest.addHeader("accept", mimeType);
            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
                Assert.fail();
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));

            String output;
            logger.info("Response for URL: "+url);
            while ((output = br.readLine()) != null) {
                logger.info(output);
            }
            logger.info("\n");
        } catch (ClientProtocolException e) {
            logger.error("An exception has occurred", e);
            Assert.fail();
        } catch (IOException e) {
            logger.error("An exception has occurred", e);
            Assert.fail();
        } finally {
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    @Test
    public void testHello() {
        getRequest(HELLO_URL, "text/plain");
    }

    @Test
    public void testEcho() {
        getRequest(ECHO_URL, "text/plain");
    }

    @Test
    public void testListEmployees() {
        getRequest(LIST_EMPLOYEES_URL, "application/xml");
    }

    @Test
    public void testGetEmployee() {
        getRequest(GET_EMPLOYEE_URL, "application/xml");
    }

    @Test
    public void testListEmployeesJSON() {
        getRequest(LIST_EMPLOYEES_JSON_URL, "application/json");
    }

    @Test
    public void testGetEmployeeJSON() {
        getRequest(GET_EMPLOYEE_JSON_URL, "application/json");
    }

}
