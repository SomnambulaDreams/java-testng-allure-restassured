package tests.player_create;

import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.rest.RestParameter;

import java.util.List;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;


@Epic("Player controller")
@Feature("Create player")
public class CreatePlayerPositiveTests extends CreatePlayerTestData {

    private static final Logger logger = Logger.getLogger(CreatePlayerPositiveTests.class.getName());


    @Test(dataProvider = "create player - positive", description = "validate positive cases of users creation")
    @Description("Validate 4 cases of user creation:" +
            "\n- Supervisor creates an admin" +
            "\n- Supervisor creates a user" +
            "\n- Admin creates an admin" +
            "\n- Admin creates a user")
    @Story("Test-01")
    @Severity(SeverityLevel.BLOCKER)
    public void validatePositiveBusinessCases(String testCase, List<RestParameter> pathParams, List<RestParameter> queryParams) {
        logger.info("Case: " + testCase);

        Response response = createPlayer(pathParams, queryParams);

        assertEquals(response.getStatusCode(), 200);
    }
}
