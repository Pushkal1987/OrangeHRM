package testCases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import pageObjects.MenuPanel;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC0003_VerifyAvailableMenuOnMenuPanel extends BaseClass{
    	
	/* This class is intended to verify the menu panel functionality of the
	 * application.
	 * It will contain methods to interact with the menu panel and verify its
	 * behavior.
	 * You can implement your test methods here to validate the menu panel
	 * functionality.
	 * 
	 */

	// ***************** Test Methods *****************

	
	// Login with valid credentials
	@Test(priority = 0, retryAnalyzer = utilities.RetryAnalyzer.class, 
			description = "Login with valid credentials")
	public void loginWithValidCredentials() {
		
		logger.info("******* Started: TC0003_VerifyMenuPanel : loginWithValidCredentials *********");
		
		try {
			// Create an instance of the LoginPage page object
			LoginPage loginPage = new LoginPage(getDriver());

			// Perform login with valid credentials
			logger.info("Logging in with valid credentials...");
			
			String username =p.getProperty("validUsername");
            String password = p.getProperty("validPassword");
          
            // Enter username and password
            logger.info("Entering username: " + username);
            loginPage.enterUsername(username);
                        
            logger.info("Entering password: " + password);
            loginPage.enterPassword(password);
            
            logger.info("Clicking the login button...");
            loginPage.clickLogin();	
            
            DashboardPage dashboardPage = new DashboardPage(getDriver());
			// Verify successful login by checking the presence of the dashboard
            boolean isTimeAtWorkExistdDisplayed = dashboardPage.isTimeAtWorkExist();
            if (isTimeAtWorkExistdDisplayed==true) 
            {
            	logger.info("Login successful: Time at Work is displayed.");
				Assert.assertTrue(true, "Login successful and Time at Work is displayed.");
			} else 
			{
				logger.error("Login failed: Time at Work is not displayed after login.");
            	Assert.fail("Login failed: Time at Work is not displayed after login.");
			}
			logger.info("Login successful and Time at Work is displayed.");
		} catch (Exception e) {
			logger.error("Exception occurred while logging in with valid credentials: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}

		logger.info("******* Finished: TC0003_VerifyMenuPanel : loginWithValidCredentials *********");
	}
	
	// Login with valid credentials
	// and verify the presence of the logo on the menu panel.
	@Test(priority = 1, dependsOnMethods = "loginWithValidCredentials",
			retryAnalyzer = utilities.RetryAnalyzer.class, 
			description = "Verify the presence of the logo on the menu panel")
	public void verifyLogoOnMenuPanel() {
		logger.info("******* Started: TC0003_VerifyMenuPanel : verifyLogoOnMenuPanel *********");

		try 
		{
			// Create an instance of the MenuPanel page object
			MenuPanel menuPanel = new MenuPanel(getDriver());

			// Verify if the logo is displayed
			logger.info("Verifying if the logo is displayed on the menu panel...");
			boolean isLogoDisplayed = menuPanel.isLogoDisplayed();
			if (isLogoDisplayed) {
				logger.info("Logo is displayed on the menu panel.");
				Assert.assertTrue(true);
			} else {
				logger.error("Logo is not displayed on the menu panel.");
				Assert.fail("Logo is not displayed on the menu panel.");
			}
			logger.info("Logo on menu panel verified successfully.");
		} catch (Exception e) {
			logger.error("Exception occurred while verifying the logo on the menu panel: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC0003_VerifyMenuPanel : verifyLogoOnMenuPanel *********");
	}
	
	// Verify the available menu options in the menu panel.
	@Test(priority = 2, dependsOnMethods = "loginWithValidCredentials",
			dataProvider = "MenuListData", dataProviderClass=DataProviders.class, retryAnalyzer = utilities.RetryAnalyzer.class, 
			description = "Verify available menu options in the menu panel")
	public void verifyAllAvailableMenuOptionsOnMenuPanel(String menuName) {
		logger.info("******* Started: TC0003_VerifyMenuPanel : verifyAllAvailableMenuOptionsOnMenuPanel *********");

		try {
			// Create an instance of the MenuPanel page object
			MenuPanel menuPanel = new MenuPanel(getDriver());
			
			// List of expected menu options
			boolean isMenuDisplayed = menuPanel.isMenuDisplayed(menuName);
			// Verify if the expected menu option is displayed
			logger.info("Verifying if the menu option '" + menuName + "' is displayed in the menu panel...");
			if (isMenuDisplayed) 
			{
				logger.info("Menu option '" + menuName + "' is available in the menu panel.");
				Assert.assertTrue(true, "Menu option '" + menuName + "' is available in the menu panel.");
			} else 
			{
				logger.error("Menu option '" + menuName + "' is not available in the menu panel.");
				Assert.fail("Menu option '" + menuName + "' is not available in the menu panel.");
			}
		} catch (Exception e) {
			logger.error("Exception occurred while verifying the menu options: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC0003_VerifyMenuPanel : verifyAllAvailableMenuOptionsOnMenuPanel *********");
	}
	
	// Verify Search Box functionality in the menu panel.
	@Test(priority = 3, dependsOnMethods = "loginWithValidCredentials",
            retryAnalyzer = utilities.RetryAnalyzer.class, 
            description = "Verify Search Box functionality in the menu panel")
	public void verifySearchBoxFunctionality() {
		logger.info("******* Started: TC0003_VerifyMenuPanel : verifySearchBoxFunctionality *********");

		try 
		{
			// Create an instance of the MenuPanel page object
			MenuPanel menuPanel = new MenuPanel(getDriver());
			
			// Calling menu name from properties file
			logger.info("Retrieving the expected menu name from properties file...");
			String expectedMenuName = p.getProperty("menuOptionToSearch");
			
			// Pass the expected menu name to search box
			logger.info("Entering the expected menu name '" + expectedMenuName + "' in the search box...");
			menuPanel.enterSearchText(expectedMenuName);
			
			// Get menu names list from the menu panel
			logger.info("Retrieving all menu names from the menu panel...");
			List<String> actualMenuName = menuPanel.getAllMenuNames();
			
			// Verify if the expected menu name is present in the list
			logger.info("Verifying if the expected menu name '" + expectedMenuName + "' is present in the menu panel...");
			if (actualMenuName.contains(expectedMenuName)) 
			{
				logger.info("Search box functionality is working correctly. Expected menu: '" +expectedMenuName+"', Found menu: '" + actualMenuName+"'.");
				Assert.assertTrue(true,	"Search box functionality is working correctly. Expected menu: '" +expectedMenuName+"', Found menu: '" + actualMenuName+"'.");
			} else 
			{
				logger.error("Search box functionality failed. Expected menu '" + expectedMenuName + "' not found.");
				Assert.fail("Search box functionality failed. Expected menu '" + expectedMenuName + "' not found.");
			}			
		} catch (Exception e) {
			logger.error("Exception occurred while verifying the search box functionality: " + e.getMessage());
			e.printStackTrace();
			Assert.fail("Test failed due to exception: " + e.getMessage());
		}

		logger.info("******* Finished: TC0003_VerifyMenuPanel : verifySearchBoxFunctionality *********");
	}
}
