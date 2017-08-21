package com.stc.sqm.hajj;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

@Path("/util")
public class UtilizationApi {


	@Path("/getall")
    @GET
    @Produces("application/json")
    public Response getUtilAll() {
    	
		DatabaseAccess dbAccess = new DatabaseAccess();
    	HashMap<String, Utilization> utilHash = dbAccess.getUtilAll();
    	
    	List<UtilReadings> utilRList = dbAccess.getUtilReadingsAll();
    	
    	for (int i = 0; i < utilRList.size(); i++) {
    		
    		UtilReadings ur = utilRList.get(i);
    		String key = ur.getCircuit();
    		
    		Utilization u = utilHash.get(key);
    		if (u != null) {
    			
    			List urList = u.getUtilReadings();
    			
    			if (urList == null) {
    				urList = new ArrayList<UtilReadings>();
    				//System.out.println("New UR List Created.");
    			}
    			
    			urList.add(ur);
    			u.setUtilReadings(urList);
    		}
    		
    		//System.out.println("Key (" + i + ") --> " + key);
    		
    	}
    	
   	
    	//List<Utilization> utilList = (List<Utilization>) utilHash.values();
    	
    	//Set<String> keys = utilHash.keySet();
    	
    	List<JSONObject> jsonList = new ArrayList<JSONObject>();
    	
    	for (String key: utilHash.keySet()) {
    		Utilization u = utilHash.get(key);
    		    		
    		JSONObject jsonObject = new JSONObject();
    		
    		if (u.getUtilReadings() != null && (u.getAvgUtilIn()!=null || u.getAvgUtilOut()!=null || 
    				u.getMaxUtilIn() !=null || u.getMaxUtilOut()!=null)) {
    			
    			jsonObject.put("vpnlinkname", u.getCircuit());
	    		jsonObject.put("availability", u.getAvailability());
	    		jsonObject.put("avgErrIn", u.getAvgErrorIn());
	    		jsonObject.put("avgErrOut", u.getAvgErrorOut());
	    		jsonObject.put("avgUtilIn", u.getAvgUtilIn());
	    		jsonObject.put("avgUtilOut", u.getAvgUtilOut());
	    		jsonObject.put("bandwidth", u.getConfiguredBandwidth());
	    		jsonObject.put("customer", u.getCustomer());
	    		jsonObject.put("hajj", u.getHajj());
	    		jsonObject.put("hajjvipstatus", u.getIsHajjVip());
	    		jsonObject.put("kpi_date", u.getKpiDate());
	    		jsonObject.put("maxUtilIn", u.getMaxUtilIn());
	    		jsonObject.put("maxUtilInDate", u.getMaxUtilInDate());
	    		jsonObject.put("maxUtilOut", u.getAvgUtilOut());
	    		jsonObject.put("maxUtilOutDate", u.getMaxUtilOutDate());
	    		
	    		jsonObject.put("reading", new JSONArray(u.getUtilReadings()));
	    		
	    		jsonList.add(jsonObject);
    		}
    		
    	}	
    	
    	//JSONObject jsonResponse = new JSONObject();
    	//jsonResponse.put("Utilization", jsonList);
 
        //jsonObject.put("UtilizationList", new JSONArray(utilList));  

        String result = jsonList.toString();
        
        return Response.status(200).entity(result).build();
    }
	
	
}
