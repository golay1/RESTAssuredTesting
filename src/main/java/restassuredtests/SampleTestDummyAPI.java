package restassuredtests;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/*
REST Assured testing of GET and POST requests to public API: https://dummy.restapiexample.com
Java Version 1.8; JUnit 4

JSON response:
{
"status": "success",
"data": [
	{
	"id": "1",
	"employee_name": "Tiger Nixon",
	"employee_salary": "320800",
	"employee_age": "61",
	"profile_image": ""
	},
	....
	]
}
*/

public class SampleTestDummyAPI {
	
	final static String url = "https://dummy.restapiexample.com";
	
	//GET All sometimes fails due to too many attempts on website. Try again if fails.
	@Test
	public void testGetAll() {
		
		RestAssured.baseURI = url + "/api/v1/employees";
		
		RequestSpecification request = RestAssured.given();
		request.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON);
		
		//Get and parse employee data from public site
		Response response = request.get();
	    JSONObject obj = new JSONObject(response.asString());    
	    //System.out.println(obj);
	    
	    // get and verify response status
	    String status = obj.getString("status");
	    Assert.assertEquals(status, "success");
	    
	    // get Array type
	    JSONArray results = obj.getJSONArray("data");
	    
	    for(int n = 0; n < results.length(); n++){
	
	    	int id = results.getJSONObject(n).getInt("id");
	    	String name = results.getJSONObject(n).getString("employee_name");
	    	int salary = results.getJSONObject(n).getInt("employee_salary");
	    	
	    	System.out.println("Id: " + id + ", Employee Name: " + name + " , salary: " + salary);
	    	
	    	if(id == 1){
	    		Assert.assertEquals(name, "Tiger Nixon");
	    		Assert.assertEquals(salary, 320800);
	    	}
	    	if(id == 11){
	    		Assert.assertEquals(name, "Jena Gaines");
	    		Assert.assertEquals(salary, 90560);
	    	}
	    	if(id == 24){
	    		Assert.assertEquals(name, "Doris Wilder");
	    		Assert.assertEquals(salary, 85600);
	    	}
	
	    }       
	
	}

	@Test
	public void testGetById() {
		
		RestAssured.baseURI = url + "/api/v1/employee/11";
		
		RequestSpecification request = RestAssured.given();
		request.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON);

		Response response = request.get();
	    JSONObject obj = new JSONObject(response.asString());
	    
	    //get and verify response status and data
	    String status = obj.getString("status");
	    Assert.assertEquals(status, "success");
	    	
	    String name = obj.getJSONObject("data").getString("employee_name");
    	Assert.assertEquals(name, "Jena Gaines");

    	int salary = obj.getJSONObject("data").getInt("employee_salary");
    	Assert.assertEquals(salary, 90560);
    		
	    System.out.println("Get by Id success: Employee Name: " + name + " , salary: " + salary);
	}
	
	@Test
	public void testPost(){
		RestAssured.baseURI = url + "/api/v1/create";
		RequestSpecification request = RestAssured.given();
		
		//create user
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "test"); 
		requestParams.put("salary", 123);
		requestParams.put("age", 23); 
		
		request.body(requestParams.toString());
		Response response = request.post();
		//System.out.println(response.asString());
	    JSONObject obj = new JSONObject(response.asString());
	    
	    // get and verify response status
	    String status = obj.getString("status");
	    Assert.assertEquals(status, "success");
	    
		//verify response code
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		//returns id and create time of user
		System.out.println("POST-Create successful: " + obj);
	}
}