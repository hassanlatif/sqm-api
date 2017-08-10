package com.stc.sqm.hajj;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/todo")
public class ToDoApi {

	@Path("{creatorUid}")
    @GET
    @Produces("application/json")
    public Response getToDo(@PathParam("creatorUid") String creatorUid) {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	List<ToDo> toDoList = dbAccess.getToDoList(creatorUid);
    	JSONObject jsonObject = new JSONObject();
 
        jsonObject.put("creatorUid", creatorUid);
        jsonObject.put("TodoList", new JSONArray(toDoList));  

        String result = jsonObject.toString();
        
        return Response.status(200).entity(result).build();
    }
	
	@Path("/post")
    @POST
    @Consumes("application/json")
    public Response insertToDo(String data) {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	JSONObject jsonObject = new JSONObject(data);
    	
        String result = jsonObject.getString("creatorUid");
        
        return Response.status(201).entity(result).build();
    }
    
    
}
