package restassuredtests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

//REST Assured Testing with Query Parameters
public class TestUSPopulationData {

	@Test
    public void queryParameter() {
			
		RestAssured.baseURI ="https://datausa.io/api/"; 
		RequestSpecification request = RestAssured.given();
		
		Response response = request.queryParam("drilldowns", "Nation") 
				                   .queryParam("measures", "Population") 
				                   .get("/data");
		
		String jsonString = response.asString();
		Assert.assertEquals(jsonString.contains("United States"), true);
		
		int respCode = response.getStatusCode();
		Assert.assertEquals(respCode, 200);
		
	    JSONObject obj = new JSONObject(response.asString());
		JSONArray results = obj.getJSONArray("data");
		System.out.println("US population by year:");
	    for(int n = 0; n < results.length(); n++){
	
	    	int year = results.getJSONObject(n).getInt("Year");
	    	int pop = results.getJSONObject(n).getInt("Population");

	    	System.out.println("Year: " + year + ", Population: " +pop);
	    	
	    	if(year == 2019)
	    		Assert.assertEquals(pop, 328239523);
	    	if(year == 2013)
	    		Assert.assertEquals(pop, 316128839);
	    }
		
	}
}