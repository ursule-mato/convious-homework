Feature: Scenarios testing Restaurant picker API

	I login to application I should be able to find my user credentials

	Scenario Outline: User logs in
    Given I provide login credentials "<username>" and "<password>"
    Then I should see my "<username>" in user list
 
    Examples: Invalid
  			|username |password  |
            |ursule   |balanemate|
            
            
            
    Scenario: Restaurant registration
    Given "<username>" is a registered user
    When they select restaurants endpoint
    Then they are able to add a new restaurant
    
    Scenario Outline: Voting for a restaurant
    Given "<username>" is a registered user
    When they select a vote endpoint
    And choose a "<restaurant>" to cast "<number_of_votes>"
    Then they should be able to see the casted votes in the polls
    
    Examples: casting votes
    |username|restaurant  |number_of_votes|
    |ursule  |holigans    |              3|
    |ursule  |vaithaitau  |              2|
    |bob     |holigans    |              5|
    |alice   |vaithaitau  |              1|
    |alice   |holigans    |              1|
    |alice   |open_kitchen|              1|
    |alice   |the_owl     |              1|
    
    
    