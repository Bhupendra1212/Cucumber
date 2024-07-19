package com.Practice;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;

public class BasicCode {
	public static void main(String[] args) {

		// Add PLaceid

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(Payload.AddPlayload()).when().post("/maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response()
				.asString();

		System.out.println(response);

		JsonPath js = new JsonPath(response);

		// getplace id

		String placeid = js.getString("place_id");
		System.out.println(placeid);

		// update the data

		String newAdress = "176 industrial area Chandigarh";
		given().log().all().queryParam("key", "qaclick123").queryParam("placeid", placeid)
				.body("{\r\n" + "\"place_id\":\"" + placeid + "\",\r\n" + "\"address\":\"" + newAdress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("/maps/api/place/update/json").then().log().all().assertThat().statusCode(200);

		// Get place

		given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid).when()
				.get("/maps/api/place/get/json").then().log().all().assertThat().statusCode(200);
		
		//Delete place
		
		given().log().all().queryParam("key","qaclick123").body("{\r\n"
				+ "    \"place_id\":\""+placeid+"\"\r\n"
				+ "}").when().delete("/maps/api/place/delete/json").then().log().all()
		        .assertThat().statusCode(200);
	}
}