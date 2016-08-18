package com.radek.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


/**
 * Created by radek on 2016-08-12.
 */

 public class HomePage {

     private WebDriver driver;


    @FindBy(xpath = ".//*[@class='btn btn-link dropdown-toggle'] [@data-toggle='dropdown']")
    WebElement currencyButton;

    @FindBy(xpath = ".//*[@class='currency-select btn btn-link btn-block']")
    List<WebElement> allCurrencies;

    @FindBy(xpath = ".//*[@class='form-control input-lg'][@name='search']")
    WebElement searchInput;

    @FindBy(xpath = ".//*[@id='search']/span/button")
    WebElement searchButton;

    @FindBy(xpath = ".//*[@id='content']/h1")
    WebElement searchInfoText;

    @FindBy(xpath = ".//*[@data-original-title='Compare this Product']")
    List<WebElement> addToCompareButtons;

    @FindBy(xpath = ".//*[@id='compare-total']")
    WebElement productCompareLink;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickChangeCurrency() {
        currencyButton.click();
    }

    public String getTextChangeCurrency() {
        return currencyButton.getText();
    }

    public void clickChooseCurrency(String currency) {
        for (WebElement it : allCurrencies) {
            String currencyCode = it.getAttribute("name");
            if (currencyCode.equals(currency)) {
                Actions action = new Actions(driver);
                action.moveToElement(it);
                action.perform();
                it.click();
                break;
            }
        }
    }

    public void fillSearchForm(String text) {
        searchInput.clear();
        searchInput.sendKeys(text);
    }

    public String getInputTextFromSearchForm() {
        return searchInput.getAttribute("value");
    }

    public void clickSearchButton() {
        searchButton.click();
    }

    public String getSearchInfoText() {
        return searchInfoText.getText();
    }

    public String getProductCompareLinkText() {
        return productCompareLink.getText();
    }

    public int getProductsNumberAddedToCompare() {
        String s = getProductCompareLinkText();
        return Integer.parseInt(s.replaceAll("[\\D]", ""));
    }

    public boolean addAllProductsToCompare() throws InterruptedException {
        for (WebElement it : addToCompareButtons) {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOf(it)).click();
        }
        Thread.sleep(2000);
        return getProductsNumberAddedToCompare() == addToCompareButtons.size();
    }

    public void clickProductCompareLink() {
        productCompareLink.click();
    }
}

