import Utils.TestBase;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CategoriesTests extends TestBase {
    @Test
    public void checkCorrectRequestStatusCode(){
        int expectedCode = 200;

         given()
                 .auth()
                 .oauth2(getApiToken())
                 .accept(CONTENT_TYPE)
         .when()
                 .get("/sale/categories")
         .then().assertThat().statusCode(expectedCode);

    }

    @Test
    public void checkResponseWithoutAuthentication(){
        Response response = given()
                .accept(CONTENT_TYPE)
                .when()
                .get("/sale/categories");

        response.then().assertThat().statusCode(401);
    }
}
