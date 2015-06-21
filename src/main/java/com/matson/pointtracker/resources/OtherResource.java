package com.matson.pointtracker.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/other")
public class OtherResource {
    
    @GET
    public Response getResponse(@QueryParam("parameter") String param)
    {
        return Response.ok("Your parameter was: " + param).build();
    }
}
