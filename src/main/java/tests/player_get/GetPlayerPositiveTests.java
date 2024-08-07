package tests.player_get;

import domain.responses.PlayerResponse;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.rest.RestParameter;

import java.util.List;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;


@Epic("Player controller")
@Feature("Get player")
public class GetPlayerPositiveTests extends GetPlayerTestData {

    private static final Logger logger = Logger.getLogger(GetPlayerPositiveTests.class.getName());

    @Test(dataProvider = "get player - initial users", description = "validate positive cases of getting initial users")
    @Description("Validate 2 positive cases of getting initial users:" +
            "\n- supervisor [id = 1]" +
            "\n- admin [id = 2]")
    @Story("Test-07")
    @Severity(SeverityLevel.BLOCKER)
    public void validateGettingInitialUsers(String testCase, Long id) {
        logger.info("Case: " + testCase);
        Response response = getExistingPlayer(id);
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(dataProvider = "get player - data for creating a user", description = "validate positive case of getting created user")
    @Description("Validate positive case of getting created user")
    @Story("Test-08")
    @Severity(SeverityLevel.BLOCKER)
    public void validateGettingCreatedUsers(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams) {
        logger.info("Case: " + testCase);

        PlayerResponse playerCreated = createPlayer(pathParams, queryParams).as(PlayerResponse.class);

        logger.info("Player ID: " + playerCreated.getId());
        PlayerResponse playerGot = getExistingPlayer(playerCreated.getId()).as(PlayerResponse.class);
        assertEquals(playerGot, playerCreated);
    }
}
