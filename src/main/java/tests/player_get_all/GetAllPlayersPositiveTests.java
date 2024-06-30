package tests.player_get_all;

import domain.responses.GetAllPlayersResponse;
import domain.responses.PlayerResponse;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.rest.RestParameter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


@Epic("Player controller")
@Feature("Get All players")
public class GetAllPlayersPositiveTests extends GetAllPlayersTestData {

    private static final Logger logger = Logger.getLogger(GetAllPlayersPositiveTests.class.getName());

    @Test(description = "validate positive case of getting all initial users")
    @Description("Validate of getting all initial users:" +
            "\n- supervisor [id = 1]" +
            "\n- admin [id = 2]")
    @Story("Test-10")
    @Severity(SeverityLevel.CRITICAL)
    public void validateGettingInitialUsers() {
        logger.info("Case: Get 2 initial users from all players list");
        Response response = getAllPlayers();
        assertEquals(response.getStatusCode(), 200);

        GetAllPlayersResponse getAllPlayersResponse = response.as(GetAllPlayersResponse.class);
        long playersQuantity = getAllPlayersResponse.getPlayers().stream().filter(i -> i.getId() == 1 || i.getId() == 2).count();
        logger.info("Initial users found: " + playersQuantity);
        assertEquals(playersQuantity, 2L);
    }

    @Test(dataProvider = "get all players - data for creating a user", description = "validate positive case of getting created user")
    @Description("Validate positive case of getting created user")
    @Story("Test-11")
    @Severity(SeverityLevel.CRITICAL)
    public void validateGettingCreatedUsers(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams) {
        logger.info("Case: " + testCase);

        PlayerResponse playerCreated = createPlayer(pathParams, queryParams).as(PlayerResponse.class);
        logger.info("Player ID: " + playerCreated.getId());

        // TODO: workaround implemented to get full details of created player.
        //  When an issue of [POST] player/create/{editor} fixed, update the following
        PlayerResponse playerCreatedWithDetails = getExistingPlayer(playerCreated.getId()).as(PlayerResponse.class);

        GetAllPlayersResponse getAllPlayersResponse = getAllPlayers().as(GetAllPlayersResponse.class);
        Optional<PlayerResponse> playerGotOptional = getAllPlayersResponse.getPlayers().stream().filter(i -> Objects.equals(i.getId(), playerCreated.getId())).findFirst();
        assertTrue(playerGotOptional.isPresent());

        PlayerResponse playerGot = playerGotOptional.get();
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(playerGot.getId(), playerCreatedWithDetails.getId());
        softAssert.assertEquals(playerGot.getScreenName(), playerCreatedWithDetails.getScreenName());
        softAssert.assertEquals(playerGot.getGender(), playerCreatedWithDetails.getGender());
        softAssert.assertEquals(playerGot.getAge(), playerCreatedWithDetails.getAge());
        softAssert.assertAll();
    }
}
