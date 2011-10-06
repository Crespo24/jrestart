package com.example.jrestart.service;

import com.example.jrestart.data.Data;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/echoservice")
public class EchoService {

    @Context
    private ServletContext context;

    @GET
    @Path("/echo/{message}")
    @Produces({MediaType.APPLICATION_JSON, "application/*+json"})
    public Response echoService(@PathParam("message") String message) {
        Data data = new Data();
        data.setText(message);
        if (context == null) {
            System.out.println("Context is null");
        }
        else {
            System.out.println("Context is not null");
        }
        return Response.status(200).entity(data).build();
    }
}