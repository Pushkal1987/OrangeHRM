package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AdminUserManagementPage;
import pageObjects.DashboardPage;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC004_SearchUsersFunctionality extends BaseClass{
	
	// Login with valid credentials
	@Test (priority =1)
	public void login()
	{
		logger.info("******* Started: TC004_SearchUsersFunctionality : login *********");
		boolean isLoginSuccessful = false;
		try
		{
			LoginPage lp = 	new LoginPage(getDriver());
			String username = p.getProperty("username");	// get username data from properties file
			String password = p.getProperty("password");	// get password data from properties file
			logger.info("Entering username: " + username);
			lp.enterUsername(username);						// pass the username data to the method in LoginPage class
			logger.info("Entering password: " + password);
			lp.enterPassword(password);						// pass the password data to the method in LoginPage class
			logger.info("Clicking on Login button.");
			lp.clickLogin();
			
			//Add a verification step to confirm login
	        DashboardPage dp = new DashboardPage(getDriver());
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
		logger.info("******* Finished: TC004_SearchUsersFunctionality : login *********");
		Assert.assertTrue(isLoginSuccessful, "Login was not successful.");
	}
	
	@Test (priority =2, dependsOnMethods = "login")
	public void clickUserManagementMenu()
	{
		logger.info("******* Started: TC004_SearchUsersFunctionality : clickUserManagementMenu *********");
		
		try
		{
			DashboardPage dp = new DashboardPage(getDriver());
			logger.info("Click on Admin Menu");
			dp.menuSelection("Admin");
			AdminUserManagementPage ap = new AdminUserManagementPage(getDriver());
			logger.info("Click on User Management dropdown");
			ap.clickUserManagement();
			
			boolean systemUser= ap.systemUserIsDisplayed();
				
			logger.info("Validating System User heading is display");
				if(systemUser)
				{
					logger.info("System User displayed successfully.");
					Assert.assertTrue(systemUser, "System User is displayed successfully.");
				}
				else
				{
					logger.error("System User not displyed.");
					Assert.fail("System User not displayed");
				}
		} catch(Exception e)
		{
			logger.error("******* Exception occurred while verifying the logo: " + e.getMessage()+" *******");
	        e.printStackTrace(); // Optional, prints the full stack trace
	        Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC004_SearchUsersFunctionality : clickUserManagementMenu *********");
	}
	
	// Count number of rows
	@Test(priority =3, dependsOnMethods = {"login","clickUserManagementMenu"})
	public void testTableData() 
	{
		logger.info("******* Started: TC004_SearchUsersFunctionality : testTableData *********");
			
		AdminUserManagementPage ap = new AdminUserManagementPage(getDriver());
		int rowCount = ap.getRowCount();
		logger.info("Rows counts are: "+rowCount);
				
		logger.info("******* Finished: TC004_SearchUsersFunctionality : testTableData *********");
	}
	
	// Validate Search functionality
	@Test(priority =4, dependsOnMethods = {"login","clickUserManagementMenu"})
	public void validateSearchFunctionality() throws InterruptedException
	{
		logger.info("******* Started: TC004_SearchUsersFunctionality : validateSearchFunctionality *********");
		
		AdminUserManagementPage ap = new AdminUserManagementPage(getDriver());
		String expectedUsername = p.getProperty("creatUser");
		
		logger.info("Enter User Name: "+ expectedUsername);
		ap.enterUserName(expectedUsername);
		
		logger.info("Click on Search Button");
		ap.clickSearchButton();
		
		try 
        {
            logger.info("Checking if username '"+ expectedUsername +"' is present in the table.");

            boolean isPresent = ap.isUsernamePresent(expectedUsername);

            if (isPresent) 
            {
                logger.info("Username '"+expectedUsername+"' found successfully.");
                Assert.assertTrue(true);
            } else 
            {
                logger.error("Username '"+expectedUsername+"' not found.");
                Assert.fail("Username '"+expectedUsername+"' not found.");
            }

            // Asserting to make the test fail if username not found
            Assert.assertTrue(isPresent, "Username '" + expectedUsername + "' was not found in the table.");

        } catch (Exception e) {
            logger.error("An error occurred while checking username presence: {}", e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
		
		logger.info("******* Finished: TC004_SearchUsersFunctionality : validateSearchFunctionality *********");
	}
	
	@Test(priority =5, dependsOnMethods = {"login","clickUserManagementMenu","validateSearchFunctionality"})
	public void validateClickOnAddbutton()
	{
		logger.info("******* Started: TC004_SearchUsersFunctionality : validateClickOnAddbutton *********");
		
		try
		{		
			AdminUserManagementPage ap = new AdminUserManagementPage(getDriver());
			
			logger.info("Clicking on Add button");
			ap.clickAddButton();
			boolean isAddUser = ap.addUserIsDisplayed();
			
			logger.info("Validating 'Add User' is diaplay.");
			if(isAddUser)
			{
				logger.info("'Add User' is displayed successfully.");
				Assert.assertTrue(true);
			}
			else
			{
				logger.error("'Add User' is not displyed");
				Assert.fail("'Add User' is not displyed");
			}
		} catch(Exception e)
		{
			logger.error("Exception occurred while verifying the 'Add User': " + e.getMessage());
	        e.printStackTrace(); // Optional, prints the full stack trace
	        Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC004_SearchUsersFunctionality : validateClickOnAddbutton *********");			
	}	
	
	//@Test(priority =5, dependsOnMethods = {"login","clickUserManagementMenu","validateSearchFunctionality","validateClickOnAddbutton"})
	public void selectUserRole()
	{
		logger.info("******* Started: TC004_SearchUsersFunctionality : selectUserRole *********");
		AdminUserManagementPage ap = new AdminUserManagementPage(getDriver());
		logger.info("Click on User Role dropdown");
		ap.drpUserRole();
		logger.info("Select User Role option 'ESS'");
		ap.selectUserRoleOption("ESS");
		logger.info("Validating 'ESS' option is selected.");
		String selectedOption = ap.getSelectedUserRole();
		logger.info("Selected User Role is: " + selectedOption);
		// Validate if the selected option is "ESS"
		if (selectedOption.equalsIgnoreCase("ESS")) 
		{
			logger.info("ESS option is selected successfully.");
			Assert.assertTrue(true);
		} else 
		{
			logger.error("ESS option is not selected.");
			Assert.fail("ESS option is not selected.");
		}
		logger.info("******* Finished: TC004_SearchUsersFunctionality : selectUserRole *********");
	}
	
	@Test(priority =6, dependsOnMethods = {"login","clickUserManagementMenu","validateSearchFunctionality","validateClickOnAddbutton"})
	public void validateAddUserFunctionality() throws InterruptedException
	{
		logger.info("******* Started: TC004_SearchUsersFunctionality : validateAddUserFunctionality *********");

		AdminUserManagementPage ap = new AdminUserManagementPage(getDriver());
		logger.info("Click on User Role dropdown");
		ap.drpUserRole();
		ap.selectUserRoleOption("Admin");
		String employeeName = "Jobin Sam";
		
		//**************************************** Validation Pending***************************
		logger.info("Enter Employee Name: " + employeeName);
		ap.enterEmployeeName(employeeName);
		String statusOption = "Enabled";
		logger.info("Selecting Status: "+statusOption);
		ap.selectStatusOption(statusOption);
		logger.info("Enter Username: " + employeeName);
		ap.enterUsername(employeeName);
		String pwd ="admin@123";
		logger.info("Entering Password: "+pwd);
		ap.enterPassword(pwd);
		logger.info("Entering Confirm Password: "+pwd);
		ap.enterConfirmPassword(pwd);
		ap.clickSaveButton();
		logger.info("Save Button clicked successfully..");
		
		try 
        {
            logger.info("Checking if Employee Name '"+ employeeName +"' is present in the table.");

            boolean isPresent = ap.isEmployeeNamePresent(employeeName);

            if (isPresent) 
            {
                logger.info("Employee Name '"+employeeName+"' found successfully.");
                Assert.assertTrue(true);
            } else 
            {
                logger.error("Employee Name '"+employeeName+"' not found.");
                Assert.fail("Employee Name '"+employeeName+"' not found.");
            }

            // Asserting to make the test fail if username not found
            Assert.assertTrue(isPresent, "Employee Name '" + employeeName + "' was not found in the table.");

        } catch (Exception e) {
            logger.error("An error occurred while checking username presence: {}", e.getMessage());
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
		logger.info("******* Finished: TC004_SearchUsersFunctionality : validateAddUserFunctionality *********");
	}
}
