package tests.player_create;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.data.DataGenerator;
import utils.listener.RestAssuredListener;
import utils.rest.Rest;
import utils.rest.RestMethod;
import utils.rest.RestParameter;

import java.util.List;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;


@Epic("Player controller")
@Feature("Create player")
public class CreatePlayerNegativeTests extends CreatePlayerTestData {

    private static final Logger logger = Logger.getLogger(CreatePlayerNegativeTests.class.getName());

    @Test(dataProvider = "create player - negative", description = "validate negative cases of users creation")
    @Description("Validate a variety of negative scenarios based on path- and query- parameters:")
    @Story("Test-02")
    @Severity(SeverityLevel.CRITICAL)
    public void validateNegativeBusinessCases(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams) {
        logger.info("Case: " + testCase);

        Response response = new Rest
                .Builder(baseUrl, endpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .build()
                .executeRequest();
        assertNotEquals(response.getStatusCode(), 200);
    }

    @Test(dataProvider = "create player - positive", description = "validate response status code when using incorrect endpoint")
    @Description("Validate response status code when using incorrect endpoint")
    @Story("Test-03")
    @Severity(SeverityLevel.NORMAL)
    public void validateResponseCodeWhenIncorrectEndpoint(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams) {
        logger.info("Case: " + testCase + " when using incorrect endpoint");

        String incorrectEndpoint = endpoint.replace("create", "create1");
        Response response = new Rest
                .Builder(baseUrl, incorrectEndpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .build()
                .executeRequest();
        assertEquals(response.getStatusCode(), 404);
    }

    @Test(dataProvider = "create player - combo of positive data and incorrect rest methods", description = "validate response status code when using incorrect REST method")
    @Description("Validate response status code when using incorrect REST method")
    @Story("Test-04")
    @Severity(SeverityLevel.NORMAL)
    public void validateResponseCodeWhenIncorrectRestMethod(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams, RestMethod method) {
        logger.info("Case: " + testCase + " when using incorrect REST method");

        Response response = new Rest
                .Builder(baseUrl, endpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .build()
                .executeRequest();
        assertEquals(response.getStatusCode(), 405);
    }

    @Test(dataProvider = "create player - positive", description = "validate response status code when 2 users created with the same login")
    @Description("Validate response status code when 2 users created with the same login")
    @Story("Test-05")
    @Severity(SeverityLevel.CRITICAL)
    public void validateResponseCodeWhenTwoUsersCreatedWithTheSameLogin(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams) {
        logger.info("Case: " + testCase + " users with the same login.");

        Response response1 = new Rest
                .Builder(baseUrl, endpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .build()
                .executeRequest();

        List<RestParameter> updatedQueryParams = updateListItem("screenName", DataGenerator.getRandomString("10 Latin characters"), queryParams);

        Response response2 = new Rest
                .Builder(baseUrl, endpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(updatedQueryParams)
                .build()
                .executeRequest();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response1.getStatusCode(), 200);
        softAssert.assertNotEquals(response2.getStatusCode(), 200);
        softAssert.assertAll();
    }

    @Test(dataProvider = "create player - positive", description = "validate response status code when 2 users created with the same screenName")
    @Description("Validate response status code when 2 users created with the same screenName")
    @Story("Test-06")
    @Severity(SeverityLevel.CRITICAL)
    public void validateResponseCodeWhenTwoUsersCreatedWithTheSameScreenName(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams) {
        logger.info("Case: " + testCase + " users with the same screenName");

        Response response1 = new Rest
                .Builder(baseUrl, endpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .build()
                .executeRequest();

        List<RestParameter> updatedQueryParams = updateListItem("login", DataGenerator.getRandomString("10 Latin characters"), queryParams);

        Response response2 = new Rest
                .Builder(baseUrl, endpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(updatedQueryParams)
                .build()
                .executeRequest();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response1.getStatusCode(), 200);
        softAssert.assertNotEquals(response2.getStatusCode(), 200);
        softAssert.assertAll();
    }
}
