package runner;

import config.SuiteConfig;
import org.testng.TestNG;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;


public class TestRunner {

    private static final Logger logger = Logger.getLogger(TestRunner.class.getName());
    private static final String suite = SuiteConfig.instance().suite();


    public static void main(String[] args) {
        TestNG testNG = new TestNG();
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
            default:
                throw new RuntimeException("Unexpected value: " + suite + ".\nPlease check 'suite' field in 'suite.properties' file.");
        }
        return suites;
    }
}
