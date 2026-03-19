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

	
	@Test(description="QUAL-3")
	public void addUser() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();	
		loginPage.enterUserName("Admin");
		loginPage.enterUserPass("admin123");		
		Thread.sleep(10000);
	}	
	
	@Test(description="QUAL-4: change group")
	public void addgroup() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();	
		loginPage.enterUserName("Admin");
		loginPage.enterUserPass("admin123");		
		Thread.sleep(10000);
	}	
}
