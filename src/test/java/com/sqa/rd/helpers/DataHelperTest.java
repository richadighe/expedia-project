package com.sqa.rd.helpers;

import org.testng.*;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import com.sqa.rd.helpers.data.*;

public class DataHelperTest {
	@DataProvider(name = "textData")
	public Object[][] getData() {
		Object[][] data;

		data = DataHelper.getTextFileData("C:\\Users\\Richa\\workspace\\basic-project\\src\\main\\resources\\",
				"data1.csv", TextFormat.CSV, true);

		// data =
		// DataHelper.getTextFileData("/basic-project/src/main/resources/",
		// "data1.csv", TextFormat.CSV, true);
		DisplayHelper.multArray(data);
		return data;
	}

	@Test(dataProvider = "textData")
	public void textReadingFile(String number, String isPrime) {
		try {
			System.out.println("Number " + number + ", is Prime? (" + isPrime + ")");
			boolean actualResult = isPrime(Integer.parseInt(number));
			Assert.assertEquals(actualResult, Boolean.parseBoolean(isPrime), "Number is not prime based on data set.");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private boolean isPrime(int number) {
		boolean isPrime = true;
		for (int i = 2; i <= number / 2; i++) {
			if (number % i == 0) {
				isPrime = false;
			}
		}
		return isPrime;
	}
}