package restassuredtests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestRestApiReqres {
	//tests API responses to GET and POST from this website:
	final static String url = "https://reqres.in";

	@Test
	public void testGetAll() {
		
		RestAssured.baseURI = url + "/api/users";
		
		RequestSpecification request = RestAssured.given();
		request.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON);
		
		//Get and parse employee data
		Response response = request.get();
	    JSONObject obj = new JSONObject(response.asString());
	    
	    //verify response code
		Response resp = RestAssured.get(url);
		int respCode = resp.getStatusCode();
		Assert.assertEquals(respCode, 200);
	    
	    //print first 6 users (first page)
	    JSONArray results = obj.getJSONArray("data");
	    for(int n = 0; n < results.length(); n++){
	
	    	int id = results.getJSONObject(n).getInt("id");
	    	String fname = results.getJSONObject(n).getString("first_name");
	    	String lname = results.getJSONObject(n).getString("last_name");
	    	String email = results.getJSONObject(n).getString("email");
	    	
	    	System.out.println("Id: " + id + ", Name: " + fname + lname + ", Email: " + email);
	    	
	    	//verify user id=7
	    	if(id == 7){
	    		Assert.assertEquals(fname, "Michael");
	    		Assert.assertEquals(lname, "Lawson");
	    		Assert.assertEquals(email, "michael.lawson@reqres.in");
	    	}
	    }       
	}

	@Test
	public void testGetById() {
		
		RestAssured.baseURI = url + "/api/users/2";
		
		RequestSpecification request = RestAssured.given();
		request.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON);
	
		Response response = request.get();
	    JSONObject obj = new JSONObject(response.asString());
	    
	    //verify response code and data
		Response resp = RestAssured.get(url);
		int respCode = resp.getStatusCode();
		Assert.assertEquals(respCode, 200);
	    
    	int id = obj.getJSONObject("data").getInt("id");
    	Assert.assertEquals(id, 2);
    		
	    String fname = obj.getJSONObject("data").getString("first_name");
    	Assert.assertEquals(fname, "Janet");
    		
	    String lname = obj.getJSONObject("data").getString("last_name");
    	Assert.assertEquals(lname, "Weaver");
    		
	    String email = obj.getJSONObject("data").getString("email");
    	Assert.assertEquals(email, "janet.weaver@reqres.in");
    		
	    System.out.println("GET by Id successful: Id: " + id + ", Name: " + fname + lname + ", Email: " + email);
	}
	
	@Test
	public void testPost(){
		RestAssured.baseURI = url + "/api/users";
		RequestSpecification request = RestAssured.given();
		
		//create user
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "morpheus"); 
		requestParams.put("job", "leader");
		
		request.body(requestParams.toString());
		Response response = request.post();
	    
		//verify response code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		
		//returns id and create time of user
	    JSONObject obj = new JSONObject(response.asString());
		System.out.println("POST-Create successful: " + obj);
	}
}