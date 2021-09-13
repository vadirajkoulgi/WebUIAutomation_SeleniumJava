package com.jupitortoys.Utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class basicUtils {
    /*
     * get global properties It
     */

    private static String applicationURL = "https://jupiter.cloud.planittesting.com";
    private static String projPath = System.getProperty("user.dir");
    private static String fileSeparator = System.getProperty("file.separator");

    private static String pathGlobalProperties = projPath + fileSeparator + "src" + fileSeparator + "main"
            + fileSeparator + "resources" + fileSeparator + "properties" + fileSeparator
            + "globalProperties.properties";

    public static Properties getGlobalPropertiesFromFile() {
        try {

            InputStream globalPropFile = new FileInputStream(pathGlobalProperties);
            Properties globalProperties = new Properties();
            globalProperties.load(globalPropFile);
            return globalProperties;

        } catch (Exception e) {
            System.out.println("Error : Exception while reading global properties file : " + e.getMessage());
            return null;
        }
    }

    public static String getGlobalPropertyFromFile(String propKey) {
        try {

            InputStream globalPropFile = new FileInputStream(pathGlobalProperties);
            Properties globalProperties = new Properties();
            globalProperties.load(globalPropFile);
            return globalProperties.getProperty(propKey);

        } catch (Exception e) {
            System.out.println("Error : Exception while reading global properties file : " + e.getMessage());
            return null;
        }
    }

    /*
     * Launch Application in the given browser (info from global properties file)
     * 
     */

    public static WebDriver launchApplication() {
        String browserType = getGlobalPropertyFromFile("browser");
        String expectedPageTitle = "Jupiter Toys";

        WebDriver driver = null;
        try {
            if (browserType.equalsIgnoreCase("Chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }

            if (browserType.equalsIgnoreCase("FireFox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }

            if (browserType.equalsIgnoreCase("Edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            // Fail the test execution if driver is null
            Assert.assertNotNull(driver);

            // Browser at full screen max size & delete all cookies
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();

            // launch application
            driver.get(applicationURL);

            // Validate the page title
            String actualPageTitle = driver.getTitle();
            Assert.assertNotNull(actualPageTitle, "Exception after app launch - Page title can't be null");
            Assert.assertEquals(actualPageTitle, expectedPageTitle,
                    "Invalid PageTitle - Expected:" + expectedPageTitle + " Actual : " + actualPageTitle);

            System.out.println("Application is launched properly with tile - " + actualPageTitle);
            return driver;

        } catch (Exception e) {
            System.out.println("Error : Exception while launching Application : " + e.getMessage());
            return null;
        }

    }

    // Generate random alphabetic chars

    protected static String getAlphabeticString(int n) {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String AlphabeticString = randomString.replaceAll("[^A-Za-z]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphabeticString.length(); k++) {
            if (Character.isLetter(AlphabeticString.charAt(k)) && (n > 0)) {

                r.append(AlphabeticString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }

    // Generate random alphaNumeric chars

    protected static String getAlphaNumericString(int n) {

        // length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString = new String(array, Charset.forName("UTF-8"));

        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();

        // remove all spacial char
        String AlphaNumericString = randomString.replaceAll("[^A-Za-z0-9]", "");

        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < AlphaNumericString.length(); k++) {
            if (Character.isLetter(AlphaNumericString.charAt(k)) && (n > 0)
                    || Character.isDigit(AlphaNumericString.charAt(k)) && (n > 0)) {

                r.append(AlphaNumericString.charAt(k));
                n--;
            }
        }

        // return the resultant string
        return r.toString();
    }

}
