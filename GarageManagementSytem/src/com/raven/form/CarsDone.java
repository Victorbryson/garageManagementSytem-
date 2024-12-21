/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.raven.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 *
 * @author matot
 */
public class CarsDone extends javax.swing.JPanel {

    /**
     * Creates new form CarsDone
     */
    public CarsDone() {
        initComponents();
        
         jTable1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {},
        new String [] {
            "Reg No", "Owner Name", "Owner Phone No", "Car Name", "Car Plate", 
            "Service Name", "Service Cost", "Status", "Employee Name", "Payment"
        }
    ) {
        // Define column classes to ensure proper data handling
        Class[] types = new Class [] {
            String.class, String.class, String.class, String.class, String.class,
            String.class, String.class, String.class, String.class, String.class
        };
        
        @Override
        public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }
        
        // Optionally make cells non-editable
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    });
    
    // Set selection mode to single selection
    jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
         loadTableData(); 
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
                "ce.payment, " +
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
                    "WHERE ce.status = 'done' " +  // Only show undone cars
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
                rs.getString("payment"),
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
  private void saveAsPDF(JPanel receiptPanel, String receiptNumber) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Save Receipt as PDF");
    fileChooser.setSelectedFile(new File("Receipt_" + receiptNumber + ".pdf"));
    
    if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
        try {
            // Create PDF document
            Document document = new Document(new Rectangle(receiptPanel.getWidth(), receiptPanel.getHeight()));
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileChooser.getSelectedFile()));
            document.open();
            
            // Convert panel to image
            BufferedImage image = new BufferedImage(
                receiptPanel.getWidth(), 
                receiptPanel.getHeight(), 
                BufferedImage.TYPE_INT_RGB
            );
            receiptPanel.paint(image.getGraphics());
            
            // Add image to PDF
            Image pdfImage = Image.getInstance(image, null);
            document.add(pdfImage);
            document.close();
            
            JOptionPane.showMessageDialog(null, "Receipt saved successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error saving PDF: " + e.getMessage());
        }
    }
} 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        markAsDone = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new com.raven.swing.table.Table();
        printReceipt = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(242, 246, 253));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Cars Done");

        markAsDone.setBackground(new java.awt.Color(204, 255, 255));
        markAsDone.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        markAsDone.setForeground(new java.awt.Color(0, 0, 0));
        markAsDone.setText("Mark As Paid");
        markAsDone.setActionCommand("");
        markAsDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markAsDoneActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reg No", "Owner Name", "Owner Phone No", "Car Name", "Car Plate", "Service Name", "Service Cost", "Status", "Employee Name", "payment"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        printReceipt.setBackground(new java.awt.Color(204, 255, 255));
        printReceipt.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        printReceipt.setForeground(new java.awt.Color(0, 0, 0));
        printReceipt.setText("Print Receipt");
        printReceipt.setActionCommand("");
        printReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printReceiptActionPerformed(evt);
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
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(markAsDone, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(printReceipt, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(517, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 491, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(markAsDone)
                    .addComponent(printReceipt))
                .addGap(24, 24, 24))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(69, 69, 69)
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
            JOptionPane.showMessageDialog(this, "Please select a car to mark as paid");
            return;
        }

        // Gather data from selected row
        String regNo = jTable1.getValueAt(selectedRow, 0).toString();
        String customerName = jTable1.getValueAt(selectedRow, 1).toString();
        String phoneNumber = jTable1.getValueAt(selectedRow, 2).toString();
        String carName = jTable1.getValueAt(selectedRow, 3).toString();
        String carPlate = jTable1.getValueAt(selectedRow, 4).toString();
        String serviceName = jTable1.getValueAt(selectedRow, 5).toString();
        String serviceCost = jTable1.getValueAt(selectedRow, 6).toString();
        String employeeName = jTable1.getValueAt(selectedRow, 8).toString();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
            conn.setAutoCommit(false);  // Start transaction

            // 1. Update car_entry table
            String updateSql = "UPDATE car_entry SET payment = 'paid' WHERE reg_no = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
            updateStmt.setString(1, regNo);
            updateStmt.executeUpdate();

            // 2. Get customer_id
            String customerIdSql = "SELECT customer_id FROM customers WHERE CONCAT(first_name, ' ', last_name) = ?";
            PreparedStatement custStmt = conn.prepareStatement(customerIdSql);
            custStmt.setString(1, customerName);
            ResultSet rs = custStmt.executeQuery();
            
            int customerId = 0;
            if (rs.next()) {
                customerId = rs.getInt("customer_id");
            }

            // 3. Generate unique receipt number
            String receiptNo = "RCP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            // 4. Insert into payment table
            String insertSql = "INSERT INTO payment (customer_id, car_plate, phone_number, car_name, " +
                             "service_name, service_cost, employee_name, warranty, payment_date, receipt_no) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
            
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, customerId);
            insertStmt.setString(2, carPlate);
            insertStmt.setString(3, phoneNumber);
            insertStmt.setString(4, carName);
            insertStmt.setString(5, serviceName);
            insertStmt.setDouble(6, Double.parseDouble(serviceCost));
            insertStmt.setString(7, employeeName);
            insertStmt.setString(8, "6 months");
            insertStmt.setString(9, receiptNo);
            
            insertStmt.executeUpdate();

            conn.commit();
            JOptionPane.showMessageDialog(this, "Payment recorded successfully!");
            loadTableData();  // Refresh the table

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this, "Error recording payment: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }

        private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {
            // TODO add your handling code here:

    }//GEN-LAST:event_markAsDoneActionPerformed

    private void printReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printReceiptActionPerformed
        // TODO add your handling code here:
      int selectedRow = jTable1.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Please select a record to print receipt");
        return;
    }

    // Create receipt panel with fixed width
    JPanel receiptPanel = new JPanel();
    receiptPanel.setLayout(new BoxLayout(receiptPanel, BoxLayout.Y_AXIS));
    receiptPanel.setBackground(Color.WHITE);
    receiptPanel.setPreferredSize(new Dimension(400, 650));
    receiptPanel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.BLACK, 1),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));

    // Header Panel
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
    headerPanel.setBackground(Color.WHITE);
    headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Company Logo/Name
    JLabel logo = new JLabel("AUTO CARE GARAGE");
    logo.setFont(new Font("Arial", Font.BOLD, 24));
    logo.setForeground(new Color(44, 62, 80));
    logo.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Fancy separator
    JSeparator fancySeparator = createFancySeparator();
    
    // Address and Contact Panel
    JPanel contactPanel = new JPanel();
    contactPanel.setLayout(new BoxLayout(contactPanel, BoxLayout.Y_AXIS));
    contactPanel.setBackground(Color.WHITE);
    contactPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    
    String[] contactInfo = {
        "123 Mechanic Street",
        "City, State 12345",
        "Tel: (123) 456-7890",
        "service@autocare.com"
    };
    
    for (String info : contactInfo) {
        JLabel label = new JLabel(info);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        contactPanel.add(label);
    }

    // Receipt Information Panel
    JPanel receiptInfoPanel = new JPanel(new GridLayout(2, 2, 10, 5));
    receiptInfoPanel.setBackground(Color.WHITE);
    receiptInfoPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
    receiptInfoPanel.setMaximumSize(new Dimension(360, 50));

    String receiptNumber = "RCP" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new java.util.Date());

    addReceiptInfoField(receiptInfoPanel, "Receipt No:", receiptNumber);
    addReceiptInfoField(receiptInfoPanel, "Date:", currentDate);

    // Customer Details Panel
    JPanel customerPanel = createSectionPanel("CUSTOMER DETAILS", new String[][]{
        {"Customer:", jTable1.getValueAt(selectedRow, 1).toString()},
        {"Phone:", jTable1.getValueAt(selectedRow, 2).toString()},
        {"Vehicle:", jTable1.getValueAt(selectedRow, 3).toString()},
        {"Plate No:", jTable1.getValueAt(selectedRow, 4).toString()}
    });

    // Service Details Panel
    JPanel servicePanel = createSectionPanel("SERVICE DETAILS", new String[][]{
        {"Service:", jTable1.getValueAt(selectedRow, 5).toString()},
        {"Technician:", jTable1.getValueAt(selectedRow, 8).toString()},
        {"Warranty:", "6 months"},
        {"Cost:", "$" + jTable1.getValueAt(selectedRow, 6).toString()}
    });

    // Total Amount Panel
    JPanel totalPanel = new JPanel();
    totalPanel.setBackground(Color.WHITE);
    totalPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
    totalPanel.setMaximumSize(new Dimension(360, 50));
    totalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

    JLabel totalLabel = new JLabel("Total Amount: $" + jTable1.getValueAt(selectedRow, 6).toString());
    totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
    totalPanel.add(totalLabel);

    // Footer Panel
    JPanel footerPanel = new JPanel();
    footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
    footerPanel.setBackground(Color.WHITE);
    footerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

    String[] footerText = {
        "Thank you for your business!",
        "Warranty valid for 6 months",
        "Terms & Conditions Apply"
    };

    for (String text : footerText) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", text.equals("Thank you for your business!") ? Font.ITALIC : Font.PLAIN, 
                              text.equals("Thank you for your business!") ? 14 : 10));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        footerPanel.add(label);
        footerPanel.add(Box.createRigidArea(new Dimension(0, 3)));
    }

    // Buttons Panel
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
    buttonsPanel.setBackground(Color.WHITE);

    // Print Button
    JButton printButton = createStyledButton("Print Receipt", new Color(52, 152, 219));
    printButton.addActionListener(e -> printReceipt(receiptPanel));

    // Save Button
    JButton saveButton = createStyledButton("Save as PDF", new Color(46, 204, 113));
    saveButton.addActionListener(e -> saveAsPDF(receiptPanel, receiptNumber));

    buttonsPanel.add(printButton);
    buttonsPanel.add(saveButton);

    // Add all components to main panel
    receiptPanel.add(headerPanel);
    headerPanel.add(logo);
    headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    headerPanel.add(contactPanel);
    receiptPanel.add(fancySeparator);
    receiptPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    receiptPanel.add(receiptInfoPanel);
    receiptPanel.add(customerPanel);
    receiptPanel.add(servicePanel);
    receiptPanel.add(totalPanel);
    receiptPanel.add(footerPanel);
    receiptPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    receiptPanel.add(buttonsPanel);

    // Show in dialog
    JDialog receiptDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Receipt", true);
    receiptDialog.add(new JScrollPane(receiptPanel));
    receiptDialog.setSize(450, 700);
    receiptDialog.setLocationRelativeTo(this);
    receiptDialog.setVisible(true);
}

