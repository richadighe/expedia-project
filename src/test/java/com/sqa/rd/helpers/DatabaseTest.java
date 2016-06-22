package com.sqa.rd.helpers;

import java.sql.*;

import org.testng.annotations.*;

public class DatabaseTest {
	@Test
	public void databaseTest() throws ClassNotFoundException, SQLException {
		System.out.println("Database Test:");
		// Get Specific Driver for the database you are using
		Class.forName("com.mysql.jdbc.Driver");
		// Create Connection to database using database string with username and
		// password
		Connection dbconn = DriverManager.getConnection("jdbc:mysql://localhost:8889/sqadb", "root", "root");
		// Create statement object to run SQL commands
		Statement statement = dbconn.createStatement();
		// Capture the resultset of an executed SQL statement
		ResultSet resultSet = statement.executeQuery("select * from person");
		// Iterate through the results to capture or display all items
		while (resultSet.next()) {
			// TODO display or capture elements
			String zip = resultSet.getString("zip");
			String city = resultSet.getString("city");
			int id = resultSet.getInt("id");
			String name = resultSet.getString("name");
			String address = resultSet.getString("address");
			System.out.println(
					"Id:" + id + "\tName:" + name + "\tCity:" + city + "\tAddress:" + address + "\tZip:" + zip);
		}
		// Close connections
		resultSet.close();
		statement.close();
		dbconn.close();
	}
}
