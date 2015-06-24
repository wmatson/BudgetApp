package com.matson.pointtracker.resources;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.matson.pointtracker.dao.PointDao;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("")
public class RootResource {

    private final PointDao pointDao;

    @Inject
    public RootResource(PointDao dao) {
        pointDao = dao;
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

    @GET
    @Path("points")
    public Response getPoints() {
        Gson gson = new Gson();
        return Response.ok(gson.toJson(pointDao.getPoints())).build();
    }

}
