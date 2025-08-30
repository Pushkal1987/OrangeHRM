package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC0001_VerifyUIElementsOnLoginPage extends BaseClass
{
	/* This class is intended to verify the UI elements on the login page.
	 * It will contain methods to check the presence of various UI components
	 * such as title, logo, username field, password field, login button and Forgot Password Link.
	 * You can implement your test methods here to validate the UI elements.
	 * 
	 * 
	 */
	
	//***************** Test Methods *****************
	
	// This test case verifies the title of the login page.
	@Test(priority = 0, retryAnalyzer = utilities.RetryAnalyzer.class, description = "Verify the title of the login page")
	public void verifyLoginPageTitle() 
	{		
		logger.info("******* Started: TC0001_VerifyUIElementsOnLoginPage : verifyLoginPageTitle *********");

		try 
		{		
			// Load expected title from properties file
			String expectedTitle = p.getProperty("expectedTitle"); 
			String actualTitle = getDriver().getTitle();
			logger.info("Expected title is: " + expectedTitle + ", Actual title is: " + actualTitle);
			// Check if the actual title matches the expected title
			logger.info("Verifying the login page title...");
			if(expectedTitle.equals(actualTitle))
			{
				// If the titles match, log the success message
				logger.info("Title matched successfully.");
				Assert.assertTrue(true);
			}
			else
			{
				// If the titles do not match, log the error message and fail the test
				logger.error("Title did not match. Expected: '" +expectedTitle + "' Found: " + actualTitle);
				Assert.fail("Title mismatch. Expected: '" + expectedTitle + "', Found: '" + actualTitle+ "'.");
			}

			// Uncomment the line below if you want to use Assert to verify the title
			//Assert.assertEquals(actualTitle, expectedTitle, "Login page title does not match.");
			
			logger.info("Login page title verified successfully.");
		} catch (Exception e) 
		{
			// Log the exception message and stack trace if an error occurs
			logger.error("Exception occurred while verifying the login page title: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC0001_VerifyUIElementsOnLoginPage : verifyLoginPageTitle *********");
	}

	// This test case verifies if the logo is displayed on the login page.
	@Test(priority = 1, retryAnalyzer = utilities.RetryAnalyzer.class, description = "Verify if the logo is displayed on the login page")
	public void verifyLogoIsDisplayed() 
	{
		logger.info("******* Started: TC0001_VerifyUIElementsOnLoginPage : verifyLogoIsDisplayed *********");

		try 
		{
			LoginPage loginPage = new LoginPage(getDriver());
			boolean isLogoDisplayed = loginPage.isLogoDisplayed();
			// Check if the logo is displayed
			logger.info("Checking if logo is displayed...");
			if (isLogoDisplayed) 
			{
				// If the logo is displayed, log the success message
				logger.info("Logo is displayed successfully.");
				Assert.assertTrue(true);
			} else 
			{
				// If the logo is not displayed, log the error message and fail the test
				logger.error("Logo is not displayed.");
				Assert.fail("Logo display verification failed.");
			}
		} catch (Exception e) 
		{
			// Log the exception message and stack trace if an error occurs
			logger.error("Exception occurred while verifying the logo: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC0001_VerifyUIElementsOnLoginPage : verifyLogoIsDisplayed *********");
	}

	// This test case verifies if the username label is displayed on the login page.
	@Test(priority = 2, retryAnalyzer = utilities.RetryAnalyzer.class, description = "Verify if the username label is displayed on the login page")
	public void verifyUsernameLabelIsDisplayed() 
	{
		logger.info("******* Started: TC0001_VerifyUIElementsOnLoginPage : verifyUsernameLabelIsDisplayed *********");

		try 
		{
			LoginPage loginPage = new LoginPage(getDriver());
			boolean isUsernameLabelDisplayed = loginPage.isUsernameLabelDisplayed();
			// Check if the username label is displayed
			logger.info("Checking if username label is displayed...");
			if (isUsernameLabelDisplayed) 
			{
				// If the username label is displayed, log the success message
				logger.info("Username label is displayed successfully.");
				Assert.assertTrue(true);
			} else 
			{
				// If the username label is not displayed, log the error message and fail the test
				logger.error("Username label is not displayed.");
				Assert.fail("Username label display verification failed.");
			}
		} catch (Exception e) 
		{
			// Log the exception message and stack trace if an error occurs
			logger.error("Exception occurred while verifying the username label: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC0001_VerifyUIElementsOnLoginPage : verifyUsernameLabelIsDisplayed *********");
	}
	
	// This test case verifies if the password label is displayed on the login page.
	@Test(priority = 3, retryAnalyzer = utilities.RetryAnalyzer.class, description = "Verify if the password label is displayed on the login page")
	public void verifyPasswordLabelIsDisplayed() 
	{
		logger.info("******* Started: TC0001_VerifyUIElementsOnLoginPage : verifyPasswordLabelIsDisplayed *********");

		try 
		{
			LoginPage loginPage = new LoginPage(getDriver());
			boolean isPasswordLabelDisplayed = loginPage.isPasswordLabelDisplayed();
			// Check if the password label is displayed
			logger.info("Checking if password label is displayed...");
			if (isPasswordLabelDisplayed) 
			{
				// If the password label is displayed, log the success message
				logger.info("Password label is displayed successfully.");
				Assert.assertTrue(true);
			} else 
			{
				// If the password label is not displayed, log the error message and fail the test
				logger.error("Password label is not displayed.");
				Assert.fail("Password label display verification failed.");
			}
		} catch (Exception e) 
		{
			// Log the exception message and stack trace if an error occurs
			logger.error("Exception occurred while verifying the password label: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC0001_VerifyUIElementsOnLoginPage : verifyPasswordLabelIsDisplayed *********");
	}
	
	// This test case verifies if the login button is displayed on the login page.
	@Test(priority = 4, retryAnalyzer = utilities.RetryAnalyzer.class, description = "Verify if the login button is displayed on the login page")
	public void verifyLoginButtonIsDisplayed() {
		logger.info("******* Started: TC0001_VerifyUIElementsOnLoginPage : verifyLoginButtonIsDisplayed *********");

		try 
		{
			LoginPage loginPage = new LoginPage(getDriver());
			boolean isLoginButtonDisplayed = loginPage.isLoginButtonDisplayed();
			// Check if the login button is displayed
			logger.info("Checking if login button is displayed...");
			if (isLoginButtonDisplayed) 
			{
				// If the login button is displayed, log the success message
				logger.info("Login button is displayed successfully.");
				Assert.assertTrue(true);
			} else 
			{
				// If the login button is not displayed, log the error message and fail the test
				logger.error("Login button is not displayed.");
				Assert.fail("Login button display verification failed.");
			}
		} catch (Exception e) 
		{
			// Log the exception message and stack trace if an error occurs
			logger.error("Exception occurred while verifying the login button: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC0001_VerifyUIElementsOnLoginPage : verifyLoginButtonIsDisplayed *********");
	}
	
	// This test case verifies if the "Forgot Password" link is displayed on the login page.
	@Test(priority = 5, retryAnalyzer = utilities.RetryAnalyzer.class, description = "Verify if the Forgot Password link is displayed on the login page")
	public void verifyForgotPasswordLinkIsDisplayed() 
	{
		logger.info(
				"******* Started: TC0001_VerifyUIElementsOnLoginPage : verifyForgotPasswordLinkIsDisplayed *********");

		try 
		{
			LoginPage loginPage = new LoginPage(getDriver());
			boolean isForgotPasswordLinkDisplayed = loginPage.isForgotPasswordLinkDisplayed();
			// Check if the Forgot Password link is displayed
			logger.info("Checking if Forgot Password link is displayed...");
			if (isForgotPasswordLinkDisplayed) 
			{
				// If the Forgot Password link is displayed, log the success message
				logger.info("Forgot Password link is displayed successfully.");
				Assert.assertTrue(true);
			} else 
			{
				// If the Forgot Password link is not displayed, log the error message and fail the test
				logger.error("Forgot Password link is not displayed.");
				Assert.fail("Forgot Password link display verification failed.");
			}
		} catch (Exception e) 
		{
			// Log the exception message and stack trace if an error occurs
			logger.error("Exception occurred while verifying the Forgot Password link: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info(
				"******* Finished: TC0001_VerifyUIElementsOnLoginPage : verifyForgotPasswordLinkIsDisplayed *********");
	}
}
