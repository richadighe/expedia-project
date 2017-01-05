package com.sqa.rd.helpers;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import org.apache.commons.io.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.testng.annotations.*;

public class WebPageInit {
	private static String baseURL = "http://www.google.com";
	private static WebDriver driver;

	public static void captureScreenshot(String keywords) {
		// get screenshots of test
		File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(src, new File("screenshots/" + keywords + "_" + new Date().getTime() + ".png"));
		} catch (IOException e) {
		}
	}
	// @DataProvider
	// public Object[][] dp() {
	// return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2, "b" },
	// };
	// }

	/**
	 * @return the baseURL
	 */
	public static String getBaseURL() {
		return baseURL;
	}

	/**
	 * @return the driver
	 */
	public static WebDriver getDriver() {
		return driver;
	}

	@BeforeClass
	public static void setup() {
		// driver = new FirefoxDriver();
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(baseURL);
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	// constructor to initialize base URL
	public WebPageInit(String baseURL) {
		super();
		WebPageInit.baseURL = baseURL;
	}

}
