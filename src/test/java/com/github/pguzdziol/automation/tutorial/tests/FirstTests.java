package com.github.pguzdziol.automation.tutorial.tests;

import com.github.pguzdziol.automation.tutorial.pages.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
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
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}

	@BeforeMethod(alwaysRun = true)
	public void openHomePage() {
		homePage = new HomePage(driver);
		homePage = homePage.open();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void testAddingItemToCard() {

		SearchResultsPage searchResultsPage = homePage.navigationMenu().searchFor("Books", "Selenium");
		String itemTitle = searchResultsPage.getFirstResultTitle();
		ProductDetailsPage productDetailsPage = searchResultsPage.clickFirstResultTitle();
		assert (productDetailsPage.getProductTitle().equals(itemTitle));
		AddToCartConfirmPage addToCartConfirmPage = productDetailsPage.addToCart();
		assert (addToCartConfirmPage.getConfirmationText().equals("1 item added to Cart"));
		CartPage cartPage = addToCartConfirmPage.navigationMenu().navigateToCartPage();
		assert (cartPage.getFirstItemText().contains(itemTitle));
	}

	@Test
	public void testSignInSignOut() {
		homePage.navigationMenu().navigateToLoginPage().loginAs("automation.user2015@gmail.com", "@ut0m@t!0n");
		assert (homePage.navigationMenu().isUserLogged("Automation"));
		LoginPage loginPage = homePage.navigationMenu().signOut();
		assert (loginPage.isLoaded());
	}
}
