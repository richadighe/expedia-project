/**
 *   File Name: DataHelper.java<br>
 *
 *   Dighe, Richa<br>
 *   Java Boot Camp Exercise<br>
 *   Instructor: Richa Dighe<br>
 *   Created: Jun 13, 2016
 *   
 */

package com.sqa.rd.helpers;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import com.sqa.rd.helpers.data.*;

public class DataHelper {

	public static Object[][] evalDatabaseTable(String driverClassString, String databaseStringUrl, String username,
			String password, String tableName) throws ClassNotFoundException, SQLException {

		return evalDatabaseTable(driverClassString, databaseStringUrl, username, password, tableName, 0, 0);
	}

	/**
	 * @param driverClassString
	 * @param databaseStringUrl
	 * @param username
	 * @param password
	 * @param tableName
	 * @param rowOffset
	 * @param colOffset
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Object[][] evalDatabaseTable(String driverClassString, String databaseStringUrl, String username,
			String password, String tableName, int rowOffset, int colOffset)
			throws ClassNotFoundException, SQLException {

		Object[][] myData;
		ArrayList<Object> myArrayData = new ArrayList<Object>();

		Class.forName(driverClassString);

		Connection dbconn = DriverManager.getConnection(databaseStringUrl, username, password);

		Statement stmt = dbconn.createStatement();

		ResultSet rs = stmt.executeQuery("select * from " + tableName);

		int numOfColumns = rs.getMetaData().getColumnCount();
		int curRow = 1;

		while (rs.next()) {
			if (curRow > rowOffset) {
				Object[] rowData = new Object[numOfColumns - colOffset];

				for (int i = 0, j = colOffset; i < rowData.length; i++) {
					rowData[i] = rs.getString(i + colOffset + 1);
				}

				myArrayData.add(rowData);

			}
			curRow++;

		}

		myData = new Object[myArrayData.size()][];

		for (int i = 0; i < myData.length; i++) {
			myData[i] = (Object[]) myArrayData.get(i);
		}

		// Step 5
		rs.close();
		stmt.close();
		dbconn.close();

		return myData;
	}

	@SuppressWarnings("javadoc")
	public static Object[][] getExcelFileData(String fileLocation, String fileName, Boolean hasLabels) {

		ArrayList<Object> results = new ArrayList<Object>();
		Object[][] resultsObject;
		// TODO Implement Helper Method
		try {

			// Get the file using basic File and relative path to directory
			String fullFilePath = fileLocation + fileName;
			/* Old Fileformat */
			// Get the file using basic File and relative path to directory
			// InputStream oldExcelFormatFile = new FileInputStream(new
			// File(fullFilePath));
			// // Get the workbook instance for XLS file or XML (Must use the
			// // HSSFWorkbook for old format
			// @SuppressWarnings("resource")
			// HSSFWorkbook workbook = new HSSFWorkbook(oldExcelFormatFile);
			// // Get first sheet from the workbook, HSSF for old format
			// HSSFSheet sheet = workbook.getSheetAt(0);

