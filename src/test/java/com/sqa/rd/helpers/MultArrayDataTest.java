package com.sqa.rd.helpers;

import org.testng.*;
import org.testng.annotations.*;

public class MultArrayDataTest {
	@DataProvider
	public Object[][] dp() {
		// return new Object[][] { new Object[] { 1, "a" }, new Object[] { 2,
		// "b" },
		return MultArray.getData();
	}

	/*
	 * public void f(Integer n, String s) { System.out.println("Test " + n);
	 * System.out.println("Value " + s); }
	 */
	@Test(dataProvider = "dp")
	public void multArrayTest(String title, int num1, int num2, int num3, int expectedResult) {
		System.out.println(title);
		System.out.println("Adding: " + num1 + "+" + num2 + "+" + num3 + "=" + expectedResult + "?");
		int actualResult = addNumbers(num1, num2, num3);
		Assert.assertEquals(expectedResult, actualResult);
		System.out.println("Passed");
	}

	private int addNumbers(int num1, int num2, int num3) {
		return (num1 + num2 + num3);
	}
}