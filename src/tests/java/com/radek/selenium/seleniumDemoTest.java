package com.radek.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


/**
 * Created by radek on 2016-08-12.
 */

public class seleniumDemoTest {

    WebDriver driver;
    HomePage homePage;
    ShoppingCartPage shoppingCartPage;
    ProductComparisonPage productComparisonPage;
    WebDriverWait wait;


    @BeforeSuite
    public void setup() throws InterruptedException {
        driver = new FirefoxDriver();
        homePage = new HomePage(driver);
        productComparisonPage = new ProductComparisonPage(driver);
        shoppingCartPage = new ShoppingCartPage(driver);
        System.out.println("You are testing in firefox");
        driver.get("http://demo.opencart.com/");
        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(homePage.currencyButton));
    }

    @Test(priority = 0)
    public void changeCurrencyOnHomePage() {
        homePage.clickChangeCurrency();
        homePage.clickChooseCurrency("GBP");
        Assert.assertTrue(homePage.getTextChangeCurrency().equals("£ Currency"), "Currency has been changed to GBP");
    }

    @Test(priority = 1)
    public void searchForIPodAndAddAllToCompare() throws InterruptedException {
        homePage.fillSearchForm("iPod");
        Assert.assertTrue(homePage.getInputTextFromSearchForm().equals("iPod"), "iPod text has been typed in form");
        homePage.clickSearchButton();
        Assert.assertTrue(homePage.getSearchInfoText().equals("Search - iPod"), "Searching for iPod was performed");
        Assert.assertNotEquals(homePage.addToCompareButtons.size(), 0, "Minimum one product was found");
        Assert.assertTrue(homePage.addAllProductsToCompare(), "All products from first page added to compare");
    }

    @Test(priority = 2)
    public void compareAlliPods() {
        homePage.clickProductCompareLink();
        Assert.assertTrue(productComparisonPage.getProductComparisonHeaderText().equals("Product Comparison"),
                "Product comparison page is opened");
    }

    @Test(priority = 3)
    public void findAndRemoveNotAvailableProducts() {
        int numberOfNotAvailable = productComparisonPage.checkProductAvailability().size();
        if (numberOfNotAvailable != 0) {
            for (int i = 0; i < numberOfNotAvailable; i++) {
                int column = productComparisonPage.checkProductAvailability().get(0);
                productComparisonPage.removeFromComparison(column);
            }
        }
    }

    @Test(priority = 4)
    public void addRandomProductToCart() throws InterruptedException {
        int productColumn1 = productComparisonPage.chooseRandomProduct();
        String priceInCompare1 = productComparisonPage.getProductPrice(productColumn1);
        String productName1 = productComparisonPage.getProductName(productColumn1);
        productComparisonPage.addProductToCart(productColumn1);
        Assert.assertTrue(productComparisonPage.successAlert.isDisplayed(), "Product added to cart");
        productComparisonPage.clickCartButton(1);
        productComparisonPage.clickViewCartLink();
        wait.until(ExpectedConditions.visibilityOf(shoppingCartPage.shoppingCartHeader));
        Assert.assertEquals(shoppingCartPage.getProductsNamesAndPrices(1).get(productName1), priceInCompare1);

    }


    @AfterSuite
    public void tearDown() {
        driver.close();
    }
}

