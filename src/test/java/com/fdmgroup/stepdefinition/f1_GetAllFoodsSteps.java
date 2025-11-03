package com.fdmgroup.stepdefinition;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fdmgroup.pojos.Foods;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class f1_GetAllFoodsSteps {

	
	private Response response;
	private List<Foods> foodsList;
	private String baseUrl = "http://localhost:3000/foods";
    private String foodId;
    private String newPrice;
    private Foods foodItem;

	
	
	
	
	@Given("the base URL is {string}")
    public void the_base_url_is(String uri) {
        baseURI = uri ;
    }
	
	@When("User sent a GET request to {string}")
	public void user_sent_a_get_request_to(String url) {
		
		response = given()
	    		.contentType(ContentType.JSON)
	    		.when()
	    		.get(baseURI + url)
	    		.then()
	    		.assertThat()
	    		.statusCode(200)
	    		.headers("Content-Type", startsWith("application/json"))
	    		.extract().response();
	    
	    foodsList = response.jsonPath().getList("", Foods.class);
	}
	
	@Then("User receives a valid response with status code {int}")
	public void user_receives_a_valid_response_with_status_code(Integer expectedStatusCode) {
		 assertThat(response.getStatusCode(), is(expectedStatusCode));
	}

	@Then("the response contains a list of foods with all the information")
	public void the_response_contains_a_list_of_foods_with_all_the_information() {
		assertThat("Food list should not be empty", foodsList, hasSize(greaterThan(0)));

        List<String> foodNames = new ArrayList<>();
        for (Foods food : foodsList) {
            foodNames.add(food.getName());
            System.out.println("ID: " + food.getId() + 
                               " | Name: " + food.getName() + 
                               " | Price: $" + food.getPrice());
        }

        assertThat(foodNames, hasItem("cucumber salad"));
	}

	@When("User sends a POST request to {string} and give following details")
	public void user_sends_a_post_request_to_and_give_following_details(String URL, io.cucumber.datatable.DataTable dataTable) {
		
		List<Map<String, String>> dataList = dataTable.asMaps(String.class, String.class);

	    for (Map<String, String> data : dataList) {
	        Foods newFood = new Foods(
	                Integer.parseInt(data.get("id")),
	                data.get("Name"),
	                data.get("price")
	        );

	        System.out.println("checking url is correct " + baseURI + URL );
	    response = given()
	            .contentType(ContentType.JSON)
	            .body(newFood)
	            .when()
	            .post(baseURI + URL)
	            .then()	                
	            .extract()
	            .response();

	    System.out.println("user Created new food : " + newFood);
	}
	}

	@Then("User will get a valid response with status code {int}")
	public void user_will_get_a_valid_response_with_status_code(Integer expectedcode) {
		assertThat(response.getStatusCode(), is(expectedcode));
	    
	}

	@Then("the new food item will be visible in the db")
	public void the_new_food_item_will_be_visible_in_the_db() {
		Response getResponse = given()
	            .contentType(ContentType.JSON)
	            .when()
	            .get(baseURI +":3000" + "/foods")
	            .then()
	            .assertThat()
	            .statusCode(200)
	            .extract()
	            .response();

	    
	    List<Foods> foodsList = getResponse.jsonPath().getList("", Foods.class);

	List<String> foodNames = new ArrayList<>();
	    for (Foods food : foodsList) {
	        foodNames.add(food.getName());
	    }

	    // Verification of new food entries
	    assertThat(foodNames, hasItem("pizza"));
	    assertThat(foodNames, hasItem("donut"));

	    
	    System.out.println("Verified pizza and donut is now in the foods list!");
	}
	
	@Given("the food item with ID {string} exists")
	public void the_food_item_with_id_exists(String id) {
	    foodId = id;

	    // Send GET request to verify item exists
	    response = RestAssured
	        .given()
	        .contentType(ContentType.JSON)
	        .log().all()
	        .when()
	        .get(baseUrl  +"/"+ foodId);
	        

	    //assertThat("Food item should exist", response.getStatusCode(), equalTo(200));

	    // Deserialize into POJO
	    foodItem = response.as(Foods.class);
	}

	@When("I update the price of the food item to {string}")
	public void i_update_the_price_of_the_food_item_to(String price) {
	    newPrice = price;

	    
	    String updateBody = String.format("{ \"price\": %s }", newPrice);

	    response = RestAssured
	        .given()
	        .contentType(ContentType.JSON)
	        .body(updateBody)
	        .when()
	        .patch(baseUrl +"/" + foodId)   // 
	        .then()
	        .extract()
	        .response();

	    System.out.println("PATCH Response for ID " + foodId + ": " + response.asPrettyString());

	    assertThat("Update should be successful", response.getStatusCode(), equalTo(200));
	}

	@Then("the food items price should be updated to {string}")
	public void the_food_items_price_should_be_updated_to(String expectedPrice) {
	    response = RestAssured
	        .given()
	        .contentType(ContentType.JSON)
	        .when()
	        .get(baseUrl +"/"  + foodId);

	    Foods updatedItem = response.as(Foods.class);

	    System.out.println("Updated item: " + updatedItem);

	    assertThat("Price should match after patch update",
	        String.valueOf(updatedItem.getPrice()), equalTo(expectedPrice));
	}

	
	
	
	
	
	
	
}
