package testBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.text.RandomStringGenerator;
import org.apache.logging.log4j.LogManager;  //Log4J
import org.apache.logging.log4j.Logger;  //Log4J
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws IOException
	{
		//Loading config.properties file
		FileInputStream file = new FileInputStream("./src/test/resources/config.properties");   
		//or FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);
				
		logger=LogManager.getLogger(this.getClass());    //we pass the current class by using this.getClass()
		
		//For Headless Testing
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless=new");
				
		//WebDriverManager.chromedriver().setup();
		
		switch(br.toLowerCase())
		{
			case "chrome" : driver=new ChromeDriver(); break;		// Add options in ChromeDriver(options) for headless testing
			case "edge" : driver=new EdgeDriver(); break;
			case "firefox" : driver=new FirefoxDriver(); break;
			default : System.out.println("Invalid browser name...."); return;
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().deleteAllCookies();
		driver.get(p.getProperty("appURL"));   //Reading url from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass
	public void tearDown() throws InterruptedException
	{
		Thread.sleep(2000);
		driver.close();
	}
			
	/*public String captureScreen(String className, String methodName) throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd_hh.mm.ss").format(new Date());
		 String filename = className + "_" + methodName + "_" + timeStamp + ".png"; 	// add className if we want to mention className is screenshot.
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+filename;
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}*/
	
	// Capture screenshot as Base64
    public String captureScreenAsBase64() 
    {  
    	TakesScreenshot ts = (TakesScreenshot) driver;
    	String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);
    	
        return base64Screenshot;
    }
    
	// Scrolls until the element is no longer displayed.	
	//public int timeoutSeconds =10;
    
    public void scrollUntilElementNotDisplayed(WebElement element, int timeoutSeconds) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));

        try {
            wait.until(driver1 -> {
                try 
                {
                    if (!element.isDisplayed()) 
                    {
                        return true; // already hidden
                    }
                    js.executeScript("arguments[0].scrollIntoView(true);", element);
                    js.executeScript("window.scrollBy(0, 100);"); // offset scroll
                    return false;
                } catch (NoSuchElementException | StaleElementReferenceException e) 
                {
                    return true; // removed from DOM
                }
            });
            System.out.println("✅ Element is no longer displayed.");
        } catch (TimeoutException e) 
        {
            throw new TimeoutException("❌ Element still displayed after " + timeoutSeconds + " seconds.");
        }
    }
    
    // Generate random strings 
    public String randomString() 
    {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('a', 'z')
            .build();
        return generator.generate(5);
    }

    // Generate random numbers
    public String randomNumber() 
    {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('0', '9')
            .build();
        return generator.generate(10);
    }

    // Generate random alphanumeric string
    public String randomAlphaNumeric() 
    {
        RandomStringGenerator alphaGen = new RandomStringGenerator.Builder()
            .withinRange('a', 'z')
            .build();
        RandomStringGenerator numGen = new RandomStringGenerator.Builder()
            .withinRange('0', '9')
            .build();
        return alphaGen.generate(3) + "@" + numGen.generate(3);
    }
 }
