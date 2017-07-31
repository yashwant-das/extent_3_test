/**
 * Created by Yash on 7/27/17.
 */

import com.aventstack.extentreports.ExtentReports;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

//import com.aventstack.extentreports.ExtentReports_Demo;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class ExtentReports_Demo {
    ExtentHtmlReporter htmlReporter;
    ExtentReports extent;
    ExtentTest logger;
    WebDriver driver;





    //Application Permission Management
    public void AllowAppPermission() {

        //Waiting for the system dialog prompting the user to grant or deny a permission
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.android.packageinstaller:id/permission_allow_button")));

        boolean status = element.isDisplayed();
        if (status) {
            System.out.println("PERMISSIONS_REQUEST_ALLOWED");
            driver.findElement(By.id("com.android.packageinstaller:id/permission_allow_button")).click();
        } else {
            System.out.println("PERMISSIONS_REQUEST_NOT_DISPLAYED");
        }
    }



    @BeforeTest
    public void startReport() throws MalformedURLException {

        /* Appium Caps */
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("deviceName", "Moto G4 Plus");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "7.0");

        File file = new File("/Users/apple/IdeaProjects/AppTest/APK/app-internal.apk");
        caps.setCapability("app", file.getAbsolutePath());

        driver = new AndroidDriver(new URL("http://172.16.16.20:4723/wd/hub"), caps);

        /* Reporter */
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/STMExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "Test Demo");
        extent.setSystemInfo("Environment", "Automation Testing");
        extent.setSystemInfo("User Name", "Yashwant Das");

        htmlReporter.config().setDocumentTitle("Title of the Report Comes here");
        htmlReporter.config().setReportName("Name of the Report Comes here");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
    }

    @Test
    public void passTest(){
        logger = extent.createTest("passTest");
        Assert.assertTrue(true);
        logger.log(Status.PASS, MarkupHelper.createLabel("Test Case Passed is passTest", ExtentColor.GREEN));
    }

    @Test
    public void failTest(){
        logger = extent.createTest("failTest");
        Assert.assertTrue(false);
        logger.log(Status.PASS, "Test Case (failTest) Status is passed");
        logger.log(Status.PASS, MarkupHelper.createLabel("Test Case (failTest) Status is passed", ExtentColor.GREEN));
    }

    @Test
    public void skipTest(){
        logger = extent.createTest("skipTest");
        throw new SkipException("Skipping - This is not ready for testing ");
    }

    @AfterMethod
    public void getResult(ITestResult result){
        if(result.getStatus() == ITestResult.FAILURE){
            //logger.log(Status.FAIL, "Test Case Failed is "+result.getName());
            //MarkupHelper is used to display the output in different colors
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
        }else if(result.getStatus() == ITestResult.SKIP){
            //logger.log(Status.SKIP, "Test Case Skipped is "+result.getName());
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        }
    }
    @AfterTest
    public void endReport(){
        extent.flush();
    }
}
