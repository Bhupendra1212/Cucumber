import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import Files.Payload;
public class Basic {
	public static void main(String[] args) {
		
		//Validate if add place API is working as per expected Validate scope and server 
		
		
		
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		given().log().all().queryParam("key" , "qaclick123").header("Content-Type","application/json")
	.body(Payload.addPlace()).when().post("/maps/api/place/add/json")
	.then().log().all().assertThat().statusCode(200).body("scope",equalTo("APP")).
	header("Server","Apache/2.4.52 (Ubuntu)");
		
		
		//Add place--->update place with new address-->Get place to validate if new address is present in response
		
		
	
	}

}
