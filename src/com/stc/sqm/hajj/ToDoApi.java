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
    public Response getToDoByCreator(@PathParam("creatorUid") String creatorUid) {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	List<ToDo> toDoList = dbAccess.getToDoListByCreator(creatorUid);
    	JSONObject jsonObject = new JSONObject();
 
        jsonObject.put("creatorUid", creatorUid);
        jsonObject.put("TodoList", new JSONArray(toDoList));  

        String result = jsonObject.toString();
        
        return Response.status(200).entity(result).build();
    }
	
	@Path("/getall")
    @GET
    @Produces("application/json")
    public Response getToDoAll() {
    	
		DatabaseAccess dbAccess = new DatabaseAccess();
    	List<ToDo> toDoList = dbAccess.getToDoListAll();
    	JSONObject jsonObject = new JSONObject();
 
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
	
	@Path("/cleartask/{taskId}")
    @GET
    @Produces("application/json")
    public Response setToDoTaskAsCleared(@PathParam("taskId") int taskId) {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	    	
        int rowsUpdated = dbAccess.setToDoTaskAsCleared(taskId);
        
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("RowsUpdated", rowsUpdated);  

        String result = jsonObject.toString();
        
        return Response.status(200).entity(result).build();
    }
	
	@Path("/setstatus/{taskId}/{status}")
    @GET
    @Produces("application/json")
    public Response setToDoStatus(@PathParam("taskId") int taskId, @PathParam("status") String status) {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	    	
        int rowsUpdated = dbAccess.setToDoStatus(taskId, status);
        
        JSONObject jsonObject = new JSONObject();
        
        jsonObject.put("RowsUpdated", rowsUpdated);  

        String result = jsonObject.toString();
        
        return Response.status(200).entity(result).build();
    }
    
    
	@Path("/addtodo")
    @POST
    @Consumes("application/json")
    public Response addNewToDo(String data) {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	JSONObject jsonObject = new JSONObject(data);
    	
    	ToDo toDo = new ToDo();
    	
    	toDo.setTaskId(jsonObject.getInt("taskId"));
    	toDo.setCreatorUid(jsonObject.getString("creatorUid"));
    	toDo.setAssignedUid(jsonObject.getString("assignedUid"));
    	toDo.setAssignedShift(jsonObject.getString("assignedShift"));
    	toDo.setAssignedEmail(jsonObject.getString("assignedEmail"));
    	toDo.setAssignedMobile(jsonObject.getString("assignedMobile"));
    	toDo.setTaskDesc(jsonObject.getString("taskDesc"));
    	toDo.setStatus("PENDING");
    	toDo.setPriority(jsonObject.getInt("priority"));
    	
    	boolean result = dbAccess.addNewToDo(toDo);
        
        return Response.status(201).entity(result+"").build();
    }
	
}
