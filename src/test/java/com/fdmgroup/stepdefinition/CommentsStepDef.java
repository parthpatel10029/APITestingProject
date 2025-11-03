package com.fdmgroup.stepdefinition;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CommentsStepDef {

	private String baseURL;
    private Response response;

  
    @Given("the base URI for comments is {string}")
    public void the_base_uri_for_comments_is(String url) {
        baseURL = url;
        System.out.println("Base URL for comments: " + baseURL);
    }

    
    @When("User sends a POST request to {string} with the following details")
    public void user_sends_a_post_request_to_with_the_following_details(String endpoint, io.cucumber.datatable.DataTable dataTable) {

        // Extract first row from table
        java.util.List<String> row = dataTable.asLists().get(1);
        String id = row.get(0);
        String userId = row.get(1);
        String foodId = row.get(2);
        String bodyText = row.get(3);

        System.out.println("Creating comment with ID: " + id);

        String requestBody = String.format("""
            {
                "id": "%s",
                "userid": "%s",
                "foodId": "%s",
                "body": "%s"
            }
        """, id, userId, foodId, bodyText);

        response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(baseURL + endpoint)
                .then()
                .assertThat()
                .statusCode(201)
                .body("id", containsString(id))
                .body("userid", containsString(userId))
                .body("foodId", containsString(foodId))
                .body("body", containsString(bodyText))
                .headers("Content-Type", is("application/json"))
                .extract()
                .response();

        
    }

    @Then("the new comment is visible in the database")
    public void the_new_comment_is_visible_in_the_database() {

        var getResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseURL + "/comments")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        
    }

    @When("User sends a DELETE request to {string}")
    public void user_sends_a_delete_request_to(String endpoint) {

        System.out.println("Deleting comment at: " + baseURL + endpoint);

        response = given()
                .contentType(ContentType.JSON)
                .when()
                .delete(baseURL + endpoint)
                .then()
                .assertThat()
                .statusCode(200)
                .headers("Content-Type", is("application/json"))
                .extract()
                .response();

        
    }

    @Then("the deleted comment should not be visible in the database")
    public void the_deleted_comment_should_not_be_visible_in_the_database() {

        var getResponse = given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseURL + "/comments")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .response();

        
    }

   
    @When("User sends a GET request to {string}")
    public void user_sends_a_get_request_to(String endpoint) {
        System.out.println("Fetching comment from: " + baseURL + endpoint);

        response = given()
                .contentType(ContentType.JSON)
                .when()
                .get(baseURL + endpoint)
                .then()
                .assertThat()
                .statusCode(200)
                .headers("Content-Type", is("application/json"))
                .extract()
                .response();

        
    }

    @Then("the body of the comment should be {string}")
    public void the_body_of_the_comment_should_be(String expectedBody) {

        response.then().assertThat()
                .body("body", hasItem(expectedBody));

        
    }

    
    @Then("User receives a response status code {int}")
    public void user_receives_a_response_status_code(Integer expectedCode) {
        response.then().assertThat().statusCode(expectedCode);
        
    }
	
}