			// * New File format Get the file using basic File and relative path
			// * to directory
			InputStream newExcelFormatFile = new FileInputStream(new File(fullFilePath));
			// *Get the workbook* instance for XLS file or XML (Must use the
			XSSFWorkbook workbook = new XSSFWorkbook(newExcelFormatFile);
			// Get first sheet from the workbook,
			// HSSF for old format
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
						System.out.print(cell.getNumericCellValue() + "\t\t\t");
						rowData.add((int) cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t\t\t");
						rowData.add(cell.getStringCellValue());
						break;
					// case CELLTYPEDATE:
					}
				}
				Object[] rowDataObject = new Object[rowData.size()];
				rowData.toArray(rowDataObject);
				results.add(rowDataObject);
				System.out.println("");
			}
			// Close File Read Stream
			// oldExcelFormatFile.close();

			// // Close File Read Stream
			newExcelFormatFile.close();

			// Create an OutputStream to write
			// FileOutputStream out = new FileOutputStream(new
			// File("src/main/resources/excel-output.xls"));
			// // Write the workbook
			// workbook.write(out);
			// // Close output Stream
			// out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		resultsObject = new Object[results.size()][];
		results.toArray(resultsObject);
		return resultsObject;
	}

	public static Object[][] getTextFileData(String fileLocation, String fileName, TextFormat textFormat) {
		return getTextFileData(fileLocation, fileName, textFormat, false);
	}

	// public static Object[][] getTextFileData(String fileLocation, String
	// fileName, TextFormat textFormat,
	// Boolean hasLabels) {
	// if (hasLabels == true)
	// return getTextFileData(fileLocation, fileName, textFormat, true);
	// else
	// return getTextFileData(fileLocation, fileName, textFormat, false);
	// }

	public static Object[][] getTextFileData(String fileLocation, String fileName, TextFormat textFormat,
			Boolean hasLabels, Object... dataTypes) {
		Object[][] data;
		ArrayList<String> lines = openFileAndCollectData(fileLocation, fileName);

		// TODO Implement CSV format
		switch (textFormat) {
		case CSV:
			data = parseCSVData(lines, hasLabels, dataTypes);
			break;
		case XML:
			data = parseXMLData(lines, hasLabels);
			break;
		case TAB:
			data = parseTabData(lines, hasLabels);
			break;
		case JSON:
			data = parseJSONData(lines, hasLabels);
			break;
		default:
			data = null;
			break;
		}
		return data;
	}

	public static Object[][] getTextFileData(String fileLocation, String fileName, TextFormat textFormat,
			Object... dataTypes) {
		return getTextFileData(fileLocation, fileName, textFormat, false, dataTypes);
	}

	/**
	 * @param fileLocation
	 * @param fileName
	 * @return
	 */
	private static Object convertDataType(String parameter, Object dataType) {
		if (dataType.equals(Integer.TYPE)) {
			return Integer.parseInt(parameter);
		} else if (dataType.equals(Boolean.TYPE)) {
			return Boolean.parseBoolean(parameter);
		} else {
			System.out.println("Data type is a String or not recognized, returning a String for (" + parameter + ")");
			return parameter;
		}
	}

	private static ArrayList<String> openFileAndCollectData(String fileLocation, String fileName) {
		// Create a full relative file path
		String filePath = fileLocation + fileName;
		// Array to hold lines from file
		ArrayList<String> dataLines = new ArrayList<String>();
		try {

			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(filePath);
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			// Create String to hold current line
			String line = bufferedReader.readLine();
			// While there is a line to read or not null line
			// (also setting line to current line)
			while (line != null) {
				// Pass current line to the gatherDataString Method
				dataLines.add(line);
				line = bufferedReader.readLine();
			}
			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filePath + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + filePath + "'");
			// Alternative we could just do this:
			// ex.printStackTrace();
		}
		return dataLines;
	}

	private static Object[][] parseCSVData(ArrayList<String> lines, boolean hasLabels, Object[] dataTypes) {

		ArrayList<Object> results = new ArrayList<Object>();
		// Check for labels on first line
		if (hasLabels) {
			// Remove any labels present
			lines.remove(0);
		}

		// String to be scanned to find the pattern.
		String pattern = "(,*)([a-zA-Z0-9\\s]+)(,*)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		for (int i = 0; i < lines.size(); i++) {
			int curDataType = 0;
			ArrayList<Object> curMatches = new ArrayList<Object>();
			Matcher m = r.matcher(lines.get(i));
			while (m.find()) {
				if (dataTypes.length > 0) {
					try {
						curMatches.add(convertDataType(m.group(2), dataTypes[curDataType]));
					} catch (Exception e) {
						System.out.println("DataTypes provided do not match parsed data results.");
					}
				} else {
					curMatches.add(m.group(2));
				}
				curDataType++;
			}
			Object[] resultsObj = new Object[curMatches.size()];
			curMatches.toArray(resultsObj);
			results.add(resultsObj);
		}
		System.out.println("Results:" + results);
		Object[][] resultsObj = new Object[results.size()][];
		results.toArray(resultsObj);
		return resultsObj;
	}

	private static Object[][] parseJSONData(ArrayList<String> lines, Boolean hasLabels) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param lines
	 * @param hasLabels
	 * @return
	 */
	private static Object[][] parseTabData(ArrayList<String> lines, Boolean hasLabels) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param lines
	 * @param hasLabels
	 * @return
	 */
	private static Object[][] parseXMLData(ArrayList<String> lines, Boolean hasLabels) {
		// TODO Auto-generated method stub
		return null;
	}
}