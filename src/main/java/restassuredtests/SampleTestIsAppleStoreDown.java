package restassuredtests;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class SampleTestIsAppleStoreDown {
	
	final static String url = "https://istheapplestoredown.com/api/v1/status/worldwide";
	final static String [] countries = { "ae", "at", "au", "be-fr", "be-nl", "br", "ca", 
						"ch-de", "ch-fr","cn","cz","de","dk","es", "fi", "fr", "hk", 
						"hk-zh", "hu", "id", "ie", "it", "jp", "kr", "lu", "mx", "my", 
						"nl","no", "nz", "ph", "pl", "pt", "ru", "se", "sg", "th",  
						"th-en", "tr", "tw", "uk", "us", "vn", "xf" };
	@Test
	public void testResponseCode(){
		// get and verify the response status code
		Response resp = RestAssured.get(url);
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 200);
	}

	@Test
	public void testCountryStatus(){
		
		Response resp = RestAssured.get( url );
		
		for (String country : countries){
			Map<String, String> map = resp.jsonPath().get(country);
			if (map.get("status").equals("n"))
				System.out.println("Store is up in: " + map.get("name" ));
			else if (map.get("status").equals("y"))
				System.out.println("Store is down in: " + map.get("name" ));
		}
	}
}