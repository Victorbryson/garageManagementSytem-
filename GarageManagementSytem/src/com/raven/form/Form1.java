package com.raven.form;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Form1 extends javax.swing.JPanel {

    public Form1() {
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
            String sql = "SELECT customer_id, CONCAT(first_name, ' ', last_name) as names, " +
                        "email, car_plate, phone_number FROM customers ORDER BY customer_id DESC";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            // Get table model
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            
            // Clear existing table data
            model.setRowCount(0);
            
            // Add data to table
            while (rs.next()) {
                Object[] row = {
                    rs.getString("customer_id"),
                    rs.getString("names"),
                    rs.getString("email"),
                    rs.getString("car_plate"),
                    rs.getString("phone_number")
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
        
        String sql = "SELECT * FROM customers WHERE customer_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, customerId);
        rs = stmt.executeQuery();
        
        if(rs.next()) {
            // Populate the form fields with the data
            firstNameTxt.setText(rs.getString("first_name"));
            lastNameTxt.setText(rs.getString("last_name"));
            identificationNumberTxt.setText(rs.getString("identification_number"));
            phoneNumberTxt.setText(rs.getString("phone_number"));
            emailTxt.setText(rs.getString("email"));
            addressTxt.setText(rs.getString("address"));
            carBrandTxt.setText(rs.getString("car_brand"));
            carModelTxt.setText(rs.getString("car_model"));
            carPlateTxt.setText(rs.getString("car_plate"));
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

        jLabel1 = new javax.swing.JLabel();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        firstNameTxt = new javax.swing.JTextField();
        lastNameTxt = new javax.swing.JTextField();
        identificationNumberTxt = new javax.swing.JTextField();
        phoneNumberTxt = new javax.swing.JTextField();
        emailTxt = new javax.swing.JTextField();
        addressTxt = new javax.swing.JTextField();
        carBrandTxt = new javax.swing.JTextField();
        submitButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        carPlateTxt = new javax.swing.JTextField();
        carModelTxt = new javax.swing.JTextField();
        resetButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(242, 246, 253));
        setForeground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Customers");

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

        jPanel2.setBackground(new java.awt.Color(242, 246, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("First Name");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Last Name");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("ID");

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
        jLabel9.setText("Car Brand");

        emailTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTxtActionPerformed(evt);
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

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Car Plate");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Car Model");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(carModelTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(submitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addGap(6, 6, 6)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(firstNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                                            .addComponent(lastNameTxt)
                                            .addComponent(identificationNumberTxt)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(emailTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(carBrandTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(phoneNumberTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(addressTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(carPlateTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(firstNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lastNameTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(identificationNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carBrandTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(carModelTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(carPlateTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(submitButton)
                .addContainerGap())
        );

        resetButton.setBackground(new java.awt.Color(204, 255, 255));
        resetButton.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        resetButton.setForeground(new java.awt.Color(255, 0, 51));
        resetButton.setText("Reset");
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Names", "Email", "Car Plate", "Phone No"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(762, 762, 762))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(resetButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addGap(8, 8, 8)))
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(79, 79, 79))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(deleteButton)
                    .addComponent(searchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resetButton)
                .addGap(32, 32, 32))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void emailTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTxtActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        // TODO add your handling code here:
        Connection record;
       PreparedStatement insert;
        
        String firstName = firstNameTxt.getText();
        String lastName = lastNameTxt.getText();
        String identificationNumber = identificationNumberTxt.getText();
        String phoneNumber = phoneNumberTxt.getText();
        String carBrand = carBrandTxt.getText();
        String carModel = carModelTxt.getText();
        String carPlate = carPlateTxt.getText();
        String address = addressTxt.getText();
         String email = emailTxt.getText();
        
         try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            record = DriverManager.getConnection("jdbc:mysql://localhost/garage","root","Matoto@victoire2003");
            insert = record.prepareStatement("INSERT INTO customers (first_name, last_name, identification_number, " +
                    "phone_number, email, car_brand, car_model, car_plate, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            insert.setString(1, firstName);
            insert.setString(2, lastName);
            insert.setString(3, identificationNumber);
            insert.setString(4, phoneNumber);
            insert.setString(5, email);
            insert.setString(6, carBrand);
            insert.setString(7, carModel);
            insert.setString(8, carPlate);
            insert.setString(9, address);
            
            
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

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        // TODO add your handling code here:
           firstNameTxt.setText("");
         lastNameTxt.setText("");
                 identificationNumberTxt.setText("");
                 phoneNumberTxt.setText("");
                 carBrandTxt.setText("");
                 carModelTxt.setText("");
                 carPlateTxt.setText("");
                 addressTxt.setText("");
                 emailTxt.setText("");
               
    }//GEN-LAST:event_resetButtonActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
           // TODO add your handling code here:
        String studentId = JOptionPane.showInputDialog(null, "Enter the student Id:", "Retrieve Student", JOptionPane.QUESTION_MESSAGE);
        
         if (studentId == null || studentId.trim().isEmpty()) {
        JOptionPane.showMessageDialog(null, "No Student Id entered. Search canceled.", "Input Error", JOptionPane.ERROR_MESSAGE);
        return;
         }
         
         studentId = studentId.trim();
         
         try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             Connection con = DriverManager.getConnection("jdbc:mysql://localhost/garage","root","Matoto@victoire2003");
             String sql = "SELECT * FROM cuctomers WHERE customer_id=?";
             
             PreparedStatement statement = con.prepareStatement(sql);
             statement.setString(1, studentId);
             ResultSet rs = statement.executeQuery();
             
             if(rs.next()){
                 firstNameTxt.setText(rs.getString("first_name"));
                 lastNameTxt.setText(rs.getString("last_name"));
                 identificationNumberTxt.setText(rs.getString("identification_number"));
                 phoneNumberTxt.setText(rs.getString("phone_number"));
                 carBrandTxt.setText(rs.getString("car_brand"));
                carModelTxt.setText(rs.getString("car_model"));
                 carPlateTxt.setText(rs.getString("car_plate"));
                 addressTxt.setText(rs.getString("address"));
                 
             }else {
                JOptionPane.showMessageDialog(null, "No record found for Customer NID: " + studentId, "No Results", JOptionPane.INFORMATION_MESSAGE); 
             }
             rs.close();
            statement.close();
            con.close();
             
         }catch (Exception e) {
             JOptionPane.showMessageDialog(null, "Error while searching: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);    
         }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        String customerId = JOptionPane.showInputDialog(this, 
        "Enter Customer ID to delete:", 
        "Delete Loan Record", 
        JOptionPane.QUESTION_MESSAGE);

    // Check if user canceled the input or entered an empty string
    if (customerId == null || customerId.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Please enter a valid Customer ID",
            "Invalid Input",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Confirm deletion
    int confirm = JOptionPane.showConfirmDialog(this,
        "Are you sure you want to delete this Fee record for Customer ID: " + customerId + "?",
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
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, customerId);
        
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
                "No record found with the given Student ID.",
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
            "Please select a customer to update",
            "No Selection",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    String customerId = jTable1.getValueAt(selectedRow, 0).toString();
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        // Validate input fields
        if (firstNameTxt.getText().trim().isEmpty() || 
            lastNameTxt.getText().trim().isEmpty() || 
            identificationNumberTxt.getText().trim().isEmpty()) {
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
        String sql = "UPDATE customers SET " +
            "first_name=?, last_name=?, identification_number=?, " +
            "phone_number=?, email=?, address=?, " +
            "car_brand=?, car_model=?, car_plate=? " +
            "WHERE customer_id=?";
            
        stmt = conn.prepareStatement(sql);
        
        // Set parameters
        stmt.setString(1, firstNameTxt.getText().trim());
        stmt.setString(2, lastNameTxt.getText().trim());
        stmt.setString(3, identificationNumberTxt.getText().trim());
        stmt.setString(4, phoneNumberTxt.getText().trim());
        stmt.setString(5, emailTxt.getText().trim());
        stmt.setString(6, addressTxt.getText().trim());
        stmt.setString(7, carBrandTxt.getText().trim());
        stmt.setString(8, carModelTxt.getText().trim());
        stmt.setString(9, carPlateTxt.getText().trim());
        stmt.setString(10, customerId);
        
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTxt;
    private javax.swing.JTextField carBrandTxt;
    private javax.swing.JTextField carModelTxt;
    private javax.swing.JTextField carPlateTxt;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextField emailTxt;
    private javax.swing.JTextField firstNameTxt;
    private javax.swing.JTextField identificationNumberTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField lastNameTxt;
    private javax.swing.JTextField phoneNumberTxt;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JButton submitButton;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    private void displayDataActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    

     // Helper method to clear fields
    private void clearFields() {
        firstNameTxt.setText("");
        lastNameTxt.setText("");
        identificationNumberTxt.setText("");
        phoneNumberTxt.setText("");
        carBrandTxt.setText("");
        carModelTxt.setText("");
        carPlateTxt.setText("");
        addressTxt.setText("");
        emailTxt.setText("");
    }

 
//    private void updateTableWithSearchResults(String jsonResponse) {
//        // Parse JSON response and update the JTable
//    }
//    private void dispose() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}
