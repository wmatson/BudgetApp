package com.matson.pointtracker.resources;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("other")
public class OtherResource {
    
    public Response getResponse()
    {
        return Response.ok("other hello").build();
    }
}
