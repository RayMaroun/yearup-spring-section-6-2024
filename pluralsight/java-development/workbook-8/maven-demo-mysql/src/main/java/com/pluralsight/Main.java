package com.pluralsight;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a country code to search for the City:");
        String countryCode = scanner.nextLine();

        // Step 1 - Load the Driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Step 2 - Create the connection
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/world",
                "root",
                "P@ssw0rd"
        );

        // Step 3 - Create the statement
        //Statement statement = connection.createStatement();

        // Step 4 - Create the query
        //String query = "SELECT Name FROM city WHERE CountryCode = '" + countryCode + "'";
        String query = "SELECT Name FROM city WHERE CountryCode = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, countryCode);

        // Step 5 - Execute the query
        ResultSet results = preparedStatement.executeQuery();

        // Step 6 - Go through the results

        while (results.next()) {
            String city = results.getString("Name");
            System.out.println(city);
        }

        // Step 7 - Close the connection
        results.close();
        preparedStatement.close();
        connection.close();


/*        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/northwind",
                "root",
                "P@ssw0rd"
        );

        Statement statement = connection.createStatement();

        String query = """
                SELECT  EmployeeID, FirstName, LastName, Title
                FROM employees
                WHERE Country = 'USA'
                """;

        ResultSet results = statement.executeQuery(query);

        while (results.next()) {
            String employeeId = results.getString(1);
            String firstName = results.getString(2);
            String lastName = results.getString(3);
            String title = results.getString(4);

            System.out.println("Employee ID: " + employeeId);
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Title: " + title);
            System.out.println("=====================================================");

        }

        connection.close();*/


    }
}
