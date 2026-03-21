package listeners;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;

import utils.XrayJsonGenerator;
import utils.CustomAnnotation.XrayTest;

public class XrayListener implements ITestListener ,ISuiteListener {
	

	public static class TestResultData {
	        public String testKey;
	        public String status;
	        public String comment;
	        public String start;
	        public String finish;

	        public TestResultData(String testKey, String status, String comment) {
	            this.testKey = testKey;
	            this.status = status;
	            this.comment = comment;
	            String now = java.time.OffsetDateTime.now().toString();
	            this.start = now;
	            this.finish = now;
	        }
	    }

	    public static List<TestResultData> results = new ArrayList<>();

	    private void captureResult(ITestResult result, String status, String comment) {
	        Method method = result.getMethod().getConstructorOrMethod().getMethod();

	        if (method.isAnnotationPresent(XrayTest.class)) {

	            String key = method.getAnnotation(XrayTest.class).key();	           

	            results.add(new TestResultData(key, status, comment));

	            System.out.println("[XRAY] Captured: " + key + " → " + status);
	        } else {
	            System.out.println("[XRAY][WARN]  No XrayTest annotation on: " + method.getName());
	        }
	    }

	    @Override
	    public void onTestSuccess(ITestResult result) {
	        captureResult(result, "PASSED", "Executed successfully & Passed.");
	    }

	    @Override
	    public void onTestFailure(ITestResult result) {
	        captureResult(result, "FAILED", "Executed successfully & Failed.");
	    }

	    @Override
	    public void onTestSkipped(ITestResult result) {
	        captureResult(result, "SKIPPED", "Executed successfully & Skipped.");
	    }    
	    @Override
		public void onFinish(ISuite suite) {
	    	  System.out.println("[XRAY] Generating JSON report...");
	          XrayJsonGenerator.generateJson();
		}
}
