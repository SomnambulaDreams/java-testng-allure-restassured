package utils.listener;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;
import utils.retry.RetryAnalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


public class RetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}
