/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;


import jakarta.mail.Authenticator;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author matot
 */
public class MarkAsDone extends javax.swing.JPanel {

    /**
     * Creates new form MarkAsDone
     */
    public MarkAsDone() {
        initComponents();
        loadTableData(); 
        
//         jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
//    @Override
//    public void mouseClicked(java.awt.event.MouseEvent evt) {
//        // Get the selected row index
//        int selectedRow = jTable1.getSelectedRow();
//        if(selectedRow != -1) {  // Check if a row is selected
//            // Get the customer ID from the first column
//            String regNo = jTable1.getValueAt(selectedRow, 0).toString();
//            fetchCustomerData(regNo);
//        }
//    }
//});
    }

 private void loadTableData() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        // Create database connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
        
        // Fixed SQL query with proper syntax
        String sql = "SELECT " +
                    "ce.reg_no, " +
                    "ce.status, " +
                    "CONCAT(c.first_name, ' ', c.last_name) as owner_name, " +
                    "CONCAT(c.car_brand, ' - ', c.car_model) as car_name, " +
                    "c.car_plate, " +
                    "c.phone_number, " +
                    "e.emp_name, " +
                    "s.service_name, " +
                    "s.cost, " +
                    "s.estimated_time " +
                    "FROM car_entry ce " +
                    "JOIN customers c ON ce.customer_id = c.customer_id " +
                    "JOIN services s ON ce.service_id = s.service_id " +
                    "JOIN employee e ON ce.emp_id = e.emp_id " +
                    "WHERE ce.status != 'done' " +  // Only show undone cars
                    "ORDER BY ce.reg_no DESC";
                    
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        while (rs.next()) {
            Object[] row = {
                rs.getString("reg_no"),
                rs.getString("owner_name"),
                 rs.getString("phone_number"),
                rs.getString("car_name"),
                rs.getString("car_plate"),
                rs.getString("service_name"),
                rs.getString("cost"),
                rs.getString("status"),
                rs.getString("emp_name"),
            };
            model.addRow(row);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading table data: " + e.getMessage(), 
            "Database Error", 
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

      


// Update the getCustomerEmail method to fetch more details
private CustomerServiceDetails getCustomerDetails(String regNo) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    CustomerServiceDetails details = new CustomerServiceDetails();
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
        
        String sql = "SELECT " +
                    "c.email, " +
                    "CONCAT(c.first_name, ' ', c.last_name) as customer_name, " +
                    "c.car_brand, " +
                    "c.car_model, " +
                    "c.car_plate, " +
                    "s.service_name, " +
                    "s.cost, " +
                    "e.emp_name " +
                    "FROM car_entry ce " +
                    "JOIN customers c ON ce.customer_id = c.customer_id " +
                    "JOIN services s ON ce.service_id = s.service_id " +
                    "JOIN employee e ON ce.emp_id = e.emp_id " +
                    "WHERE ce.reg_no = ?";
                    
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, regNo);
        rs = stmt.executeQuery();
        
