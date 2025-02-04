package com.mycommerce.step_definitions;


import com.microsoft.playwright.*;
import com.mycommerce.utilities.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;

import java.awt.*;

public class Hooks {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext context;
    private static Page page;

    @BeforeAll
    public static void globalSetUp() throws Exception {
        // Initialize Playwright and Browser once for the entire test suite
        if (page == null) {
            // Read browser type from configuration
            String browserName = ConfigReader.get("browser");
            boolean isHeadless = Boolean.parseBoolean(ConfigReader.get("isHeadless")); // Optional: default to false

            // Initialize Playwright
            playwright = Playwright.create();

            switch (browserName.toLowerCase()) {
                case "chromium":
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                    break;
                case "chrome":
                    browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                            .setChannel("chrome") // Use Chrome specifically
                            .setHeadless(isHeadless));
                    break;
                case "firefox":
                    browser = playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                    break;
                case "webkit":
                    browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(isHeadless));
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser type: " + browserName);
            }


        }else{
            throw new Exception("A page is already set, please check!");
        }
        System.out.println("Global setup completed: Playwright and Browser initialized");
    }

    @Before
    public void setUp() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        // Get the screen size as a Dimension object
        Dimension screenSize = toolkit.getScreenSize();

        // Extract the width and height
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
       // Create a new browser context and page before each scenario

        context = browser.newContext(new Browser.NewContextOptions()
                        .setViewportSize(screenWidth, screenHeight).setScreenSize(screenWidth, screenHeight));
        page = context.newPage();

        System.out.println("Scenario setup: Browser context and page created");
    }

    @After
    public void tearDown() {
        // Close the browser context and page after each scenario
        if (page != null) {
            page.close();
            System.out.println("Scenario teardown: Page closed");
        }
        if (context != null) {
            context.close();
            System.out.println("Scenario teardown: Browser context closed");
        }
    }

    @AfterAll
    public static void globalTearDown() {
        // Close the browser and Playwright after all tests
        if (browser != null) {
            browser.close();
            System.out.println("Global teardown: Browser closed");
        }
        if (playwright != null) {
            playwright.close();
            System.out.println("Global teardown: Playwright closed");
        }
    }

    public static Page getPage() {
        return page;
    }
}

