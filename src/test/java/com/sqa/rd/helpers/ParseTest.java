/**
 *   File Name: Test.java<br>
 *
 *   Dighe, Richa<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Richa Dighe<br>
 *   Created: Jun 11, 2016
 *   
 */
package com.sqa.rd.helpers;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ParseTest {
	public static ArrayList<Object> tests = new ArrayList<Object>();
	public static StringBuilder testString = new StringBuilder();

	public static void main(String[] args) {
		// The name of the file to open.
		String fileNameOld = "temp.txt";
		String fileName = "temp.json";
		// This will reference one line at a time
		String line = null;
		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				// TODO Evalaute the line
				gatherDataString(line);
			}
			evaluateDataString();
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
		displayTests();
	}

	public static void mainNew(String args[]) {

		// String to be scanned to find the pattern.
		String line = "This order was placed for QT3000! OK?";
		String pattern = "(.*)(\\d+)(.*)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		if (m.find()) {
			System.out.println("Found value: " + m.group(0));
			System.out.println("Found value: " + m.group(1));
			System.out.println("Found value: " + m.group(2));
		} else {
			System.out.println("NO MATCH");
		}
	}

	private static void displayTests() {
		System.out.println(testString.toString());

	}

	private static void evaluateDataString() {
		// String[] tests = testString.toString().split("},{");
		// for(int i = 0; i < tests.length; i++) {
		// //String[] elements = "\d";
		// "\d+"
		// }
		// String regexString = "\\"\\d+\\"";
		String myString = testString.toString();

		// String pattern = "(\")(\\A)(\")";
		String pattern = "(\")([n,u,m]+[1,2]+)(\")";
		// String pattern = "(\")([a-zA-Z]+[0-9]+)(\")";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(myString);

		while (m.find()) {
			System.out.println("Found value: " + m.group(2));
		}
	}

	private static void gatherDataString(String line) {
		testString.append(line);
		// System.out.println(line);
	}

	// private static void displayTests() {
	// System.out.println("Number of tests:" + tests.size());
	// for(int i = 0; i < tests.size(); i++) {
	// String[] parameters = (String[])tests.get(i);
	// System.out.print(parameters[0] + ",");
	// System.out.print(parameters[1] + ",");
	// System.out.println(parameters[2]);
	// }
	// }

	// private static void evaluateLine(String line) {
	// String[] parameters = new String[3];
	// String[] elements = line.split(",");
	// parameters[0] = elements[0].split("=")[1];
	// parameters[1] = elements[1].split("=")[1];
	// parameters[2] = elements[2].split("=")[1];
	// tests.add(parameters);
	// }
}
