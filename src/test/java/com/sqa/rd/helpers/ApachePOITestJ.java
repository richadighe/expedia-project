package com.sqa.rd.helpers;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.testng.*;
import org.testng.annotations.*;

import com.sqa.rd.helpers.exceptions.*;

public class ApachePOITestJ {
	@DataProvider
	public Object[][] getData() {
		Object[][] data = DataHelperPOI.getExcelFileData("src/main/resources/", "calc-area-dp.xlsx", true);
		DisplayHelper.multArray(data);
		return data;
	}

	@Test(enabled = false)
	public void test() {
		try {

			// Get File based on class loader (Setup Needed)
			ClassLoader classLoader = ApachePOITest.class.getClassLoader();
			//
			// Get InputStream via Class Loader (Setup Needed)
			InputStream oldExcelFormatFile = classLoader.getResourceAsStream("poi-example.xls");
			InputStream newExcelFormatFile = classLoader.getResourceAsStream("poi-example.xlsx");

			// Get the file using basic File and relative path to directory
			// InputStream oldExcelFormatFile = new FileInputStream(new
			// File("poi-example.xls"));

			// Get the workbook instance for XLS file or XML (Must use the
			// HSSFWorkbook for old format
			XSSFWorkbook workbook = new XSSFWorkbook(newExcelFormatFile);

			// Get first sheet from the workbook, HSSF for old format
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {

				Row row = rowIterator.next();

				// For each row, iterate through each columns
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					// Gather and print contents
					Cell cell = cellIterator.next();

					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						// System.out.println("Calling a boolean value!!!!");
						System.out.print(cell.getBooleanCellValue() + "\t\t\t");
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t\t\t");
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t\t\t");
						break;
					}
				}
				System.out.println("");
			}
			// Close File Read Stream
			newExcelFormatFile.close();
			// Create an OutputStream to write
			FileOutputStream out = new FileOutputStream(new File("src/main/resources/excel-output.xlsx"));
			// Write the workbook
			workbook.write(out);
			// Close output Stream
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test(dataProvider = "getData")
	public void testCalcArea(String shape, int expectedResult, int parameter1, int parameter2)
			throws IncorrectShapeParametersException, UnsupportedShapeException {
		// int argument = 4;
		// int argument2 = 2;
		// String shape = "Rectangle";
		int actualResult;
		if (shape.equalsIgnoreCase("Circle")) {
			actualResult = calcArea(Shape.CIRCLE, parameter1);
		} else if (shape.equalsIgnoreCase("Square")) {
			actualResult = calcArea(Shape.SQUARE, parameter1);
		} else if (shape.equalsIgnoreCase("Rectangle")) {
			actualResult = calcArea(Shape.RECTANGLE, parameter1, parameter2);
		} else {
			throw new UnsupportedShapeException();
		}
		Assert.assertEquals(actualResult, expectedResult);
	}

	private int calcArea(Shape shape, int... parameters) throws IncorrectShapeParametersException {
		switch (shape) {
		case SQUARE:
			if (parameters.length == 1) {
				return parameters[0] * parameters[0];
			} else {
				throw new IncorrectShapeParametersException();
			}
		case RECTANGLE:
			if (parameters.length == 2) {
				return parameters[0] * parameters[1];
			} else {
				throw new IncorrectShapeParametersException();
			}
		default:
			System.out.println("Shape is not supported");
			break;
		}
		return 0;
	}

}
