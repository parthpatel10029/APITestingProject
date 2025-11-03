Feature: Performing retrival, create and update actions on foods upon users request
Background: Given the base URL is "http://localhost"

Scenario: Get list of all available foods
When User sent a GET request to ":3000/foods"
Then User receives a valid response with status code 200
And the response contains a list of foods with all the information


Scenario: Creating a new food entery in db
When User sends a POST request to ":3000/foods" and give following details
    | id | Name          | price |
    | 5  | pizza | 7.50  |
        | 6  | donut | 8.50  |
  Then User will get a valid response with status code 201
  And the new food item will be visible in the db

  
  Scenario Outline: Update price of a food item
    Given the food item with ID "<id>" exists
    When I update the price of the food item to "<price>"
    Then the food items price should be updated to "<price>"

    Examples:
      | id  | price  |
      | 1 | 12.5  |
      | 2 | 15.99  |
      | 3 | 20  |
