package tests.player_create;

import org.testng.annotations.DataProvider;
import tests.BaseTest;
import utils.data.ExcelReader;
import utils.rest.RestMethod;

import java.io.File;
import java.util.List;
import java.util.Map;


public class CreatePlayerTestData extends BaseTest {

    private static final String filepath = System.getProperty("user.dir") + File.separator + testConfig.playerCreateFilepath();
    private static final ExcelReader testData = new ExcelReader(filepath);

    protected static String endpoint = endpoint();
    protected static RestMethod method = method();


    @DataProvider(name = "create player - positive", parallel = true)
    public Object[][] testDataForPositiveTests() {
        String sheetName = testConfig.sheetPositive();
        return prepareDataForCreatingPlayer(testData, sheetName);
    }

    @DataProvider(name = "create player - negative", parallel = true)
    public Object[][] testDataForNegativeTests() {
        String sheetName = testConfig.sheetNegative();
        return prepareDataForCreatingPlayer(testData, sheetName);
    }

    @DataProvider(name = "create player - combo of positive data and incorrect rest methods", parallel = true)
    public Object[] incorrectRestMethods() {
        Object[][] positive = testDataForPositiveTests();
        Object[] incorrectRestMethods = RestMethod.getValuesExcept(method).toArray();
        return combine(positive, incorrectRestMethods);
    }

    private static String endpoint() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if(content.size() == 1)
            return (String) content.get(0).get("endpoint");
        else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'create_player' data file.");
    }

    private static RestMethod method() {
        List<Map<String, Object>> content = testData.readTestingDataFromSheet(testConfig.sheetEndpoint());
        if(content.size() == 1) {
            String methodLabel = (String) content.get(0).get("method");
            return RestMethod.getValueByString(methodLabel);
        }
        else
            throw new RuntimeException("Please, check data in 'endpoint' sheet for 'create_player' data file.");
    }
}
