package com.jupitortoys.pageObjects;

import com.jupitortoys.Utils.basicUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class landingPage extends basicUtils {

    WebDriver driver = null;

    private static WebDriverWait maxWait;
    // private static WebDriverWait minWait;

    public landingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        maxWait = new WebDriverWait(driver, Integer.parseInt(getGlobalPropertyFromFile("maxWaitTime")));
        // minWait = new WebDriverWait(driver,
        // Integer.parseInt(getGlobalPropertyFromFile("minWaitTime")));
    }

    // Declare all objects related to landing page

    @FindBy(id = "nav-home")
    WebElement homeTopMenu;
    @FindBy(id = "nav-shop")
    WebElement shopTopMenu;
    @FindBy(id = "nav-contact")
    WebElement contactTopMenu;
    @FindBy(id = "forename")
    WebElement nameFieldInContactsPage;
    @FindBy(id = "product-1")
    WebElement firstProductOnShopPage;
    @FindBy(css = "a[class='btn btn-success btn-large']")
    WebElement startShoppingButtonOnHomePage;

    // Navigate to COntact page from tope menu option
    public Boolean navigateToContactPage() {
        try {
            contactTopMenu.click();
            maxWait.until(ExpectedConditions.visibilityOf(nameFieldInContactsPage));

            System.out.println("Navigate to Contacts is Successful");
            return true;

        } catch (Exception e) {
            System.out.println("Error while Navigate to Contacts page " + e.getMessage());
            return false;
        }
    }

    // Navigate to Shop page from tope menu option
    public Boolean navigateToShopPage() {
        try {
            shopTopMenu.click();
            maxWait.until(ExpectedConditions.visibilityOf(firstProductOnShopPage));

            System.out.println("Navigate to Shop page is Successful");
            return true;

        } catch (Exception e) {
            System.out.println("Error while Navigate to Shop page " + e.getMessage());
            return false;
        }
    }

    // Navigate to Home page from tope menu option
    public Boolean navigateToHomePage() {
        try {
            homeTopMenu.click();
            maxWait.until(ExpectedConditions.visibilityOf(startShoppingButtonOnHomePage));

            System.out.println("Navigate to Home page is Successful");
            return true;

        } catch (Exception e) {
            System.out.println("Error while Navigate to Home page - " + e.getMessage());
            return false;
        }
    }

}
