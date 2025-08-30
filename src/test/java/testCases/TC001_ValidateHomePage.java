package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.LoginPage;
import testBase.BaseClass;

public class TC001_ValidateHomePage extends BaseClass{
	
	@Test(priority =1, retryAnalyzer = utilities.RetryAnalyzer.class)
	public void verifyTitle()
	{
		logger.info("******* Started: TC001_ValidateTitleOfThePage : verifyTitle *********");
		
		try
		{
			String title = getDriver().getTitle();
			System.out.println("Page title is: "+title);
			logger.info("Page title is: " + title);
			
			//Assert.assertEquals(title, "OrangeHRM");
			
			if(title.equals("OrangeHRM"))
			{
				logger.info("Title matched successfully.");
				Assert.assertTrue(true);
			}
			else
			{
				logger.error("Title did not match. Expected: OrangeHRM, Found: " + title);
				Assert.fail("Title mismatch.");
			}
			
		} catch (Exception e)
		{
			logger.error("Exception occurred while verifying the title: " + e.getMessage());
	        e.printStackTrace(); // Optional, prints the full stack trace
	        Assert.fail("Test failed due to exception: " + e.getMessage());
		}
				
		logger.info("******* Finished: TC001_ValidateTitleOfThePage : verifyTitle *********");
	}
	
	@Test(priority =2, retryAnalyzer = utilities.RetryAnalyzer.class)
	public void verifyLogo()
	{
		logger.info("******* Started: TC001_ValidateTitleOfThePage : verifyLogo *********");
		try 
		{
			LoginPage hp = new LoginPage(getDriver());
			boolean logo =hp.isLogoDisplayed();
			
			if(logo==true)
			{
				logger.info("Logo displayed successfully.");
				Assert.assertTrue(true);
			}
			else
			{
				logger.error("Logo not displyed");
				Assert.fail("Logo not displyed");
			}
		} catch(Exception e)
		{
			logger.error("Exception occurred while verifying the logo: " + e.getMessage());
	        e.printStackTrace(); // Optional, prints the full stack trace
	        Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("******* Finished: TC001_ValidateTitleOfThePage : verifyLogo *********");
	}
}
