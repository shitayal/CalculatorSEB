package stepDefinitions;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hooks {
    public static WebDriver driver;
    public static WebDriverWait wait;
    protected static ExtentReports extent;
    protected static ExtentTest test;
    private static String libWithDriversLocation = System.getProperty("user.dir") + "\\lib\\";

    @BeforeClass
    public WebDriver openBrowser() {

        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        wait = new WebDriverWait(driver, 200);
        return driver;

    }

    private static String capture() {
        String dest = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH-mm-ss");
            Date date = new Date();
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            dest = System.getProperty("user.dir") + "\\ErrorScreenshots\\" + dateFormat.format(date)
                    + ".png";
            File destination = new File(dest);
            FileUtils.copyFile(source, destination);
        } catch (Exception e) {
            e.getMessage();
            System.out.println(e.getMessage());
        }
        return dest;
    }


    @BeforeSuite
    public void startTest() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/testReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setDocumentTitle("Calculator Demo");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);

    }

    @AfterMethod
    public void endTest(ITestResult result) throws Exception {

        String path = capture();
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getTestClass().getName() + " FAILED ", ExtentColor.RED));
            test.fail(result.getThrowable());
            test.fail("Fail", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, MarkupHelper.createLabel(result.getTestClass().getName() + " PASSED ", ExtentColor.GREEN));
        } else {
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getTestClass().getName() + " SKIPPED ", ExtentColor.ORANGE));
            test.skip(result.getThrowable());
        }

    }


    @AfterClass
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @AfterSuite
    public void endReport() {
        extent.flush();

    }
}




