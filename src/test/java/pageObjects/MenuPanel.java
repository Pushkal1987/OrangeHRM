package pageObjects;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MenuPanel extends BasePage {
	/* This class is intended to represent the Menu Panel of the application.
	 * It will contain methods to interact with the menu panel and verify its behavior.
	 * You can implement your methods here to validate the menu panel functionality.
	 * 
	 */
	
	
    // Constructor to initialize the WebDriver
	public MenuPanel(WebDriver driver) {
		super(driver);
	}

	// ***************** Page Elements *****************

	// Logo xpath
	@FindBy(xpath="//img[@alt='client brand banner']")
	WebElement imgLogo;

	// Menu list xpath
	@FindBy(xpath="(//ul[@class='oxd-main-menu'])//li")
	public List<WebElement> menuLists;
	
	// Search box xpath
	@FindBy(xpath="//input[@placeholder='Search']")
	WebElement txtSearchBox;
	
	// ***************** Page Methods *****************
	
	// Method to check if the logo is displayed
	public boolean isLogoDisplayed() {
		// Wait until logo is displayed
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.visibilityOf(imgLogo));
		// Check if the logo is displayed
		return imgLogo.isDisplayed();
	}	
	
	// Method to click on the "Admin" menu
	public void clickAdminMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Admin")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "PIM" menu
	public void clickPIMMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("PIM")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "Leave" menu
	public void clickLeaveMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Leave")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "Time" menu
	public void clickTimeMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Time")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "Recruitment" menu
	public void clickRecruitmentMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Recruitment")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "My Info" menu
	public void clickMyInfoMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("My Info")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "Performance" menu
	public void clickPerformanceMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Performance")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "Dashboard" menu
	public void clickDashboardMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Dashboard")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "Directory" menu
	public void clickDirectoryMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Directory")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "Maintenance" menu
	public void clickMaintenanceMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Maintenance")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to click on the "Buzz" menu
	public void clickBuzzMenu() {
		for (WebElement menu : menuLists) {
			if (menu.getText().equalsIgnoreCase("Buzz")) {
				menu.click();
				break;
			}
		}
	}
	
	// Method to get all menu names
	public List<String> getAllMenuNames() {
	    List<String> menuNames = new ArrayList<>();
	    for (WebElement menu : menuLists) {
	        String text = menu.getText().trim();
	        if (!text.isEmpty()) {
	            menuNames.add(text);
	        }
	    }
	    return menuNames;
	}
	
	// Method to verify if the menu is displayed
	public boolean isMenuDisplayed(String menuName) {
	    for (WebElement menu : menuLists) {
	    	String actualMenuName = menu.getText();
	        if (actualMenuName != null && actualMenuName.trim().equalsIgnoreCase(menuName)) 
	        {	            
	            System.out.println("Menu: '" + actualMenuName + "' is displayed.");
	            return true; // found → return true immediately
	        }
	    }
	    return false; // not found → return false
	}
	
	// Method to enter text in the search box
	public void enterSearchText(String searchText) {
		// Wait until the search box is visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(txtSearchBox));

		// Clear the search box and enter the text
		txtSearchBox.clear();
		txtSearchBox.sendKeys(searchText);
	}
}
