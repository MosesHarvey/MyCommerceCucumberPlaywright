


✅ How to Integrate Extent Reports with Cucumber Java & Playwright
To use Extent Reports with Cucumber Java in Playwright, follow these steps:

🔹 Step 1: Add Extent Report Dependencies
In your pom.xml, add the Extent Report dependencies:

<!-- Extent Report dependencies -->
    <dependency>
        <groupId>com.aventstack</groupId>
        <artifactId>extentreports</artifactId>
        <version>5.0.9</version>
    </dependency>

🔹 Step 2: Create Extent Report Manager Class
Create a new Java class to manage the Extent Report lifecycle.

📄 ExtentReportManager.java

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
private static ExtentReports extent;
private static ExtentTest test;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";
            
            ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportPath);
            htmlReporter.config().setDocumentTitle("Playwright Test Report");
            htmlReporter.config().setReportName("Cucumber Playwright Test Execution");
            htmlReporter.config().setTheme(Theme.STANDARD);

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }

    public static ExtentTest createTest(String testName) {
        test = extent.createTest(testName);
        return test;
    }
}

Step 3: Capture Test Execution in Hooks
Modify the Hooks.java file to initialize and finalize the Extent Report.

📄 Hooks.java

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {
private static ExtentReports extent = ExtentReportManager.getReportInstance();
private ExtentTest test;

    @Before
    public void beforeScenario(Scenario scenario) {
        test = extent.createTest(scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            test.fail("Scenario Failed: " + scenario.getName());
        } else {
            test.pass("Scenario Passed: " + scenario.getName());
        }
        extent.flush(); // Save report
    }
}
Step 4: Attach Screenshots (Optional)
To capture screenshots when a test fails, update the afterScenario() method:
import com.microsoft.playwright.Page;
import java.nio.file.Paths;

@After
public void afterScenario(Scenario scenario) {
if (scenario.isFailed()) {
// Take screenshot
byte[] screenshot = Page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")).setFullPage(true));
scenario.attach(screenshot, "image/png", "Failure Screenshot");

        test.fail("Scenario Failed: " + scenario.getName())
            .addScreenCaptureFromPath("screenshot.png");
    } else {
        test.pass("Scenario Passed: " + scenario.getName());
    }
    extent.flush(); // Save report
}

🔹 Step 5: Run Cucumber Tests
Run your tests normally using:
mvn test
Your Extent Report will be generated inside the test-output/ folder.

🔹 Key setting: appendExisting = true (this allows reports to be merged automatically).
Each test run now generates a new report file like:
ExtentReport_2025-02-04_12-30-00.html
ExtentReport_2025-02-04_12-45-00.html
