package listeners;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.reporters.IReporterConfig;
import org.testng.xml.XmlSuite;

import utils.CustomAnnotation.XrayTest;

import java.io.FileWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public  class XrayReporter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        try {
            FileWriter writer = new FileWriter(outputDirectory + "/xray-testng-results.xml");

            writer.write("<testng-results>\n");

            for (ISuite suite : suites) {
                Map<String, ISuiteResult> results = suite.getResults();

                for (ISuiteResult r : results.values()) {
                    ITestContext context = r.getTestContext();

                    writeResults(writer, context.getPassedTests().getAllResults(), "PASS");
                    writeResults(writer, context.getFailedTests().getAllResults(), "FAIL");
                    writeResults(writer, context.getSkippedTests().getAllResults(), "SKIP");
                }
            }

            writer.write("</testng-results>");
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	private void writeResults(FileWriter writer, Collection<ITestResult> results, String status) throws Exception {

        for (ITestResult result : results) {

            XrayTest annotation = result.getMethod()
                    .getConstructorOrMethod()
                    .getMethod()
                    .getAnnotation(XrayTest.class);

            if (annotation != null) {

                String testKey = annotation.key();

                writer.write("  <test>\n");
                writer.write("    <testKey>" + testKey + "</testKey>\n");
                writer.write("    <status>" + status + "</status>\n");
                writer.write("  </test>\n");
            }
        }
    }
}
