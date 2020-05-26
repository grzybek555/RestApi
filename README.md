## Requirements to run tests

 1. Java 11
 2. Maven ~3.6.0
 3. Registered app clientId and Client Secure
## Running tests
Execute maven command with clientId and clientSecure values for registered app from pom.xml file location

> mvn test -DclientId="{clientId}" -DclientSecure="{clientSecure}"

## Test suite
|Test case name|Description  |Expected result
|----|--------------|--
| tc01_checkCorrectCategoriesRequestStatusCode | Checking authenticated get request on endpoint /sale/categories|Response status code 200
|tc02_checkCategoriesResponseWithoutAuthentication|Checking get request without authentication on /sale/categories endpoint|Response status code 401
|tc03_checkCategoriesResponseSchema|Checking json response schema on /sale/categories endpoint|Response json match schema
|tc04_checkNotAllowedMethodOnCategoriesPath|Checking post method on endpoint which allows only get method|Response status code 404
|tc05_checkGettingCategoryByCorrectId|Checking category request with correct id parameter|Response status code 200, response data match expected data
|tc06_checkNotAllowedMethodOnCategoryById|Checking not allowed post method on category with id parameter| Response status code 405
|tc07_checkGettingCategoryByCorrectIdSchema|Checking category with id parameter response schema|Response json match schema
|tc08_checkGettingCategoryByIncorrectId|Checking category with incorrect id parameter|Response status code 404
|tc09_checkCorrectCategoryParameterStatusCode|Checking response on correct request /sale/categories/{categoryid}/parameters|Response status code 200
|tc10_checkIncorrectCategoryParameterStatusCode|Checking on request /sale/categories/{categoryid}/parameters with incorrect categoryId|Response status code 404
|tc11_checkNotAllowedMethodOnCategoryParameter|Checking not allowed post method on /sale/categories/{categoryid}/parameters endpoint|Response status code 405




