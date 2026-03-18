package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	private WebDriver driver;
	public LoginPage(WebDriver driver){
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css="input[name='email']")
	 WebElement userName;
	
	@FindBy(css="input[name='pass']")
	private WebElement password;
	
	@FindBy(css="button[type='submit']")
	private WebElement submit;
	

	public void enterUserName(String text) {
		userName.sendKeys(text);
	}	
	
	public void click() {
		submit.click();
	}
	
	public void enterUserPass(String text) {
		
		password.sendKeys(text);
	}
}
