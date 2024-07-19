package pojo;

import static io.restassured.RestAssured.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuth2OfPojo {
	 static String[] courseTitle= {"Selenium Webdriver Java","Cypress","Protractor"};
	public static void main(String[] args) {
		String Url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AbUR2VOy3PPKcyBwuF9kpJAqClC_G1W7xD4nXn30pW8FHFpd0fPz6m-_9B13lTA95I86Fg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialcode = Url.split("code=")[1];
		System.out.println(partialcode);

		String acessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", partialcode)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(acessTokenResponse);
		String acesstoken = js.getString("access_token");

		getCourse gc = given().queryParam("access_token", acesstoken).expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(getCourse.class);

		// For get linked String
		System.out.println(gc.getLinkedIn());

		// for get instructor
		System.out.println(gc.getInstructor());

		// for get SoupUI Tittle

		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		// For price of SoupUI

		List<Api> apiCourse = gc.getCourses().getApi();
		for (int i = 0; i < apiCourse.size(); i++) {
			if (apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(apiCourse.get(i).getPrice());

			}

		}
		

		List<WebAutomation> webAutomation = gc.getCourses().getWebAutomation();
		ArrayList<String> a=new ArrayList<String>();  //Convert in array List because if any element add in array show exception in array list automatically add
		
		for (int i = 0; i < webAutomation.size(); i++) {
			// Print all the courseTitle of WebAutomation
			System.out.println(webAutomation.get(i).getCourseTitle());
			
			//Validate the courseTitle of WebAutomation is equal or not
			
			a.add(webAutomation.get(i).getCourseTitle()); //add course tittle in arraylist
				
		}
		List<String> expectedList=Arrays.asList(courseTitle); //expected array converted into the arraylist because it easy to validate
		Assert.assertTrue(a.equals(expectedList));  //Validate actual vs expected string of course title in webAutomation
		
		

		
	}

}
