/**
 *   File Name: MultArrayTest.java<br>
 *
 *   Dighe, Richa<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Richa Dighe<br>
 *   Created: Jun 1, 2016
 *   
 */

package com.sqa.rd.helpers;

import org.junit.*;

import com.sqa.rd.helpers.*;

public class MultArrayTest {

	@Test
	public void test() {
		Object[][] arrayData = MultArray.getData();
		for (int i = 0; i < arrayData.length; i++) {
			for (int j = 0; j < arrayData[i].length; j++) {
				System.out.print(arrayData[i][j] + "\t");
			}
			System.out.println();

		}
	}

}
