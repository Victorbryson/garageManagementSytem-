/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.dao;

import DatabaseConnection.DatabaseConnection;
import java.sql.*;
import com.raven.model.Customer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author matot
 */
public class CustomerDAO {
    public void insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO customers (first_name, last_name, identification_number, " +
                    "phone_number, email, address, car_brand, car_model, car_plate) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getIdentityNumber());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setString(5, customer.getEmail());
            pstmt.setString(6, customer.getAddress());
            pstmt.setString(7, customer.getCarBrand());
            pstmt.setString(8, customer.getCarModel());
            pstmt.setString(9, customer.getCarPlate());
            
            pstmt.executeUpdate();
        }
    }
    
    public void update(Customer customer) throws SQLException {
        String sql = "UPDATE customers SET first_name=?, last_name=?, identification_number=?, " +
                    "phone_number=?, email=?, address=?, car_brand=?, car_model=?, car_plate=? " +
                    "WHERE id=?";
                    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getIdentityNumber());
            pstmt.setString(4, customer.getPhoneNumber());
            pstmt.setString(5, customer.getEmail());
            pstmt.setString(6, customer.getAddress());
            pstmt.setString(7, customer.getCarBrand());
            pstmt.setString(8, customer.getCarModel());
            pstmt.setString(9, customer.getCarPlate());
            pstmt.setInt(10, customer.getCustomerId());
            
            pstmt.executeUpdate();
        }
    }
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public List<Customer> search(String keyword) throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers WHERE first_name LIKE ? OR last_name LIKE ? " +
                    "OR email LIKE ? OR car_plate LIKE ?";
                    
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchParam = "%" + keyword + "%";
            pstmt.setString(1, searchParam);
            pstmt.setString(2, searchParam);
            pstmt.setString(3, searchParam);
            pstmt.setString(4, searchParam);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
        }
        return customers;
    }
    
    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                customers.add(extractCustomerFromResultSet(rs));
            }
        }
        return customers;
    }
    
    private Customer extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        return new Customer(
            rs.getInt("id"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("identification_number"),
            rs.getString("phone_number"),
            rs.getString("email"),
            rs.getString("address"),
            rs.getString("car_brand"),
            rs.getString("car_model"),
            rs.getString("car_plate")
        );
    }
    
}
