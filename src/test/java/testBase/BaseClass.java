package testBase;

import java.io.FileInputStream;
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
	
	//public static WebDriver driver;
	
	// ThreadLoal ensures that each test thread has its own independent WebDriver instance, preventing browser/session mix-ups during parallel execution.
	protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();   // For Thread Safe in Parallel Execution
	public Logger logger;
	public Properties p;
	String reportName = "TestEvidence_TC0003.docx";
	
	public WebDriver getDriver() {
        return driver.get();
    }
	
	@BeforeClass
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws Exception
	{
        
		//Loading config.properties file
		FileInputStream file = new FileInputStream("./src/test/resources/config.properties");   
		//or FileReader file = new FileReader("./src/test/resources/config.properties");
		p = new Properties();
		p.load(file);
				
		logger = LogManager.getLogger(this.getClass());    //we pass the current class by using this.getClass()
		
		//For Headless Testing
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headless=new");
				
		//WebDriverManager.chromedriver().setup();
		WebDriver drv;
		switch(br.toLowerCase())
		{
			case "chrome" : drv = new ChromeDriver(); break;		// Add options in ChromeDriver(options) for headless testing
			case "edge" : drv = new EdgeDriver(); break;
			case "firefox" : drv = new FirefoxDriver(); break;
			default : System.out.println("Invalid browser name...."); return;
		}
		driver.set(drv);
		
		driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get().manage().deleteAllCookies();
		driver.get().get(p.getProperty("appURL"));   //Reading url from properties file
		driver.get().manage().window().maximize();
	}
	
	@AfterClass
	public void tearDown() throws InterruptedException
	{
		Thread.sleep(2000);
		driver.get().close();
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
    	TakesScreenshot ts = (TakesScreenshot) driver.get();
    	String base64Screenshot = ts.getScreenshotAs(OutputType.BASE64);
    	
        return base64Screenshot;
    }
    
	// Scrolls until the element is no longer displayed.	
	//public int timeoutSeconds =10;
    
    public void scrollUntilElementNotDisplayed(WebElement element, int timeoutSeconds) {
        JavascriptExecutor js = (JavascriptExecutor) driver.get();
        WebDriverWait wait = new WebDriverWait(driver.get(), Duration.ofSeconds(timeoutSeconds));

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
