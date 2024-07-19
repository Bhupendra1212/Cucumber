import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;
public class OAuth2 {
	public static void main(String[] args) {
		//Get Authorization code
		
		String url="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AbUR2VOw_xv76dF-HQ5h7oNTTWHVhcpkKAE4Hydw3FL3ZjJqbRQGhH6yEt8I6UtVr_JG1A&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialcode=url.split("code=")[1];
		System.out.println(partialcode);
		
		// Get access token request
		
		String accessTokenResponce=given().urlEncodingEnabled(false)
		.queryParams("code", partialcode)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js=new JsonPath(accessTokenResponce);
		String acesstoken=js.getString("access_token");
		
		
		//Get actual response
		
		String response=given().queryParam("access_token",acesstoken)
		.when().log().all()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(response);
	}
}
	
