package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.DriverManager;
import base.UiTestBase;
import pages.LoginPage;

public class LoginTest extends UiTestBase{

	private LoginPage loginPage;
	
	@BeforeMethod
	public void initPageObject() {
		loginPage=new LoginPage(DriverManager.getDriver());
	}

	
	@Test
	public void verifyText() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();	
		loginPage.enterUserName("Admin");
	//	loginPage.enterText("admin123");
		loginPage.click();
		Thread.sleep(10000);
	}	
}
