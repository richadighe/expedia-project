package com.sqa.rd.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.testng.*;
import org.testng.annotations.*;

import com.sqa.rd.helpers.data.*;

public class ExpediaTest extends WebPageInit {
	// Subroutine to find the element by the id, name or cssselector
	public static WebElement findELement(String findBy, String val) {
		if (findBy == "id")
			return getDriver().findElement(By.id(val));
		else if (findBy == "name")
			return getDriver().findElement(By.name(val));
		else if (findBy == "cssSelector")
			return getDriver().findElement(By.cssSelector(val));
		else
			return null;
	}

	@SuppressWarnings("javadoc")
	@DataProvider
	public static Object[][] getData() {
		return new Object[][] { { "SFO", "ORD", "06/27/2017", "07/01/2017", "1" },
				{ "SFO", "LAS", "6/24/2017", "7/23/2017", "4" }, { "ORD", "LAX", "7/12/2017", "7/15/2017", "1" },
				{ "LAX", "LAS", "6/30/2017", "7/12/2017", "2" }, { "LAX", "SFO", "6/27/2017", "6/29/2017", "3" }, };
	}

	// constructor
	public ExpediaTest(String baseURL) {
		super("https://www.expedia.com/");
	}

	// @Test(dataProvider = "getTextFile")
	@Test(dataProvider = "getData")
	// @Test(dataProvider = "getExcelData")
	public void expediaTest(String origin, String destination, String datefrom, String dateto, String noofppl)
			throws InterruptedException {
		double actualprice;
		getDriver().get(getBaseURL());
		System.out.println("Perform Expedia Test");

		WebDriverWait wait = new WebDriverWait(getDriver(), 60);
		WebElement clickFlightbtn = findELement("id", "tab-flight-tab");

		// getDriver().findElement(By.id("tab-flight-tab"));
		clickFlightbtn.click();

		WebElement clickroundtrip = findELement("id", "flight-type-roundtrip-label");
		// click on roundtrip
		clickroundtrip.click();
		Thread.sleep(10);

		WebElement setorigin = findELement("id", "flight-origin");
		WebElement setdestination = findELement("id", "flight-destination");
		WebElement setdatefrom = findELement("id", "flight-departing");
		WebElement setdateto = findELement("id", "flight-returning");
		WebElement setnoofppl = findELement("id", "flight-adults");
		WebElement searchbuttton = findELement("id", "search-button");

		// set origin
		setorigin.clear();
		setorigin.sendKeys(origin);
		// set destination
		setdestination.clear();
		setdestination.sendKeys(destination);
		// set depart date
		setdatefrom.clear();
		setdatefrom.sendKeys(datefrom);
		// set return date
		setdateto.clear();
		setdateto.sendKeys(dateto);
		// set no of adults
		setnoofppl.sendKeys(noofppl);
		// click search button
		searchbuttton.click();
		// wait till the flight module list appears
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("flightModuleList")));

		WebElement sortbyprice = findELement("name", "sort");
		// sort by the lowest price
		// sort the list by price ascending
		Select selcat = new Select(sortbyprice);
		selcat.selectByValue("price:asc");
		String price = null;
		try {
			price = findELement("cssSelector", ".offer-price.urgent").getText();// .split("$")[1].trim();
			WebElement totalprice = findELement("cssSelector", "span.dollars.price-emphasis");
			actualprice = Double.parseDouble(totalprice.getText().replace("$", ""));

			System.out.println("Flights found for the search result. From " + origin + " to " + destination + ". Dates:"
					+ datefrom + " to " + dateto + " for " + noofppl + " adult(s). " + actualprice);
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			// System.out.println("No flights found");
		}

		// Assert.assertTrue(actualTicketPrice > minExpectedPrice &&
		// actualTicketPrice < maxExpectedPrice,
		// "Ticket price is not within range (" + minExpectedPrice + " - " +
		// maxExpectedPrice + ")");

		Assert.assertTrue(price != null, "No flights found for the search result. From " + origin + " to " + destination
				+ ". Dates:" + datefrom + " to " + dateto + " for " + noofppl + " adult(s)");
	}

	@DataProvider
	public Object[][] getExcelData() {
		Object[][] data = DataHelper.getExcelFileData("src/main/resources/", "expediatest.xlsx", true);
		// DisplayHelper.multArray(data);
		System.out.println("Reading excel data");
		return data;
	}

	@DataProvider
	public Object[][] getTextFile() {
		Object[][] data = DataHelper.getTextFileData("src/main/resources/", "expediatestCSV.csv", TextFormat.CSV, true);
		// DisplayHelper.multArray(data);
		System.out.println("Reading CSV text file data");
		return data;
	}

}
