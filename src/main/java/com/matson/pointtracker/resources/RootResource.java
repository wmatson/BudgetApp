package com.matson.pointtracker.resources;

import com.google.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("")
public class RootResource {

    @Inject
    public RootResource() {
    }

    @GET
    public Response getIndex() {
        return Response.ok("Hello World!").build();
    }

    @GET
    @Path("withParam")
    public Response getParam(@QueryParam("param") String param) {
        return Response.ok("You included: " + param).build();
    }
    
}
