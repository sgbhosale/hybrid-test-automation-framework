package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import utils.XrayJsonGenerator;

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
			FirefoxOptions firefoxOptions = new FirefoxOptions();
		    firefoxOptions.addArguments("--headless");
			DriverManager.setDriver(new FirefoxDriver(firefoxOptions));
		}
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
		DriverManager.getDriver().get("https://www.globalsqa.com/angularJs-protractor/BankingProject");	
	}	
	
	
	@AfterMethod(alwaysRun=true)
	public void tearDown() {
		DriverManager.getDriver().quit();
		DriverManager.unload();;
	}
	
}
