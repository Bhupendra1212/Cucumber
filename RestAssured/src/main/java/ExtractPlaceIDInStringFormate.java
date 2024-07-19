import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import Files.Payload;

//Add place--->update place with new address-->Get place to validate if new address is present in response


public class ExtractPlaceIDInStringFormate {
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		//Add placeid
		
		String Responce=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").body(Payload.addPlace())
		.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).body("scope",equalTo("APP")).
		header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(Responce);  //Responce print in the string formate
		
		JsonPath js=new JsonPath(Responce); //for parsing json
		String placeId=js.get("place_id");
		
		System.out.println(placeId);
		
		//update place with new address
		String newAddress="Purvarang Pune";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json").
		body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").
		
		when().put("/maps/api/place/update/json").then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated"));
	
		
		//Get place to validate if new address is present in response
		
//		given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId)
//	.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).body("address",equalTo("Purvarang Pune"));
//		
		//OR
		
		String getplaceresponce=given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId)
		.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1=new JsonPath(getplaceresponce);
		String actualaddress= js1.getString("address");
		System.out.println(actualaddress);
		Assert.assertEquals(actualaddress, newAddress);
		
	
	}
	
	

}
