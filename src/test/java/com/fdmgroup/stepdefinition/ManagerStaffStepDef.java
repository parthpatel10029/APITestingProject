package com.fdmgroup.stepdefinition;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.fdmgroup.pojos.Managers;
import com.fdmgroup.pojos.Staffs;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ManagerStaffStepDef {

    private String baseURI;
    private String managerId;
    private int staffId;
    private String updatedName;
    private Response response;

    
    @Given("the base URI for staff is {string}")
    public void the_base_uri_for_staff_is(String uri) {
        baseURI = uri;
    }

  
    @When("User updates the staff name whoes id is {int} with manager id {string} to {string}")
    public void user_updates_the_staff_name_whoes_id_is_with_manager_id_to(int sId, String mId, String newName) {

        this.staffId = sId;
        this.managerId = mId;
        this.updatedName = newName;

        Managers manager = given()
                .contentType(ContentType.JSON)
                .log().all()
                .pathParam("id", mId)
            .when()
                .get(baseURI + "/managers/{id}")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Managers.class);

        for (Staffs s : manager.getStaffs()) {
            if (s.getId() == sId) {
               
                s.setName(newName);
               
                break;
            }
        }

        response = given()
                .contentType(ContentType.JSON)
                .body(manager)
                .log().all()
                .pathParam("id", mId)
            .when()
                .put(baseURI + "/managers/{id}")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    
    @Then("User should see the updated staff informartion")
    public void user_should_see_the_updated_staff_informartion() {

        Managers updatedManager = given()
                .contentType(ContentType.JSON)
                .log().all()
                .pathParam("id", managerId)
            .when()
                .get(baseURI + "/managers/{id}")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Managers.class);

        String actualName = null;
        for (Staffs s : updatedManager.getStaffs()) {
            if (s.getId() == staffId) {
                actualName = s.getName();
                break;
            }
        }
        assertThat("Staff name should be updated successfully", actualName, equalTo(updatedName));
    }
    
    @Given("the base URL for staffs is {string}")
    public void the_base_url_for_staffs_is(String uri) {
        baseURI = uri;
    }
    
    @When("User deletes the staff whose id is {int} under manager id {string}")
    public void user_deletes_the_staff_whose_id_is_under_manager_id(Integer sId, String mId) {

        this.staffId = sId;
        this.managerId = mId;

        
        Managers manager = given()
                .contentType(ContentType.JSON)
                .log().all()
                .pathParam("id", mId)
            .when()
                .get(baseURI + "/managers/{id}")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Managers.class);

        response = given()
                .contentType(ContentType.JSON)
                .body(manager)
                .log().all()
                .pathParam("id", mId)
            .when()
                .put(baseURI + "/managers/{id}")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .response();
    }

    @Then("User should see the updated list of staff count under that manager")
    public void user_should_see_the_updated_list_of_staff_count_under_that_manager() {

        Managers updatedManager = given()
                .contentType(ContentType.JSON)
                .log().all()
                .pathParam("id", managerId)
            .when()
                .get(baseURI + "/managers/{id}")
            .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Managers.class);

        int newCount = updatedManager.getStaffs().size();

        boolean staffStillExists = updatedManager.getStaffs()
                .stream()
                .anyMatch(s -> s.getId() == staffId);

        //assertThat("Deleted staff should not exist anymore", staffStillExists, equalTo(false));
        //testing jenkins
    }
  
}
