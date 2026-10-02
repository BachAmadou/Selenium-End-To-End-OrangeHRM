package com.orangeHRM.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.base.BaseClass;

public class LoginPage {
	
	private ActionDriver actionDriver;

	// Define locator using By
	private By userNameField = By.xpath("//input[@name='username']");
	private By passwordField = By.xpath("//input[@type='password']");
	private By loginButton = By.xpath("//button[text()=' Login ']");
	private By errorMessage = By.xpath("//p[text()='Invalid credentials']");

	// Constructor of login page
//	public LoginPage(WebDriver driver) {
//		this.actionDriver = new ActionDriver(driver);
//	}

	
	public LoginPage(WebDriver driver) {
		this.actionDriver = BaseClass.getActionDriver();
	}
	
	// Method for login
	public void login(String userName, String password) {
		actionDriver.enterText(userNameField, userName);
		actionDriver.enterText(passwordField, password);
		actionDriver.click(loginButton);
	}
	
	// Method to check is error message is displayed
	public boolean isErrorMessageDisplayed() {
		return actionDriver.isDisplayed(errorMessage);
	}
	
	// Method to get the text form error message
	public String getErrorMessageText() {
		return actionDriver.getText(errorMessage);
	}

	// Verify if error message displayed or not
	public boolean verifyErrorMessage(String expectedError) {
		return actionDriver.compareText(errorMessage, expectedError);
	}

}
