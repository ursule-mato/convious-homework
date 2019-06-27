package com.serene.tests.features.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.internal.http.Status;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.websocket.api.StatusCode;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;

import org.junit.runner.RunWith;



@RunWith(SerenityRunner.class)
public class LoginStepDefn {
	

	// @Before
	// public void setup()
	// {
    // 	RestAssured.baseURI = "https://convious-qa-g5wq.herokuapp.com/api/v1/auth/users/create";

	// }
	
	@After
	public void tearDown()
	{
        RestAssured.reset();
	}
	@Steps
	LoginAPISteps loginAPI;

	@Given("^I provide login credentials \"([^\"]*)\" and \"([^\"]*)\"$")
    public void i_provide_username_with_password(String username,String password) {
		RestAssured
		.given()
				.auth()
				.preemptive()
				.basic(username, password)
				.when()
				.get("https://convious-qa-g5wq.herokuapp.com/api/v1/auth/users/me/")
				.then()
		.assertThat().statusCode(200);
		RestAssured.get().print();

	 }


	 @Then("^I should see my name in \"([^\"]*)\" list$")
	 public void i_should_see_my_name_in_user_list(String username) throws Exception {
		RequestSpecification requestSpec = new RequestSpecBuilder().build();
		requestSpec.baseUri("https://convious-qa-g5wq.herokuapp.com/");
		requestSpec.basePath("api/v1/auth/users/");
		RestAssured.given()
			.spec(requestSpec)
			.get()
		.then()
			.body("username",containsString(username));
	 }
/** unused */
		@Given("^I can create a new user$")
		public void i_can_create_a_new_user(){

			Map<String,String> requestBody = new HashMap<String,String>();
			requestBody.put("email", "ursulee@gmail.com");
			requestBody.put("username", "ursule");
			requestBody.put("password", "balanemate");
			Response response = RestAssured.given()
				.baseUri("https://convious-qa-g5wq.herokuapp.com/")
				.basePath("api/v1/auth/users/create")
				.body(requestBody)
				.post();
				System.out.println(response);

		}
	 
}
