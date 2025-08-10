package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC002_ValidateLoginFunctionality extends BaseClass{
		
	@Test(dataProvider = "LoginData", dataProviderClass=DataProviders.class, retryAnalyzer = utilities.RetryAnalyzer.class) 	//dataProviderClass=DataProviders.class is used for getting data provider from different class or package
	public void verifyLogin(String user, String pwd, String exp)
	{
		logger.info("********** Starting TC002_ValidateLoginFunctionality : verifyLogin ************");
		
		try 
		{
			LoginPage lp = new LoginPage(driver);
			logger.info("Entering username: " + user);
			lp.enterUsername(user);
			logger.info("Entering password. "+ pwd);
			lp.enterPassword(pwd);
			logger.info("Clicking on Login button.");
			lp.clickLogin();
			
			DashboardPage dp = new 	DashboardPage(driver);	
			logger.info("Checking if Dashboard is loaded...");
			boolean targetPage = dp.isTimeAtWorkExist();
			
			if(exp.equalsIgnoreCase("Valid"))
			{
				logger.info("Expected login result: VALID ");
				if(targetPage==true)		//Data is valid and login also successful
				{
					logger.info("Login successful for valid credentials.");
					dp.clickLogoutPanel();
					dp.clickLogout();
					logger.info("Logged out successfully.");
					Assert.assertTrue(true);
				}
				else						//Data is valid but login is unsuccessful
				{
					logger.error("Login failed despite valid credentials.");
					String invalidCredentials = lp.errorLogin();
					logger.error("Checking error message with valid credentials.");
					if(invalidCredentials.equals("Invalid credentials"))
					{
						logger.info("Error message displayed : "+invalidCredentials);						
						Assert.assertTrue(false); 
					}
				}
			}
			if(exp.equalsIgnoreCase("Invalid"))
			{
				 logger.info("Expected login result: INVALID");
				if(targetPage==true)		//Data is Invalid but login also successful
				{
					logger.error("Login succeeded for invalid credentials (unexpected).");
					dp.clickLogoutPanel();
					dp.clickLogout();
					logger.info("Logged out successfully.");
					Assert.assertTrue(false);
				}
				else						//Data is Invalid and login is unsuccessful
				{
					logger.info("Login failed as expected for invalid credentials.");
					String invalidCredentials = lp.errorLogin();
					logger.error("Checking error message with invalid credentials.");
					if(invalidCredentials.equals("Invalid credentials"))
					{
						logger.info("Error message displayed : "+invalidCredentials);
						Assert.assertTrue(true); 
					}
					//Assert.assertTrue(true);
				}
			}
		} catch(Exception e)
		{
			logger.error("Exception occurred during login verification: " + e.getMessage(), e );
			Assert.fail();
		}
		logger.info("********** Finished TC002_ValidateLoginFunctionality : verifyLogin ************");
	}
}
