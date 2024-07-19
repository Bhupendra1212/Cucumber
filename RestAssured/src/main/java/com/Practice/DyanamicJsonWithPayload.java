package com.Practice;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DyanamicJsonWithPayload {

	@Test(dataProvider ="Bookdata")
	public void AddBookAPIs(String isbn, String aisle) {

		RestAssured.baseURI ="https://rahulshettyacademy.com";

		String response = given().log().all().header("Content-Type", "application/json")
				.body(Payload.AddBook(isbn, aisle)).when().post("/Library/Addbook.php").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String id = js.getString("ID");
		System.out.println(id);

		String deltemsg=given().log().all().header("Content-Type", "application/json").body(id)
		.when().delete("/Library/DeleteBook.php").then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println(deltemsg);

	}

	@DataProvider(name ="Bookdata")
	public Object[][] getdata() {

		return new Object[][] { {"dffg","234"}, {"dggffg","24534"}, {"dfgfffg","133234"}, {"dffgfggh","223434"}};

	}

}
