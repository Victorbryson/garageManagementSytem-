/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author matot
 */
public class Employee extends javax.swing.JPanel {

    /**
     * Creates new form Employee
     */
    public Employee() {
        initComponents();
           loadTableData(); 
            loadServices();
        
           jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        // Get the selected row index
        int selectedRow = jTable1.getSelectedRow();
        if(selectedRow != -1) {  // Check if a row is selected
            // Get the customer ID from the first column
            String empId = jTable1.getValueAt(selectedRow, 0).toString();
            fetchCustomerData(empId);
        }
    }
});
    }
    
    private String getSelectedServiceId() {
    String selected = serviceTxt.getSelectedItem().toString();
    // Skip if it's the default "Select a service" item
    if (selected.equals("Select a service")) {
        return null;
    }
    // Extract the ID from the "ID - Service Name" format
    return selected.split(" - ")[0];
}
    
    private void loadServices() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
        
        String sql = "SELECT service_id, service_name FROM services";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        
        // Clear existing items
        serviceTxt.removeAllItems();
        
        // Add services to combobox
        while (rs.next()) {
            String serviceItem = rs.getString("service_id") + " - " + rs.getString("service_name");
            serviceTxt.addItem(serviceItem);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading services: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }
    }
    
      private void loadTableData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Create database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
            
            // Prepare SQL query
            String sql = "SELECT emp_id,emp_name,designation,gender,phone_number, email, address, username, password, service_id FROM employee ORDER BY emp_id DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            // Get table model
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            
            // Clear existing table data
            model.setRowCount(0);
            
            // Add data to table
            while (rs.next()) {
                Object[] row = {
                    rs.getString("emp_id"),
                    rs.getString("emp_name"),
                    rs.getString("designation"),
                    rs.getString("gender"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getString("address"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("service_id")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading table data: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            // Close all connections
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connections: " + e.getMessage());
            }
        }
    }
       

       
// Method to fetch customer data when a row is selected
private void fetchCustomerData(String empId) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(
            "jdbc:mysql://localhost/garage",
            "root",
            "Matoto@victoire2003"
        );
        
String sql = "SELECT e.*, s.service_name " +
             "FROM employee e " +
             "LEFT JOIN services s ON e.service_id = s.service_id " +
             "WHERE e.emp_id = ? " +
             "ORDER BY e.emp_id DESC";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, empId);
        rs = stmt.executeQuery();
        
        if(rs.next()) {
            empNameTxt.setText(rs.getString("emp_name"));
            designationTxt.setText(rs.getString("designation"));
            genderTxt.setSelectedItem(rs.getString("gender"));
            phoneNumberTxt.setText(rs.getString("phone_number"));
            emailTxt.setText(rs.getString("email"));
            addressTxt.setText(rs.getString("address"));
            usernameTxt.setText(rs.getString("username"));
            passwordTxt.setText(rs.getString("password"));
            
            // Set the service in combobox
            String serviceId = rs.getString("service_id");
            String serviceName = rs.getString("service_name");
            serviceTxt.setSelectedItem(serviceId + " - " + serviceName);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error fetching employee data: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new com.raven.swing.table.Table();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        empNameTxt = new javax.swing.JTextField();
        designationTxt = new javax.swing.JTextField();
        phoneNumberTxt = new javax.swing.JTextField();
        emailTxt = new javax.swing.JTextField();
        addressTxt = new javax.swing.JTextField();
        usernameTxt = new javax.swing.JTextField();
        SubmitButton = new javax.swing.JButton();
        genderTxt = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        passwordTxt = new javax.swing.JPasswordField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        serviceTxt = new javax.swing.JComboBox<>();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        SubmitButton2 = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(242, 246, 253));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Employee");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Emp-Code", "Employee Name", "Designation", "Phone No", "Email"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBackground(new java.awt.Color(242, 246, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Employee Name");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Designation");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Phone Number");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Email");

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Address");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Username");

        emailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTxtActionPerformed(evt);
            }
        });

        SubmitButton.setBackground(new java.awt.Color(204, 255, 255));
        SubmitButton.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(0, 0, 0));
        SubmitButton.setText("Submit");
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
            }
        });

        genderTxt.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Assign Service");

        passwordTxt.setText("jPasswordField1");

        jLabel20.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 0));
        jLabel20.setText("Gender");

        jLabel21.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 0));
        jLabel21.setText("Password");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(58, 58, 58))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(designationTxt)
                                    .addComponent(phoneNumberTxt)
                                    .addComponent(emailTxt)
                                    .addComponent(addressTxt)
                                    .addComponent(usernameTxt)
                                    .addComponent(genderTxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(passwordTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                    .addComponent(serviceTxt, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(empNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(empNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(designationTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(genderTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serviceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubmitButton)
                .addContainerGap())
        );

        updateButton.setBackground(new java.awt.Color(204, 255, 255));
        updateButton.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        updateButton.setForeground(new java.awt.Color(0, 0, 0));
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(204, 255, 255));
        deleteButton.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(0, 0, 0));
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        SubmitButton2.setBackground(new java.awt.Color(204, 255, 255));
        SubmitButton2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        SubmitButton2.setForeground(new java.awt.Color(0, 0, 0));
        SubmitButton2.setText("Search");

        resetButton.setBackground(new java.awt.Color(204, 255, 255));
        resetButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        resetButton.setForeground(new java.awt.Color(255, 0, 51));
        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(SubmitButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(resetButton)))
                                .addGap(10, 10, 10)))
                        .addGap(14, 14, 14))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(deleteButton)
                    .addComponent(SubmitButton2))
                .addGap(18, 18, 18)
                .addComponent(resetButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void emailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTxtActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
          String empId = JOptionPane.showInputDialog(this,
            "Enter Employee ID to delete:",
            "Delete Loan Record",
            JOptionPane.QUESTION_MESSAGE);

        // Check if user canceled the input or entered an empty string
        if (empId  == null || empId .trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid Service ID",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this Fee record for Employee ID: " + empId + "?",
            "Confirm Deletion",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            // Load MySQL driver and connect to the database
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/garage",
                "root",
                "Matoto@victoire2003"
            );

            // Prepare the delete statement
            String sql = "DELETE FROM employee WHERE emp_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, empId );

            // Execute the delete statement
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this,
                    "Record deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

                // Refresh the table to show updated data
                displayDataActionPerformed(evt);
            } else {
                JOptionPane.showMessageDialog(this,
                    "No record found with the given Employee ID.",
                    "Record Not Found",
                    JOptionPane.ERROR_MESSAGE);
            }

            // Close resources
            pst.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JTableData.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                "Database driver not found: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            Logger.getLogger(JTableData.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                "Database error: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
          int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this,
            "Please select an employee to update",
            "No Selection",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    String empId = jTable1.getValueAt(selectedRow, 0).toString();
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        // Validate input fields
        if (empNameTxt.getText().trim().isEmpty() ||
            designationTxt.getText().trim().isEmpty() ||
            phoneNumberTxt.getText().trim().isEmpty() ||
            emailTxt.getText().trim().isEmpty() ||
            addressTxt.getText().trim().isEmpty() ||
            usernameTxt.getText().trim().isEmpty() ||
            new String(passwordTxt.getPassword()).trim().isEmpty() ||
            serviceTxt.getSelectedIndex() == -1) {
            
            JOptionPane.showMessageDialog(this,
                "All fields are required",
                "Validation Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get selected service ID
        String selectedService = serviceTxt.getSelectedItem().toString();
        String serviceId = selectedService.split(" - ")[0]; // Extract service_id

        // Establish database connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(
            "jdbc:mysql://localhost/garage",
            "root",
            "Matoto@victoire2003"
        );

        // Prepare update statement
        String sql = "UPDATE employee SET " +
                    "emp_name = ?, " +
                    "designation = ?, " +
                    "gender = ?, " +
                    "phone_number = ?, " +
                    "email = ?, " +
                    "address = ?, " +
                    "username = ?, " +
                    "password = ?, " +
                    "service_id = ? " +
                    "WHERE emp_id = ?";

        stmt = conn.prepareStatement(sql);

        // Set parameters
        stmt.setString(1, empNameTxt.getText().trim());
        stmt.setString(2, designationTxt.getText().trim());
        stmt.setString(3, genderTxt.getSelectedItem().toString());
        stmt.setString(4, phoneNumberTxt.getText().trim());
        stmt.setString(5, emailTxt.getText().trim());
        stmt.setString(6, addressTxt.getText().trim());
        stmt.setString(7, usernameTxt.getText().trim());
        stmt.setString(8, new String(passwordTxt.getPassword()).trim());
        stmt.setString(9, serviceId);
        stmt.setString(10, empId);

        // Execute update
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this,
                "Employee updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);

            // Refresh the table and clear the form
            loadTableData();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to update employee",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this,
            "Database error: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } catch (ClassNotFoundException ex) {
        JOptionPane.showMessageDialog(this,
            "Database driver not found: " + ex.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    } finally {
        try {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }
    }                                            

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
//        Connection record;
//        PreparedStatement insert;
//
//        String employeeName = empNameTxt.getText();
//        String designation = designationTxt.getText();
//        String gender = genderTxt.getSelectedItem();
//        String phoneNumber = phoneNumber.getText();
//        String email = emailTxt.getText();
//        String address = addressTxt.getText();
//        String username = usernameTxt.getText();
//        String password = passwordTxt.getText();
//        
//   
//        String service = serviceTxt.getSelectedItem();
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            record = DriverManager.getConnection("jdbc:mysql://localhost/garage","root","Matoto@victoire2003");
//            insert = record.prepareStatement("INSERT INTO employee (emp_name, designation, gender, " +
//                "phone_number, email, address, username, password, service_id) " +
//                "VALUES (?, ?, ?, ?, ?)");
//            insert.setString(1, employeeName);
//            insert.setString(2, designation);
//            insert.setString(3, gender);
//            insert.setString(4, phoneNumber);
//            insert.setString(5, email);
//            insert.setString(6, address);
//            insert.setString(7, username);
//            insert.setString(8, password);
//            insert.setString(9, service);
//            
//         
//            insert.executeUpdate();
//            JOptionPane.showMessageDialog(this, "Record recodered successfull!!");
//
//            clearFields();
//
//            // Refresh the table data
//            loadTableData();
//
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(management.class.getName()).log(Level.SEVERE,null,ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(management.class.getName()).log(Level.SEVERE,null, ex);
//        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        empNameTxt.setText("");
       designationTxt.setText("");
       genderTxt.setSelectedItem("");
        phoneNumberTxt.setText("");
        emailTxt.setText("");
         addressTxt.setText("");
          usernameTxt.setText(""); 
          passwordTxt.setText("");
          serviceTxt.setSelectedItem("");
    }//GEN-LAST:event_resetButtonActionPerformed

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed
        // TODO add your handling code here:
          Connection record = null;
    PreparedStatement insert = null;

    try {
        // Validate input fields
        if (empNameTxt.getText().trim().isEmpty() || 
            designationTxt.getText().trim().isEmpty() || 
            phoneNumberTxt.getText().trim().isEmpty() || 
            emailTxt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
            return;
        }
String serviceId = getSelectedServiceId();
if (serviceId == null) {
    JOptionPane.showMessageDialog(this, "Please select a service");
    return;
}
        Class.forName("com.mysql.cj.jdbc.Driver");
        record = DriverManager.getConnection("jdbc:mysql://localhost/garage","root","Matoto@victoire2003");
        
        String sql = "INSERT INTO employee (emp_name, designation, gender, phone_number, email, " +
                    "address, username, password, service_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        insert = record.prepareStatement(sql);
        insert.setString(1, empNameTxt.getText().trim());
        insert.setString(2, designationTxt.getText().trim());
        insert.setString(3, genderTxt.getSelectedItem().toString());
        insert.setString(4, phoneNumberTxt.getText().trim());
        insert.setString(5, emailTxt.getText().trim());
        insert.setString(6, addressTxt.getText().trim());
        insert.setString(7, usernameTxt.getText().trim());
        insert.setString(8, passwordTxt.getText().trim());
        insert.setString(9, serviceId);

        insert.executeUpdate();
        JOptionPane.showMessageDialog(this, "Employee record added successfully!");

        clearFields();
        loadTableData();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (insert != null) insert.close();
            if (record != null) record.close();
        } catch (SQLException ex) {
            System.err.println("Error closing connections: " + ex.getMessage());
        }
    }
    }//GEN-LAST:event_SubmitButtonActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SubmitButton;
    private javax.swing.JButton SubmitButton2;
    private javax.swing.JButton SubmitButton5;
    private javax.swing.JTextField addressTxt;
    private javax.swing.JTextField addressTxt1;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField designationTxt;
    private javax.swing.JTextField designationTxt1;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JTextField emailTxt1;
    private javax.swing.JTextField empIdTxt1;
    private javax.swing.JTextField empNameTxt;
    private javax.swing.JTextField empNameTxt1;
    private javax.swing.JComboBox<String> genderTxt;
    private javax.swing.JComboBox<String> genderTxt1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table jTable1;
    private javax.swing.JPasswordField passwordTxt;
    private javax.swing.JPasswordField passwordTxt1;
    private javax.swing.JTextField phoneNumberTxt;
    private javax.swing.JTextField phoneNumberTxt1;
    private javax.swing.JButton resetButton;
    private javax.swing.JComboBox<String> serviceTxt;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField userName1;
    private javax.swing.JTextField usernameTxt;
    // End of variables declaration//GEN-END:variables

    private void clearFields() {
        empNameTxt.setText("");
       designationTxt.setText("");
       genderTxt.setSelectedItem("");
        phoneNumberTxt.setText("");
        emailTxt.setText("");
         addressTxt.setText("");
          usernameTxt.setText(""); 
          passwordTxt.setText("");
          serviceTxt.setSelectedItem("");
          
        
    }
    private void displayDataActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
