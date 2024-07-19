package stepDefinations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;

import io.cucumber.java.en.*;
import io.cucumber.junit.Cucumber;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resource.APIResources;
import resource.TestDataBuild;
import resource.Utils;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class stepDefination extends Utils {
	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
	static String place_id;
	TestDataBuild data = new TestDataBuild();

	@Given("Add place Payload with {string},{string},{string}")
	public void add_place_Payload_with(String name, String language,String address) throws IOException {

		res = given().spec(requestSpecification()).body(data.AddPlacePayload(name, language, address));

	}

	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource,String method) {

		// constructor will be called with value of resources which you pass
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

		if (method.equalsIgnoreCase("POST")) {
			response = res.when().post(resourceAPI.getResource());
		} else if (method.equalsIgnoreCase("GET")) {
			response = res.when().get(resourceAPI.getResource());
					
		}

	}

	@Then("the API call got success with status code {int}")
	public void the_API_call_got_success_with_status_code(Integer int1) throws Throwable {

		assertEquals(response.getStatusCode(),200);

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue,String ExpectedValue) throws Throwable {

		assertEquals(getJsonPath(response,keyValue),ExpectedValue);

	}

	@Then("verify Place_Id created maps to {string} using {string}")
	public void verify_Place_Id_created_maps_to_using(String expectedName,String resource) throws IOException {
		place_id =getJsonPath(response,"place_id");

		res = given().spec(requestSpecification()).queryParam("place_id",place_id);
		user_calls_with_http_request(resource,"GET");
		String actualName =getJsonPath(response,"name");
		
		assertEquals(actualName,expectedName);
	}
	
	@Given("Delete PlacePay load")
	public void delete_PlacePay_load() throws IOException {
	   res=given().spec(requestSpecification()).body(data.DeletePlacePayload(place_id));

	}
}
