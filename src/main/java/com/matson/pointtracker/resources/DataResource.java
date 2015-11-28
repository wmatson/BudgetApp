package com.matson.pointtracker.resources;

import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.bson.Document;
import org.bson.types.ObjectId;

@Path("/data")
public class DataResource {

    private final MongoDatabase db;

    @Inject
    public DataResource(MongoDatabase database) {
        this.db = database;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollection(@QueryParam("collection") String collection) {
        return Response.ok(JSON.serialize(db.getCollection(collection).find())).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response upsertDoc(@FormParam("collection") String collection, @FormParam("toAdd") String json) {
        Document toAdd = Document.parse(json);
        if (!toAdd.containsKey("name")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("object must have a name").build();
        }
        UpdateResult result = db.getCollection(collection).replaceOne(fieldSearch("name", toAdd.get("name")), toAdd, new UpdateOptions().upsert(true));
        Document response = new Document();
        response.append("_id", result.getUpsertedId());
        return Response.ok(response.toJson()).build();
    }

    @POST
    @Path("push")
    public Response pushItem(@FormParam("collection") String collection, @FormParam("oid") String oid, @FormParam("field") String field, @FormParam("item") String itemJson) {
        Document item = Document.parse(itemJson);
        BasicDBObject pushUpdate = new BasicDBObject("$push", new BasicDBObject(field, item));
        db.getCollection(collection).findOneAndUpdate(fieldSearch("_id", new ObjectId(oid)), pushUpdate);
        return Response.ok().build();
    }
    
    @POST
    @Path("remove")
    public Response removeItem(@FormParam("collection") String collection, @FormParam("oid") String oid, @FormParam("field") String field, @FormParam("item") String itemJson) {
        BasicDBObject pushUpdate = new BasicDBObject("$pull", new BasicDBObject(field, JSON.parse(itemJson)));
        db.getCollection(collection).findOneAndUpdate(fieldSearch("_id", new ObjectId(oid)), pushUpdate);
        return Response.ok().build();
    }

    @DELETE
    public Response removeDoc(@QueryParam("collection") String collection, @QueryParam("oid") String oid) {
        BasicDBObject deleteQuery = fieldSearch("_id", new ObjectId(oid));
        db.getCollection(collection).deleteOne(deleteQuery);
        return Response.ok().build();
    }

    private static BasicDBObject fieldSearch(String field, Object value) {
        return new BasicDBObject(field, value);
    }
}
