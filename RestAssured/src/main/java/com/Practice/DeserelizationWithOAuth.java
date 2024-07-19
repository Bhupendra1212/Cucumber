package com.Practice;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;

import com.PracticePojo.WebAutomation;
import com.PracticePojo.api;
import com.PracticePojo.deserlizationPojo;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class DeserelizationWithOAuth {
	static String[] courseTitle = { "Selenium Webdriver Java", "Cypress", "Protractor" };

	public static void main(String[] args) {
		String Url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AZEOvhUYN4EWgO2j2g0cbcQ0FZhRAVKz5O_67EyXpnQntdQ5Rp_aB7c0SXhKLKIIgsXShw";
		String partialcode = Url.split("code=")[1];
		System.out.println(partialcode);

		String accessToken = given().urlEncodingEnabled(false).log().all().queryParam("code", partialcode)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(accessToken);
		String access_token = js.getString("access_token");
		System.out.println(access_token);

		deserlizationPojo course = given().queryParam("access_token", access_token).expect().defaultParser(Parser.JSON)
				.when().get("https://rahulshettyacademy.com/getCourse.php").as(deserlizationPojo.class);

		System.out.println(course.getInstructor());
		System.out.println(course.getExpertise());
		System.out.println(course.getLinkedIn());
		System.out.println(course.getServices());
		System.out.println(course.getUrl());

		System.out.println(course.getCourses().getApi().get(1).getCourseTitle());

		// Price of soap APIs

		List<api> soapApiprice = course.getCourses().getApi();
		for (int i = 0; i < soapApiprice.size(); i++) {
			if (soapApiprice.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(soapApiprice.get(i).getPrice());
			}
		}
		// Validate the courseTitle of WebAutomation is equal or not
		List<WebAutomation> WebAutomation = course.getCourses().getWebAutomation();
		ArrayList<String> a = new ArrayList<String>();

		// Print all the WebAutomation course title
		for (int i = 0; i < WebAutomation.size(); i++) {
			String WebAutomationCourseTittle = WebAutomation.get(i).getCourseTitle();
			System.out.println(WebAutomationCourseTittle);
			a.add(WebAutomation.get(i).getCourseTitle());
		}
		List<String> expectedList = Arrays.asList(courseTitle);
		Assert.assertTrue(a.equals(expectedList));
		
		
	}

}
