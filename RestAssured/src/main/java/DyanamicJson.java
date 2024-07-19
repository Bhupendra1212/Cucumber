import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

//Parameterization of API test with multiple data

public class DyanamicJson {
	
	@Test(dataProvider="Bookdata")
	public void addBook(String isbn,String aisle) {
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().header("Content-Type","application/json").
				body(Payload.addBook(isbn,aisle))
		.when().post("/Library/Addbook.php").then().log().all()
         .assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js=new JsonPath(response);
		String id=js.get("ID");
		System.out.println(id);
	}
	@DataProvider(name="Bookdata")
	public Object[][] getData() {
		//Array collection of element
		//Multi dimensional array is the collection of array
		
		return new Object[][]{{"fgtrhfghth","432434"},{"ghfgggfrv","232131"},{"fvvvrgvdf","123323"}};
	

	}

}
