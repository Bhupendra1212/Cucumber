package GraphQLQuery;
import static io.restassured.RestAssured.*;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class GraphQLScript {
	public static void main(String[] args) {
		
		//Query call
		int characterId=416;
		
		String Response=given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"query($characterId :Int! ,$episodeId:Int!) {\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    image\\n    id\\n  }\\n  location(locationId: 491) {\\n    id\\n    name\\n    dimension\\n    created\\n  }\\n  episode(episodeId: $episodeId) {\\n    name\\n    air_date\\n    id\\n  }\\n  characters(filters: {name: \\\"Bhup\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode:\\\"ManVsWild\\\"}) {\\n\\n    result {\\n      id\\n      name\\n      air_date\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":"+characterId+",\"episodeId\":500}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
	    .then().extract().response().asString();
		System.out.println(Response);
		
		JsonPath js=new JsonPath(Response);
		String charactername=js.getString("data.character.name");
		Assert.assertEquals(charactername, "Bhup");
		
		
		//Mutation
		
		String mutationResponse=given().log().all().header("Content-Type","application/json")
		.body("{\"query\":\"mutation($locationName:String!,$characterName:String!,$episodeName:String!){\\n  createLocation(location:{name:$locationName,type:\\\"HomeTown\\\",dimension:\\\"12455\\\"})\\n  {\\n    id\\n  }\\n  createCharacter(character:{name:$characterName,type:\\\"male\\\",status:\\\"alive\\\",species:\\\"fantacy\\\",gender:\\\"male\\\",image:\\\"png\\\",originId:16,locationId:16})\\n{\\n id \\n}\\n  createEpisode(episode:{name:$episodeName,air_date:\\\"12-10-2000\\\",episode:\\\"204\\\"})\\n    {\\n    id\\n  }\\n  deleteLocations(locationIds:[489]){\\n    locationsDeleted\\n  }\\n  }\",\"variables\":{\"locationName\":\"Mohali\",\"characterName\":\"Rahul\",\"episodeName\":\"Mirzapur\"}}")
	    .when().post("https://rahulshettyacademy.com/gq/graphql")
	    .then().extract().response().asString();
		
		System.out.println(mutationResponse);
		 
	} 

}
