package tests.player_get;

import domain.requests.GetPlayerRequest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;


@Epic("Player controller")
@Feature("Get player")
public class GetPlayerNegativeTests extends GetPlayerTestData {

    private static final Logger logger = Logger.getLogger(GetPlayerNegativeTests.class.getName());


    @Test(dataProvider = "get player - incorrect request body", description = "validate response status code when incorrect request body")
    @Description("Validate response status code when incorrect request body")
    @Story("Test-09")
    @Severity(SeverityLevel.BLOCKER)
    public void validateResponseWhenIncorrectRequestBody(String testCase, GetPlayerRequest request) {
        logger.info("Case: " + testCase);
        Response response = getExistingPlayer(request);
        assertEquals(response.getStatusCode(), 400);
    }
}
