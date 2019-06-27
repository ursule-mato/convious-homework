# Getting started with Convious QA Homework

The purpose of this project is to provide a plan and examples of how to test Restaurant Picker API

## Test approach

For acceptance testing I chose to write Cucumber feature files to document test cases in scenarios.
Each scenario uses Gherkin, 'Given/When/Then' style steps to replicate real-life user goals, interactions and results that in themselves document and test 
application features and functions. We use Examples to test the same scenario with multiple sets of data.

```
Feature: Login

	I login to application I should be able to find my user credentials

	Scenario Outline: User logs in
    Given I provide login credentials "<username>" and "<password>"
    Then I should see my "<username>" in user list
    
      Examples: Login
  	    |username |password     |
            |ursule   |balanemate   |
            |bob      |bobspassword |
```


In BDD approach these scenarios should be written in 3 amigo sessions together with PO/BA, engineers and of course QA.
Cucumber is a layer of BDD that helps with such documentation but is also embedded in the test suite. Each step of scenario is linked to step definitions 
where all the action happens. This is where the methods are implemented and interactions are called. 

For larger size test frameworks Screenplay model helps to scale the tasks that our virtual users can perform. In Screenplay, tests describe behaviour 
in terms of actors, who achieve their business goals by performing tasks. These tasks usually involve interacting with the application in some way.
And to perform these tasks, we give the actors various abilities. This way the method calls get redistributed to tasks instead of step definitions as I 
implemented in this case.
Actors could have such abilities like: adding restaurants, casting votes, deleting votes, reviewing results, resetting results and ect.


### Running the tests

To run Cucumber tests, use the command:

``` 
mvn clean verify 

```

### Integration tests

I was able to create a few sample integration tests that can be found under qa.homework package. They test adding a new user and asserting that it appears 
on the user list.

Integration tests are run as JUnit tests, for which commands vary depending on IDE. In eclipse, simply right-click on the IntegrationTest.java class and 
select Run As -> JUnit Test.

Results provide verbose logging.

## Manual testing

For this task I had to rely heavily on manual tests due to technical issues I faced. However, I described my manual test strategy in Feature files which
can be found under src/test/resources/Features.