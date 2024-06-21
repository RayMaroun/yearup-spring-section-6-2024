package com.pluralsight.customerAPI.dao.impl;

import com.pluralsight.customerAPI.dao.interfaces.ICustomerDAO;
import com.pluralsight.customerAPI.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCustomerDAO implements ICustomerDAO {

    private DataSource dataSource;

    @Autowired
    public JdbcCustomerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        String selectQuery = "SELECT * FROM Customers";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getString("CustomerID"));
                customer.setName(resultSet.getString("CompanyName"));
                customer.setAddress(resultSet.getString("Address"));

                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Customer findCustomerById(String id) {
        String selectByIdQuery = "SELECT * FROM Customers WHERE CustomerID = ?";
        Customer customer = new Customer();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectByIdQuery)) {
            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    customer.setId(resultSet.getString("CustomerID"));
                    customer.setName(resultSet.getString("CompanyName"));
                    customer.setAddress(resultSet.getString("Address"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    @Override
    public Customer insert(Customer customer) {
        String insertQuery = "INSERT INTO Customers (CustomerID, CompanyName, Address) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, customer.getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getAddress());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    @Override
    public void update(String id, Customer customer) {
        String updateQuery = "UPDATE Customers SET CompanyName = ?, Address = ? WHERE CustomerID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating customer failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String id) {
        String deleteQuery = "DELETE FROM Customers WHERE CustomerID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setString(1, id);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting customer failed, no rows affected");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
