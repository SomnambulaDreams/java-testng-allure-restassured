package tests.player_get;

import domain.requests.GetPlayerRequest;
import domain.responses.PlayerResponse;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.listener.RestAssuredListener;
import utils.rest.Rest;
import utils.rest.RestMethod;
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

    @Test(dataProvider = "get player - data for creating a user", description = "validate positive cases of getting created users")
    @Description("Validate 2 positive cases of getting created users")
    @Story("Test-08")
    @Severity(SeverityLevel.BLOCKER)
    public void validateGettingCreatedUsers(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams) {
        logger.info("Case: " + testCase);

        PlayerResponse playerCreated = createPlayer(pathParams, queryParams).as(PlayerResponse.class);

        logger.info("Player ID: " + playerCreated.getId());
        PlayerResponse playerGot = getExistingPlayer(playerCreated.getId()).as(PlayerResponse.class);
        assertEquals(playerGot, playerCreated);
    }

    private Response createPlayer(List<RestParameter> pathParams, List<RestParameter> queryParams) {
        return new Rest
                .Builder(baseUrl, "/player/create/{editor}", RestMethod.GET)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .build()
                .executeRequest();
    }

    private Response getExistingPlayer(Long id) {
        return new Rest
                .Builder(baseUrl, endpoint, method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .body(new GetPlayerRequest(id).toJson())
                .build()
                .executeRequest();
    }
}
