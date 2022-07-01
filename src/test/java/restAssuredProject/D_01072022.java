package restAssuredProject;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class D_01072022 {
	private final String BASE_URI = "http://localhost:3000/";
	private final String API_PATH = "testpath/";
	private final String SIG_ID = "1";
	
	public RequestSpecification connect(String base){
		RestAssured.baseURI = base;
		return RestAssured.given();
	}
	
	@SuppressWarnings("unchecked")
	private String useJsonForPOST(){
		JSONObject j1 = useJson();
		j1.put("req_from", "rest_assured");
		j1.put("status", "req ack");
		return j1.toString();
	}
	@SuppressWarnings("unchecked")
	private String useJsonForPUT(){
		JSONObject j1 = useJson();
		j1.put("req_from", "rest_assured");
		j1.put("status", "req init");
		return j1.toString();
	}

	private JSONObject useJson(){
		return new JSONObject();
	}
	

	@Test(dependsOnMethods={"firstPOST"})
	public void firstPUT(){
		connect(BASE_URI)
		.contentType(ContentType.JSON)
		.body(useJsonForPUT())
		.when()
		.put(API_PATH+SIG_ID)
		.then()
		.statusCode(200)
		.log().all();
	}

	@Test(dependsOnMethods={"firstPUT"})
	public void firstDELETE(){
		connect(BASE_URI)
		.delete(API_PATH+SIG_ID)
		.then()
		.statusCode(200)
		.log().all();
	}

	@Test(dependsOnMethods={"firstDELETE"})
	public void anotherGET(){
		connect(BASE_URI)
		.get(API_PATH)
		.then()
		.statusCode(200)
		.log().all();
	}

	@Test
	public void firstPOST(){
		connect(BASE_URI)
		.contentType(ContentType.JSON)
		.body(useJsonForPOST())
		.when()
		.post(API_PATH)
		.then()
		.statusCode(201)
		.log().all();
	}
}
