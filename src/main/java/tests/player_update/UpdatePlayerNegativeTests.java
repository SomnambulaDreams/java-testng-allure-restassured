package tests.player_update;

import domain.requests.UpdatePlayerRequest;
import domain.responses.PlayerResponse;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.rest.RestParameter;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import static domain.Role.ADMIN;
import static domain.Role.USER;
import static org.testng.Assert.assertNotEquals;


@Epic("Player controller")
@Feature("Delete player")
public class UpdatePlayerNegativeTests extends UpdatePlayerTestData {

    private static final Logger logger = Logger.getLogger(UpdatePlayerNegativeTests.class.getName());


    @Test(dataProvider = "update player - data for negative tests of access cases",
            description = "validate response status code when player doesn't have access to update an another player"
    )
    @Description("Validate response status code when player doesn't have access to update an another player")
    @Story("Test-23")
    @Severity(SeverityLevel.CRITICAL)
    public void validateResponseWhenSupervisorDeletesAdminsAccount(String testCase, String roleForDeletion, String editorRole, UpdatePlayerRequest requestBodyObject) {
        logger.info("Case: " + testCase);

        String editorLogin = getPlayerLogin(editorRole);
        Long idForDeletion = getPlayerId(roleForDeletion);

        List<RestParameter> pathParams = new LinkedList<>();
        pathParams.add(new RestParameter("editor", editorLogin));
        pathParams.add(new RestParameter("id", idForDeletion));

        Response response = updatePlayer(pathParams, requestBodyObject);

        assertNotEquals(response.getStatusCode(), 200);
    }

    private long getPlayerId(String role) {
        switch (role) {
            case "supervisor":
                return 1L;
            case "admin":
                return createPlayerByRole(ADMIN).as(PlayerResponse.class).getId();
            case "user":
                return createPlayerByRole(USER).as(PlayerResponse.class).getId();
            default:
                throw new RuntimeException("Unexpected value");
        }
    }

    private String getPlayerLogin(String role) {
        switch (role) {
            case "admin":
                return createPlayerByRole(ADMIN).as(PlayerResponse.class).getLogin();
            case "user":
                return createPlayerByRole(USER).as(PlayerResponse.class).getLogin();
            default:
                throw new RuntimeException("Unexpected value");
        }
    }
}
