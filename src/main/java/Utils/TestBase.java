package Utils;

import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;

public class TestBase {
    private String API_TOKEN;
    public static final String CATEGORIES_API_PATH = "/sale/categories/";
    public static final String CONTENT_TYPE = "application/vnd.allegro.public.v1+json";

    public static final int RESPONSE_OK_CODE = 200;
    public static final int RESPONSE_UNATHORIZED_CODE = 401;
    public static final int RESPONSE_ELEMENT_NOT_FOUND_CODE = 404;
    public static final int RESPONSE_METHOD_NOT_ALLOWED_CODE = 405;

     Logger logger = Logger.getLogger(TestBase.class);


    @BeforeClass
    public void setupClass(){
        BasicConfigurator.configure();
        defaultParser = Parser.JSON;
        baseURI = "https://api.allegro.pl";
        API_TOKEN = accessApiToken();

    }

    private String accessApiToken(){
        Response response = given()
                                .auth().preemptive().basic(System.getProperty("clientId"), System.getProperty("clientSecure"))
                                .contentType("application/x-www-form-urlencoded")
                                .formParam("grant_type", "client_credentials")
                                .when()
                                .post("https://allegro.pl/auth/oauth/token");
        JSONObject responseObject = new JSONObject(response.getBody().asString());
        return responseObject.getString("access_token");
    }

    public String getApiToken(){
        return API_TOKEN;
    }

    public RequestSpecification getAuthorizedRequest(){
        return given()
                .auth()
                .oauth2(getApiToken())
                .accept(CONTENT_TYPE);
    }

    public RequestSpecification getUnauthorizedRequest(){
        return given()
                .accept(CONTENT_TYPE);
    }

    public JSONArray getObjectArrayFromRequest(String requestPath, String objectKey){
        JSONObject responseAsJson = new JSONObject(getAuthorizedRequest()
                .when()
                .get(requestPath)
                .getBody()
                .asString());

        return responseAsJson.getJSONArray(objectKey);
    }

    private void setApiToken(String apiToken){
        API_TOKEN = apiToken;
    }
}
