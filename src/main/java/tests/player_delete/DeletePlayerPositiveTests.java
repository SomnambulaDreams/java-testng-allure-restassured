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
public class DeletePlayerPositiveTests extends DeletePlayerTestData {

    private static final Logger logger = Logger.getLogger(DeletePlayerPositiveTests.class.getName());


    @Test(dataProvider = "delete player - data for creating users", description = "validate response status code when supervisor deletes admins account")
    @Description("Validate response status code when supervisor deletes admins account")
    @Story("Test-18")
    @Severity(SeverityLevel.CRITICAL)
    public void validateResponseWhenSupervisorDeletesAdminsAccount(List<RestParameter> queryParams1WithNoRole, List<RestParameter> queryParams2WithNoRole) {
        logger.info("Case: supervisor deletes admins account");

        Long adminId = createAdminByAdmin(queryParams1WithNoRole).as(PlayerResponse.class).getId();
        Response response = deletePlayer(pathParamsForSupervisor(), adminId);

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(dataProvider = "delete player - data for creating users", description = "validate response status code when supervisor deletes users account")
    @Description("Validate response status code when supervisor deletes users account")
    @Story("Test-19")
    @Severity(SeverityLevel.CRITICAL)
    public void validateResponseWhenSupervisorDeletesUsersAccount(List<RestParameter> queryParams1WithNoRole, List<RestParameter> queryParams2WithNoRole) {
        logger.info("Case: supervisor deletes users account");

        Long userId = createUserByAdmin(queryParams1WithNoRole).as(PlayerResponse.class).getId();
        Response response = deletePlayer(pathParamsForSupervisor(), userId);

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(dataProvider = "delete player - data for creating users", description = "validate response status code when admin deletes admins account")
    @Description("Validate response status code when admin deletes admins account")
    @Story("Test-20")
    @Severity(SeverityLevel.CRITICAL)
    public void validateResponseWhenAdminDeletesAdminsAccount(List<RestParameter> queryParams1WithNoRole, List<RestParameter> queryParams2WithNoRole) {
        logger.info("Case: admin deletes admins account");

        String admin1Login = createAdminByAdmin(queryParams1WithNoRole).as(PlayerResponse.class).getLogin();
        Long admin2Id = createAdminByAdmin(queryParams2WithNoRole).as(PlayerResponse.class).getId();
        Response response = deletePlayer(pathParamsForEditor(admin1Login), admin2Id);

        assertEquals(response.getStatusCode(), 200);
    }

    @Test(dataProvider = "delete player - data for creating users", description = "validate response status code when admin deletes users account")
    @Description("Validate response status code when admin deletes users account")
    @Story("Test-21")
    @Severity(SeverityLevel.BLOCKER)
    public void validateResponseWhenAdminDeletesUsersAccount(List<RestParameter> queryParams1WithNoRole, List<RestParameter> queryParams2WithNoRole) {
        logger.info("Case: admin deletes users account");

        String adminLogin = createAdminByAdmin(queryParams1WithNoRole).as(PlayerResponse.class).getLogin();
        Long userId = createUserByAdmin(queryParams2WithNoRole).as(PlayerResponse.class).getId();
        Response response = deletePlayer(pathParamsForEditor(adminLogin), userId);

        assertEquals(response.getStatusCode(), 403);
    }
}
