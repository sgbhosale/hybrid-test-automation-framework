package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import base.DriverManager;
import base.UiTestBase;
import pages.HomePage;
import utils.CustomAnnotation.XrayTest;

public class LoginTest extends UiTestBase{

	 private HomePage homePage;
	
	@BeforeMethod
	public void initPageObject() {
		homePage=new HomePage(DriverManager.getDriver());
	}
	
	@Test
	@XrayTest(key= "QUAL-4")
	public void test4() throws InterruptedException {
		SoftAssert softAssert=new SoftAssert();	
		homePage.clickCustomerLogin();
	
	}	
	
	
}
