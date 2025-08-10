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
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener 
{

    private ExtentReports extent;
    private ExtentSparkReporter sparkReporter;
    private ExtentTest test;
    private String reportName;

    @Override
    public void onStart(ITestContext context) 
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "Test-Report-" + timeStamp + ".html";

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
        	    "   background: url('logo.png') no-repeat center center; " +
        	    "   background-size: 300px; " +
        	    "   opacity: 0.05; " +
        	    "   z-index: -1; " +
        	    "}"
        	);

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

        // Included Groups
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
        logTest(result, Status.PASS, result.getMethod().getMethodName() + " executed successfully ✅");
    }

    // This method will be called automatically when the test case Failed 
    @Override
    public void onTestFailure(ITestResult result) 
    {
        logTest(result, Status.FAIL, result.getMethod().getMethodName() + " failed ❌");
        test.log(Status.INFO, result.getThrowable().getMessage());
        
        try 
        {
            String imgPath = new BaseClass().captureScreen(
            		result.getTestClass().getRealClass().getSimpleName(),
            		result.getMethod().getMethodName());	
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e) 
        {
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
        logTest(result, Status.SKIP, result.getMethod().getMethodName() + " skipped ⚠️");
        test.log(Status.INFO, skipMessage);
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
    
    private Map<String, List<ITestResult>> sortedResults = new TreeMap<>(); // TreeMap keeps order
    
    private void logTest(ITestResult result, Status status, String message) {
    	    	
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
        test = extent.createTest(className +" : "+ methodName);
        test.assignCategory(className);
        if (result.getMethod().getGroups().length > 0) 
        {
            test.assignCategory(result.getMethod().getGroups());
        }
        test.log(status, message);
                
        // OR Send email automatically to the attached email
        try 
        {            
            File reportFile = new File(System.getProperty("user.dir") + "\\reports\\" + reportName);
            
            if (reportFile.exists()) 
        	{
    	        try 
    	        {
    	        	ImageHtmlEmail email = new ImageHtmlEmail();
    	            email.setHostName("smtp.googlemail.com");
    	            email.setSmtpPort(465);
    	            email.setAuthenticator(new DefaultAuthenticator("pushkalshripad01@gmail.com", "Pushkal@1987"));
    	            email.setSSLOnConnect(true);
    	            email.setFrom("pushkal@gmail.com");
    	            email.setSubject("Test Results");
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
        }       
    }
}