package resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	// Use public Static Static is used for not create multiple object of req
	// variable if we will not give static
	// then continously create object and every time enter in null and single test
	// cases report will generate
	public static RequestSpecification req;

	public RequestSpecification requestSpecification() throws IOException {
		// If req(variable) is null when we create log report it consider object is
		// empty and print the value but if we
		// run multiple test cases then it is directly going to else becuase initally
		// object not null;
		if (req == null) {

			// For log print we have method addFilter in that method passed log request and
			// for log resquest we have
			// to send object for that create PrintStream Class object and passed in add
			// Filter

			PrintStream log = new PrintStream(new FileOutputStream("loging.txt"));

			req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).addQueryParam("key", "qaclick123")
					.setContentType(ContentType.JSON).addFilter(RequestLoggingFilter.logRequestTo(log))
					.addFilter(ResponseLoggingFilter.logResponseTo(log)).build();
			return req;
		}
		// In the log report already one test case report generate then it will be
		// consider null and directly return req

		return req;
	}

	// For read the properties file   created the getGlobal Method in the Utils
	public static String getGlobalValue(String key) throws IOException {

		// Create one object of the properties file to access the properties file
		Properties prop = new Properties();

		// For read Properties file Create object ofv fis and Used FileInputStream
		// Method
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\Infiwave\\eclipse-workspace\\Cucumber\\src\\test\\java\\resource\\global.properties");
		// load fis file in the prop object
		prop.load(fis);
		// get the Values of the properties file with getProperty
		return prop.getProperty(key);

	}
	// This utilities is used to get anyjsonPath

	public String getJsonPath(Response response,String key) {
		String res =response.asString();
		JsonPath js = new JsonPath(res);
		return js.get(key).toString();

	}

}
