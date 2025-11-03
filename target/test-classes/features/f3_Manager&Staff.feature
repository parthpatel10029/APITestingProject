Feature: User wamnts to update and delete staff informationn

 
    Scenario: User want to upadte the list of staff
    Given the base URI for staff is "http://localhost:3000"
    When User updates the staff name whoes id is 2 with manager id "1" to "Hello"
    Then User should see the updated staff informartion
    
Scenario: Delete a specific staff under a manager and count remaining staff
    Given the base URL for staffs is "http://localhost:3000"
    When User deletes the staff whose id is 1 under manager id "1"
    Then User should see the updated list of staff count under that manager
    
    
    
    
    
    