package tests.player_get_all;

import org.testng.annotations.DataProvider;
import tests.BaseTest;
import utils.data.ExcelReader;
import utils.rest.RestMethod;

import java.io.File;
import java.util.List;
import java.util.Map;


public class GetAllPlayersTestData extends BaseTest {

    private static final String filepath = System.getProperty("user.dir") + File.separator + testConfig.playerGetAllFilepath();
    private static final ExcelReader testData = new ExcelReader(filepath);

    protected static String endpoint = endpoint();
    protected static RestMethod method = method();


    @DataProvider(name = "get all players - data for creating a user", parallel = true)
    public Object[][] getUsersDataForCreation() {
        String sheetName = testConfig.sheetPositive();
        return prepareDataForCreatingPlayer(testData, sheetName);
    }

    @DataProvider(name = "get all players - incorrect rest methods", parallel = true)
    public Object[] incorrectRestMethods() {
        return RestMethod.getValuesExcept(method).toArray();
    }

    private static String endpoint() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if (content.size() == 1)
            return (String) content.get(0).get("endpoint");
        else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'get_all_player' data file.");
    }

    private static RestMethod method() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if (content.size() == 1) {
            String methodLabel = (String) content.get(0).get("method");
            return RestMethod.getValueByString(methodLabel);
        } else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'get_all_player' data file.");
    }
}
