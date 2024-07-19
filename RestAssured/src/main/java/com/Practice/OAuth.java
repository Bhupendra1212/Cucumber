package com.Practice;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class OAuth {
	public static void main(String[] args) {
		
		//get code
		 
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AZEOvhVZxe7_JrXEtnJ40yETDcRB58U9xg-mRCn14EQwkeLkgsQ1DlJ_ho8VUJF76FAYgA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialcode=url.split("code=")[1];
		System.out.println(partialcode);
		
		//get accesstoken
		
		String accessTokenRequest=given().urlEncodingEnabled(false).
				queryParam("code",partialcode).
				queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		       .queryParam("client_secret","erZOWM9g3UtwNRj340YYaK_W").queryParam("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
	           .queryParam("grant_type","authorization_code").when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js=new JsonPath(accessTokenRequest);
		String AccessToken=js.getString("access_token");
		System.out.println(AccessToken);
		
		//get courses actual response
		
		 String response=given().queryParam("access_token",AccessToken).when().log().all().get("https://rahulshettyacademy.com/getCourse.php")
		 .asString();
		 System.out.println(response);
		
	}

}
