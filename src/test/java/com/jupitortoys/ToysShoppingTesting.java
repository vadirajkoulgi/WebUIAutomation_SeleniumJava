package com.jupitortoys;

import java.util.LinkedHashMap;
import java.util.List;

import com.jupitortoys.Utils.basicUtils;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.jupitortoys.pageObjects.*;

public class ToysShoppingTesting extends basicUtils {

    static WebDriver driver = null;
    static WebDriverWait wait;

    // Define error message

    // Initialise page objects
    static landingPage landingPg;
    static contactPage contactPg;
    static shoppingPage shoppingPg;

    @BeforeClass
    public void launchWebApplication() {
        driver = launchApplication();

        landingPg = new landingPage(driver);
        contactPg = new contactPage(driver);
        shoppingPg = new shoppingPage(driver);

    }

    @BeforeMethod
    public void initilaisePageToHomePage() {
        // Navigate back to home page
        Assert.assertTrue(landingPg.navigateToHomePage());
    }

    @AfterTest
    public void closer() {

        driver.quit();
    }

    @Test(description = "Test Case#1 - Validate the error message on the COntact page", enabled = true)
    public void validateMandotoryFileds() throws InterruptedException {
        System.out.println("=========== Test Case#1 ===========");

        // Navigate to Contact page
        Assert.assertTrue(landingPg.navigateToContactPage());

        // Click on submit button
        Assert.assertTrue(contactPg.enterSubmitButton());

        // Validate Error message on the First Name field related to mandatory field
        Assert.assertTrue(contactPg.validateErrorMessageOnFirstNameField(contactPg.firstNameMandatoryErrorMessage));

        // Validate Error message on the email ID field related to mandatory field
        Assert.assertTrue(contactPg.validateErrorMessageOnEmailIDField(contactPg.emailIDMandatoryErrorMessage));

        // Validate Error message on the Message field related to mandatory field
        Assert.assertTrue(contactPg.validateErrorMessageOnMessageField(contactPg.messageMandatoryErrorMessage));

        // Enter valid first name in mandatory fields
        Assert.assertTrue(contactPg.enterFirstName("tester"));
        // Validate Error message doesn't exists on the First Name field related to
        // mandatory field
        Assert.assertFalse(contactPg.validateErrorMessageOnFirstNameField(contactPg.firstNameMandatoryErrorMessage));

        // Enter valid emailID name in mandatory fields
        Assert.assertTrue(contactPg.enterEmailID("testemail@test.com"));
        // Validate Error message doesn't exists on the email ID field related to
        // mandatory field
        Assert.assertFalse(contactPg.validateErrorMessageOnEmailIDField(contactPg.emailIDMandatoryErrorMessage));
        // Enter valid message in mandatory fields
        Assert.assertTrue(contactPg.enterMessage("test message test test"));
        // Validate Error message doesn't exists on the Message field related to
        // mandatory field
        Assert.assertFalse(contactPg.validateErrorMessageOnMessageField(contactPg.messageMandatoryErrorMessage));

        // Navigate back to home page
        Assert.assertTrue(landingPg.navigateToHomePage());
    }

    @Test(description = "Test Case#2 - Sunbit contact and Validate successful submission message", enabled = true)
    public void successfulSubmissionOfContactDetails() {

        System.out.println("=========== Test Case#2 ===========");
        // Navigate to Contact page
        Assert.assertTrue(landingPg.navigateToContactPage());

        // Enter valid data in mandatory fields
        String fName = getAlphabeticString(20);
        Assert.assertTrue(contactPg.enterFirstName(fName));
        Assert.assertTrue(contactPg.enterEmailID(getAlphabeticString(5) + "@test.com"));
        Assert.assertTrue(contactPg.enterMessage(getAlphabeticString(5) + " " + getAlphabeticString(10)));

        // Click on submit button
        Assert.assertTrue(contactPg.enterSubmitButton());
        Assert.assertTrue(contactPg.validateSuccessMessage(fName));

        // Click on Back Button
        Assert.assertTrue(contactPg.clickOnBackButtonOnSuccessPage());

        // Navigate back to home page
        Assert.assertTrue(landingPg.navigateToHomePage());

    }

    @Test(description = "Test Case#3 - Validate the error message on the Contact page for invalid data", enabled = true)
    public void validateInvalidFileds() throws InterruptedException {
        System.out.println("=========== Test Case#3 ===========");

        // Navigate to Contact page
        Assert.assertTrue(landingPg.navigateToContactPage());
        // Enter valid data in mandatory fields
        String fName = getAlphabeticString(20);
        Assert.assertTrue(contactPg.enterFirstName(fName));
        Assert.assertTrue(contactPg.enterMessage(getAlphabeticString(5) + " " + getAlphabeticString(10)));

        // Enter valid data in emailID fields without @ char
        Assert.assertTrue(contactPg.enterEmailID(getAlphabeticString(5) + "gmail.com"));
        // Validate Error message exists on the email ID field related to Invalid data
        Assert.assertTrue(contactPg.validateErrorMessageOnEmailIDField(contactPg.emailIDInvalidErrorMessage));
        // Enter alphabets in phone number
        Assert.assertTrue(contactPg.enterTelPhoneNumber(getAlphaNumericString(10)));
        // Validate Error message exists on the telephone number field related to
        // Invalid data
        Assert.assertTrue(contactPg.validateErrorMessageOnTelNumField(contactPg.telNumberInvalidErrorMessage));

        // Navigate back to home page
        Assert.assertTrue(landingPg.navigateToHomePage());
    }

