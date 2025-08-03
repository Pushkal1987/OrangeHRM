package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import testBase.BaseClass;


public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext)
	{		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());	// for time stamp
		repName ="Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+ repName);	//specify location of the report

		sparkReporter.config().setDocumentTitle("OrangeHRM Automation Report");  //Title of the Report
		sparkReporter.config().setReportName("OrangeHRM Functional Testing"); 	//Name of the Report

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "OrangeHRM");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		String os= testContext.getCurrentXmlTest().getParameter("os");	//operating system which we have passing in the xml file
		extent.setSystemInfo("Operating System", os);

		String browser= testContext.getCurrentXmlTest().getParameter("browser");	//browser name which we have passing in the xml file
		extent.setSystemInfo("Browser", browser);

		List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includeGroups.isEmpty())
		{
			extent.setSystemInfo("Groups", includeGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result)
	{
		String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();

        test = extent.createTest(className + " : " + methodName);  // ✅ Class + Method
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, methodName + " got successfully executed");
		
		/*
		test=extent.createTest(result.getTestClass().getName());	//to display class name in report
		test.assignCategory(result.getMethod().getGroups());	//to display groups in report
		test.log(Status.PASS, result.getName()+" got successfully executed");
		*/
	}

	public void onTestFailure(ITestResult result)
	{
		String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();

        test = extent.createTest(className + " : " + methodName);  // ✅ Class + Method
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL, methodName + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(className, methodName);
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		/*
		test=extent.createTest(result.getTestClass().getName());	//to display class name in report
		test.assignCategory(result.getMethod().getGroups());	//to display groups in report

		test.log(Status.FAIL, result.getName()+" got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try 
		{
			String className = result.getTestClass().getRealClass().getSimpleName();	// if we want class name in screenshot then add these 3 line of code
	        String methodName = result.getMethod().getMethodName();						// otherwise use only 4th line code.
	        String imgPath = new BaseClass().captureScreen(className, methodName);
			//String imgPath = new BaseClass().captureScreen(result.getName());  //add captureScreen method in BaseClass
			test.addScreenCaptureFromPath(imgPath);
		} catch (IOException e1)
		{
			e1.printStackTrace();	//printStackTrace: print some warning message in console window
		}
		*/
	}

	public void onTestSkipped(ITestResult result)
	{
		String className = result.getTestClass().getRealClass().getSimpleName();
        String methodName = result.getMethod().getMethodName();

        test = extent.createTest(className + " : " + methodName);  // ✅ Class + Method
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.SKIP, methodName + " got skipped");
        test.log(Status.INFO, result.getThrowable() != null ? result.getThrowable().getMessage() : "Skipped without exception");
        
		/*
		test=extent.createTest(result.getTestClass().getName());	//to display class name in report
		test.assignCategory(result.getMethod().getGroups());	//to display groups in report

		test.log(Status.SKIP, result.getName()+" got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
		*/
	}

	public void onFinish(ITestContext testContext)
	{
		extent.flush();

        String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + repName;
        File extentReport = new File(pathOfExtentReport);

        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		/*extent.flush();

		//To open report automatically
		String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;  //to open report automatically - path of the report
		File extentReport = new File(pathOfExtentReport);

		try
		{
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e)
		{
			e.printStackTrace();	//printStackTrace: print some warning message in console window
		}
		*/

		/*// Send email automatically to the attached email
		try
		{
			URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);

			//Create the email message 
			ImageHtmlEmail email = new ImageHtmlEmail();		//Add dependency https://mvnrepository.com/artifact/org.apache.commons/commons-email
			email.setDataSourceResolver(new DataSourceUrlResolver(url));
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("pushkal@gmail.com","password"));
			email.setSSLOnConnect(true);
			email.setFrom("pushkal@gmail.com");	//Sender
			email.setSubject("Test Results");
			email.setMsg("Please find attached report");
			email.addTo("pushkal01@gmail.com");		//Receiver email
			email.attach(url, "extent report", "please check report...");
			email.send();			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		 */
	}


}
