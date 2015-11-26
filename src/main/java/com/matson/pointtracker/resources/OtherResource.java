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
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;

@Path("/data")
public class OtherResource {
    
    private final MongoDatabase db;

    @Inject
    public OtherResource(MongoDatabase database) {
        this.db = database;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCollection(@QueryParam("collection") String collection)
    {
        return Response.ok(JSON.serialize(db.getCollection(collection).find())).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response upsertDoc(@FormParam("collection") String collection, @FormParam("toAdd") String json)
    {
        Document toAdd = Document.parse(json);
        UpdateResult result = db.getCollection(collection).replaceOne(new BasicDBObject("_id", toAdd.get("_id")), toAdd, new UpdateOptions().upsert(true));
        Document response = new Document();
        response.append("_id", result.getUpsertedId());
        return Response.ok(response.toJson()).build();
    }
    
    @DELETE
    public Response removeDoc(@QueryParam("collection") String collection, @QueryParam("oid") String oid)
    {
        BasicDBObject deleteQuery = new BasicDBObject("_id", new ObjectId(oid));
        db.getCollection(collection).deleteOne(deleteQuery);
        return Response.ok().build();
    }
}
