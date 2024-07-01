package tests.player_delete;

import domain.Role;
import domain.User;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import tests.BaseTest;
import utils.data.ExcelReader;
import utils.rest.RestMethod;
import utils.rest.RestParameter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DeletePlayerTestData extends BaseTest {

    private static final String filepath = System.getProperty("user.dir") + File.separator + testConfig.playerDeleteFilepath();
    private static final ExcelReader testData = new ExcelReader(filepath);

    protected static String endpoint = endpoint();
    protected static RestMethod method = method();


    @DataProvider(name = "delete player - data for creating users", parallel = true)
    public Object[][] getUsersDataForCreation() {
        String sheetName = testConfig.sheetPositive();
        return prepareDataForCreatingPlayer(testData, sheetName);
    }


    private static String endpoint() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if (content.size() == 1)
            return (String) content.get(0).get("endpoint");
        else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'delete_player' data file.");
    }

    private static RestMethod method() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if (content.size() == 1) {
            String methodLabel = (String) content.get(0).get("method");
            return RestMethod.getValueByString(methodLabel);
        } else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'delete_player' data file.");
    }

    protected List<RestParameter> pathParamsForSupervisor() {
        return pathParamsForEditor(Role.SUPERVISOR.value);
    }

    protected List<RestParameter> pathParamsForAdmin() {
        return pathParamsForEditor(User.ADMIN_1.login);
    }

    protected List<RestParameter> pathParamsForEditor(String editor) {
        List<RestParameter> pathParams = new LinkedList<>();
        pathParams.add(new RestParameter("editor", editor));
        return pathParams;
    }

    protected Object[][] prepareDataForCreatingPlayer(ExcelReader file, String sheetName) {
        List<Map<String, Object>> content = file.readTestingDataFromSheet(sheetName);

        Object[][] result = new Object[content.size()][2];
        for (int dataIndex = 0; dataIndex < content.size(); dataIndex++) {
            Map<String, Object> dataSample = content.get(dataIndex);
            result[dataIndex][0] = getQueryParamsForCreatingPlayerWithNoRole(dataSample);
            result[dataIndex][1] = getQueryParamsForCreatingPlayerWithNoRole(dataSample);
        }
        return result;
    }

    protected Response createAdminBySupervisor(List<RestParameter> queryParams) {
        List<RestParameter> pathParams = pathParamsForSupervisor();
        List<RestParameter> queryParamsUpdated = new LinkedList<>(queryParams);
        queryParamsUpdated.add(new RestParameter("role", Role.ADMIN.value));
        return createPlayer(pathParams, queryParamsUpdated);
    }

    protected Response createUserBySupervisor(List<RestParameter> queryParams) {
        List<RestParameter> pathParams = pathParamsForSupervisor();
        List<RestParameter> queryParamsUpdated = new LinkedList<>(queryParams);
        queryParamsUpdated.add(new RestParameter("role", Role.USER.value));
        return createPlayer(pathParams, queryParamsUpdated);
    }

    protected Response createAdminByAdmin(List<RestParameter> queryParams) {
        List<RestParameter> pathParams = pathParamsForAdmin();
        List<RestParameter> queryParamsUpdated = new LinkedList<>(queryParams);
        queryParamsUpdated.add(new RestParameter("role", Role.ADMIN.value));
        return createPlayer(pathParams, queryParamsUpdated);
    }

    protected Response createUserByAdmin(List<RestParameter> queryParams) {
        List<RestParameter> pathParams = pathParamsForAdmin();
        List<RestParameter> queryParamsUpdated = new LinkedList<>(queryParams);
        queryParamsUpdated.add(new RestParameter("role", Role.USER.value));
        return createPlayer(pathParams, queryParamsUpdated);
    }
}
