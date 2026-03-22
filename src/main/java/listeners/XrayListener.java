package listeners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;

import model.TestResultData;
import utils.XrayJsonGenerator;
import utils.CustomAnnotation.XrayTest;

public class XrayListener implements ITestListener ,ISuiteListener {
	

	

	    public static List<TestResultData> results = new CopyOnWriteArrayList<>();

	    private void captureResult(ITestResult result, String status, String comment) {
	        Method method = result.getMethod().getConstructorOrMethod().getMethod();
	        String start = java.time.Instant.ofEpochMilli(result.getStartMillis()).toString();
	        String end = java.time.Instant.ofEpochMilli(result.getEndMillis()).toString();

	        if (method.isAnnotationPresent(XrayTest.class)) {

	            String key = method.getAnnotation(XrayTest.class).key();	           

	            results.add(new TestResultData(key, status, comment, start, end));

	            System.out.println("[XRAY] Captured: " + key + " → " + status);
	        } else {
	            System.out.println("[XRAY][WARN]  No XrayTest annotation on: " + method.getName());
	        }
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	        captureResult(result, "PASSED", "Test executed successfully");
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	    	 String comment = result.getThrowable() != null
	    	            ? result.getThrowable().getMessage()
	    	            : "Test Failed";

	    	    captureResult(result, "FAILED", comment);
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {
	        captureResult(result, "SKIPPED", "Test Skipped.");
	    }  
	    
	    @Override
		public void onFinish(ISuite suite) {
	    	  System.out.println("[XRAY] Generating JSON report...");
	          XrayJsonGenerator.generateJson();
		}
}
