import org.testng.Assert;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class VerifyAmount {
	JsonPath js=new JsonPath(Payload.coursePrice());
	//6. Verify if Sum of all Course prices matches with Purchase Amount
	
	@Test
	public void sumOfCourses() {
		int sum=0;
		int sum1=0;
		int count=js.getInt("courses.size()");
		for(int i=0;i<count;i++) {
			int price=js.get("courses["+i+"].price");
			
			int copies=js.get("courses["+i+"].copies");
			
			sum=price*copies;
			sum1=sum+sum1;
			
			
		}
		System.out.println(sum1);
		int purchaseamnt=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum1,purchaseamnt);
		}

	}


