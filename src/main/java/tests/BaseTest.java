package tests;

import config.EnvConfig;
import config.TestConfig;
import utils.data.DataGenerator;
import utils.data.ExcelReader;
import utils.data.Header;
import io.restassured.RestAssured;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.rest.RestMethod;
import utils.rest.RestParameter;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class BaseTest {

    protected static final EnvConfig envConfig = EnvConfig.instance();
    protected static final TestConfig testConfig = TestConfig.instance();

    protected static String baseUrl = envConfig.baseUrl();
    protected static List<RestParameter> headers = Header.getDefaultHeaders(envConfig.host(), envConfig.referer());


    @BeforeMethod
    public void enableLogging() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
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
        List<RestParameter> queryParams = new LinkedList<>();

        queryParams.add(new RestParameter("login", DataGenerator.getValue((String) map.get("login"))));
        queryParams.add(new RestParameter("password", DataGenerator.getValue((String) map.get("password"))));
        queryParams.add(new RestParameter("screenName", DataGenerator.getValue((String) map.get("screenName"))));
        queryParams.add(new RestParameter("role", DataGenerator.getValue((String) map.get("role"))));
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
