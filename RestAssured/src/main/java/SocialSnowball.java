import io.restassured.RestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.testng.Assert;
import static io.restassured.RestAssured.*;

public class SocialSnowball {
	public static void main(String[] args) {
		
		RestAssured.baseURI="https://qa.socialsnowball.io/nova";
		SessionFilter session=new SessionFilter();
		String response=given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{\r\n"
				+ "    \"email\": \"bhupendrabhagat1212+12@gmail.com\",\r\n"
				+ "    \"password\": \"Welcome@1212\"\r\n"
				+ "}").log().all().filter(session).when().post("//api/login").
				then().assertThat().statusCode(200).extract().response().asString();
	}

}
