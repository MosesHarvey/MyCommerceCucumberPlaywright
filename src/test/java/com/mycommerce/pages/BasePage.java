package com.mycommerce.pages;



import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.options.WaitForSelectorState.VISIBLE;


public class BasePage {
    private final Page page;
    private final Locator consentBtn;
    private final Locator scrollUp;

    public BasePage(Page page) {
        this.page = page;
        //Locators
        this.consentBtn = page.locator("button:has-text('Consent')");
        this.scrollUp = page.locator("#scrollUp");

    }

    public void clickConsentBtn() {
        consentBtn.waitFor(new Locator.WaitForOptions().setTimeout(5000));
        consentBtn.click();
    }

       public boolean isLocatorWithTextVisible(String visibleText){
        return page.locator(":has-text(\"" + visibleText + "\")").first().isVisible();

    }

    public static void navigateToGivenUrl(Page page, String url){
        page.navigate(url);

    }




    public static boolean isElementWithTextVisible(Page page, String visibleText) {
        try {
            // Locate the text and wait for its visibility
            Locator textLocator = page.getByText(visibleText).first();
            textLocator.waitFor(new Locator.WaitForOptions().setState(VISIBLE));

            // Check visibility and return true if visible
            if (textLocator.isVisible()) {
                System.out.println("Element with the given text is visible.");
                return true;
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., timeout, locator not found, etc.)
            System.out.println("Element with the given text is not visible: " + e.getMessage());
        }
        // Default return if element is not visible or an exception occurs
        return false;
    }


    public static String getPageTitle(Page page){
        return page.title();
    }


    public static void acceptAlert(Page page) {

        page.onDialog(dialog -> {
            System.out.println("Dialog message: " + dialog.message());
            dialog.accept(); // Click the OK button
        });
    }
    public void scrollToBottom(){
        page.evaluate("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollToTop() {
        scrollUp.click();

    }

    public void scrollToTopWithoutArrow() {
    page.evaluate("window.scrollTo(0, 0)");
    }

    public boolean isTheHeadingWithGivenTextVisible(String headingText) {
        Locator heading = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(headingText));
        return heading.isVisible();

    }


}
