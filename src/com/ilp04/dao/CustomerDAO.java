package com.ilp04.dao;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
import com.ilp04.entity.Customer;
 
public class CustomerDAO {
    // Get connection
    public Connection getConnection() {
        String connectionURL = "jdbc:mysql://localhost:3306/bankdb";
        String userName = "root";
        String password = "experion@123";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    // Close connection
    public Connection closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    // Get all customer details
    public ArrayList<Customer> getAllCustomers(Connection connection) {
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM customer");
            while (resultSet.next()) {
                int customerCode = resultSet.getInt(1);
                String customerFirstName = resultSet.getString(2);
                String customerLastName = resultSet.getString(3);
                String address = resultSet.getString(4);
                long phNumber = resultSet.getLong(5);
                long aadharNo = resultSet.getLong(6);
                Customer customer = new Customer(customerCode, customerFirstName, customerLastName, address, phNumber, aadharNo);
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }
    // Insert new customer
    public int insertCustomer(Connection connection, Customer customer) {
        String insertSQL = "INSERT INTO customer (customerFirstName, customerLastName, address, phNumber, aadharNo) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, customer.getCustomerFirstname());
            preparedStatement.setString(2, customer.getCustomerLastname());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setLong(4, customer.getPhonenumber());
            preparedStatement.setLong(5, customer.getAadharNo());
            int affectedRows = preparedStatement.executeUpdate();
 
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setCustomerCode(generatedKeys.getInt(1));
                    }
                }
            }
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    // Update customer
    public int updateCustomer(Connection connection, Customer customer) {
        String updateSQL = "UPDATE customer SET customerFirstName = ?, customerLastName = ?, address = ?, phNumber = ?, aadharNo = ? WHERE customerCode = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, customer.getCustomerFirstname());
            preparedStatement.setString(2, customer.getCustomerLastname());
            preparedStatement.setString(3, customer.getAddress());
            preparedStatement.setLong(4, customer.getPhonenumber());
            preparedStatement.setLong(5, customer.getAadharNo());
            preparedStatement.setInt(6, customer.getCustomerCode());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}