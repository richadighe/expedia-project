/**
 *   File Name: DataHelper.java<br>
 *
 *   LastName, FirstName<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Jean-francois Nepton<br>
 *   Created: Jun 13, 2016
 *
 */

package com.sqa.rd.helpers;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * DataHelper //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author LastName, FirstName
 * @version 1.0.0
 * @since 1.0
 *
 */
public class DataHelperPOI {

	public static Object[][] getExcelFileData(String fileLocation, String fileName, Boolean hasLabels) {
		// Object[][] data = { { "Square", 36, 6, 0 }, { "Rectangle", 12, 4, 3 }
		// };
		ArrayList<Object> results = new ArrayList<Object>();
		Object[][] resultsObject;
		// TODO Implement Helper Method
		try {

			// Get File based on class loader (Setup Needed)
			// ClassLoader classLoader = ApachePOITest.class.getClassLoader();
			//
			// Get InputStream via Class Loader (Setup Needed)
			// InputStream oldExcelFormatFile =
			// classLoader.getResourceAsStream("poi-example.xls");
			// InputStream newExcelFormatFile =
			// classLoader.getResourceAsStream("poi-example.xlsx");

			// Get the file using basic File and relative path to directory
			String fullFilePath = fileLocation + fileName;
			InputStream newExcelFormatFile = new FileInputStream(new File(fullFilePath));

			// Get the workbook instance for XLS file or XML (Must use the
			// HSSFWorkbook for old format
			@SuppressWarnings("resource")
			// HSSFWorkbook workbook = new HSSFWorkbook(newExcelFormatFile);
			XSSFWorkbook workbook = new XSSFWorkbook(newExcelFormatFile);

			// Get first sheet from the workbook, HSSF for old format
			// HSSFSheet sheet = workbook.getSheetAt(0);
			XSSFSheet sheet = workbook.getSheetAt(0);
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();

			// Move one row if there is a label
			if (hasLabels) {
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				ArrayList<Object> rowData = new ArrayList<Object>();

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
						rowData.add(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print((int) cell.getNumericCellValue() + "\t\t\t");
						rowData.add((int) cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t\t\t");
						rowData.add(cell.getStringCellValue());
						break;
					}
				}
				Object[] rowDataObject = new Object[rowData.size()];
				rowData.toArray(rowDataObject);
				results.add(rowDataObject);
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
		resultsObject = new Object[results.size()][];
		results.toArray(resultsObject);
		return resultsObject;
	}

	// public static Object[][] getExcelFileData(String fileLocation, String
	// fileName, TextFormat textFormat) {
	// }
	//
	// public static Object[][] getExcelFileData(String fileLocation, String
	// fileName, TextFormat textFormat,
	// Boolean hasLabels, Object... dataTypes) {
	// }
	//
	// public static Object[][] getExcelFileData(String fileLocation, String
	// fileName, TextFormat textFormat,
	// Object... dataTypes) {
	// }

}