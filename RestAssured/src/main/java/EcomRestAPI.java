
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojoForEcom.ViewOrderlist;
import pojoForEcom.loginRequest;
import pojoForEcom.loginResponse;
import pojoForEcom.order;
import pojoForEcom.orderDetails;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

public class EcomRestAPI {
	
	
	public static void main(String[] args) {
		
		

		// Login the EcomWebsite

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		loginRequest loginreq = new loginRequest();
		loginreq.setUserEmail("bhupendrabhagat1212@gmail.com");
		loginreq.setUserPassword("Welcome@1212");

		RequestSpecification request = given().log().all().spec(req).body(loginreq);
		loginResponse loginResponse = request.when().post("/api/ecom/auth/login").then().log().all().extract()
				.response().as(loginResponse.class);

		System.out.println(loginResponse.getToken());
		String token = loginResponse.getToken();
		System.out.println(loginResponse.getUserId());
		String userId = loginResponse.getUserId();
		System.out.println(loginResponse.getMessage());

		// Add Create post

		RequestSpecification addproductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification reqAddProduct = given().log().all().spec(addproductBaseReq).param("productName", "nature")
				.param("productAddedBy", userId).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "1000")
				.param("productDescription", "Addias Originals").param("productFor", "women")
				.multiPart("productImage", new File("D:\\ShirtImage2.png"));

		String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().response().asString();
		JsonPath js = new JsonPath(addProductResponse);
		String productId = js.get("productId");

		// Create Product Order

		RequestSpecification createPrdOrdBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();

		orderDetails orderdetails = new orderDetails();
		orderdetails.setCountry("India");
		orderdetails.setProductOrderedId(productId);

		List<orderDetails> orderDetailslist = new ArrayList<orderDetails>();
		orderDetailslist.add(orderdetails);
		order oderlist = new order();
		oderlist.setOrders(orderDetailslist);

		RequestSpecification createOrderReq = given().log().all().spec(createPrdOrdBase).body(oderlist);

		ViewOrderlist CreateorderRes = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all().extract()
				.response().as(ViewOrderlist.class);
		
		System.out.println(CreateorderRes);
		String orders=CreateorderRes.getOrders().get(0);
		System.out.println("Order id" +orders);
		
		
		//View The product Order
		
		RequestSpecification viewprdBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();
		RequestSpecification viewprdReq=given().log().all().spec(viewprdBase).queryParam("id",orders);
		String response=viewprdReq.when().get("/api/ecom/order/get-orders-details").then().log().all().extract().response().asString();
		System.out.println(response);
		
	
				
		
	 // Delete the product

		RequestSpecification deleteprdBase = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();

		RequestSpecification deleteprdreq = given().log().all().spec(deleteprdBase).pathParam("productId", productId);

		String deleteprdres = deleteprdreq.when()
				.delete("https://rahulshettyacademy.com/api/ecom/product/delete-product/{productId}").then().log().all()
				.extract().response().asString();
		JsonPath js1 = new JsonPath(deleteprdres);
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));

	}

}