import org.testng.Assert;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		
		JsonPath js=new JsonPath(Payload.coursePrice());
		
		//1. Print No of courses returned by API
		
		int count=js.getInt("courses.size()");
		System.out.println(count);
		
		//2.Print Purchase Amount
		
		int amount=js.getInt("dashboard.purchaseAmount");
		System.out.println(amount);
		
		//3. Print Title of the first course
		
		String tittle=js.get("courses[0].title");
		System.out.println(tittle);
		
		
		//4. Print All course titles and their respective Prices
		
		for(int i=0;i<count;i++) {
			String coursetittle=js.get("courses["+i+"].title");
			System.out.println(coursetittle);
			
//			System.out.println(js.get("courses["+i+"].price").toString());
			
	//		OR
			int a=js.getInt("courses["+i+"].price");
			System.out.println(a);
		}
		
     //	5. Print no of copies sold by RPA Course
			System.out.println("Number of copies of RPA:-");
			for(int i=0;i<count;i++) {
				String tittlecourse=js.get("courses["+i+"].title");
				if(tittlecourse.equalsIgnoreCase("RPA")) {
					int numberofcopies=js.get("courses["+i+"].copies");
					System.out.println(numberofcopies);
				}
				
						
				
			}
			int sum=0;
			int sum1=0;
			
			//6. Verify if Sum of all Course prices matches with Purchase Amount
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
	
//}
