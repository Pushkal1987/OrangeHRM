package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DashboardPage extends BasePage{

	public DashboardPage(WebDriver driver) 
	{
		super(driver);		
	}
	
	// Time at Work panel name
	@FindBy(xpath="//p[normalize-space()='Time at Work']")
	WebElement txtTimeAtWorkExist;
	
	// Logout panel WebElement
	@FindBy(xpath="//i[@class='oxd-icon bi-caret-down-fill oxd-userdropdown-icon']")
	WebElement btnlogoutPanel;
	
	// Logout button WebElement
	@FindBy(xpath="//a[normalize-space()='Logout']")
	WebElement btnLogout;
	
	// To select menu's
	@FindBy(xpath="//ul[@class='oxd-main-menu']//li")
	List <WebElement> menuPanelList;
	
	// Logout panel click
	public void clickLogoutPanel() throws InterruptedException
	{
		btnlogoutPanel.click();
		//Thread.sleep(5000);
	}
	
	// Logout button click
	public void clickLogout()
	{
		// Sol 1
		btnLogout.click();
		// Sol 2
		//Select sc= new Select(btnLogout);
		//sc.selectByVisibleText("Logout");
	}
	
	// Verify Time at Work isdisplayed
	public boolean isTimeAtWorkExist()
	{
		try 
		{
			return txtTimeAtWorkExist.isDisplayed();
		} catch (Exception e)
		{
			return false;
		}
	}
	
	// Menu selection
	public void menuSelection(String menuName)
	{
		for (WebElement item : menuPanelList) 
		{
		    String menuText = item.getText().trim();
		    if (menuText.equalsIgnoreCase(menuName)) 
		    {
		        item.click();   // Click only this menu
		        break;          // Exit loop after clicking
		    }
		}			
	}
}
