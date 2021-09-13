package com.jupitortoys.pageObjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.support.ui.WebDriverWait;
import com.jupitortoys.Utils.basicUtils;

public class shoppingPage extends basicUtils {
    WebDriver driver = null;

    private static WebDriverWait maxWait;
    private static WebDriverWait minWait;

    public shoppingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);

        maxWait = new WebDriverWait(driver, Integer.parseInt(getGlobalPropertyFromFile("maxWaitTime")));
        minWait = new WebDriverWait(driver, Integer.parseInt(getGlobalPropertyFromFile("minWaitTime")));
    }

    // Declare all objects related to landing page

    // Items Name
    @FindBy(css = "li[class='product ng-scope']>div>h4[class='product-title ng-binding']")
    List<WebElement> itemNames;

    @FindBy(css = "li[class='product ng-scope']>div>p>span[class='product-price ng-binding']")
    List<WebElement> itemPrices;

    @FindBy(css = "li[class='product ng-scope']>div>p>a[ng-click='add(item)']")
    List<WebElement> itemBuyButtons;

    @FindBy(css = "li[id='nav-cart']>a>span[class='cart-count ng-binding']")
    WebElement chartItemNum;

    // Shopping cart page
    // cart empty alert message
    @FindBy(css = ".alert")
    WebElement emptyCartMessage;

    @FindBy(css = "p[class='cart-msg']")
    WebElement chartMessage;
    @FindBy(css = "strong[class='total ng-binding']")
    WebElement totalPriceOnTable;
    @FindBy(css = "a.btn-checkout")
    WebElement checkOutButton;
    @FindBy(css = "a[class='btn btn-danger ng-scope']")
    WebElement emptyCartButton;

    @FindBy(css = "input[name='quantity']")
    List<WebElement> quantitiesOnTable;
    // Number of columns identified from the header fields
    @FindBy(css = "table[class='table table-striped cart-items']>thead>tr>th")
    List<WebElement> tableColumnHeader;
    // To find the number of rows
    @FindBy(css = "table[class='table table-striped cart-items']>tbody>tr")
    List<WebElement> tableRows;

    private String tagNameForColumn = "td";

    // Select an item by name given
    // Returns the price of the selected item
    public String selectItemByName(String itemName) {
        try {
            String price = null;
            for (int i = 0; i < itemNames.size(); i++) {
                if (itemNames.get(i).getText().equalsIgnoreCase(itemName)) {
                    price = itemPrices.get(i).getText();
                    itemBuyButtons.get(i).click();

                    System.out.println("The index of the selected item  is: " + i);
                    break;
                }
            }

            return price;
        } catch (Exception e) {
            System.out.println("Error while selecting items on shopping page " + e.getMessage());
            return null;
        }
    }

    public Boolean validateChartNumber(String chartNumber) {
        try {
            if (chartItemNum.getText().equals(chartNumber)) {
                System.out.println("The chart count matching : " + chartNumber);
                return true;
            } else {
                System.out.println("The chart count doesn't match : Expected = " + chartNumber + " Actual = "
                        + chartItemNum.getText());
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error while validating the chart numbers on shopping page " + e.getMessage());
            return null;
        }
    }

    public Boolean clickOnCartButton() {
        try {
            minWait.until(ExpectedConditions.visibilityOf(chartItemNum));
            chartItemNum.click();
            maxWait.until(ExpectedConditions.visibilityOf(chartMessage));
            System.out.println("Cart is opened");
            return true;
        } catch (Exception e) {
            System.out.println("Error while clicking on cart on shopping page " + e.getMessage());
            return null;
        }
    }

    // Check if empty cart alert message exists
    public Boolean isAlertEmptyCartExists() {
        try {
            minWait.until(ExpectedConditions.visibilityOf(emptyCartMessage));
            System.out.println("Empty cart alert message exists" + emptyCartMessage.getText());
            return true;

        } catch (Exception e) {
            System.out.println("Empty cart alert message doesn't exists");
            return false;
        }

    }

    // Get the header values for column names of the table
    public List<String> getTableColumns() {
        List<String> allHeaderNames = new ArrayList<String>();
        try {
            for (WebElement header : tableColumnHeader) {
                String headerName = header.getText();
                allHeaderNames.add(headerName);
            }
            return allHeaderNames;
        } catch (Exception e) {
            System.out
                    .println("Exception while getting column names from cart table on shopping page " + e.getMessage());
            return null;
        }
    }

    // Get the cart data into a List <Map> variable, with key as column name
    public List<LinkedHashMap<String, String>> getTableData() {
        List<LinkedHashMap<String, String>> allTableData = new ArrayList<LinkedHashMap<String, String>>();
        List<String> columnNames = getTableColumns();
        try {
            Integer indexRow = 0;
            Integer indexColName = 0;
            for (WebElement row : tableRows) {
                LinkedHashMap<String, String> eachRowData = new LinkedHashMap<>();
                // System.out.println("---" + row.getText());
                for (WebElement cell : row.findElements(By.tagName(tagNameForColumn))) {

                    // System.out.println(columnNames.get(indexColName) + "---" + cell.getText());
                    eachRowData.put(columnNames.get(indexColName), cell.getText());

                    indexColName++;
                }
                // Since quantities are not recognised from td tagname, use below identifier
                eachRowData.put("Quantity", quantitiesOnTable.get(indexRow).getAttribute("value"));
                indexRow++;
                allTableData.add(eachRowData);
                indexColName = 0;
            }
            return allTableData;
        } catch (Exception e) {
            System.out.println("Exception while getting table data from cart table on shopping page " + e.getMessage());
            return null;
        }
    }

    public Double getTotalPriceFromTable() {

        return Double.parseDouble(totalPriceOnTable.getText().replaceAll("[^0-9.]", ""));

    }

}
