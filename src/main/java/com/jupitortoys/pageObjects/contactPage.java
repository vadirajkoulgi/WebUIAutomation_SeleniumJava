package com.jupitortoys.pageObjects;

import com.jupitortoys.Utils.basicUtils;
import org.testng.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class contactPage extends basicUtils {
    WebDriver driver = null;

    private static WebDriverWait maxWait;
    private static WebDriverWait minWait;

    public String firstNameMandatoryErrorMessage = "Forename is required";
    public String emailIDMandatoryErrorMessage = "Email is required";
    public String emailIDInvalidErrorMessage = "Please enter a valid email";
    public String telNumberInvalidErrorMessage = "Please enter a valid telephone number";
    public String messageMandatoryErrorMessage = "Message is required";

    public contactPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        maxWait = new WebDriverWait(driver, Integer.parseInt(getGlobalPropertyFromFile("maxWaitTime")));
        minWait = new WebDriverWait(driver, Integer.parseInt(getGlobalPropertyFromFile("minWaitTime")));
    }

    // Declare all objects related to landing page

    @FindBy(id = "forename")
    WebElement firstNameTextBox;
    @FindBy(id = "surname")
    WebElement secondNameTextBox;
    @FindBy(id = "email")
    WebElement emialIdTextBox;
    @FindBy(id = "telephone")
    WebElement telNumTextBox;
    @FindBy(id = "message")
    WebElement messageTextBox;
    // a[contains(text(),'Submit')]
    // className = "btn-contact btn btn-primary"
    @FindBy(css = "a[class='btn-contact btn btn-primary']")
    WebElement submitButton;

    // Error Messages for each fields

    @FindBy(id = "forename-err")
    WebElement errorFirstName;
    @FindBy(id = "email-err")
    WebElement errorEmailID;

    @FindBy(id = "telephone-err")
    WebElement errorTelNum;

    @FindBy(id = "message-err")
    WebElement errorMessage;

    // Successful sumbission of contact details

    @FindBy(css = "div[class='alert alert-success']")
    WebElement successMessage;
    @FindBy(css = "a[class='btn']")
    WebElement backButton;

    // Enter first name
    public Boolean enterFirstName(String frstName) {
        Assert.assertNotNull(frstName, "Null value in First name to enter ");
        try {
            firstNameTextBox.sendKeys(frstName);
            System.out.println("First Name [" + frstName + "] entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Error while entering first name on Contacts page " + e.getMessage());
            return false;
        }
    }

    // Enter second name
    public Boolean enterSecondName(String secName) {
        Assert.assertNotNull(secName.trim(), "Null value in Second name to enter ");
        try {
            secondNameTextBox.sendKeys(secName);
            System.out.println("Second Name [" + secName + "] entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Error while entering second name on Contacts page " + e.getMessage());
            return false;
        }
    }

    // Enter emialId name
    public Boolean enterEmailID(String emailID) {
        Assert.assertNotNull((emailID.trim()), "Null value in emailID to enter ");
        try {
            emialIdTextBox.sendKeys(emailID);
            System.out.println("EmailID [" + emailID + "] entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Error while entering email ID on Contacts page " + e.getMessage());
            return false;
        }
    }

    // Enter Tel number
    public Boolean enterTelPhoneNumber(String telNum) {
        Assert.assertNotNull((telNum.trim()), "Null value in telNum to enter ");
        try {
            telNumTextBox.sendKeys(telNum);
            System.out.println("Tel Number [" + telNum + "] entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Error while entering Telephome number on Contacts page " + e.getMessage());
            return false;
        }
    }

    // Enter Message
    public Boolean enterMessage(String msg) {
        Assert.assertNotNull((msg), "Null value in Message to enter ");
        try {
            messageTextBox.sendKeys(msg);
            System.out.println("Message [" + msg + "] entered successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Error while entering Message on Contacts page " + e.getMessage());
            return false;
        }
    }

    // Clik on submit button
    public Boolean enterSubmitButton() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            submitButton.click();
            System.out.println("Submit button clicked successfully");
            return true;
        } catch (Exception e) {
            System.out.println("Error while clicking submit button on Contacts page " + e.getMessage());
            return false;
        }
    }

    // Validate error message on the First name field
    public Boolean validateErrorMessageOnFirstNameField(String errorMsg) {
        try {
            minWait.until(ExpectedConditions.visibilityOf(errorFirstName));
            if (errorFirstName.getText().trim().equalsIgnoreCase(errorMsg)) {
                System.out.println("Error message on First Name field matches : " + errorMsg);
                return true;
            } else {
                System.out.println("Error message on First Name field doesn't matches : expected: " + errorMsg
                        + " Actual : " + errorFirstName.getText().trim());
                return false;
            }
        } catch (TimeoutException eTimeout) {
            System.out.println("Error message on First Name field doesn't exists");
            return false;
        } catch (Exception e) {
            System.out.println(
                    "Error while validating error message for first name field on Contacts page " + e.getMessage());
            return null;
        }
    }

    // Validate error message on the email ID field
    public Boolean validateErrorMessageOnEmailIDField(String errorMsg) {
        try {
            minWait.until(ExpectedConditions.visibilityOf(errorEmailID));
            if (errorEmailID.getText().trim().equalsIgnoreCase(errorMsg)) {
                System.out.println("Error message on email ID field matches : " + errorMsg);
                return true;
            } else {
                System.out.println("Error message on email ID field doesn't matches : expected: " + errorMsg
                        + " Actual : " + errorEmailID.getText().trim());
                return false;
            }
        } catch (TimeoutException eTimeout) {
            System.out.println("Error message on email ID field doesn't exists");
            return false;
        } catch (Exception e) {
            System.out.println(
                    "Error while validating error message for emailID field on Contacts page " + e.getMessage());
            return null;
        }
    }

    // Validate error message on the telphone field
    public Boolean validateErrorMessageOnTelNumField(String errorMsg) {
        try {
            minWait.until(ExpectedConditions.visibilityOf(errorTelNum));
            if (errorTelNum.getText().trim().equalsIgnoreCase(errorMsg)) {
                System.out.println("Error message on Tel Number field matches : " + errorMsg);
                return true;
            } else {
                System.out.println("Error message on Tel Number field doesn't matches : expected: " + errorMsg
                        + " Actual : " + errorTelNum.getText().trim());
                return false;
            }
        } catch (TimeoutException eTimeout) {
            System.out.println("Error message on Tel Num field doesn't exists");
            return false;
        } catch (Exception e) {
            System.out.println(
                    "Error while validating error message for Tel Number field on Contacts page " + e.getMessage());
            return null;
        }
    }

    // Validate error message on the message field
    public Boolean validateErrorMessageOnMessageField(String errorMsg) {
        try {
            minWait.until(ExpectedConditions.visibilityOf(errorMessage));
            if (errorMessage.getText().trim().equalsIgnoreCase(errorMsg)) {
                System.out.println("Error message on Message field matches : " + errorMsg);
                return true;
            } else {
                System.out.println("Error message on Message field doesn't matches : expected: " + errorMsg
                        + " Actual : " + errorMessage.getText().trim());
                return false;
            }
        } catch (TimeoutException eTimeout) {
            System.out.println("Error message on Message field doesn't exists");
            return false;
        } catch (Exception e) {
            System.out.println(
                    "Error while validating error message for Tel Number field on Contacts page " + e.getMessage());
            return null;
        }
    }

    // Validate success message after submission of the contact details
    // The success message contains the first name

    public Boolean validateSuccessMessage(String FirstName) {

        String expectedSuccessMessage = "Thanks " + FirstName + ", we appreciate your feedback.";
        try {
            maxWait.until(ExpectedConditions.visibilityOf(successMessage));
            if (successMessage.getText().trim().equals(expectedSuccessMessage)) {
                System.out.println("Success message on Message submission exists and is correct "
                        + successMessage.getText().trim());
                return true;
            } else {
                System.out.println("Success message on Message submission exists but is not correct Expected "
                        + expectedSuccessMessage + "Actual " + successMessage.getText().trim());
                return false;
            }

        } catch (TimeoutException eTimeout) {
            System.out.println("Success message on Message submission doesn't exists");
            return false;
        } catch (Exception e) {
            System.out.println("Exception on  Message submission page " + e.getMessage());
            return null;
        }

    }

    // Click on Vack button on the success page
    public Boolean clickOnBackButtonOnSuccessPage() {
        try {
            maxWait.until(ExpectedConditions.visibilityOf(backButton));
            backButton.click();
            // Validate the contact page opens properly
            minWait.until(ExpectedConditions.visibilityOf(firstNameTextBox));
            return true;
        } catch (Exception e) {
            System.out.println("Exception while clicking on back button on Message submission page " + e.getMessage());
            return null;
        }

    }
}
