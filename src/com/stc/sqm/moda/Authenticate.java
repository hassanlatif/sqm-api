package com.stc.sqm.moda;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/auth")
public class Authenticate {

	@Path("{username}")
    @GET
    @Produces("application/json")
    public Response getUserRoles(@PathParam("username") String userName) {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	List<String> userRoles = dbAccess.getUserRole(userName);
    	JSONObject jsonObject = new JSONObject();
 
        jsonObject.put("userName", userName);
        jsonObject.put("roles", new JSONArray(userRoles));  

        String result = jsonObject.toString();
        
        return Response.status(200).entity(result).build();
    }
    
    @Path("{username}/{password}")
    @GET
    @Produces("application/json")
    public Response authenticateUser(@PathParam("username") String userName, 
    								 @PathParam("password") String password) {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	User user = dbAccess.getUser(userName, password);
    	JSONObject jsonObject = new JSONObject();
    	int status = 401;
    	
    	if (user!=null) {
            jsonObject.put("userName", user.getUserName());
            jsonObject.put("roles", new JSONArray(user.getRoles()));  
            jsonObject.put("authorized", true);
            status = 200;
    	}
    	else {
    		jsonObject.put("userName", userName);
    		jsonObject.put("authorized", false);
    	}
    	
    	String result = jsonObject.toString();
    	return Response.status(status).entity(result).build();    	
    }

    
}
