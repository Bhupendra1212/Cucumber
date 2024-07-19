package com.Practice;

import static org.testng.Assert.assertEquals;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class NestedJson {
	
	public static void main(String[] args) {
		int sum=0;
		int sum1=0;
		JsonPath js = new JsonPath(Payload.CourseProfile());

		// 1. Print No of courses returned by API

		int NoOfcourse = js.getInt("courses.size()");
		System.out.println(NoOfcourse);

		// 2.Print Purchase Amount
		int amount = js.getInt("dashboard.purchaseAmount");
		System.out.println(amount);

		// 3. Print Title of the first course
		String FirstCourseTitle = js.getString("courses[0].title");
		System.out.println(FirstCourseTitle);

		// 4. Print All course titles and their respective Prices

		for (int i = 0; i < NoOfcourse; i++) {
			String alltitle = js.getString("courses[" + i + "].title");

			System.out.println("CourseTitle:-" + alltitle);
			int price = js.getInt("courses[" + i + "].price");
			System.out.println("Price:-" + price);

			// 5. Print no of copies sold by RPA Course

//			String RPACopies=js.getString("courses[2].copies");
//			System.out.println(RPACopies);
//			
//			   OR

			if (alltitle.equalsIgnoreCase("RPA")) {
				String RPACopies = js.getString("courses[" + i + "].copies");
				System.out.println(RPACopies);
			}
			
			//6. Verify if Sum of all Course prices matches with Purchase Amount
				int numOfCopies=js.getInt("courses[" + i + "].copies");
				sum=price*numOfCopies;
				sum1=sum+sum1;
				
		   

			}
		System.out.println(sum1);
		Assert.assertEquals(amount,sum1);
		
	}
}
