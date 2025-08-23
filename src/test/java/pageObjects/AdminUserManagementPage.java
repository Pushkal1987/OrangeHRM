package pageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdminUserManagementPage extends BasePage{

	public AdminUserManagementPage(WebDriver driver) 
	{
		super(driver);
	}
	
	// ***************** User Management Page Elements *****************
		// 	User Management dropdown WebElement
		@FindBy(xpath="//span[normalize-space()='User Management']")
		WebElement menuUserManagement;	
		// Users option under Users Management menu
		@FindBy(xpath="//a[normalize-space()='Users']")
		WebElement optionUsers;
		// System Users sub-heading WebElement
		@FindBy(xpath="//h5[normalize-space()='System Users']")
		WebElement headSystemUser;
	    // icon caret fill WebElement
		@FindBy(xpath="//i[@class='oxd-icon bi-caret-up-fill']")
		WebElement iconCaretFillDown;
	
	// ***************** Search User Elements *****************
		// Username text box WebElement
		@FindBy(xpath="//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@class='oxd-input oxd-input--active']")
		WebElement txtUserName;
		
		// ***************** User Role dropdown options *****************
			// User Role dropdown WebElement
			@FindBy(xpath="(//div[@class='oxd-select-wrapper'])[1]")
			WebElement drpUserRole;
		    // User Role dropdown options "Select"
		    @FindBy(xpath="//div[@role='listbox']//div[1]")
		    WebElement optSelect;  
		    // User Role dropdown options "Admin"
		    @FindBy(xpath="(//div[@role='listbox'])//div[2]")
		    WebElement optAdmin;
		    // User Role dropdown option "ESS"
		    @FindBy(xpath="//div[@role='listbox']//div[3]")    
		    WebElement optESS;
	    
		// ***************** Employee Name Elements *****************
			// Employee Name text box WebElement
			@FindBy(xpath="//input[@placeholder='Type for hints...']")
			WebElement txtEmployeeName;
			
			@FindBy(xpath="//div[@role='listbox']")
			List<WebElement> listEmployeeName;
		
		// ***************** Status Options *****************
			// Status dropdown WebElement
			@FindBy(xpath="(//div[@class='oxd-select-wrapper'])[2]")
			WebElement drpStatus;
			// Select Status dropdown options "Select"
		    @FindBy(xpath="//div[@role='listbox']//div[1]")
		    WebElement optSelectStatus;
		    // Select Status dropdown options "Enabled"
		    @FindBy(xpath="//div[@role='listbox']//div[2]")
		    WebElement optEnabled;
		    // Select Status dropdown options "Disabled"
		    @FindBy(xpath="//div[@role='listbox']//div[3]")
		    WebElement optDisabled;
	    
		// Reset button WebElement
		@FindBy(xpath="//button[normalize-space()='Reset']")
		WebElement btnReset;
		// Search button WebElement
		@FindBy(xpath="//button[normalize-space()='Search']")
		WebElement btnSearch;
	
	// ***************** Records Found Table WebElements *****************
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
    
    // ***************** Click on +Add button *****************
	    // Add button
	    @FindBy(xpath="//button[normalize-space()='Add']")
	    WebElement btnAdd;
	    // ***************** Add User page Elements *****************
		    // Heading Add User
		    @FindBy(xpath="//h6[normalize-space()='Add User']")
		    WebElement headAddUser;
	    
	    // ***************** User Role dropdown options *****************
		    // Use Search Users Elements
		
		// ***************** Employee Name Elements ***************** 
		    // Use Search Users Elements
	    
	    // ***************** Status dropdown options *****************
		    // Use Search Users Elements
		    
	    // Username text box WebElement
		    // Use Search Users Elements
		
		// Password text box WebElement
		    @FindBy(xpath="//div[@class='oxd-grid-item oxd-grid-item--gutters user-password-cell']//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@type='password']")
		    WebElement txtPassword;
		// Confirm Password text box WebElement
			@FindBy(xpath = "//div[@class='oxd-grid-item oxd-grid-item--gutters']//div[@class='oxd-input-group oxd-input-field-bottom-space']//div//input[@type='password']")
			WebElement txtConfirmPassword;
		// Cancel button WebElement
			@FindBy(xpath="//button[normalize-space()='Cancel']")
			WebElement btnCancel;
		// Save button WebElement
			@FindBy(xpath="//button[normalize-space()='Save']")
			WebElement btnSave;
		    
    // ***************** Page Methods *****************
			
    // Check if User Management menu is displayed
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
    
    // Click on Add User button to add new user
    public void clickAddButton()
    {
    	btnAdd.click();
    }
    
    // Check if Add User heading is displayed
    public boolean addUserIsDisplayed()
    {
		return headAddUser.isDisplayed();   	
    }
    
    // Select User Role option "ESS"
    public void selectUserRoleOption(String userRoleSelection)
    {
    	switch (userRoleSelection.toLowerCase()) 
    	{
    		case "admin": optAdmin.click();		break;
    		case "select": optSelect.click();	break;
    		case "ess": optESS.click();   		break;
    		default: System.out.println("Invalid user role selection.");
    	}
    }
    
    // Enter Employee Name data
	public void enterEmployeeName(String employeeName) throws InterruptedException 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		// Type the employee name first
		txtEmployeeName.clear();
		txtEmployeeName.sendKeys(employeeName);
		// Wait until suggestions appear
		wait.until(ExpectedConditions.visibilityOfAllElements(listEmployeeName));
		// Iterate suggestions and select match
		Thread.sleep(10);
		for(WebElement option : listEmployeeName)
		{
			String optionText = option.getText().trim();
            
	        if (optionText.contains(employeeName.trim())) 
	        {  
	        	System.out.println("Option displayed is: "+optionText);
	        	option.click();
	        	break;  // stop after selecting
	        }
		}
	}
	
	// Select Status option
	public void selectStatusOption(String statusSelection)
	{
		drpStatus.click(); // Open the dropdown

		switch (statusSelection.toLowerCase()) 
		{
			case "select": optSelectStatus.click(); 	break;
			case "enabled": optEnabled.click();			break;
			case "disabled": optDisabled.click();		break;
			default: System.out.println("Invalid status selection.");
		}
	}
	
	// Enter Username data
	public void enterUsername(String username)
	{
		txtUserName.clear();
		txtUserName.sendKeys(username);
	}

	// Enter Password data	
	public void enterPassword(String password) {
		txtPassword.clear();
		txtPassword.sendKeys(password);
	}
	
	// Enter Confirm Password data
	public void enterConfirmPassword(String confirmPassword) {
		txtConfirmPassword.clear();
		txtConfirmPassword.sendKeys(confirmPassword);
	}

	// Click on Cancel button
	public void clickCancelButton()
	{
		btnCancel.click();
	}

	// Click on Save button
	public void clickSaveButton()
	{
		btnSave.click();
	}
	
	// Get the selected User Role from dropdown
	public String getSelectedUserRole() 
	{
		drpUserRole.click(); // Open the dropdown
		return drpUserRole.getText().trim(); // Get the selected option text
	}
	
	public boolean isEmployeeNamePresent(String expectedEmployeeName) 
    {         
    	for (WebElement cell : employeeNameColumn) 
    	{
	        String cellText = cell.getText().trim();
	                
	        if (cellText.equalsIgnoreCase(expectedEmployeeName.trim())) 
	        {                   
	        	return true;
	        }
    	}
    	return false;        
    }
}
