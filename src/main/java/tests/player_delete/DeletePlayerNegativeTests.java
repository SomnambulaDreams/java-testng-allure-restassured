package tests.player_delete;

import domain.responses.PlayerResponse;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.rest.RestParameter;

import java.util.List;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;


@Epic("Player controller")
@Feature("Delete player")
public class DeletePlayerNegativeTests extends DeletePlayerTestData {

    private static final Logger logger = Logger.getLogger(DeletePlayerNegativeTests.class.getName());


    @Test(description = "validate response status code when supervisor deletes their own account")
    @Description("Validate response status code when supervisor deletes their own account")
    @Story("Test-13")
    @Severity(SeverityLevel.BLOCKER)
    public void validateResponseWhenSupervisorDeletesOwnAccount() {
        logger.info("Case: supervisor deletes their own account");

        Response response = deletePlayer(pathParamsForSupervisor(), 1L);

        assertEquals(response.getStatusCode(), 403);
    }

    @Test(dataProvider = "delete player - data for creating users", description = "validate response status code when admin deletes supervisor's account")
    @Description("Validate response status code when admin deletes supervisor's account")
    @Story("Test-14")
    @Severity(SeverityLevel.BLOCKER)
    public void validateResponseWhenAdminDeletesSupervisorsAccount(List<RestParameter> queryParams1WithNoRole, List<RestParameter> queryParams2WithNoRole) {
        logger.info("Case: admin deletes supervisor's account");

        String adminLogin = createAdminByAdmin(queryParams1WithNoRole).as(PlayerResponse.class).getLogin();
        Response response = deletePlayer(pathParamsForEditor(adminLogin), 1L);

        assertEquals(response.getStatusCode(), 403);
    }

    @Test(dataProvider = "delete player - data for creating users", description = "validate response status code when user deletes supervisor's account")
    @Description("Validate response status code when user deletes supervisor's account")
    @Story("Test-15")
    @Severity(SeverityLevel.BLOCKER)
    public void validateResponseWhenUserDeletesSupervisorsAccount(List<RestParameter> queryParams1WithNoRole, List<RestParameter> queryParams2WithNoRole) {
        logger.info("Case: user deletes supervisor's account");

        String userLogin = createUserByAdmin(queryParams1WithNoRole).as(PlayerResponse.class).getLogin();
        Response response = deletePlayer(pathParamsForEditor(userLogin), 1L);

        assertEquals(response.getStatusCode(), 403);
    }

    @Test(dataProvider = "delete player - data for creating users", description = "validate response status code when user deletes admins account")
    @Description("Validate response status code when user deletes admins account")
    @Story("Test-16")
    @Severity(SeverityLevel.BLOCKER)
    public void validateResponseWhenUserDeletesAdminsAccount(List<RestParameter> queryParams1WithNoRole, List<RestParameter> queryParams2WithNoRole) {
        logger.info("Case: user deletes admins account");

        String userLogin = createUserByAdmin(queryParams1WithNoRole).as(PlayerResponse.class).getLogin();
        Long adminId = createAdminByAdmin(queryParams2WithNoRole).as(PlayerResponse.class).getId();
        Response response = deletePlayer(pathParamsForEditor(userLogin), adminId);

        assertEquals(response.getStatusCode(), 403);
    }

    @Test(dataProvider = "delete player - data for creating users", description = "validate response status code when user deletes users account")
    @Description("Validate response status code when user deletes users account")
    @Story("Test-17")
    @Severity(SeverityLevel.BLOCKER)
    public void validateResponseWhenUserDeletesUsersAccount(List<RestParameter> queryParams1WithNoRole, List<RestParameter> queryParams2WithNoRole) {
        logger.info("Case: user deletes users account");

        String user1Login = createUserByAdmin(queryParams1WithNoRole).as(PlayerResponse.class).getLogin();
        Long user2Id = createUserByAdmin(queryParams2WithNoRole).as(PlayerResponse.class).getId();
        Response response = deletePlayer(pathParamsForEditor(user1Login), user2Id);

        assertEquals(response.getStatusCode(), 403);
    }
}
