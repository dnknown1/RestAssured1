package restAssuredProject;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class TestRun {
	private final String BASE_URL = "http://localhost:3000/";
	private final String API_PATH = "testpath/";
	
	private Response read(String path){
		return RestAssured.get(path);
	}

	private Response delete(String path){
		return RestAssured.get(path);
	}
	
	private String appendId(String i){
		return BASE_URL+API_PATH+i;
	}
	
	@Test
	public void dryRun(){
		System.out.println("HI TestNG");
	}
	
	//@Test
	public void firstGET(){
		System.out.println(read(BASE_URL+API_PATH).asString());
	}
	
	//@Test
	public void firstDELETE(){
		String url = appendId("1");
		System.out.println(delete(url).asString());
	}
	
	@Test
	public void anotherGET(){
		RestAssured.baseURI = BASE_URL;
		given()
		.get(API_PATH)
		.then()
		.statusCode(200)
		.log().all();
	}

	@Test
	public void anotherDELETE(){
		RestAssured.baseURI = BASE_URL;
		given()
		.delete(API_PATH+"2")
		.then()
		.statusCode(200)
		.log().all();
	}
}
