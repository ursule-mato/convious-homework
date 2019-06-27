package qa.homework;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.beust.jcommander.internal.Maps;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTests {

	private static String AUTH_TOKEN = null; 
	RequestSpecification restClient;

	private static Map<String,String> customer = Maps.newHashMap();
	private static Map<String,String> restaurant = Maps.newHashMap();

	@BeforeClass
	public static void prepareTestData() {
		customer.put("email", "ursulee@gmail.com");
		customer.put("username", String.format("ursule-%s",RandomStringUtils.randomAlphabetic(11)));
		customer.put("password", "balanemate");
		restaurant.put("name", "vaithaitau");
	}

	@Before
	public void prepareRestClient() {
		RestAssured.reset();
		restClient= RestAssured.given()
				.baseUri("https://convious-qa-g5wq.herokuapp.com/")
				.header("Content-Type","application/json")
				.log().all();
		System.out.println("Resetting");
	}

	//authorisation error
	@Test
	public void a_should_createAccount() {
		restClient
		.basePath("api/v1/auth/users/create")
		.body(customer)
		.post().then().assertThat().body(containsString(customer.get("email")));
		System.out.println(customer);
	}


	@Test
	public void shoud_add_new_restaurant () throws Exception {
		restClient
		.basePath("api/v1/restaurants/")
		.body(restaurant).post().then().assertThat().body(containsString(restaurant.get("name")));

	}

	@Test
	public void b_should_authorizeAccessForNewAccount() throws ClientProtocolException, IOException {

		Gson gson = new Gson();
		AUTH_TOKEN = (String) gson.fromJson(
				Request.Post("https://convious-qa-g5wq.herokuapp.com/api/v1/auth/token/login")
				.addHeader("Content-Type","application/json")
				.bodyString(gson.toJson(customer), ContentType.APPLICATION_JSON)
				.execute()
				.returnContent().asString(),java.util.Map.class)
				.get("auth_token");
		//		
		//		This one doesnt work - redirects to localhost:8080 
		//		restClient.basePath("/api/v1/auth/token/login")
		//			.body(customer)
		//			.log().all()
		//			.post()
		//			.then().assertThat().statusCode(200);
		//			AUTH_TOKEN = (String) RestAssured.get().andReturn().as(HashMap.class).get("auth_token").get();

		assertThat(AUTH_TOKEN, is(notNullValue()));

	}

	@Test
	public void c_should_getInformation() throws Exception {
		Gson gson = new Gson();
		Map<String,String> actualCustomerData = gson.fromJson(
				Request.Get("https://convious-qa-g5wq.herokuapp.com//api/v1/auth/users/me/")
				.addHeader("Authorization",String.format("Token %s", AUTH_TOKEN))
				.execute()
				.returnContent().asString(),Map.class);

		assertThat(actualCustomerData.get("email"), is(customer.get("email")));
		assertThat(actualCustomerData.get("username"), is(customer.get("username")));
	}





}
