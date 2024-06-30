package tests;

import config.EnvConfig;
import config.TestConfig;
import domain.Role;
import domain.requests.DeletePlayerRequest;
import domain.requests.GetPlayerRequest;
import domain.requests.UpdatePlayerRequest;
import domain.responses.PlayerResponse;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.data.DataGenerator;
import utils.data.ExcelReader;
import utils.data.Header;
import utils.listener.RestAssuredListener;
import utils.rest.Rest;
import utils.rest.RestParameter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static domain.Endpoint.*;


public class BaseTest {

    protected static final EnvConfig envConfig = EnvConfig.instance();
    protected static final TestConfig testConfig = TestConfig.instance();

    protected static String baseUrl = envConfig.baseUrl();
    protected static List<RestParameter> headers = Header.getDefaultHeaders(envConfig.host(), envConfig.referer());
    private static final List<RestParameter> updatedHeaders = new LinkedList<>(BaseTest.headers);

    static {
        updatedHeaders.add(new RestParameter("Content-Type", "application/json"));
    }


    @Step("Create player")
    public static Response createPlayer(List<RestParameter> pathParams, List<RestParameter> queryParams) {
        return new Rest
                .Builder(baseUrl, PLAYER_CREATE.route , PLAYER_CREATE.method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .build()
                .executeRequest();
    }

    @Step("Create player")
    public static Response createPlayerByRole(List<RestParameter> pathParams, Role role) {
        List<RestParameter> queryParams = new LinkedList<>();
        queryParams.add(new RestParameter("login", DataGenerator.getValue("string of 10 Latin chars")));
        queryParams.add(new RestParameter("password", DataGenerator.getValue("acceptable password")));
        queryParams.add(new RestParameter("screenName", DataGenerator.getValue("string of 10 Latin chars")));
        queryParams.add(new RestParameter("gender", DataGenerator.getValue("random gender")));
        queryParams.add(new RestParameter("age", DataGenerator.getValue("acceptable age")));
        queryParams.add(new RestParameter("role", role.value));

        return new Rest
                .Builder(baseUrl, PLAYER_CREATE.route , PLAYER_CREATE.method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .queryParams(queryParams)
                .build()
                .executeRequest();
    }

    @Step("Get existing player")
    public static Response getExistingPlayer(Long id) {
        return new Rest
                .Builder(baseUrl, PLAYER_GET.route, PLAYER_GET.method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(updatedHeaders)
                .body(new GetPlayerRequest(id).toJson())
                .build()
                .executeRequest();
    }

    @Step("Get existing player")
    public static Response getExistingPlayer(GetPlayerRequest request) {
        return new Rest
                .Builder(baseUrl, PLAYER_GET.route, PLAYER_GET.method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(updatedHeaders)
                .body(request.toJson())
                .build()
                .executeRequest();
    }

    @Step("Get all players")
    public static Response getAllPlayers() {
        return new Rest
                .Builder(baseUrl, PLAYER_GET_ALL.route, PLAYER_GET_ALL.method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .build()
                .executeRequest();
    }

    @Step("Delete player")
    public static Response deletePlayer(List<RestParameter> pathParams, Long id) {
        return new Rest
                .Builder(baseUrl, PLAYER_DELETE.route, PLAYER_DELETE.method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(headers)
                .pathParams(pathParams)
                .body(new DeletePlayerRequest(id).toJson())
                .build()
                .executeRequest();
    }

    @Step("Update player")
    public static Response updatePlayer(List<RestParameter> pathParams, UpdatePlayerRequest request) {
        return new Rest
                .Builder(baseUrl, PLAYER_UPDATE.route, PLAYER_UPDATE.method)
                .listeners(new AllureRestAssured(), new RestAssuredListener())
                .headers(updatedHeaders)
                .pathParams(pathParams)
                .body(request.toJson())
                .build()
                .executeRequest();
    }

    @Step("Restore Supervisor")
    public void restoreSupervisor() {
        String currentLogin = getExistingPlayer(1L).as(PlayerResponse.class).getLogin();

        if (!currentLogin.equalsIgnoreCase(Role.SUPERVISOR.value)) {

            List<RestParameter> pathParams = new LinkedList<>();
            pathParams.add(new RestParameter("editor", currentLogin));
            pathParams.add(new RestParameter("id", 1));

            UpdatePlayerRequest request = new UpdatePlayerRequest("supervisor", null, null, null, null, null, true);
            updatePlayer(pathParams, request);
        }
    }


    @BeforeMethod
    public void enableLogging() {
        restoreSupervisor();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        restoreSupervisor();
        if (result.getStatus() == ITestResult.FAILURE)
            result.getThrowable().printStackTrace(new PrintWriter(new StringWriter()));
    }

    protected Object[][] combine(Object[][] a, Object[][] b) {
        List<Object[]> combinedArraysList = new LinkedList<>();
        for (Object[] aItem : a)
            for (Object[] bItem : b)
                combinedArraysList.add(ArrayUtils.addAll(aItem, bItem));
        return combinedArraysList.toArray(new Object[0][0]);
    }

    protected Object[][] combine(Object[][] a, Object[] b) {
        List<Object[]> combinedArraysList = new LinkedList<>();
        for (Object[] aItem : a)
            for (Object bItem : b)
                combinedArraysList.add(ArrayUtils.addAll(aItem, bItem));
        return combinedArraysList.toArray(new Object[0][0]);
    }

    protected Object[][] prepareDataForCreatingPlayer(ExcelReader file, String sheetName) {
        List<Map<String, Object>> content = file.readTestingDataFromSheet(sheetName);

        Object[][] result = new Object[content.size()][3];
        for (int dataIndex = 0; dataIndex < content.size(); dataIndex++) {
            Map<String, Object> dataSample = content.get(dataIndex);
            result[dataIndex][0] = dataSample.get("testCase");

            List<RestParameter> pathParams = new LinkedList<>();
            pathParams.add(new RestParameter("editor", DataGenerator.getValue((String) dataSample.get("editor"))));
            result[dataIndex][1] = pathParams;

            result[dataIndex][2] = getQueryParamsForCreatingPlayer(dataSample);
        }
        return result;
    }

    protected List<RestParameter> getQueryParamsForCreatingPlayer(Map<String, Object> map) {
        List<RestParameter> queryParams = new LinkedList<>(getQueryParamsForCreatingPlayerWithNoRole(map));
        queryParams.add(new RestParameter("role", DataGenerator.getValue((String) map.get("role"))));
        return queryParams;
    }

    protected List<RestParameter> getQueryParamsForCreatingPlayerWithNoRole(Map<String, Object> map) {
        List<RestParameter> queryParams = new LinkedList<>();

        queryParams.add(new RestParameter("login", DataGenerator.getValue((String) map.get("login"))));
        queryParams.add(new RestParameter("password", DataGenerator.getValue((String) map.get("password"))));
        queryParams.add(new RestParameter("screenName", DataGenerator.getValue((String) map.get("screenName"))));
        queryParams.add(new RestParameter("gender", DataGenerator.getValue((String) map.get("gender"))));
        queryParams.add(new RestParameter("age", DataGenerator.getValue((String) map.get("age"))));

        return queryParams;
    }

    protected List<RestParameter> updateListItem(String key, String newValue, List<RestParameter> list) {
        List<RestParameter> result = new LinkedList<>();
        for (RestParameter listItem : list) {
            if (listItem.getKey().equals(key))
                result.add(new RestParameter(key, newValue));
            else
                result.add(listItem);
        }
        return result;
    }
}
