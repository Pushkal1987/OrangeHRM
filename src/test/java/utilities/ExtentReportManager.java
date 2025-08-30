package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.ImageHtmlEmail;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener 
{
	public static ExtentReports extent;
	// ThreadLocal is typically used in parallel test execution frameworks (like TestNG + Extent Reports) to ensure that each test thread gets its own copy of ExtentTest object without interfering with others.
	private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();  	// For Thread Safe in Parallel Execution
	 
    private ExtentSparkReporter sparkReporter;
    //private ExtentTest test;
    private String reportName;
    public String timeStamp;
    private Map<String, List<ITestResult>> sortedResults = new TreeMap<>(); // TreeMap keeps order

    @Override
    public void onStart(ITestContext context) 
    {
        // Initialize Extent Report
        timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "Test-Report-" + timeStamp + ".html";

        // Create report directory if it doesn't exist
        sparkReporter = new ExtentSparkReporter("./reports/" + reportName);

        // Modern Theme
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("🚀 OrangeHRM Automation Report");
        sparkReporter.config().setReportName("OrangeHRM Functional Testing - QA Environment");

        // Custom CSS for branding
        sparkReporter.config().setCss(
        	    "body { font-family: 'Segoe UI', sans-serif; background-size: cover; }" +
        	    ".badge-primary { background-color: #007bff; }" +
        	    ".badge-success { background-color: #28a745; }" +
        	    ".badge-danger { background-color: #dc3545; }" +
        	    ".badge-warning { background-color: #ffc107; color: black; }" +
        	    ".brand-logo { display: flex; align-items: center; gap: 10px; font-weight: bold; font-size: 1.2rem; color: #fff; }" +
        	    
        	    // Watermark logo
        	    "body::before { " +
        	    "   content: ''; " +
        	    "   position: fixed; " +
        	    "   top: 0; left: 0; right: 0; bottom: 0; " +
        	    "   background: url('file:///" + System.getProperty("user.dir") + "/resources/logo.png') no-repeat center center fixed;" +
        	    "   background-size: 300px 300px; " +
        	    "   opacity: 0.1; " +
        	    "   z-index: -1; " +
        	    "}"
        	);
        // Set report name     
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Static Info
        extent.setSystemInfo("Application", "OrangeHRM");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        // Dynamic Info
        extent.setSystemInfo("Operating System", context.getCurrentXmlTest().getParameter("os"));
        extent.setSystemInfo("Browser", context.getCurrentXmlTest().getParameter("browser"));

        // Get groups from the test context
        List<String> groups = context.getCurrentXmlTest().getIncludedGroups();
        if (!groups.isEmpty()) 
        {
            extent.setSystemInfo("Groups", groups.toString());
        }
    }

    // This method will be called automatically on test case Success 
    @Override
    public void onTestSuccess(ITestResult result) 
    {        
    	String methodName = result.getMethod().getMethodName();
    	logTest(result, Status.PASS, MarkupHelper.createLabel(methodName + ", EXECUTED SUCCESSFULLY ✅", ExtentColor.GREEN));
        //test.log(Status.INFO, "Test passed successfully.");
    	test.get().log(Status.INFO, "Test passed successfully.");
        
        try 
        {
	        // Get screenshot as Base64
	        String base64Screenshot = new BaseClass().captureScreenAsBase64();
	
	        // Attach Base64 screenshot to Extent Report
	        test.get().addScreenCaptureFromBase64String(base64Screenshot,"Screenshot: " + result.getMethod().getMethodName());
		} catch (Exception e) {
			System.err.println("Error while capturing screenshot: " + e.getMessage());
			e.printStackTrace();
		}
    }
    
    // This method will be called automatically when the test case Failed 
    @Override
    public void onTestFailure(ITestResult result) 
    {
    	String methodName = result.getMethod().getMethodName();
        logTest(result, Status.FAIL, MarkupHelper.createLabel( methodName + ", FAILED ❌" , ExtentColor.RED));
        test.get().log(Status.INFO, result.getThrowable().getMessage());
        
        try 
        {
        	/*// Capture screenshot and attach to Extent Report
            String imgPath = new BaseClass().captureScreen(
            		result.getTestClass().getRealClass().getSimpleName(),
            		result.getMethod().getMethodName());	
            test.addScreenCaptureFromPath(imgPath);*/
        	
        	// Get screenshot as Base64
            String base64Screenshot = new BaseClass().captureScreenAsBase64();

            // Attach Base64 screenshot to Extent Report
            test.get().addScreenCaptureFromBase64String(base64Screenshot,"Screenshot: " + result.getMethod().getMethodName());
        } /*catch (IOException e) 
        {
            // Use this exception if we want to capture screenshot and save it in a file
        	e.printStackTrace();
        }*/ catch (Exception e) 
        {
            System.err.println("Error while capturing screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // This method will be called automatically when the test case Skipped
    @Override
    public void onTestSkipped(ITestResult result) 
    {
        String skipMessage = result.getThrowable() != null
                ? result.getThrowable().getMessage()
                : "Skipped without exception";
        
        String methodName = result.getMethod().getMethodName();
        logTest(result, Status.SKIP, MarkupHelper.createLabel(methodName + ", SKIPPED ⚠️" , ExtentColor.ORANGE));
        test.get().log(Status.INFO, skipMessage);
    }
  
    // This method will be called automatically on test case Finished
    @Override
    public void onFinish(ITestContext context) 
    {
        extent.flush();

        File reportFile = new File(System.getProperty("user.dir") + "\\reports\\" + reportName);
        
        if (reportFile.exists()) 
    	{
	        try 
	        {
	        	Desktop.getDesktop().browse(reportFile.toURI());           
	        } catch (IOException e) 
	        {
	        	e.printStackTrace();
	        }
    	} else 
    	{
    		System.err.println("Report file not found onFinish: " + reportFile.getAbsolutePath());
    	}
    }
        
    private void logTest(ITestResult result, Status status, Markup markup) {
    	    	
    	String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();
        String description = result.getMethod().getDescription();

        // Extract Test Case ID (from description or method name)
        String testCaseId = "";
        if (description != null && description.matches("TC\\d+.*")) 
        {
            testCaseId = description.split(":")[0].trim(); // e.g., "TC001"
        } else if (methodName.matches("TC\\d+.*")) 
        {
            testCaseId = methodName.replaceAll("(TC\\d+).*", "$1");
        } else 
        {
            testCaseId = "ZZZ_" + methodName; // fallback for sorting
        }

        // Store results grouped by Test Case ID
        sortedResults.computeIfAbsent(testCaseId, k -> new ArrayList<>()).add(result);

        // Normal Extent logging
        test.set(extent.createTest(methodName));
        test.get().assignCategory(className);
        if (result.getMethod().getGroups().length > 0) 
        {
            test.get().assignCategory(result.getMethod().getGroups());
        }
        test.get().log(status, markup);
                
        /*// OR Send email automatically to the attached email
        try 
        {            
            File reportFile = new File(System.getProperty("user.dir") + "\\reports\\" + reportName);
            
            if (reportFile.exists()) 
        	{
    	        try 
    	        {
    	        	timeStamp =new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    	        	
    	        	ImageHtmlEmail email = new ImageHtmlEmail();
    	            email.setHostName("smtp.googlemail.com");
    	            email.setSmtpPort(465);
    	            email.setAuthenticator(new DefaultAuthenticator("pushkalshripad01@gmail.com", "password"));
    	            email.setSSLOnConnect(true);
    	            email.setFrom("pushkal@gmail.com");
    	            email.setSubject("Test Results for Date: "+ timeStamp);
    	            email.setMsg("Please find attached report");
    	            email.addTo("pushkalshripad11@gmail.com");
    	
    	            EmailAttachment attachment = new EmailAttachment();
    	            attachment.setPath(reportFile.getAbsolutePath());
    	            attachment.setDisposition(EmailAttachment.ATTACHMENT);
    	            attachment.setDescription("Please check report...");
    	            attachment.setName(reportFile.getName());
    	
    	            email.attach(attachment);
    	            email.send();           
    	        } catch (Exception e) 
    	        {
    	        	throw new FileNotFoundException("Report file not found on Send Mail: " + reportFile.getAbsolutePath());
    	        	//e.printStackTrace();
    	        }
        	} else 
        	{
        		System.err.println("Report file not found on Send Mail: " + reportFile.getAbsolutePath());
        	}
        } catch (Exception e) 
        {
            e.printStackTrace();
        } */     
    }
    
    // This method logs a step with an attached screenshot in Base64 format
    public static void logStepWithScreenshot(ExtentTest test, String message) {
        test.log(Status.INFO, message);
        try 
        {
            String base64Screenshot = new BaseClass().captureScreenAsBase64();
            test.addScreenCaptureFromBase64String(base64Screenshot, "Screenshot for: " + message);
        } catch (Exception e) 
        {
            test.log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
        }
    }
}