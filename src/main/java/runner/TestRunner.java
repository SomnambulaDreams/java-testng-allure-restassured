package runner;

import config.TestNgConfig;
import org.testng.TestNG;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


public class TestRunner {

    private static final Logger logger = Logger.getLogger(TestRunner.class.getName());
    private static final TestNgConfig testNgConfig = TestNgConfig.instance();
    private static final String suite = testNgConfig.suite();
    private static final int threadCount = testNgConfig.threadCount();
    private static final int dataProviderThreadCount = testNgConfig.dataProviderThreadCount();


    public static void main(String[] args) {
        TestNG testNG = new TestNG();
        testNG.setThreadCount(threadCount);
        testNG.setDataProviderThreadCount(dataProviderThreadCount);
        testNG.setTestSuites(suites());
        testNG.run();
    }


    private static List<String> suites() {
        List<String> suites = new LinkedList<>();
        switch (suite) {
            case "smoke":
                suites.add("suites/smoke.xml");
                logger.info(String.format("Suite %s added to execution", suite));
                break;
            case "regression":
                suites.add("suites/regression.xml");
                logger.info(String.format("Suite %s added to execution", suite));
                break;
            case "smoke3threads":
                suites.add("suites/smoke3threads.xml");
                logger.info(String.format("Suite %s added to execution", suite));
                break;
            case "regression3threads":
                suites.add("suites/regression3threads.xml");
                logger.info(String.format("Suite %s added to execution", suite));
                break;
            default:
                throw new RuntimeException("Unexpected value: " + suite + ".\nPlease check 'suite' field in 'testng.properties' file.");
        }
        return suites;
    }
}
