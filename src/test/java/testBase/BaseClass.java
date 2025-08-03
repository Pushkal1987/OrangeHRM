package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;
import com.google.common.io.Files;
import org.apache.logging.log4j.LogManager;  //Log4J
import org.apache.logging.log4j.Logger;  //Log4J

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
	
	public String captureScreen(String className, String methodName) throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());
		 String filename = className + "_" + methodName + "_" + timeStamp + ".png"; 	// add className if we want to mention className is screenshot.
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots\\"+filename;
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;
	}

}
