package com.raven.form;



import com.raven.form.management;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class Services extends javax.swing.JPanel {

    /**
     * Creates new form Services
     */
    public Services() {
        initComponents();
           loadTableData(); 
        
           jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        // Get the selected row index
        int selectedRow = jTable1.getSelectedRow();
        if(selectedRow != -1) {  // Check if a row is selected
            // Get the customer ID from the first column
            String customerId = jTable1.getValueAt(selectedRow, 0).toString();
            fetchCustomerData(customerId);
        }
    }
});
        
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
            String sql = "SELECT service_id, service_name, cost, estimated_time, warranty FROM services ORDER BY service_id DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            // Get table model
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            
            // Clear existing table data
            model.setRowCount(0);
            
            // Add data to table
            while (rs.next()) {
                Object[] row = {
                    rs.getString("service_id"),
                    rs.getString("service_name"),
                    rs.getString("cost"),
                    rs.getString("estimated_time"),
                    rs.getString("warranty")
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
private void fetchCustomerData(String customerId) {
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
        
        String sql = "SELECT * FROM services WHERE service_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, customerId);
        rs = stmt.executeQuery();
        
        if(rs.next()) {
            // Populate the form fields with the data
            serviceNameTxt.setText(rs.getString("service_name"));
            serviceDescTxt.setText(rs.getString("service_description"));
            costTxt.setText(rs.getString("cost"));
            estimatedTimeTxt.setText(rs.getString("estimated_time"));
            warrantyTxt.setText(rs.getString("warranty"));
            
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error fetching customer data: " + e.getMessage(),
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
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        serviceNameTxt = new javax.swing.JTextField();
        costTxt = new javax.swing.JTextField();
        estimatedTimeTxt = new javax.swing.JTextField();
        warrantyTxt = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        serviceDescTxt = new javax.swing.JTextArea();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(242, 246, 253));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Services");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Service Name", "Cost", "Estimated T", "Warranty"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBackground(new java.awt.Color(242, 246, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Service Name");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Service description");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Cost ");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Estimated Time");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Warranty");

        warrantyTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                warrantyTxtActionPerformed(evt);
            }
        });

        submitButton.setBackground(new java.awt.Color(204, 255, 255));
        submitButton.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        submitButton.setForeground(new java.awt.Color(0, 0, 0));
        submitButton.setText("Submit");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        serviceDescTxt.setColumns(20);
        serviceDescTxt.setRows(5);
        jScrollPane2.setViewportView(serviceDescTxt);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                            .addComponent(serviceNameTxt))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(58, 58, 58)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(estimatedTimeTxt)
                            .addComponent(costTxt)
                            .addComponent(warrantyTxt))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(serviceNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(costTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(estimatedTimeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(warrantyTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(submitButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        searchButton.setBackground(new java.awt.Color(204, 255, 255));
        searchButton.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        searchButton.setForeground(new java.awt.Color(0, 0, 0));
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(resetButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(deleteButton)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resetButton))
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

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
        serviceNameTxt.setText("");
        
        costTxt.setText("");
        estimatedTimeTxt.setText("");
        warrantyTxt.setText("");
    }//GEN-LAST:event_resetButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
//        String studentId = JOptionPane.showInputDialog(null, "Enter the student Id:", "Retrieve Student", JOptionPane.QUESTION_MESSAGE);
//
//        if (studentId == null || studentId.trim().isEmpty()) {
//            JOptionPane.showMessageDialog(null, "No Student Id entered. Search canceled.", "Input Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        studentId = studentId.trim();
//
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/garage","root","Matoto@victoire2003");
//            String sql = "SELECT * FROM cuctomers WHERE customer_id=?";
//
//            PreparedStatement statement = con.prepareStatement(sql);
//            statement.setString(1, studentId);
//            ResultSet rs = statement.executeQuery();
//
//            if(rs.next()){
//                serviceNameTxt.setText(rs.getString("first_name"));
//                lastNameTxt.setText(rs.getString("last_name"));
//                costTxt.setText(rs.getString("identification_number"));
//                estimatedTimeTxt.setText(rs.getString("phone_number"));
//                carBrandTxt.setText(rs.getString("car_brand"));
//                carModelTxt.setText(rs.getString("car_model"));
//                carPlateTxt.setText(rs.getString("car_plate"));
//                addressTxt.setText(rs.getString("address"));
//
//            }else {
//                JOptionPane.showMessageDialog(null, "No record found for Customer NID: " + studentId, "No Results", JOptionPane.INFORMATION_MESSAGE);
//            }
//            rs.close();
//            statement.close();
//            con.close();
//
//        }catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Error while searching: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
//        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        String serviceId = JOptionPane.showInputDialog(this,
            "Enter Service ID to delete:",
            "Delete Loan Record",
            JOptionPane.QUESTION_MESSAGE);

        // Check if user canceled the input or entered an empty string
        if (serviceId  == null || serviceId .trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid Service ID",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this Fee record for Service ID: " + serviceId + "?",
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
            String sql = "DELETE FROM services WHERE service_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, serviceId );

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
                    "No record found with the given Service ID.",
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
                "Please select a service to update",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String serviceId = jTable1.getValueAt(selectedRow, 0).toString();
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Validate input fields
            if (serviceNameTxt.getText().trim().isEmpty() ||
                
                costTxt.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Required fields cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/garage",
                "root",
                "Matoto@victoire2003"
            );

            // Prepare update statement
            String sql = "UPDATE services SET " +
            "service_name=?, service_description=?, cost=?, " +
            "estimated_time=?, warranty=? " +
            "WHERE service_id=?";

            stmt = conn.prepareStatement(sql);

            // Set parameters
            stmt.setString(1, serviceNameTxt.getText().trim());
            stmt.setString(2, serviceDescTxt.getText().trim());
            stmt.setString(3, costTxt.getText().trim());
            stmt.setString(4, estimatedTimeTxt.getText().trim());
            stmt.setString(5, warrantyTxt.getText().trim());
          
            stmt.setString(6, serviceId);

            // Execute update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this,
                    "Customer updated successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

                // Refresh the table and clear the form
                loadTableData();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to update customer",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error updating customer: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connections: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
        Connection record;
        PreparedStatement insert;

        String serviceName = serviceNameTxt.getText();
        String serviceDesc = serviceDescTxt.getText();
        String cost = costTxt.getText();
        String estimatedTime = estimatedTimeTxt.getText();
   
        String warranty = warrantyTxt.getText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            record = DriverManager.getConnection("jdbc:mysql://localhost/garage","root","Matoto@victoire2003");
            insert = record.prepareStatement("INSERT INTO services (service_name, service_description, cost, " +
                "estimated_time, warranty) " +
                "VALUES (?, ?, ?, ?, ?)");
            insert.setString(1, serviceName);
            insert.setString(2, serviceDesc);
            insert.setString(3, cost);
            insert.setString(4, estimatedTime);
            insert.setString(5, warranty);
         
            insert.executeUpdate();
            JOptionPane.showMessageDialog(this, "Record recodered successfull!!");

            clearFields();

            // Refresh the table data
            loadTableData();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(management.class.getName()).log(Level.SEVERE,null,ex);
        } catch (SQLException ex) {
            Logger.getLogger(management.class.getName()).log(Level.SEVERE,null, ex);
        }
    }//GEN-LAST:event_submitButtonActionPerformed

    private void warrantyTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_warrantyTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_warrantyTxtActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField costTxt;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField estimatedTimeTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextArea serviceDescTxt;
    private javax.swing.JTextField serviceNameTxt;
    private javax.swing.JButton submitButton;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField warrantyTxt;
    // End of variables declaration//GEN-END:variables

     private void clearFields() {
        serviceNameTxt.setText("");
        serviceDescTxt.setText("");
        costTxt.setText("");
        estimatedTimeTxt.setText("");
        warrantyTxt.setText("");
        
    }
    private void displayDataActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
