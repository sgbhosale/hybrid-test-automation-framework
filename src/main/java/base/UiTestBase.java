package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class UiTestBase {

	@Parameters("browser")
	@BeforeMethod
	public void browserLaunch(@Optional("chrome")String browser) {
		if(browser.equalsIgnoreCase("chrome")) {
			ChromeOptions options=new ChromeOptions();
			options.addArguments();
			WebDriver webdriver=new ChromeDriver(options);
			DriverManager.setDriver(webdriver);
		}
		else if(browser.equalsIgnoreCase("firefox")) {
			DriverManager.setDriver(new FirefoxDriver());
		}
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
		DriverManager.getDriver().get("https://facebook.com");	
	}	
	
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		DriverManager.getDriver().quit();
		DriverManager.unload();;
	}
}
