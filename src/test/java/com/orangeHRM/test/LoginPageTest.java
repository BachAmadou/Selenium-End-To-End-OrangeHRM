package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;

public class LoginPageTest extends BaseClass {
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setupPages() {
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test
	public void verifyValidLoginTest() {
		loginPage.login("admin", "admin123");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after successfull login");
		homePage.logout();
		staticWait(3);
	}
	
	@Test
	public void invalidLoginTest() {
		loginPage.login("admin", "admin");
		String expectedErrorMsg = "Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMsg), "Test Failed Invalid Error Message");
	}

}
