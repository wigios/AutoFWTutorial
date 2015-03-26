package com.github.pguzdziol.automation.tutorial.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class FirstTests {

    private WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = new FirefoxDriver();
        driver.manage()
                .timeouts()
                .implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeMethod(alwaysRun = true)
    public void openHomePage() {
        driver.get("http://www.amazon.com");
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testAddingItemToCard() {
        //Select 'Books' from search category dropdown
        new Select(driver.findElement(By.id("searchDropdownBox")))
                .selectByVisibleText("Books");

        //Enter search key: 'Selenium'
        driver.findElement(By.id("twotabsearchtextbox"))
                .sendKeys("Selenium");

        //Click 'Go' button
        driver.findElement(By.xpath("//*[@value='Go']"))
                .click();

        //Click the first search result item title
        WebElement firstItemTitleElement = driver.findElement(By.className("s-access-title"));
        String firstItemTitle = firstItemTitleElement.getText();
        firstItemTitleElement.click();

        //Verify product title
        assert (driver.findElement(By.id("productTitle"))
                .getText()
                .equals(firstItemTitle));

        //Click 'Add to cart' button
        driver.findElement(By.id("add-to-cart-button"))
                .click();

        //Verify confirmation text appears
        assert (driver.findElement(By.id("confirm-text"))
                .getText()
                .equals("1 item added to Cart"));

        //Navigate to 'Cart' page
        driver.findElement(By.id("nav-cart"))
                .click();

        //Verify item is displayed on Shopping Cart list
        assert (driver.findElement(By.className("a-list-item"))
                .getText()
                .contains(firstItemTitle));
    }

    @Test
    public void testSignInSignOut() {
        //Navigate to 'Your Account' page
        driver.findElement(By.id("nav-link-yourAccount"))
                .click();
        driver.findElement(By.linkText("Sign in"))
                .click();

        //Enter e-mail address
        driver.findElement(By.id("ap_email"))
                .sendKeys("automation.user2015@gmail.com");

        //Enter password
        driver.findElement(By.id("ap_password"))
                .sendKeys("@ut0m@t!0n");

        //Click 'Sign in using our secure server' button
        driver.findElement(By.id("signInSubmit-input"))
                .click();

        //Verify 'Your Account' button contains the name of the user
        assert (driver.findElement(By.id("nav-link-yourAccount"))
                .getText()
                .contains("Hello, Automation"));

        //Hover over a "Your account" button and click on "Sign Out"
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(By.id("nav-link-yourAccount")))
                .perform();
        driver.findElement(By.linkText("Not Automat...? Sign Out"))
                .click();

        //Verify "Sign In" form appears
        assert (driver.findElement(By.name("signIn"))
                .isDisplayed());
    }
}
