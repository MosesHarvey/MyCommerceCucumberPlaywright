package com.mycommerce.steps;


import com.microsoft.playwright.Page;
import com.mycommerce.appdata.AccountInfo;
import com.mycommerce.appdata.AppConstant;
import com.mycommerce.pages.HeaderComponent;
import com.mycommerce.pages.LoginSignUpPage;
import com.mycommerce.utilities.PlaywrightManager;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class LoginStep {

    private HeaderComponent headerComponent = new HeaderComponent();
    private  LoginSignUpPage loginPage = new LoginSignUpPage();



// ===============login with valid credentials================



    @When("the user enters correct email address")
    public void the_user_enters_correct_email_address() {

       loginPage.fillEmailField(AppConstant.USER_EMAIL);
    }

    @When("the user enters correct password")
    public void the_user_enters_correct_password() {
       loginPage.fillPasswordField(AppConstant.PASSWORD);
    }

    @When("the user clicks login button")
    public void the_user_clicks_login_button() {
       loginPage.clickLoginButton();
       PlaywrightManager.getPage().waitForLoadState();
    }
    @Then("Logged in as username should be visible")
    public void Logged_in_as_username_should_be_visible() {

        assertTrue(headerComponent.getLoggedInAsUserNameTab().isVisible());
        assertEquals(AccountInfo.name, headerComponent.getLoggedInAsUserNameTab().textContent());

    }

    @Then("Logged in as registered username should be visible")
    public void logged_in_as_registered_username_should_be_visible() {

        assertTrue(headerComponent.getLoggedInAsUserNameTab().isVisible());
        assertEquals(AppConstant.USER_NAME, headerComponent.getLoggedInAsUserNameTab().textContent());

    }

// ================ login with incorrect credentials =============

    @When("the user enters incorrect email address {string} or incorrect password {string}")
    public void the_user_enters_incorrect_email_address_or_incorrect_password(String email, String password) {
        loginPage.fillEmailField(email);
        loginPage.fillPasswordField(password);
    }

    @Then("the user should see {string} error message")
    public void the_user_should_see_error_message(String errorMessage) {
        assertTrue(loginPage.isErrorMessageVisible());
        assertEquals(errorMessage, loginPage.getErrorMessageText());

    }

// =============================== logout scenario ============================

    @When("the user logged in with valid credentials")
    public void the_user_logged_in_with_valid_credentials() {
    loginPage.login(AppConstant.USER_EMAIL, AppConstant.PASSWORD);
    }

    @When("the user clicks the Logout button")
    public void the_user_clicks_the_logout_button() {
        loginPage.clickLogoutButton();
    }



}
