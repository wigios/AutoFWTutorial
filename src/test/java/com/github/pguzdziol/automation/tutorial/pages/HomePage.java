package com.github.pguzdziol.automation.tutorial.pages;

import com.github.pguzdziol.automation.tutorial.components.NavigationMenu;
import org.openqa.selenium.WebDriver;

public class HomePage {
	private static final String AMAZON_HOME_PAGE_URL = "http://www.amazon.com";
	private final NavigationMenu navigationMenu;
	private WebDriver driver;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.navigationMenu = new NavigationMenu(driver);
	}

	public HomePage open() {
		driver.get(AMAZON_HOME_PAGE_URL);
		return this;
	}

	public NavigationMenu navigationMenu() {
		return navigationMenu;
	}
}