    @Test(description = "Test Case#4 - Validate the shop cart", enabled = true)

    public void validateShopCart() throws InterruptedException {
        System.out.println("=========== Test Case#4 ===========");
        SoftAssert softAssert = new SoftAssert();
        double totalPrices = 0.00;
        double FunnyCowPrice = 0.00;
        double FluffyBunnyPrice = 0.00;

        Integer fluffyBunnyCount = 0;
        Integer funnyCowCount = 0;
        Integer totalItemCount = 0;

        String tempPrice = null;
        // Navigate back to home page
        Assert.assertTrue(landingPg.navigateToShopPage());
        // Verify that the initial cart count is 0
        softAssert.assertTrue(shoppingPg.validateChartNumber("0"));

        // Select the Funny Cow item and get the price
        tempPrice = shoppingPg.selectItemByName("Funny Cow");
        Assert.assertNotNull(tempPrice);
        funnyCowCount++;
        totalItemCount++;
        FunnyCowPrice = Double.parseDouble(tempPrice.replaceAll("[^0-9.]", ""));
        totalPrices = totalPrices + FunnyCowPrice;

        // Verify that the cart count is set to 1
        softAssert.assertTrue(shoppingPg.validateChartNumber(totalItemCount.toString()),
                "Chart count validation failed");

        // Select the Funny Cow item 2nd time and get the price
        tempPrice = shoppingPg.selectItemByName("Funny Cow");
        Assert.assertNotNull(tempPrice);
        funnyCowCount++;
        totalItemCount++;
        Double.parseDouble(tempPrice.replaceAll("[^0-9.]", ""));
        totalPrices = totalPrices + Double.parseDouble(tempPrice.replaceAll("[^0-9.]", ""));
        // Verify that the cart count is set to 2
        softAssert.assertTrue(shoppingPg.validateChartNumber(totalItemCount.toString()),
                "Chart count validation failed");

        // Select the Fluffy Bunny item time and get the price
        tempPrice = shoppingPg.selectItemByName("Fluffy Bunny");
        Assert.assertNotNull(tempPrice);
        fluffyBunnyCount++;
        totalItemCount++;
        FluffyBunnyPrice = Double.parseDouble(tempPrice.replaceAll("[^0-9.]", ""));
        totalPrices = totalPrices + FluffyBunnyPrice;

        // Verify that the cart count is set to 3
        softAssert.assertTrue(shoppingPg.validateChartNumber(totalItemCount.toString()),
                "Chart count validation failed");

        System.out.println("Total price = " + totalPrices);
        System.out.println("FunnyCowPrice =" + FunnyCowPrice);
        System.out.println("FluffyBunnyPrice =" + FluffyBunnyPrice);

        // Click on cart
        Assert.assertTrue(shoppingPg.clickOnCartButton());
        // Exclude execution if empty cart
        Assert.assertFalse(shoppingPg.isAlertEmptyCartExists());

        // Validate the cart table
        List<LinkedHashMap<String, String>> allTableData = shoppingPg.getTableData();
        // System.out.println(allTableData);

        for (LinkedHashMap<String, String> data : allTableData) {
            if (data.get("Item").toString().equalsIgnoreCase("Fluffy Bunny")) {
                softAssert.assertEquals(Double.parseDouble(data.get("Price").replaceAll("[^0-9.]", "")),
                        FluffyBunnyPrice, "Price validation is failed for Fluffy Bunny");
                softAssert.assertEquals(Double.parseDouble(data.get("Subtotal").replaceAll("[^0-9.]", "")),
                        (FluffyBunnyPrice * fluffyBunnyCount), "Subtotal is not matching for Fluffy Bunny item");
                softAssert.assertEquals(data.get("Quantity").replaceAll("[^0-9]", ""), fluffyBunnyCount.toString(),
                        "Count/Quantity is not matching for Fluffy Bunny item");
            }

            if (data.get("Item").toString().equalsIgnoreCase("Funny Cow")) {
                softAssert.assertEquals(Double.parseDouble(data.get("Price").replaceAll("[^0-9.]", "")), FunnyCowPrice,
                        "Price validation is failed for FunnyCow");
                softAssert.assertEquals(Double.parseDouble(data.get("Subtotal").replaceAll("[^0-9.]", "")),
                        (FunnyCowPrice * funnyCowCount), "Subtotal is not matching for FunnyCow item");
                softAssert.assertEquals(data.get("Quantity").replaceAll("[^0-9]", ""), funnyCowCount.toString(),
                        "Count/Quantity is not matching for FunnyCow item");
            }
            softAssert.assertAll();
            System.out.println(
                    "Cart table data are matching for " + data.get("Item") + " item in Price, Subtotal and Quantity");
        }

        // Validate Total Amount
        Double actualTotalPrice = shoppingPg.getTotalPriceFromTable();
        Assert.assertNotNull(actualTotalPrice);
        softAssert.assertEquals(actualTotalPrice, totalPrices, "Mismatch in Total price");

        // Through all alerts
        softAssert.assertAll();

    }

}
