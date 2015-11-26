package com.matson.pointtracker.resources;

import com.google.inject.Inject;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

@Path("")
public class RootResource {

    @Inject
    public RootResource() {
    }

    @GET
    public void getIndex(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.html").forward(request, response);
    }

    @GET
    @Path("withParam")
    public Response getParam(@QueryParam("param") String param) {
        return Response.ok("You included: " + param).build();
    }
    
}
