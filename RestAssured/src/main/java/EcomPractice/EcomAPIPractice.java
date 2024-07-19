package EcomPractice;


	import static io.restassured.RestAssured.given;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import io.restassured.builder.RequestSpecBuilder;
	import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoForEcom.loginResponse;
import pojoForEcom.order;

	public class EcomAPIPractice {
		public static void main(String[] args) {
			
			//Login the WebSite
			
			RequestSpecification LoginBase=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
					                   .setContentType(ContentType.JSON).build();
			
			LoginPagePojo login=new LoginPagePojo();
			login.setUserEmail("bhupendrabhagat1212@gmail.com");
			login.setUserPassword("Welcome@1212");
			
			RequestSpecification loginReq=given().log().all().spec(LoginBase).body(login);
			loginResponsePojo loginresponse=loginReq.when().post("/api/ecom/auth/login").
					then().log().all().extract().response().as(loginResponsePojo.class);
	

			System.out.println(loginresponse.getToken());
			String token=loginresponse.getToken();
			
			System.out.println(loginresponse.getUserId());
			String userId=loginresponse.getUserId();
			
			System.out.println(loginresponse.getMessage());
			
			
			//Create the product
			
			RequestSpecification createPrdBase=new RequestSpecBuilder().
					setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization",token).build();
			
			RequestSpecification createPrdReq=given().log().all().spec(createPrdBase).param("productName","qwerty").param("productAddedBy", userId).
			param("productCategory","fashion").param("productSubCategory","shirts").param("productPrice","1000")
			.param("productDescription","Addias Originals").param("productFor", "women")
			.multiPart("productImage", new File("D:\\ShirtImage2.png"));
			
			
			String response=createPrdReq.when().post("/api/ecom/product/add-product").then().log().all().extract()
			.response().asString();
			JsonPath js=new JsonPath(response);
			System.out.println(js.get("productId"));
			String productId=js.get("productId");
			System.out.println(js.get("message"));
			
			
			
			
			//Create order of the product
			
			OrderList orderdetails=new OrderList();
			orderdetails.setCountry("India");
			orderdetails.setProductOrderedId(productId);
			List<OrderList> orderlist=new ArrayList<OrderList>();
			orderlist.add(orderdetails);
			Order order=new Order();
			order.setOrders(orderlist);
			
			RequestSpecification createOrderBase=new RequestSpecBuilder().setBasePath("https://rahulshettyacademy.com")
					.setContentType(ContentType.JSON).addHeader("Authorization",token).build();
			RequestSpecification createBaseReq=given().log().all().spec(createOrderBase).body(order);
		OrderOutput createBaseRes=createBaseReq.when().post("https://rahulshettyacademy.com/api/ecom/order/create-order")
			.then().log().all().extract().response().as(OrderOutput.class);
			
			System.out.println(createBaseRes.getOrders().get(0));
			System.out.println(createBaseRes.getMessage());
			
			
			
			
		}

	}