        if(rs.next()) {
            details.setEmail(rs.getString("email"));
            details.setCustomerName(rs.getString("customer_name"));
            details.setCarBrand(rs.getString("car_brand"));
            details.setCarModel(rs.getString("car_model"));
            details.setCarPlate(rs.getString("car_plate"));
            details.setServiceName(rs.getString("service_name"));
            details.setCost(rs.getDouble("cost"));
            details.setEmployeeName(rs.getString("emp_name"));
        }
        
    } catch (Exception e) {
        System.err.println("Error getting customer details: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }
    return details;
}

private class CustomerServiceDetails {
    private String email;
    private String customerName;
    private String carBrand;
    private String carModel;
    private String carPlate;
    private String serviceName;
    private double cost;
    private String employeeName;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}

private void sendNotificationEmail(CustomerServiceDetails details, String regNo) {
    final String username = "matotovictoire@gmail.com";
    final String password = "tqcz fnpe eify iveq";
    
    Properties prop = new Properties();
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true");
    prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
    
    // Create authenticated session
    Session session = Session.getInstance(prop, new Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    });

    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(details.getEmail()));
        message.setSubject("Your Car Service is Complete - " + regNo);
        
        // Create a detailed and professional email content
        String emailContent = "Dear " + details.getCustomerName() + ",\n\n"
                + "We are pleased to inform you that the service for your vehicle has been completed and it is ready for pickup.\n\n"
                + "Service Details:\n"
                + "---------------\n"
                + "Registration Number: " + regNo + "\n"
                + "Vehicle: " + details.getCarBrand() + " " + details.getCarModel() + "\n"
                + "License Plate: " + details.getCarPlate() + "\n"
                + "Service Performed: " + details.getServiceName() + "\n"
                + "Service Cost: $" + String.format("%.2f", details.getCost()) + "\n"
                + "Serviced by: " + details.getEmployeeName() + "\n\n"
                + "Please note:\n"
                + "- Your vehicle has been thoroughly checked and serviced\n"
                + "- All requested maintenance has been completed\n"
                + "- You can collect your vehicle during our business hours\n"
                + "- Please bring your ID and service registration document\n\n"
                + "If you have any questions about the service performed or need to schedule a pickup time, "
                + "please don't hesitate to contact us.\n\n"
                + "Thank you for choosing our services. We appreciate your trust in our team.\n\n"
                + "Best regards,\n"
                + details.getEmployeeName() + "\n"
                + "Garage Management Team\n\n"
                + "This is an automated message. Please do not reply to this email.";

        // Set the email content
        message.setText(emailContent);

        // Send the message
        Transport.send(message);
        
        System.out.println("Detailed notification email sent successfully to " + details.getEmail());
        
    } catch (MessagingException e) {
        System.err.println("Error sending email: " + e.getMessage());
        e.printStackTrace(); // Add this for more detailed error information
    }
}
   private void updateCarStatus(String regNo) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
        
        String sql = "UPDATE car_entry SET status = 'done' WHERE reg_no = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, regNo);
        
        int result = stmt.executeUpdate();
        
        if (result > 0) {
            // Get customer details and send notification
            CustomerServiceDetails details = getCustomerDetails(regNo);
            if (details.getEmail() != null && !details.getEmail().isEmpty()) {
                sendNotificationEmail(details, regNo);
                JOptionPane.showMessageDialog(this,
                    "Car marked as done and detailed notification email sent!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Car marked as done but could not send email (no email address found)",
                    "Partial Success",
                    JOptionPane.WARNING_MESSAGE);
            }
            // Refresh the table
            loadTableData();
        } else {
            JOptionPane.showMessageDialog(this,
                "Failed to update car status",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error updating car status: " + e.getMessage(),
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
}
        
        
        
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new com.raven.swing.table.Table();
        markAsDone = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(242, 246, 253));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Cars Not Done");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reg No", "Owner Name", "Owner Phone No", "Car Name", "Car Plate", "Service Name", "Service Cost", "Status", "Employee Name"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        markAsDone.setBackground(new java.awt.Color(204, 255, 255));
        markAsDone.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        markAsDone.setForeground(new java.awt.Color(0, 0, 0));
        markAsDone.setText("Mark as done");
        markAsDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markAsDoneActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(markAsDone, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(747, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 488, Short.MAX_VALUE)
                .addComponent(markAsDone)
                .addGap(23, 23, 23))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(65, 65, 65)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(69, Short.MAX_VALUE)))
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

    private void markAsDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_markAsDoneActionPerformed
        // TODO add your handling code here:
         int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this,
            "Please select a car to mark as done",
            "Selection Required",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Get registration number from selected row
    String regNo = jTable1.getValueAt(selectedRow, 0).toString();

    // Show confirmation dialog
    String message = "Are you sure you want to mark car with registration " + regNo + " as done?";
    int confirm = JOptionPane.showConfirmDialog(
        this,
        message,
        "Confirm Mark as Done",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        updateCarStatus(regNo);
    }
    
        }

        private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:
           
    }//GEN-LAST:event_markAsDoneActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table jTable1;
    private javax.swing.JButton markAsDone;
    // End of variables declaration//GEN-END:variables
}
