package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage{
	
	/* This class is intended to represent the Login Page of the application.
	 * It will contain methods to interact with the login page elements
	 * and verify the login functionality.
	 * You can implement your methods here to validate the login functionality.
	 * You can also add methods to handle login with valid and invalid credentials,
	 * and to verify error messages for invalid login attempts.
	 * You can also add methods to handle the Forgot Password functionality.
	 * 
	 * 
	 */
	
	// Constructor to initialize the WebDriver
	public LoginPage(WebDriver driver)
	{
		super(driver);
	}
	
	
	// ***************** Page Elements *****************
	
	// Logo xpath
	@FindBy(xpath="//div[@class='orangehrm-login-logo']//img[@alt='orangehrm-logo']")
	WebElement imglogo;
	
	// Username label xpath
	@FindBy(xpath="//label[normalize-space()='Username']")
	WebElement lblUsername;
	
	// Username text box xpath
	@FindBy(xpath="//input[@placeholder='Username']")
	WebElement txtUsername;
	
	// Usename text box blank - error message Required xpath
	@FindBy(xpath="(//span[normalize-space()='Required'])[1]")
	WebElement msgUsernameRequired;
	
	// Password label xpath
	@FindBy(xpath="//label[normalize-space()='Password']")
	WebElement lblPassword;
	
	// Password text box xpath
	@FindBy(xpath="//input[@placeholder='Password']")
	WebElement txtPassword;
	
	// Password text box blank - error message Required xpath
	@FindBy(xpath="(//span[normalize-space()='Required'])[2]")
	WebElement msgPasswordRequired;
		
	// login button xpath
	@FindBy(xpath="//button[normalize-space()='Login']")
	WebElement btnLogin;
	
	// Error message for Invalid credentials
	@FindBy(xpath="//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")
	WebElement msgInvalidCredentials;
	
	// Forgot password link xpath
	@FindBy(xpath="//p[@class='oxd-text oxd-text--p orangehrm-login-forgot-header']")
	WebElement lnkForgotPassword;
	
	// Reset Password heading xpath
	@FindBy(xpath="//h6[normalize-space()='Reset Password']")
	WebElement lblResetPassword;
		
	// ***************** Page Methods *****************
	
	// is logo displayed method
	public boolean isLogoDisplayed()
	{
		// Wait until logo is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(imglogo));
		return imglogo.isDisplayed();
	}
	
	// is User Name label displayed method
	public boolean isUsernameLabelDisplayed() 
	{
		// Wait until User Name lable is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(lblUsername));
		return lblUsername.isDisplayed();
	}

	// Enter user name method
	public void enterUsername(String userName)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(txtUsername));
		txtUsername.clear();
		txtUsername.sendKeys(userName);
	}
	
	// is Username text box blank error message displayed method
	public boolean isUsernameRequiredMessageDisplayed() 
	{
		// Wait until Username Required message is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(msgUsernameRequired));
		return msgUsernameRequired.isDisplayed();
	}
	
	// is Password label displayed method
	public boolean isPasswordLabelDisplayed() 
	{
		// Wait until Password lable is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(lblPassword));
		return lblPassword.isDisplayed();
	}
	
	// Enter password method
	public void enterPassword(String password)
	{
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(txtPassword));
		txtPassword.clear();
		txtPassword.sendKeys(password);
	}
	
	// is Password text box blank error message displayed method
	public boolean isPasswordRequiredMessageDisplayed() 
	{
		// Wait until Password Required message is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(msgPasswordRequired));
		return msgPasswordRequired.isDisplayed();
	}
	
	// is Login button displayed method
	public boolean isLoginButtonDisplayed() 
	{
		// Wait until Login button is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(btnLogin));
		return btnLogin.isDisplayed();
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
	
    // Method to verify error message for invalid credentials
	public String errorLogin()
	{
		return msgInvalidCredentials.getText();
	}
	
	// is Forgot Password link displayed method
	public boolean isForgotPasswordLinkDisplayed() 
	{
		// Wait until Forgot Password link is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(lnkForgotPassword));
		return lnkForgotPassword.isDisplayed();
	}
	
	// Click on Forgot Password link
	public void clickForgotPassword() 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(lnkForgotPassword));
		lnkForgotPassword.click();
	}
	
	// is Reset Password label displayed method
	public boolean isResetPasswordLabelDisplayed() 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(lblResetPassword));
		return lblResetPassword.isDisplayed();
	}
}
