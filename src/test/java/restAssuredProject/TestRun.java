package restAssuredProject;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestRun {
	private final String BASE_URI = "http://localhost:3000/";
	private final String API_PATH = "testpath/";

	public RequestSpecification connect(String base){
		RestAssured.baseURI = base;
		return RestAssured.given();
	}

	private Response read(String path){
		return RestAssured.get(path);
	}

	private Response delete(String path){
		return RestAssured.get(path);
	}

	private String appendId(String i){
		return BASE_URI+API_PATH+i;
	}

	@Test
	public void dryRun(){
		System.out.println("HI TestNG");
	}

	//@Test
	public void firstGET(){
		System.out.println(read(BASE_URI+API_PATH).asString());
	}

	//@Test
	public void firstDELETE(){
		String url = appendId("0");
		System.out.println(delete(url).asString());
	}

	@Test(dependsOnMethods={"firstPOST"})
	public void anotherGET(){
		connect(BASE_URI)
		.get(API_PATH)
		.then()
		.statusCode(200)
		.log().all();
	}

	////@Test
	public void anotherDELETE(){
		connect(BASE_URI)
		.delete(API_PATH+"2")
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
	
	@Test
	public void firstPUT(){
		connect(BASE_URI)
		.contentType(ContentType.JSON)
		.body(useJsonForPUT())
		.when()
		.put(API_PATH)
		.then()
		.statusCode(201)
		.log().all();
	}
	
	public String useJsonForPOST(){
		JSONObject j1 = useJson();
		j1.put("req_from", "rest_assured");
		j1.put("status", "req ack");
		return j1.toString();
	}
	public String useJsonForPUT(){
		JSONObject j1 = useJson();
		j1.put("req_from", "rest_assured");
		j1.put("status", "req init");
		return j1.toString();
	}
	public JSONObject useJson(){
		return new JSONObject();
	}
}
