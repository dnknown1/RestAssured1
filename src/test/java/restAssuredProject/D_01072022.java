package restAssuredProject;

import org.json.simple.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class D_01072022 {
	private final String BASE_URI = "http://localhost:3000/";
	private final String API_PATH = "testpath/";
	
	public RequestSpecification connect(String base){
		RestAssured.baseURI = base;
		return RestAssured.given();
	}
	
	@SuppressWarnings("unchecked")
	private String useJsonData(String rf, String sts){
		JSONObject j1 = useJson();
		j1.put("req_from", rf);
		j1.put("status", sts);
		return j1.toString();
	}

	private JSONObject useJson(){
		return new JSONObject();
	}

	
	@Test(dependsOnMethods={"firstPOST"}, dataProvider="putData")
	public void firstPUT(String id, String rf, String sts){
		connect(BASE_URI)
		.contentType(ContentType.JSON)
		.body(useJsonData(rf, sts))
		.when()
		.put(API_PATH+id)
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

	@Test(dependsOnMethods={"firstDELETE"})
	public void anotherGET(){
		connect(BASE_URI)
		.get(API_PATH)
		.then()
		.statusCode(200)
		.log().body();
	}

	@Test(dataProvider="postData")
	public void firstPOST(String rf, String sts){
		connect(BASE_URI)
		.contentType(ContentType.JSON)
		.body(useJsonData(rf, sts))
		.when()
		.post(API_PATH)
		.then()
		.statusCode(201)
		.log().body();
	}
	
	@DataProvider(name="postData")
	public Object[][] providerPOST(){
		Object[][] postData = {
			{"RA1","init"},
			{"RA2","init"}
		};		
		return postData;
	}
	
	@DataProvider(name="putData")
	public Object[][] providerPUT(){
		Object[][] putData = {
			{"1","RA1","ack"},
			{"2","RA2","ack"}
		};
		return putData;
	}
	
	@DataProvider(name="deleteData")
	public Object[][] providerDELETE(){
		Object[][] deleteData = {{"1"},{"2"}};
		return deleteData;
	}
}
