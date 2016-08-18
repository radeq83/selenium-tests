package com.radek.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by radek on 2016-08-12.
 */

public class ShoppingCartPage {

    WebDriver driver;

    @FindBy(xpath = ".//*[@id='content']/h1")
    WebElement shoppingCartHeader;

    public ShoppingCartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public Map getProductsNamesAndPrices(int prodCount) {
        HashMap productsInCart = new HashMap<String, String>();
        for (int i = 1; i <= prodCount; i++) {
            String prodName = driver.findElement(By.cssSelector(".table.table-bordered>tbody>tr:nth-of-type(" + i +
                    ")>td:nth-of-type(2)>a")).getText();
            String prodPrice = driver.findElement(By.cssSelector(".table.table-bordered>tbody>tr:nth-of-type(" + i +
                    ")>td:nth-of-type(5)")).getText();
            productsInCart.put(prodName, prodPrice);
        }
        return productsInCart;
    }

}