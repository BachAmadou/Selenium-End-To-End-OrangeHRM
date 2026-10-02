package com.orangeHRM.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.orangeHRM.actiondriver.ActionDriver;
import com.orangeHRM.utilities.LoggerManager;

public class BaseClass {
	
	protected static Properties prop;
	protected static WebDriver driver;
	private static ActionDriver actionDriver;
	public static final Logger logger = LoggerManager.getLogger(BaseClass.class);
	
	@BeforeSuite
	public void loadConfig() throws IOException {
		// Load the configuration file
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);	
		logger.info("Config.properties file loaded");
		logger.trace("Trace message");
		logger.error("This is an error message");
		logger.debug("This is a debug message");
		logger.fatal("This is a fatal message");
		logger.warn("This is a warn message");
	}
	
	@BeforeMethod
	public void setup() throws IOException {
		System.out.println("Setting up WebDriver for: " + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(3);
		logger.info("WebDriver initialized and browser maximized");
		
		// Initialize actionDriver only once(Singleton design)
		if(actionDriver == null) {
			actionDriver = new ActionDriver(driver);
			logger.info("ActioinDriver instance is created.");
		}
	}
	
	// Initialize the webDriver based on browser defined in config.properties file
	private void launchBrowser() {
		String browser = prop.getProperty("browser");
		
		if(browser.equalsIgnoreCase("chrome")) {
			logger.info("ChromeDriver instance is created");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
			logger.info("FirefoxDriver instance is created");
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
			logger.info("EdgeDriver instance is created");
		} else {
			throw new IllegalArgumentException("Browser Not Supported:" + browser);
		}
	}
	
	private void configureBrowser() {
	// Implicit Wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
	
		// Maximize the driver
		driver.manage().window().maximize();
		
		// Navigate to the URL
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to navigate to the url:" + e.getMessage());
		}
	}
	
	@AfterMethod
	public void tearDown() {
		if(driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("Unable to quit the browser:" + e.getMessage());
			}
		}
		logger.info("WebDriver instance is closed");
		driver = null;
		actionDriver = null;
	}
	
	// Getter method for prop
	public static Properties getProp() {
		return prop;
	}
	
	/*	// Driver getter method
	public WebDriver getDriver() {
		return driver;
	}
	*/
	
	// Getter method for WebDriver
	public static WebDriver getDriver() {
		if(driver == null) {
			System.out.println("WedDriver is not initialized");
			throw new IllegalStateException("WebDriver is not initialized");
		}
		return driver;
	}
	
	// Getter method for ActionDriver
		public static ActionDriver getActionDriver() {
			if(actionDriver == null) {
				System.out.println("ActionDriver is not initialized");
				throw new IllegalStateException("ActionDriver is not initialized");
			}
			return actionDriver;
		}
	
	
	// Driver setter method
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
	// Use this method when we want to pause or debug or wait a little bit more.
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}
}
