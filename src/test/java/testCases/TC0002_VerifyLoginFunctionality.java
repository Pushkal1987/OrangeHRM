package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC0002_VerifyLoginFunctionality extends BaseClass 
{
	/* This class is intended
	 * to verify the login functionality of the application by using Data Driven Testing.
	 * It will contain methods to perform login with valid and invalid credentials
	 * and verify the expected outcomes.
	 * You can implement your test methods here to validate the login functionality.
	 *
	 *
	 */
	
	//***************** Test Methods *****************
	
	// This test case verifies the login functionality with valid and invalid credentials.
	// It uses Data Driven Testing to test both valid and invalid credentials.
	@Test(priority =0, dataProvider = "LoginData", dataProviderClass=DataProviders.class, retryAnalyzer = utilities.RetryAnalyzer.class, description = "Verify login functionality with valid and invalid credentials")
	public void verifyLoginFunctionality(String username, String password, String expectedResult) 
	{
		logger.info("********** Starting TC002_ValidateLoginFunctionality : verifyLoginFunctionality ************");
		
		try 
		{
			// Refresh the page to ensure a clean state before starting the test
			driver.navigate().refresh();
			
			// Fetching username and password from DataProvider
			LoginPage loginPage = new LoginPage(driver);
			logger.info("Entering username:- " + username);
			loginPage.enterUsername(username);
			logger.info("Entering password:- "+ password);
			loginPage.enterPassword(password);
			logger.info("Clicking on Login button.");
			loginPage.clickLogin();
					
			// After clicking login, we check if the dashboard page is loaded
			DashboardPage dashboardPage = new 	DashboardPage(driver);	
			logger.info("Checking if Dashboard is loaded...");
			// isTimeAtWorkExist() method checks if the target page is loaded
			boolean targetPage = dashboardPage.isTimeAtWorkExist();
			
			logger.info("Target page loaded: " + targetPage);
			if(expectedResult.equalsIgnoreCase("Valid"))	// Data is valid and login successful
			{
				// If the expected result is "Valid", we check if the login was successful
				logger.info("Expected login result: VALID ");
				if(targetPage==true)		//Data is valid and login also successful
				{
					// If the target page is loaded, it indicates a successful login
					logger.info("Login successful for valid credentials.");
					dashboardPage.clickLogoutPanel();
					dashboardPage.clickLogout();
					logger.info("Logged out successfully.");
					Assert.assertTrue(true," Login Test passed with valid credentials.");
				}
				else						//Data is valid but login is unsuccessful
				{
					// If the target page is not loaded, it indicates a failure in login
					logger.error("Login failed despite valid credentials.");
					String invalidCredentials = loginPage.errorLogin();
					logger.error("Checking error message with valid credentials.");
					if(invalidCredentials.equals("Invalid credentials"))
					{
						// If the error message matches, log the success
						logger.info("Error message displayed : "+invalidCredentials);						
						Assert.fail("Login Test failed with Valid credentials."); 
					}
				}
			}
			if(expectedResult.equalsIgnoreCase("Invalid"))
			{
				// If the expected result is "Invalid", we check if the login was unsuccessful
				logger.info("Expected login result: INVALID");
				if(targetPage==true)		//Data is Invalid but login also successful
				{
					// If the target page is loaded, it indicates a failure in login
					logger.error("Login successfull with invalid credentials (unexpected).");
					dashboardPage.clickLogoutPanel();
					dashboardPage.clickLogout();
					logger.info("Logged out successfully.");
					Assert.fail("Login successfully with Invalid credentials.");
				}
				else						//Data is Invalid and login is unsuccessful
				{
					// If the target page is not loaded, it indicates a successful failure in login
					logger.info("Login failed as expected for invalid credentials.");
					String invalidCredentials = loginPage.errorLogin();
					logger.error("Checking error message with invalid credentials.");
					if(invalidCredentials.equals("Invalid credentials"))
					{
						// If the error message matches, log the success
						logger.info("Error message displayed : "+invalidCredentials);
						Assert.assertTrue(true," Login Test passed with Invalid credentials." ); 
					}
					//Assert.assertTrue(true);
				}
			}
		} catch(Exception e)
		{
			// Log the exception if any error occurs during the verification
			logger.error("Exception occurred during login verification: " + e.getMessage(), e );
			Assert.fail();
		}
		logger.info("********** Finished TC002_ValidateLoginFunctionality : verifyLoginFunctionality ************");
	}
	
	// This test case verifies the error message displayed when the user tries to login with invalid credentials.
	@Test(priority =1, retryAnalyzer = utilities.RetryAnalyzer.class, description = "Verify Error message is displayed for invalid login credentials")
	public void verifyErrorMessageIsDisplayed() 
	{
		logger.info("********** Starting TC002_ValidateLoginFunctionality : verifyErrorMessageIsDisplayed ************");

		try 
		{
			// Refresh the page to ensure a clean state before starting the test
			driver.navigate().refresh();
			LoginPage loginPage = new LoginPage(driver);
			
			// Fetch invalid credentials from properties file
			String invalidUsername = p.getProperty("invalidUsername");
			String invalidPassword = p.getProperty("invalidPassword");
			
			logger.info("Entering username:- " + invalidUsername);
			loginPage.enterUsername(invalidUsername);
			logger.info("Entering password:- " + invalidPassword);
			loginPage.enterPassword(invalidPassword);
			logger.info("Clicking on Login button.");
			loginPage.clickLogin();
			
			// Fetch "Invalid credentials" error message from properties file
			String expectedErrorMessage = p.getProperty("invalidCredentialsMessage"); 
			String actualErrorMessage = loginPage.errorLogin();
			logger.info("Checking error message for invalid credentials.");
			
			// Validate the error message
			if(actualErrorMessage.equals(expectedErrorMessage))
			{
				// If the error message matches, log success
				logger.info("Error message matched successfully. Expected: '" + expectedErrorMessage + "', Found: '" + actualErrorMessage + "'");
				Assert.assertTrue(true);
			}
			else
			{
				// If the error message does not match, log failure
				logger.error("Error message did not match. Expected: '" +expectedErrorMessage + "' Found: " + actualErrorMessage);
				Assert.fail("Error message mismatch. Expected: '" + expectedErrorMessage + "', Found: '" + actualErrorMessage+ "'.");
			}
		} catch (Exception e) 
		{
			// Log the exception if any error occurs during the verification
			logger.error("Exception occurred during invalid login verification: " + e.getMessage(), e);
			Assert.fail();
		}

		logger.info("********** Finished TC002_ValidateLoginFunctionality :verifyErrorMessageIsDisplayed ************");
	}
}