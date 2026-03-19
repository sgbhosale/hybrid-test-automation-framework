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

	
	@Test(description="QUAL-36: Add user to cartlist")
	public void addUserFunctionality() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();	
		loginPage.enterUserName("Admin");
		loginPage.enterUserPass("admin123");		
		Thread.sleep(10000);
	}	
}
