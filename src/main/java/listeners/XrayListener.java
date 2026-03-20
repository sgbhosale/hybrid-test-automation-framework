package listeners;

import java.lang.reflect.Method;

import org.testng.ITestListener;
import org.testng.ITestResult;

import utils.CustomAnnotation.XrayTest;

public class XrayListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        Method method = result.getMethod().getConstructorOrMethod().getMethod();

        if (method.isAnnotationPresent(XrayTest.class)) {
            XrayTest xrayTest = method.getAnnotation(XrayTest.class);

            String testKey = xrayTest.key();

            // Inject into TestNG description (this goes into XML)
            result.getMethod().setDescription(testKey);

            // Optional: log for debugging
            System.out.println("Mapped Xray Test Key: " + testKey);
        }
    }
}