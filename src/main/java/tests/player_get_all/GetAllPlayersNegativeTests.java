package tests.player_get_all;

import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.listener.RestAssuredListener;
import utils.rest.Rest;
import utils.rest.RestMethod;

import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;


@Epic("Player controller")
@Feature("Get All players")
public class GetAllPlayersNegativeTests extends GetAllPlayersTestData {

    private static final Logger logger = Logger.getLogger(GetAllPlayersNegativeTests.class.getName());

    @Test(dataProvider = "get all players - incorrect rest methods", description = "validate response status code when using incorrect REST method")
    @Description("Validate response status code when using incorrect REST method")
    @Story("Test-12")
    @Severity(SeverityLevel.NORMAL)
    public void validateResponseCodeWhenIncorrectRestMethod(RestMethod method) {
        logger.info("Case: Get all players when using incorrect REST method - " + method);

        Response response = new Rest
                .Builder(baseUrl, endpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .build()
                .executeRequest();
        assertEquals(response.getStatusCode(), 405);
    }
}
