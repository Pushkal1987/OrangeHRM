package pageObjects;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage{
	
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	// Logo xpath
	@FindBy(xpath="//div[@class='orangehrm-login-logo']//img[@alt='orangehrm-logo']")
	WebElement imglogo;
	
	// user name WebElement
	@FindBy(xpath="//input[@placeholder='Username']")
	WebElement txtUsername;
	
	// password WebElement
	@FindBy(xpath="//input[@placeholder='Password']")
	WebElement txtPassword;
		
	// login WebElement
	@FindBy(xpath="//button[normalize-space()='Login']")
	WebElement btnLogin;
	
	// Error message Invalid credentials
	@FindBy(xpath="//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
	WebElement msgInvalidCredentials;
	
	// logo is display method
	public boolean isLogoDisplayed()
	{
		// Wait until logo is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(imglogo));
		return imglogo.isDisplayed();
	}
	
	// Enter user name method
	public void enterUsername(String userName)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(txtUsername));
		txtUsername.clear();
		txtUsername.sendKeys(userName);
	}
	
	// Enter password method
	public void enterPassword(String password)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(txtPassword));
		txtPassword.clear();
		txtPassword.sendKeys(password);
	}
	
	// Click on login button
	public void clickLogin()
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(btnLogin));
		//Sol1
		btnLogin.click();
		
		//Sol2
		//btnLogin.submit();
		
		//Sol3
		//Actions act = new Actions(driver);
		//act.moveToElement(btnLogin).build().perform();
		
		//Sol4
		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("arguments[0].click();", btnLogin);
		
		//Sol5
		//btnLogin.sendKeys(Keys.RETURN);
		
		//Sol6
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//wait.until(ExpectedConditions.elementToBeClickable(btnLogin));
	}
	
	// Combined login method
    public void combinedLogin(String username, String password) 
    {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
	
	public String errorLogin()
	{
		return msgInvalidCredentials.getText();
	}
}
