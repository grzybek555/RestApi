package Utils;

import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;

public class TestBase {
    private String API_TOKEN;
    public static final String CONTENT_TYPE = "application/vnd.allegro.public.v1+json";
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

    private void setApiToken(String apiToken){
        API_TOKEN = apiToken;
    }
}
