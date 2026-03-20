package listeners;

import java.io.File;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import base.DriverManager;
import utils.CustomAnnotation.Author;
import utils.CustomAnnotation.XrayTest;
import utils.ScreenShotUtils;

public class TestListener implements ITestListener, ISuiteListener{
	private ExtentReports extent = new ExtentReports();
	private static Map<String, ExtentTest> parentNodes = new ConcurrentHashMap<>();
	private static ThreadLocal<ExtentTest> childTest = new ThreadLocal<>();

	ScreenShotUtils sc = new ScreenShotUtils();

	@Override
	public void onStart(ISuite suite) {
		ExtentSparkReporter spark = new ExtentSparkReporter(new File("Test_Execution_Report_"+setDateTime() + ".html"));
		spark.config().setReportName("Test_Execution_Report_"+suite.getName());
		extent.attachReporter(spark);
		// Add environment/system details
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("OS Version", System.getProperty("os.version"));
		extent.setSystemInfo("Java Version", System.getProperty("java.version"));
		extent.setSystemInfo("User", System.getProperty("user.name"));

	}

	@Override
	public void onFinish(ISuite suite) {
		extent.flush();
	}

	@Override
	public void onStart(ITestContext context) {
		// Create parent node for each <test> tag
		String testName = context.getName(); // "Test" or "TestParallel"
		ExtentTest parent = extent.createTest(testName);
		parentNodes.put(testName, parent);

	}
	@Override
	public void onFinish(ITestContext context) {
	    int totalPassed = context.getPassedTests().size();
	    int totalFailed = context.getFailedTests().size();
	    int totalSkipped = context.getSkippedTests().size();

	    ExtentTest summary = extent.createTest("Execution Summary for <test>: " + context.getName());

	    // Log totals
	    summary.info("Total Passed: " + totalPassed);
	    summary.info("Total Failed: " + totalFailed);
	    summary.info("Total Skipped: " + totalSkipped);

	    // Log passed test methods
	    if (!context.getPassedTests().getAllResults().isEmpty()) {
	        StringBuilder passedMethods = new StringBuilder();
	        context.getPassedTests().getAllResults().forEach(result ->
	            passedMethods.append(result.getMethod().getMethodName()).append(", ")
	        );
	        summary.info("Passed Methods: " + passedMethods.toString().replaceAll(", $", ""));
	    }

	    // Log failed test methods
	    if (!context.getFailedTests().getAllResults().isEmpty()) {
	        StringBuilder failedMethods = new StringBuilder();
	        context.getFailedTests().getAllResults().forEach(result ->
	            failedMethods.append(result.getMethod().getMethodName()).append(", ")
	        );
	        summary.info("Failed Methods: " + failedMethods.toString().replaceAll(", $", ""));
	    }

	    // Log skipped test methods
	    if (!context.getSkippedTests().getAllResults().isEmpty()) {
	        StringBuilder skippedMethods = new StringBuilder();
	        context.getSkippedTests().getAllResults().forEach(result ->
	            skippedMethods.append(result.getMethod().getMethodName()).append(", ")
	        );
	        summary.info("Skipped Methods: " + skippedMethods.toString().replaceAll(", $", ""));
	    }
	}
	
	@Override
	public void onTestStart(ITestResult result) {
		String parentName = result.getTestContext().getName();
		ExtentTest parent = parentNodes.get(parentName);

		// Child node under parent
		ExtentTest child = parent.createNode(result.getMethod().getMethodName());
		  // Read @Author annotation
	    Author author = result.getMethod().getConstructorOrMethod()
	                          .getMethod()
	                          .getAnnotation(Author.class);
	    if (author != null) {
	        child.assignAuthor("Author: "+author.value());
	    }

		childTest.set(child);
		childTest.get().info("Starting test: " + result.getMethod().getMethodName());
		
		  Method method = result.getMethod().getConstructorOrMethod().getMethod();

		    if (method.isAnnotationPresent(XrayTest.class)) {
		        XrayTest xrayTest = method.getAnnotation(XrayTest.class);

		        String testKey = xrayTest.key();

		        // Set description
		        result.getMethod().setDescription(testKey);

		        // Force attribute (backup)
		        result.setAttribute("testKey", testKey);
		    }
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		childTest.get().pass("Test Passed: " + result.getMethod().getMethodName());
		childTest.remove();
		setXrayKey(result);
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String screenshotPath = sc.captureScreenshot(DriverManager.getDriver(), result.getMethod().getMethodName());
		childTest.get().fail("Test failed: " + result.getThrowable()).addScreenCaptureFromPath(screenshotPath);
		childTest.remove();
		setXrayKey(result);
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		childTest.get().skip("Test skipped: " + result.getMethod().getMethodName());
		childTest.remove();
		setXrayKey(result);
	}

	public String setDateTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
		return now.format(formatter);
	}
	
	 private void setXrayKey(ITestResult result) {
	        XrayTest annotation = result.getMethod()
	            .getConstructorOrMethod()
	            .getMethod()
	            .getAnnotation(XrayTest.class);

	        if (annotation != null) {
	            result.setAttribute("test_key", annotation.key());
	        }
	    }
}

