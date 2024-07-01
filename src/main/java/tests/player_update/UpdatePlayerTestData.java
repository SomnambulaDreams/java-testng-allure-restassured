package tests.player_update;

import domain.Role;
import domain.User;
import domain.requests.UpdatePlayerRequest;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import tests.BaseTest;
import utils.data.DataGenerator;
import utils.data.ExcelReader;
import utils.rest.RestMethod;
import utils.rest.RestParameter;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class UpdatePlayerTestData extends BaseTest {

    private static final String filepath = System.getProperty("user.dir") + File.separator + testConfig.playerUpdateFilepath();
    private static final ExcelReader testData = new ExcelReader(filepath);

    protected static String endpoint = endpoint();
    protected static RestMethod method = method();


    @DataProvider(name = "update player - data for updating supervisors account", parallel = true)
    public Object[][] getUsersDataForUpdatingSupervisorsAccount() {
        String sheetName = testConfig.sheetPositiveSupervisorOwn();
        return prepareDataForUpdatingSupervisorsAccount(sheetName);
    }


    @DataProvider(name = "update player - data for negative tests of access cases", parallel = true)
    public Object[][] getUsersDataForUpdatingPlayer() {
        String sheetName = testConfig.sheetNegative();
        return prepareDataForUpdatingPlayer(sheetName);
    }


    private static String endpoint() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if (content.size() == 1)
            return (String) content.get(0).get("endpoint");
        else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'update_player' data file.");
    }

    private static RestMethod method() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if (content.size() == 1) {
            String methodLabel = (String) content.get(0).get("method");
            return RestMethod.getValueByString(methodLabel);
        } else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'update_player' data file.");
    }

    protected Object[][] prepareDataForUpdatingSupervisorsAccount(String sheetName) {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(sheetName);

        Object[][] result = new Object[content.size()][3];
        for (int dataIndex = 0; dataIndex < content.size(); dataIndex++) {
            Map<String, Object> dataSample = content.get(dataIndex);
            result[dataIndex][0] = dataSample.get("testCase");

            List<RestParameter> pathParams = new LinkedList<>();
            pathParams.add(new RestParameter("editor", DataGenerator.getValue((String) dataSample.get("editor"))));
            pathParams.add(new RestParameter("id", DataGenerator.getValue((String) dataSample.get("id"))));
            result[dataIndex][1] = pathParams;

            result[dataIndex][2] = getRequestForUpdatingPlayer(dataSample);
        }
        return result;
    }

    protected Object[][] prepareDataForUpdatingPlayer(String sheetName) {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(sheetName);

        Object[][] result = new Object[content.size()][4];
        for (int dataIndex = 0; dataIndex < content.size(); dataIndex++) {
            Map<String, Object> dataSample = content.get(dataIndex);
            result[dataIndex][0] = dataSample.get("testCase");
            result[dataIndex][1] = dataSample.get("passive user");
            result[dataIndex][2] = dataSample.get("role");
            result[dataIndex][3] = getRequestForUpdatingPlayer(dataSample);
        }
        return result;
    }

    protected UpdatePlayerRequest getRequestForUpdatingPlayer(Map<String, Object> map) {
        return new UpdatePlayerRequest(
                DataGenerator.getValue((String) map.get("login")),
                DataGenerator.getValue((String) map.get("password")),
                DataGenerator.getValue((String) map.get("screenName")),
                map.get("role"),
                DataGenerator.getValue((String) map.get("gender")),
                DataGenerator.getValue((String) map.get("age")),
                (Boolean) map.get("ignoreNulls")
        );
    }

    protected Response createPlayerByRole(Role role) {
        List<RestParameter> pathParams = pathParamsForAdmin();
        return createPlayerByRole(pathParams, role);
    }

    protected List<RestParameter> pathParamsForAdmin() {
        return pathParamsForEditor(User.ADMIN_2.login);
    }

    protected List<RestParameter> pathParamsForEditor(String editor) {
        List<RestParameter> pathParams = new LinkedList<>();
        pathParams.add(new RestParameter("editor", editor));
        return pathParams;
    }
}
