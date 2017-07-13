package com.stc.sqm.moda;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

//import org.apache.tomcat.jdbc.pool.DataSource;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/auth")
public class Authenticate {

    @GET
    @Produces("application/json")
    public Response authenticateUser() throws JSONException {
    	
    	DatabaseAccess dbAccess = new DatabaseAccess();
    	List<String> userRoles = dbAccess.getUserRole("hassan");
    	
    	for(String userRole: userRoles) {
    		System.out.println(userRole);
    	}
 
        StringBuilder sb = new StringBuilder();
        sb.append("ISTANBUL");
 
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("original", sb.toString());
        jsonObject.put("reversed", sb.reverse().toString());
 
        String result = "" + jsonObject;
        
        return Response.status(200).entity(result).build();
    }

    
}
