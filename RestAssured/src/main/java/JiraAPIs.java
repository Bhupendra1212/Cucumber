import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.File;

import org.testng.Assert;

public class JiraAPIs {
	public static void main(String[] args) {
		
		RestAssured.baseURI="http://localhost:8080";
		
		//Login Scenario
		
		SessionFilter session=new SessionFilter();
		String response=given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \r\n"
				+ "    \"username\": \"bhupendrabhagat1212\",\r\n"
				+ "    \"password\": \"Welcome@1212\"\r\n"
				+ "    }").log().all().filter(session).when().post("/rest/auth/1/session").
				then().extract().response().asString();
		
		//Create issue in jira
//		
//		given().log().all().header("Content-Type","application/json")
//		.body("{\r\n"
//				+ "    \"fields\": {\r\n"
//				+ "        \"project\": {\r\n"
//				+ "            \"key\": \"AP\"\r\n"
//				+ "        },\r\n"
//				+ "        \"summary\": \"Creating a issue\",\r\n"
//				+ "        \"description\":\"Creating a issue using project key\",\r\n"
//				+ "        \"issuetype\": {\r\n"
//				+ "            \"name\": \"Bug\"\r\n"
//				+ "        }\r\n"
//				+ "    }\r\n"
//				+ "}").log().all().filter(session).when().post("/rest/api/2/issue").then().log().all()
//		.assertThat().statusCode(201);
////		
		//Comment on the issue in the jira
		String ExpectedMsg="Hi how are you?";
		String addcommentresponce=given().pathParam("key","10008").log().all().header("Content-Type","application/json")
		.body("{\r\n"
				+ "    \"body\": \""+ExpectedMsg+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all()
		.assertThat().extract().response().asString();
		
		JsonPath js=new JsonPath(addcommentresponce);
		String commentId=js.getString("id");
		
		
		//Add Attached in the jira
		
//		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("key", "10008")
//		.header("Content-Type","multipart/form-data").multiPart("file",new File("jira.txt"))
//		.when().post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
		
		
		//Get the issues
		//query parameter if we want to specific field 
		
		String issueDetails=given().filter(session).pathParam("key","10008").log().all().queryParam("fields","comment")
		.when().get("/rest/api/2/issue/{key}").then().log().all()
		.assertThat().extract().response().asString();
		System.out.println(issueDetails);
		
		JsonPath js1= new JsonPath(issueDetails);
		int commentcount=js1.getInt("fields.comment.comments.size()");
		for(int i=0;i<commentcount;i++) {
			String commentIdIssue=js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId)) {
				String message=js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(message, ExpectedMsg);
			}
		}
		
		
	}

}
