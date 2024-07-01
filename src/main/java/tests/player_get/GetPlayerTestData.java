package tests.player_get;

import domain.requests.GetPlayerRequest;
import org.testng.annotations.DataProvider;
import tests.BaseTest;
import utils.data.DataGenerator;
import utils.data.ExcelReader;
import utils.rest.RestMethod;

import java.io.File;
import java.util.List;
import java.util.Map;


public class GetPlayerTestData extends BaseTest {

    private static final String filepath = System.getProperty("user.dir") + File.separator + testConfig.playerGetFilepath();
    private static final ExcelReader testData = new ExcelReader(filepath);

    protected static String endpoint = endpoint();
    protected static RestMethod method = method();


    @DataProvider(name = "get player - initial users", parallel = true)
    public Object[][] getInitialUsersIds() {
        Object[][] result = new Object[2][2];
        result[0][0] = "Get initial user: supervisor [id = 1]";
        result[0][1] = 1L;
        result[1][0] = "Get initial user: admin [id = 2]";
        result[1][1] = 2L;
        return result;
    }

    @DataProvider(name = "get player - incorrect request body", parallel = true)
    public Object[][] getIncorrectRequestBody() {
        Object[][] result = new Object[7][2];
        result[0][0] = "PlayerId ignored";
        result[0][1] = new GetPlayerRequest(DataGenerator.getValue("null"), true);
        result[1][0] = "PlayerId = null";
        result[1][1] = new GetPlayerRequest(DataGenerator.getValue("null"), false);
        result[2][0] = "PlayerId is empty";
        result[2][1] = new GetPlayerRequest(DataGenerator.getValue("empty"), false);
        result[3][0] = "PlayerId is space ' '";
        result[3][1] = new GetPlayerRequest(DataGenerator.getValue("space"), false);
        result[4][0] = "PlayerId is a random boolean";
        result[4][1] = new GetPlayerRequest(DataGenerator.getValue("random boolean"), false);
        result[5][0] = "PlayerId is a random double";
        result[5][1] = new GetPlayerRequest(DataGenerator.getValue("random double"), false);
        result[6][0] = "PlayerId is a random string";
        result[6][1] = new GetPlayerRequest(DataGenerator.getValue("string of 10 Latin chars"), false);
        return result;
    }

    @DataProvider(name = "get player - data for creating a user", parallel = true)
    public Object[][] getUsersDataForCreation() {
        String sheetName = testConfig.sheetPositive();
        return prepareDataForCreatingPlayer(testData, sheetName);
    }


    private static String endpoint() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if (content.size() == 1)
            return (String) content.get(0).get("endpoint");
        else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'get_player' data file.");
    }

    private static RestMethod method() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if (content.size() == 1) {
            String methodLabel = (String) content.get(0).get("method");
            return RestMethod.getValueByString(methodLabel);
        } else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'get_player' data file.");
    }
}
