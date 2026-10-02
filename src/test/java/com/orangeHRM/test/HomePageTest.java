package com.orangeHRM.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.orangeHRM.base.BaseClass;
import com.orangeHRM.pages.HomePage;
import com.orangeHRM.pages.LoginPage;

public class HomePageTest extends BaseClass{
	
private LoginPage loginPage;
private HomePage homePage;

@BeforeMethod
public void setupPage() {
	
	loginPage = new LoginPage(getDriver());
	homePage = new HomePage(getDriver());
}

@Test
public void verifyOrangeHRMLogo() {
		
	loginPage.login("admin", "admin123");
	Assert.assertTrue(homePage.verifyOrangeHRMlogo(), "Logo is visible");
}

}
