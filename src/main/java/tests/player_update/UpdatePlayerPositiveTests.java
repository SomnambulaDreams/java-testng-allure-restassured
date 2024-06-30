package tests.player_update;

import domain.requests.UpdatePlayerRequest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.rest.RestParameter;

import java.util.List;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;


@Epic("Player controller")
@Feature("Delete player")
public class UpdatePlayerPositiveTests extends UpdatePlayerTestData {

    private static final Logger logger = Logger.getLogger(UpdatePlayerPositiveTests.class.getName());


    @Test(dataProvider = "update player - data for updating supervisors account", description = "validate response status code when supervisor updates their own account")
    @Description("Validate response status code when supervisor updates their own account")
    @Story("Test-22")
    @Severity(SeverityLevel.CRITICAL)
    public void validateResponseWhenSupervisorDeletesAdminsAccount(String testCase, List<RestParameter> pathParams, UpdatePlayerRequest requestBodyObject) {
        logger.info("Case: " + testCase);

        // TODO: after test execution supervisor's login was set as empty value, which is meets requirements but blocks futher tests execution.
        //  It's impossible to update supervisor's account in this state due to 403 Forbidden error
        //  For now, it's a blocker for automation tests related to supervisor's activity.
        Response response = updatePlayer(pathParams, requestBodyObject);

        assertEquals(response.getStatusCode(), 200);
    }
}
