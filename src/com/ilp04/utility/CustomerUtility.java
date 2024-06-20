package com.ilp04.utility;
import java.util.ArrayList;
import java.util.Scanner;
import com.ilp04.entity.Customer;
import com.ilp04.service.CustomerService;
import com.ilp04.service.CustomerServiceImpl;
public class CustomerUtility {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose an option:");
            System.out.println("1. Display all customers");
            System.out.println("2. Insert into customer table");
            System.out.println("3. Update customer table");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    getAllCustomers();
                    break;
                case 2:
                    insertIntoCustomer();
                    break;
                case 3:
                    updateCustomer();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
    private static void getAllCustomers() {
        CustomerService customerService = new CustomerServiceImpl();
        ArrayList<Customer> customerList = customerService.getAllCustomers();
        System.out.printf("%-15s %-15s %-15s %-30s %-15s %-15s%n",
                "Customer Code", "First Name", "Last Name", "Address", "Phone Number", "Aadhar No");
        System.out.println("----------------------------------------------------------------------------------------------");
        // Print each customer's details in a table row
        for (Customer customer : customerList) {
            System.out.printf("%-15s %-15s %-15s %-30s %-15s %-15s%n",
                    customer.getCustomerCode(),
                    customer.getCustomerFirstname(),
                    customer.getCustomerLastname(),
                    customer.getAddress(),
                    customer.getPhonenumber(),
                    customer.getAadharNo());
        }
    }
    private static void insertIntoCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer details:");
        System.out.print("First Name: ");
        String firstName = scanner.next();
        System.out.print("Last Name: ");
        String lastName = scanner.next();
        System.out.print("Address: ");
        scanner.nextLine();  // Consume the newline character left by next()
        String address = scanner.nextLine();
        System.out.print("Phone Number: ");
        long phoneNumber = scanner.nextLong();
        System.out.print("Aadhar Number: ");
        long aadharNo = scanner.nextLong();
        Customer customer = new Customer(0, firstName, lastName, address, phoneNumber, aadharNo);
        CustomerService customerService = new CustomerServiceImpl();
        int result = customerService.insertIntoCustomer(customer);
        if (result > 0) {
            System.out.println("Customer inserted successfully.");
        } else {
            System.out.println("Failed to insert customer.");
        }
    }
    private static void updateCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Customer Code to update: ");
        int customerCode = scanner.nextInt();
        System.out.println("Choose field to update:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Address");
        System.out.println("4. Phone Number");
        System.out.println("5. Aadhar Number");
        int fieldChoice = scanner.nextInt();
        String fieldName = "";
        String newValue = "";
        switch (fieldChoice) {
            case 1:
                fieldName = "customer_firstname";
                System.out.print("Enter new First Name: ");
                newValue = scanner.next();
                break;
            case 2:
                fieldName = "customer_lastname";
                System.out.print("Enter new Last Name: ");
                newValue = scanner.next();
                break;
            case 3:
                fieldName = "address";
                System.out.print("Enter new Address: ");
                scanner.nextLine();  // Consume the newline character
                newValue = scanner.nextLine();
                break;
            case 4:
                fieldName = "phonenumber";
                System.out.print("Enter new Phone Number: ");
                newValue = scanner.next();
                break;
            case 5:
                fieldName = "aadharno";
                System.out.print("Enter new Aadhar Number: ");
                newValue = scanner.next();
                break;
            default:
                System.out.println("Invalid field choice.");
                return;
        }
        CustomerService customerService = new CustomerServiceImpl();
        int result = customerService.updateCustomerField(customerCode, fieldName, newValue);
        if (result > 0) {
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Failed to update customer.");
        }
    }
}