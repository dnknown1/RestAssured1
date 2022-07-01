package restAssuredProject;


import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RaProject {
	private final String BASE_URI = "https://petstore.swagger.io/v2/";
	private final String API_PATH = "user/";
	
	private static String uname = "dnknown"; 

	public RequestSpecification connect(String base){
		RestAssured.baseURI = base;
		return RestAssured.given();
	}
	
	@SuppressWarnings("unchecked")
	private String genUserData(String user, String fn, String ln, String em, String pwd, String ph, int st){
		JSONObject j1 = useJson();
		j1.put("username", user);
		j1.put("firstName", fn);
		j1.put("lastName", ln);
		j1.put("email", em);
		j1.put("password", pwd);
		j1.put("phone", ph);
		j1.put("userStatus", st);
		
		
		return j1.toString();
	}
	
	private JSONObject useJson(){
		return new JSONObject();
	}

	
	@Test(dependsOnMethods={"firstPOST"}, dataProvider="putData")
	public void firstPUT(String u, String fn, String ln, String em, String pwd, String ph, int st){
		connect(BASE_URI)
		.contentType(ContentType.JSON)
		.body(genUserData(u, fn, ln, em, pwd, ph, st))
		.when()
		.put(API_PATH+u)
		.then()
		.statusCode(200)
		.log().all();
	}

	@Test(dependsOnMethods={"firstPUT"},dataProvider="deleteData")
	public void firstDELETE(String id){
		connect(BASE_URI)
		.delete(API_PATH+id)
		.then()
		.statusCode(200)
		.log().all();
	}

	@Test(dataProvider="deleteData", dependsOnMethods={"firstDELETE"})
	public void anotherGET(String u){
		System.out.println(u);
		connect(BASE_URI)
		.get(API_PATH + u)
		.then()
		.statusCode(200)
		.log().body();
	}

	@Test(dataProvider="postData")
	public void firstPOST(String u, String fn, String ln, String em, String pwd, String ph, int st){
		connect(BASE_URI)
		.contentType(ContentType.JSON)
		.body(genUserData(u, fn, ln, em, pwd, ph, st))
		.when()
		.post(API_PATH)
		.then()
		.statusCode(200)
		.log().all();
	}
	
	@DataProvider(name="postData")
	public Object[][] providerPOST(){
		Object[][] postData = {
			{uname,"003pxd","india", "abc@xyz.com", "pass123","9876543210", 0}
		};		
		return postData;
	}
	
	@DataProvider(name="putData")
	public Object[][] providerPUT(){
		Object[][] putData = {
			{uname,"003pxd","744", "abc@xyz.com", "pass123","9876543210", 0}
		};
		return putData;
	}
	
	@DataProvider(name="deleteData")
	public Object[][] providerDELETE(){
		Object[][] deleteData = {{uname}};
		return deleteData;
	}
}

