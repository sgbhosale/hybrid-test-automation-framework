package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.DriverManager;
import base.UiTestBase;
import pages.LoginPage;
import utils.CustomAnnotation.XrayTest;

public class LoginTest extends UiTestBase{

	private LoginPage loginPage;
	
	@BeforeMethod
	public void initPageObject() {
		loginPage=new LoginPage(DriverManager.getDriver());
	}

	
	@Test(description="Login Test case")
	@XrayTest(key = "QUAL-2")
	public void verifyText() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();	
		loginPage.enterUserName("Admin");
		loginPage.enterUserPass("admin123");		
		Thread.sleep(10000);
	}	
}
