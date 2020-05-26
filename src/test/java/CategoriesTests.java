import Utils.TestBase;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CategoriesTests extends TestBase {

    Logger logger = Logger.getLogger(CategoriesTests.class);

    @Test
    public void tc01_checkCorrectCategoriesRequestStatusCode(){

        getAuthorizedRequest()
                .when()
                     .get(CATEGORIES_API_PATH)
                .then().assertThat().statusCode(RESPONSE_OK_CODE);
    }

    @Test
    public void tc02_checkCategoriesResponseWithoutAuthentication(){
        getUnauthorizedRequest()
                .when()
                    .get(CATEGORIES_API_PATH)
                .then()
                    .assertThat()
                    .statusCode(RESPONSE_UNATHORIZED_CODE);
    }

    @Test
    public void tc03_checkCategoriesResponseSchema(){
        getAuthorizedRequest()
                .when()
                    .get(CATEGORIES_API_PATH)
                .then()
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath("categories-schema.json"));
    }

    @Test
    public void tc04_checkNotAllowedMethodOnCategoriesPath(){
        getAuthorizedRequest()
                .when()
                    .post(CATEGORIES_API_PATH)
                .then()
                    .statusCode(RESPONSE_METHOD_NOT_ALLOWED_CODE);
    }

    @Test
    public void tc05_checkGettingCategoryByCorrectId(){
        JSONObject testBaseObject = getObjectArrayFromRequest(CATEGORIES_API_PATH, "categories").getJSONObject(0);
        Response response = getAuthorizedRequest()
                .when()
                .get(CATEGORIES_API_PATH+testBaseObject.getString("id"));

        response.then().statusCode(RESPONSE_OK_CODE);

        JSONObject objectGetById = new JSONObject(response.getBody().asString());
        JSONAssert.assertEquals(testBaseObject, objectGetById, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void tc06_checkNotAllowedMethodOnCategoryById(){
        JSONObject testBaseObject = getObjectArrayFromRequest(CATEGORIES_API_PATH, "categories").getJSONObject(0);
        getAuthorizedRequest()
                .when()
                    .post(CATEGORIES_API_PATH+testBaseObject.getString("id"))
                .then()
                    .statusCode(RESPONSE_METHOD_NOT_ALLOWED_CODE);
    }

    @Test
    public void tc07_checkGettingCategoryByCorrectIdSchema(){
        JSONObject testBaseObject = getObjectArrayFromRequest(CATEGORIES_API_PATH, "categories").getJSONObject(0);
        getAuthorizedRequest()
                .when()
                    .get(CATEGORIES_API_PATH+testBaseObject.getString("id"))
                .then()
                    .assertThat()
                    .body(matchesJsonSchemaInClasspath("category-schema.json"));
    }

    @Test
    public void tc08_checkGettingCategoryByIncorrectId(){
        getAuthorizedRequest()
                .when()
                    .get(CATEGORIES_API_PATH+"incorrectId")
                .then()
                    .statusCode(RESPONSE_ELEMENT_NOT_FOUND_CODE);
    }

    @Test
    public void tc09_checkCorrectCategoryParameterStatusCode(){
        JSONObject testBaseObject = getObjectArrayFromRequest(CATEGORIES_API_PATH, "categories").getJSONObject(0);
        getAuthorizedRequest()
                .when()
                    .get(CATEGORIES_API_PATH+testBaseObject.getString("id")+"/parameters")
                .then()
                    .statusCode(RESPONSE_OK_CODE);
    }

    @Test
    public void tc10_checkIncorrectCategoryParameterStatusCode(){
        getAuthorizedRequest()
                .when()
                .get(CATEGORIES_API_PATH+"testIncorrect/parameters")
                .then()
                .statusCode(RESPONSE_ELEMENT_NOT_FOUND_CODE);
    }

    @Test
    public void tc11_checkNotAllowedMethodOnCategoryParameter(){
        JSONObject testBaseObject = getObjectArrayFromRequest(CATEGORIES_API_PATH, "categories").getJSONObject(0);
        getAuthorizedRequest()
                .when()
                .post(CATEGORIES_API_PATH+testBaseObject.getString("id")+"/parameters")
                .then()
                .statusCode(RESPONSE_METHOD_NOT_ALLOWED_CODE);
    }
}