// Helper Methods
private JSeparator createFancySeparator() {
    JSeparator separator = new JSeparator() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 
                                        10.0f, new float[]{5.0f}, 0.0f));
            g2d.setPaint(new Color(44, 62, 80));
            g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
        }
    };
    separator.setMaximumSize(new Dimension(360, 1));
    return separator;
}
private void addReceiptInfoField(JPanel panel, String label, String value) {
    JLabel labelComponent = new JLabel(label);
    JLabel valueComponent = new JLabel(value);
    labelComponent.setFont(new Font("Arial", Font.BOLD, 12));
    valueComponent.setFont(new Font("Arial", Font.PLAIN, 12));
    panel.add(labelComponent);
    panel.add(valueComponent);
}

private JPanel createSectionPanel(String title, String[][] data) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(Color.WHITE);
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    panel.setMaximumSize(new Dimension(360, 150));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
    panel.add(titleLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));

    for (String[] row : data) {
        JPanel rowPanel = new JPanel(new GridLayout(1, 2));
        rowPanel.setBackground(Color.WHITE);
        
        JLabel label = new JLabel(row[0]);
        JLabel value = new JLabel(row[1]);
        
        label.setFont(new Font("Arial", Font.BOLD, 12));
        value.setFont(new Font("Arial", Font.PLAIN, 12));
        
        rowPanel.add(label);
        rowPanel.add(value);
        
        panel.add(rowPanel);
    }

    return panel;
}

private JButton createStyledButton(String text, Color color) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setForeground(Color.WHITE);
    button.setBackground(color);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setPreferredSize(new Dimension(120, 30));
    return button;
}

private void printReceipt(JPanel receiptPanel) {
    PrinterJob job = PrinterJob.getPrinterJob();
    job.setJobName("Print Receipt");
    
    job.setPrintable((graphics, pageFormat, pageIndex) -> {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D) graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
        
        double scaleX = pageFormat.getImageableWidth() / receiptPanel.getWidth();
        double scaleY = pageFormat.getImageableHeight() / receiptPanel.getHeight();
        double scale = Math.min(scaleX, scaleY);
        
        g2d.scale(scale, scale);
        receiptPanel.paint(g2d);
        
        return Printable.PAGE_EXISTS;
    });
    
    if (job.printDialog()) {
        try {
            job.print();
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(null, "Printing Failed: " + e.getMessage());
        }
    }
    
    }//GEN-LAST:event_printReceiptActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table jTable1;
    private javax.swing.JButton markAsDone;
    private javax.swing.JButton printReceipt;
    // End of variables declaration//GEN-END:variables
}
