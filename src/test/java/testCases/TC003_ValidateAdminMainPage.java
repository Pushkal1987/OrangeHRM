package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AdminUserManagementPage;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC003_ValidateAdminMainPage extends BaseClass{
		
	@Test(priority =1)
	public void login()
	{
		
		logger.info("******* Started: TC003_ValidateAdminMainPage : login *********");
		boolean isLoginSuccessful = false;
		try
		{
			LoginPage lp = 	new LoginPage(driver);
			String username = p.getProperty("username");	// get username data from properties file
			String password = p.getProperty("password");	// get password data from properties file
			logger.info("Entering username: " + username);
			
			lp.enterUsername(username);						// pass the username data to the method in LoginPage class
			logger.info("Entering password: " + password);
			lp.enterPassword(password);	
			
			logger.info("Clicking on Login button.");
			lp.clickLogin();
			
			
			//Add a verification step to confirm login
	        DashboardPage dp = new DashboardPage(driver);
	        isLoginSuccessful = dp.isTimeAtWorkExist(); // some element visible only after successful login
	        
	        if (isLoginSuccessful) 
	        {
	        	logger.info("Login successful for valid credentials.");
	        } 
	        else 
	        {
	        	logger.info("Login failed as expected for invalid credentials.");
	        }
		} catch(Exception e)
		{
			logger.error("Exception occurred during login: " + e.getMessage(), e);
	        Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC003_ValidateAdminMainPage : login *********");
		Assert.assertTrue(isLoginSuccessful, "Login was not successful.");
	}
	
	@Test(priority =2, dependsOnMethods = "login")
	public void clickAdminMenu()
	{
		logger.info("******* Started: TC003_ValidateAdminMainPage : clickAdminMenu *********");
		try
		{
			DashboardPage dp = new DashboardPage(driver);
			logger.info("Clicked Admin menu successfully.");
			dp.menuSelection("Admin");			// Select Admin menu in menu panel
		} catch (Exception e) {
	        logger.error("Exception occurred while clicking Admin menu: " + e.getMessage(), e);
	        Assert.fail("Test failed due to exception while selecting Admin menu: " + e.getMessage());
	    }
		logger.info("******* Finished: TC003_ValidateAdminMainPage : clickAdminMenu *********");
	}
	
	@Test(priority =3, dependsOnMethods = {"login","clickAdminMenu"})
	public void verifySystemUser() 
	{
		logger.info("******* Started: TC003_ValidateAdminMainPage : verifySystemUser *********");
		try 
		{
			AdminUserManagementPage ap = new AdminUserManagementPage(driver);
			boolean systemUser= ap.systemUserIsDisplayed();
			
			if(systemUser)
			{
				logger.info("System User displayed successfully.");
				Assert.assertTrue(true);
			}
			else
			{
				logger.error("System User not displyed.");
				Assert.fail("System User not displyed");
			}
		} catch(Exception e)
		{
			logger.error("******* Exception occurred while verifying the logo: " + e.getMessage()+" *******");
	        e.printStackTrace(); // Optional, prints the full stack trace
	        Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC003_ValidateAdminMainPage : verifySystemUser *********");
	}
}
