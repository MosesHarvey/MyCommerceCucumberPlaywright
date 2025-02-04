package com.mycommerce.step_definitions;

import com.github.javafaker.Faker;
import com.microsoft.playwright.Page;
import com.mycommerce.appdata.AppConstant;
import com.mycommerce.pages.*;
import com.mycommerce.utilities.ConfigReader;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

public class ProductsStep {

    private final Page page;
    private  HeaderComponent headerComponent;
    private ProductSection productSection;
    private  ProductDetailPage productDetailPage;
    SoftAssertions softAssertions;
    private BasePage basePage;
    private AddedModal addedModal;
    List<String>productNames;
    private CartPage cartPage;
    private SideBarSection sideBarSection;

    public ProductsStep() {
        page = Hooks.getPage();
        basePage = new BasePage(page);
        headerComponent = new HeaderComponent(page);
        this.productSection = new ProductSection(page);
        this.productDetailPage = new ProductDetailPage(page);
        this.addedModal = new AddedModal(page);
        this.cartPage = new CartPage(page);
        this.sideBarSection = new SideBarSection(page);
        this.softAssertions = new SoftAssertions();
    }

    // ======================Verify All Products and product detail page =============
    @When("the user clicks on Products tab")
    public void the_user_clicks_on_products_tab() {
       headerComponent.navigateToProductPage();
    }


    @Then("the products list is visible")
    public void the_products_list_is_visible() {
       Assert.assertTrue(productSection.getProductCount()>0);
    }
    @When("the user clicks on View Product of the first product")
    public void the_user_clicks_on_View_Product_of_the_first_product() {
        productSection.clickViewProductButtonByIndex(1);
    }
    @Then("the user should be landed on the product detail page")
    public void the_user_should_be_landed_on_the_product_detail_page() {

     Assert.assertEquals( AppConstant.PRODUCT_DETAIL_PAGE_TITLE,page.title());
    }
    @Then("the following details should be visible:")
    public void the_following_details_should_be_visible(List<String> details) {


        System.out.println(details.toString());
       Assert.assertTrue(productDetailPage.getProductName().isVisible());
        Assert.assertTrue(productDetailPage.getProductCategory().isVisible());
        Assert.assertTrue(productDetailPage.getProductPrice().isVisible());
        Assert.assertTrue(productDetailPage.getAvailability().isVisible());
        Assert.assertTrue(productDetailPage.getCondition().isVisible());
        Assert.assertTrue(productDetailPage.getBrand().isVisible());
        Assert.assertFalse(productDetailPage.getProductName().textContent().isEmpty());
        Assert.assertFalse(productDetailPage.getProductCategory().textContent().isEmpty());
        Assert.assertFalse(productDetailPage.getProductPrice().textContent().isEmpty());
        Assert.assertFalse(productDetailPage.getAvailability().textContent().isEmpty());
        Assert.assertFalse(productDetailPage.getCondition().textContent().isEmpty());
        Assert.assertFalse(productDetailPage.getBrand().textContent().isEmpty());
    }

    // ==============Product Search in the Search Box =================

    @When("the user enters a product name in the search input and clicks the search button")
    public void the_user_enters_a_product_name_in_the_search_input_and_clicks_the_search_button() throws InterruptedException {
        productSection.inputSearchTerm(AppConstant.SEARCH_TERM);
        productSection.clickSearchIcon();

    }

    @Then("the page url including the product name should be correct")
    public void the_page_url_including_the_product_name_should_be_correct() {
        String currentUrl = Hooks.getPage().url();
        System.out.println(currentUrl);
      Assert.assertEquals(currentUrl, AppConstant.PRODUCT_SEARCH_URL);


    }


    @Then("all products related to the search should be visible")
    public void all_products_related_to_the_search_should_be_visible() {
        List<String> products = productSection.getProductNames();
        for (String product : products) {
            SoftAssertions soft = new SoftAssertions();
            soft.assertThat(product.toLowerCase().contains(AppConstant.SEARCH_TERM.toLowerCase())).isTrue();
        }
    }

    // =========== View Products By Branding =================
    @Then("the user verifies that Brands are visible under Brands heading:")
    public void the_user_verifies_that_brands_are_visible_under_brands_heading(List<String>brands) {
        brands.forEach(brand -> {
            softAssertions.assertThat(sideBarSection.isTheBrandVisible(brand)).isTrue();
        });

    }

    @When("the user clicks on any brand name")
    public void the_user_clicks_on_any_brand_name() {
        sideBarSection.clickOnAnyBrandName();
    }

    @Then("the user verifies that they are navigated to the brand page")
    public void the_user_verifies_that_they_are_navigated_to_the_brand_page() {
        Assert.assertEquals(sideBarSection.getExpectedBrandPageTitle(), basePage.getPageTitle(page));
    }

    @Then("the user should see that the brand products are displayed")
    public void the_user_should_see_that_the_brand_products_are_displayed() {
       String expectedBrandProductHeading = "Brand - "+ sideBarSection.getBrandNames().get(Integer.parseInt(ConfigReader.get("brandIndex")))+" Products";
       Assert.assertTrue(basePage.isTheHeadingWithGivenTextVisible(expectedBrandProductHeading));
    }

    // ============= Search Products and Verify Cart After Login =============================

    @When("the user adds those products to cart")
    public void the_user_adds_those_products_to_cart() {
        int productCount = productSection.getProductCount();
        productNames = productSection.getProductNames();
        for (int i = 0; i < productCount; i++) {
            productSection.addProductToCartByIndex(i);
            addedModal.clickContinueShoppingBtnOnAddedModal();
        }
    }

    @Then("the user verifies that products are visible in cart")
    public void the_user_verifies_that_products_are_visible_in_cart() {
        String productsDescriptions=cartPage.getItemDescriptions().toString();
        productNames.forEach(productName -> {Assert.assertTrue(productsDescriptions.contains(productName));});

    }

    // =========== Add Review on a Products page =================
    @When("the user clicks on View Product button on any product")
    public void the_user_clicks_on_view_product_button_on_any_product() {
        productSection.clickViewProductButtonByIndex(1);
    }

    @Then("the user verifies a heading text {string} to be visible")
    public void the_user_verifies_a_heading_text_to_be_visible(String writeReviewHeading) {
        Assert.assertTrue(productDetailPage.isWriteReviewHeadingVisible());
        Assert.assertEquals(writeReviewHeading, productDetailPage.getWriteReviewHeading());

    }

    @When("the user enters name, email, and review")
    public void the_user_enters_name_email_and_review() {
        Faker faker = new Faker();
        productDetailPage.fillNameOnReview(faker.name().firstName());
        productDetailPage.fillEmailOnReview(faker.internet().emailAddress());
        productDetailPage.fillReviewTextarea(faker.lorem().paragraph());
    }

    @When("the user clicks Submit button under review form")
    public void the_user_clicks_submit_button_under_review_form() {
       productDetailPage.clickSubmitReviewBtn();
    }

}
