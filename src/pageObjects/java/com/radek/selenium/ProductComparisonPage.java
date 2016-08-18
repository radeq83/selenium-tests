package com.radek.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by radek on 2016-08-13.
 */

public class ProductComparisonPage {

    WebDriver driver;

    @FindBy(xpath = ".//*[@id='content']/h1")
    WebElement productComparisonHeader;

    @FindBy(xpath = ".//*[@value='Add to Cart']")
    List<WebElement> addToCartButton;

    @FindBy(css = ".alert.alert-success")
    WebElement successAlert;

    @FindBy(xpath = ".//*[@id='cart']")
    WebElement cartButton;

    @FindBy(xpath = ".//*[@id='cart']/ul/li[2]/div/p/a[1]/strong")
    WebElement viewCartLink;


    public ProductComparisonPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public String getProductComparisonHeaderText() {
        return productComparisonHeader.getText();
    }

    public ArrayList<Integer> checkProductAvailability() {
        ArrayList<Integer> columns = new ArrayList<Integer>();
        for (int i = 2; i < 6; i++) {
            if (driver.findElement(By.cssSelector(".table.table-bordered>tbody>tr:nth-of-type(6)>td:nth-of-type(" + i + ")"
            )).getText().equals("Out Of Stock")) {
                columns.add(i);
            }
        }
        return columns;
    }

    public void removeFromComparison(int i) {
        driver.findElement(By.cssSelector(".table.table-bordered>tbody:nth-of-type(2)>tr:nth-of-type(1)>" +
                "td:nth-of-type(" + i + ")>a")).click();
    }

    public int getNumberOfAddToCartButtons() {
        return addToCartButton.size();
    }

    public int chooseRandomProduct() {
        int min = 2;
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(".//*[@value='Add to Cart']")));
        int max = getNumberOfAddToCartButtons();
        if (min > max) {
            throw new IllegalArgumentException("No products available to add to cart");
        }
        Random r = new Random();
        int rnd = r.nextInt((max - min) + 1) + min;
        return rnd;
    }

    public String getProductPrice(int columnOfProduct) {
        return driver.findElement(By.cssSelector(".table.table-bordered>tbody>tr:nth-of-type(3)>td:nth-of-type("
                + columnOfProduct + ")")).getText();
    }

    public String getProductName(int columnOfProduct) {
        return driver.findElement(By.cssSelector(".table.table-bordered>tbody>tr:nth-of-type(1)>td:nth-of-type("
                + columnOfProduct + ")")).getText();
    }

    public void addProductToCart(int columnOfProduct) {
        driver.findElement(By.cssSelector(".table.table-bordered>tbody:nth-of-type(2)>tr:nth-of-type(1)>td:nth-of-type("
                + columnOfProduct + ")>input")).click();
    }

    public void clickCartButton(int prodNumber) {
        String s = Integer.toString(prodNumber);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.textToBePresentInElement(cartButton, s));
        cartButton.click();
    }

    public void clickViewCartLink() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOf(viewCartLink)).click();

    }
}
