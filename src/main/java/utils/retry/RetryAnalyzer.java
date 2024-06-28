package utils.retry;

import config.SuiteConfig;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import runner.TestRunner;

import java.util.logging.Logger;


public class RetryAnalyzer implements IRetryAnalyzer {

    private static final Logger logger = Logger.getLogger(TestRunner.class.getName());

    private static final int maxRetries = SuiteConfig.instance().maxRetries();
    private int counter = 1;

    @Override
    public boolean retry(ITestResult iTestResult) {
        logger.info("Test execution try: # " + counter + " of " + maxRetries);
        if (counter < maxRetries) {
            counter++;
            return true;
        }
        return false;
    }
}
