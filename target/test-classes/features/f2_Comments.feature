Feature: Performing create, delete, and retrieve actions on comments when user request

Background: 
  
  Given the base URI for comments is "http://localhost:3000"



	Scenario: Create a new comment with foodId
  When User sends a POST request to "/comments" with the following details
    | id | userid | foodId | body           |
    | 5  | 2      | 3      | Very delicious |
  Then User receives a response status code 201
  And the new comment is visible in the database


	Scenario: Delete the comment based on comment id
  When User sends a DELETE request to "/comments/5"
  Then User receives a response status code 200
  And the deleted comment should not be visible in the database


	Scenario: Get comment information using userId and foodId
  When User sends a GET request to "/comments?userid=1&foodId=4"
  Then User receives a response status code 200
  And the body of the comment should be "amazing"
