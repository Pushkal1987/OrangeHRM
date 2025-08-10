package pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminUserManagementPage extends BasePage{

	public AdminUserManagementPage(WebDriver driver) 
	{
		super(driver);
	}
	
	// 	User Management dropdown WebElement
	@FindBy(xpath="//span[normalize-space()='User Management']")
	WebElement menuUserManagement;
	
	
	// Users option under Users Management menu
	@FindBy(xpath="//a[normalize-space()='Users']")
	WebElement optionUsers;
	
	// System User sub-heading WebElement
	@FindBy(xpath="//h5[normalize-space()='System Users']")
	WebElement headSystemUser;
		
	// Username text box WebElement
	@FindBy(xpath="//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']")
	WebElement txtUserName;
	
	// User Role dropdown WebElement
	@FindBy(xpath="(//div[@class='oxd-select-wrapper'])[1]")
	WebElement drpUserRole;
	
	// Employee Name text box WebElement
	@FindBy(xpath="//input[@placeholder='Type for hints...']")
	WebElement txtEmployeeName;
	
	// Status dropdown WebElement
	@FindBy(xpath="(//div[@class='oxd-select-wrapper'])[2]")
	WebElement drpStatus;
	
	// Reset button WebElement
	@FindBy(xpath="//button[normalize-space()='Reset']")
	WebElement btnReset;
	
	// Search button WebElement
	@FindBy(xpath="//button[normalize-space()='Search']")
	WebElement btnSearch;
	
	// Records Found text WebElement
	@FindBy(xpath="//span[contains(normalize-space(), 'Records Found')]")
	WebElement headRecordsFound;
	
	// Table Rows (excluding header)
	@FindBy(xpath="//div[@class='oxd-table']//div[2]//div[@class='oxd-table-card']")  
    List<WebElement> totalRows;
	
	// All Columns for each Row
	@FindBy(xpath="//div[@class='oxd-table-card']//div//div[c]")
	List <WebElement> totalColumns;
	
	// Username column
    @FindBy(xpath = "//div[@class='oxd-table-body']//div//div[2]")
    List<WebElement> usernameColumn;
    
    // User Role column
    @FindBy(xpath="//div[@class='oxd-table-body']//div//div[3]")
    List<WebElement> userRoleColumn;
    
    // Employee Name column
    @FindBy(xpath="//div[@class='oxd-table-body']//div//div[4]")
    List<WebElement> employeeNameColumn;
    
    // Status column
    @FindBy(xpath="//div[@class='oxd-table-body']//div//div[5]")
    List<WebElement> statusColumn;
	
	// Edit Buttons
    @FindBy(xpath = "//div[@class='oxd-table-body']//div//div[6]//div//button[2]")
    List<WebElement> editButtons;

    // Delete Buttons
    @FindBy(xpath = "//div[@class='oxd-table-body']//div//div[6]//div//button[1]")
    List<WebElement> deleteButtons;
    
    // Add button
    @FindBy(xpath="//button[normalize-space()='Add']")
    WebElement btnAdd;
    
    // Heading Add User
    @FindBy(xpath="//h6[normalize-space()='Add User']")
    WebElement headAddUser;
    
    

	// check System Users heading is display
	public boolean systemUserIsDisplayed()
	{
		return headSystemUser.isDisplayed();
	}
	
	// click on User Management and Users
	public void clickUserManagement()
	{
		menuUserManagement.click();
		optionUsers.click();
	}
	
	// Enter data in UserName
	public void enterUserName(String expectedUsername)
	{
		txtUserName.clear();
		txtUserName.sendKeys(expectedUsername);
	}
	
	// Click on User Role dropdown
	public void drpUserRole()
	{
		drpUserRole.click();
	}
	
	// Enter data in Employee Name
	public void enterEmployeeName()
	{
		txtEmployeeName.clear();
		txtEmployeeName.sendKeys("manda akhil user");
	}
	
	// Click on Status dropdown
	public void drpStatus()
	{
		drpStatus.click();
	}
	
	// Click on Reset button
	public void clickResetButton()
	{
		btnReset.click();
	}
	
	// Click on Search button
	public void clickSearchButton()
	{
		btnSearch.click();
	}

	public boolean recordsFoundIsDisplayed()
	{
		return headRecordsFound.isDisplayed();		
	}
	
	// --- Utility methods ---

	// Total rows count of table
    public int getRowCount() 
    {
        return totalRows.size();
    }
    
    public boolean isUsernamePresent(String expectedUsername) 
    {         
    	for (WebElement cell : usernameColumn) 
    	{
	        String cellText = cell.getText().trim();
	                
	        if (cellText.equalsIgnoreCase(expectedUsername.trim())) 
	        {                   
	        	return true;
	        }
    	}
    	return false;        
    }
    
    public void clickAddButton()
    {
    	btnAdd.click();
    }
    
    public boolean addUserIsDisplayed()
    {
		return headAddUser.isDisplayed();   	
    }
}
