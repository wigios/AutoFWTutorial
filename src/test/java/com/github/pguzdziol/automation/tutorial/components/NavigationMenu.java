package com.github.pguzdziol.automation.tutorial.components;

import com.github.pguzdziol.automation.tutorial.pages.CartPage;
import com.github.pguzdziol.automation.tutorial.pages.LoginPage;
import com.github.pguzdziol.automation.tutorial.pages.SearchResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class NavigationMenu {
	private static final By SEARCH_DROPDOWN_BOX = By.id("searchDropdownBox");
	private static final By SEARCH_INPUT = By.id("twotabsearchtextbox");
	private static final By SEARCH_GO_BUTTON = By.xpath("//*[@value='Go']");
	private static final By NAVIGATION_ITEM_CART = By.id("nav-cart");
	private static final By NAVIGATION_ITEM_YOUR_ACCOUNT = By.id("nav-link-yourAccount");
	private static final By SIGN_IN_BUTTON = By.linkText("Sign in");
	private static final By SIGN_OUT_BUTTON = By.linkText("Not Automat...? Sign Out");
	private WebDriver driver;

	public NavigationMenu(WebDriver driver) {
		this.driver = driver;
	}

	public SearchResultsPage searchFor(String category, String searchKey) {
		new Select(driver.findElement(SEARCH_DROPDOWN_BOX))
				.selectByVisibleText(category);
		driver.findElement(SEARCH_INPUT)
				.sendKeys(searchKey);
		driver.findElement(SEARCH_GO_BUTTON)
				.click();
		return new SearchResultsPage(driver);
	}

	public CartPage navigateToCartPage() {
		driver.findElement(NAVIGATION_ITEM_CART)
				.click();
		return new CartPage(driver);
	}

	public LoginPage navigateToLoginPage() {
		driver.findElement(NAVIGATION_ITEM_YOUR_ACCOUNT)
				.click();
		driver.findElement(SIGN_IN_BUTTON)
				.click();
		return new LoginPage(driver);
	}

	public boolean isUserLogged(String name) {
		return driver.findElement(NAVIGATION_ITEM_YOUR_ACCOUNT)
				.getText()
				.contains("Hello, " + name);
	}

	public LoginPage signOut() {
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(NAVIGATION_ITEM_YOUR_ACCOUNT))
				.perform();
		driver.findElement(SIGN_OUT_BUTTON)
				.click();
		return new LoginPage(driver);
	}
}
