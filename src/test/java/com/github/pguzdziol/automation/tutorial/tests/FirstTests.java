package com.github.pguzdziol.automation.tutorial.tests;

import com.github.pguzdziol.automation.tutorial.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class FirstTests {

	private WebDriver driver;
	private HomePage homePage;

	@BeforeClass(alwaysRun = true)
	public void setUp() {
		driver = new FirefoxDriver();
		driver.manage()
				.timeouts()
				.implicitlyWait(5, TimeUnit.SECONDS);
	}

	@BeforeMethod(alwaysRun = true)
	public void openHomePage() {
		homePage = new HomePage(driver).open();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void testAddingItemToCard() {
		SearchResultsPage searchResultsPage = homePage.open()
				.navigationMenu()
				.searchFor("Books", "Selenium");
		String itemTitle = searchResultsPage.getFirstResultTitle();
		ProductDetailsPage productDetailsPage = searchResultsPage.clickFirstResultTitle();
		assert (productDetailsPage.getProductTitle()
				.equals(itemTitle));
		AddToCartConfirmPage addToCartConfirmPage = productDetailsPage.addToCart();
		assert (addToCartConfirmPage.getConfirmationText()
				.equals("1 item added to Cart"));
		CartPage cartPage = addToCartConfirmPage.navigationMenu()
				.navigateToCartPage();
		assert (cartPage.getFirstItemText()
				.contains(itemTitle));
	}

	@Test
	public void testSignInSignOut() { //TODO refactor to use page objects
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
