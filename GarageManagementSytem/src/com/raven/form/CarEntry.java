package com.raven.form;

import java.awt.Frame;
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

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author matot
 */
public class CarEntry extends javax.swing.JPanel {

    /**
     * Creates new form CarEntry
     */
    public CarEntry() {
        initComponents();
       loadTableData(); 
            loadServices();
            loadCustomers();
            loadEmployees();
        
           jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        // Get the selected row index
        int selectedRow = jTable1.getSelectedRow();
        if(selectedRow != -1) {  // Check if a row is selected
            // Get the customer ID from the first column
            String regNo = jTable1.getValueAt(selectedRow, 0).toString();
            fetchCustomerData(regNo);
        }
    }
});
    }
    
        private String getSelectedServiceId() {
    String selected = serviceType.getSelectedItem().toString();
    // Skip if it's the default "Select a service" item
    if (selected.equals("Select a service")) {
        return null;
    }
    // Extract the ID from the "ID - Service Name" format
    return selected.split(" - ")[0];
}
        
          private String getSelectedCustomerId() {
    String selected = customerName.getSelectedItem().toString();
    // Skip if it's the default "Select a service" item
    if (selected.equals("Select a customer")) {
        return null;
    }
    // Extract the ID from the "ID - Service Name" format
    return selected.split(" - ")[0];
}
          
            private String getSelectedEmployeeId() {
    String selected = employeeName.getSelectedItem().toString();
    // Skip if it's the default "Select a service" item
    if (selected.equals("Select a employee")) {
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
        serviceType.removeAllItems();
        
        // Add services to combobox
        while (rs.next()) {
            String serviceItem = rs.getString("service_id") + " - " + rs.getString("service_name");
            serviceType.addItem(serviceItem);
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
             
   private void loadCustomers() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
        
        String sql = "SELECT first_name, last_name FROM customers";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        
        // Clear existing items
        customerName.removeAllItems();
        
        // Add services to combobox
        while (rs.next()) {
            String serviceItem = rs.getString("first_name") + " - " + rs.getString("last_name");
            customerName.addItem(serviceItem);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading customers: " + e.getMessage(), 
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
              
  private void loadEmployees() {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
        
        String sql = "SELECT emp_id, emp_name FROM employee";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        
        // Clear existing items
       employeeName.removeAllItems();
        
        // Add services to combobox
        while (rs.next()) {
            String serviceItem = rs.getString("emp_id") + " - " + rs.getString("emp_name");
            employeeName.addItem(serviceItem);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading Employee: " + e.getMessage(), 
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
        
        // Prepare SQL query with JOINS to get related data
        String sql = "SELECT " +
                    "ce.reg_no, " +
                    "CONCAT(c.first_name, ' ', c.last_name) as owner_name, " +
                    "CONCAT(c.car_brand, ' - ', c.car_model) as car_name, " +
                    "c.car_plate, " +
                    "c.phone_number, " +
                    "e.emp_name, " +
                    "s.service_name " +
                    "FROM car_entry ce " +
                    "JOIN customers c ON ce.customer_id = c.customer_id " +
                    "JOIN services s ON ce.service_id = s.service_id " +
                    "JOIN employee e ON ce.emp_id = e.emp_id " +
                    "ORDER BY ce.reg_no DESC";
                    
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        
        // Get table model and set column names to match your table
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setColumnIdentifiers(new Object[]{
            "Reg No", "Owner Name", "Car Name", "Car Plate", "Phone No", "Employee Name", "Service Type"
        });
        
        // Clear existing table data
        model.setRowCount(0);
        
        // Add data to table
        while (rs.next()) {
            Object[] row = {
                rs.getString("reg_no"),
                rs.getString("owner_name"),
                rs.getString("car_name"),
                rs.getString("car_plate"),
                rs.getString("phone_number"),
                rs.getString("emp_name"),
                rs.getString("service_name")
            };
            model.addRow(row);
        }
        
        // Debug output
        System.out.println("Query executed successfully. Number of rows: " + model.getRowCount());
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, 
            "Error loading table data: " + e.getMessage(), 
            "Database Error", 
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Add this for debugging
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
    
    
    private void fetchCustomerData(String regNo) {
   
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
        
        String sql = "SELECT ce.*, " +
                    "CONCAT(c.first_name, ' ', c.last_name) as customer_name, " +
                    "s.service_name, " +
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
            // Set the registration number
            registrationNumberTxt.setText(rs.getString("reg_no"));
            
            // Set the customer in combobox
            String customerFullName = rs.getString("customer_name");
            String customerId = rs.getString("customer_id");
            customerName.setSelectedItem(customerId + " - " + customerFullName);
            
            // Set the service in combobox
            String serviceId = rs.getString("service_id");
            String serviceName = rs.getString("service_name");
            serviceType.setSelectedItem(serviceId + " - " + serviceName);
            
            // Set the employee in combobox
            String empId = rs.getString("emp_id");
            String empName = rs.getString("emp_name");
            employeeName.setSelectedItem(empId + " - " + empName);
        }
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Error fetching car entry data: " + e.getMessage(),
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
    
    // Create a new JDialog class for the modal
public class DisplayDataModal extends javax.swing.JDialog {
    private javax.swing.JTable dataTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel titleLabel;

    public DisplayDataModal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
    }

    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Car Entry Records");

        mainPanel.setBackground(new java.awt.Color(242, 246, 253));

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24));
        titleLabel.setForeground(new java.awt.Color(140, 110, 207));
        titleLabel.setText("Car Entry Records");

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {
                "RegNo", "Owner Name", "Car Name", "Car Plate", "Phone No", "Employee Name", "Service Type"
            }
        ));
        scrollPane.setViewportView(dataTable);

        closeButton.setBackground(new java.awt.Color(204, 255, 255));
        closeButton.setFont(new java.awt.Font("SansSerif", 1, 14));
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 876, Short.MAX_VALUE)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    public void loadData() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
            
            String sql = "SELECT " +
                        "ce.reg_no, " +
                        "CONCAT(c.first_name, ' ', c.last_name) as owner_name, " +
                        "CONCAT(c.car_brand, ' - ', c.car_model) as car_name, " +
                        "c.car_plate, " +
                        "c.phone_number, " +
                        "e.emp_name, " +
                        "s.service_name " +
                        "FROM car_entry ce " +
                        "JOIN customers c ON ce.customer_id = c.customer_id " +
                        "JOIN services s ON ce.service_id = s.service_id " +
                        "JOIN employee e ON ce.emp_id = e.emp_id " +
                        "ORDER BY ce.reg_no DESC";
                        
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("reg_no"),
                    rs.getString("owner_name"),
                    rs.getString("car_name"),
                    rs.getString("car_plate"),
                    rs.getString("phone_number"),
                    rs.getString("emp_name"),
                    rs.getString("service_name")
                };
                model.addRow(row);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading data: " + e.getMessage(), 
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
}


public class SearchCarEntryDialog extends javax.swing.JDialog {
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JPanel resultPanel;
    private javax.swing.JTextField searchField;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JLabel regNoLabel;
    private javax.swing.JButton closeButton;
    
    // Labels for displaying results
    private javax.swing.JLabel ownerLabel;
    private javax.swing.JLabel carLabel;
    private javax.swing.JLabel plateLabel;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JLabel employeeLabel;
    private javax.swing.JLabel serviceLabel;

    public SearchCarEntryDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
    }

    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        searchPanel = new javax.swing.JPanel();
        resultPanel = new javax.swing.JPanel();
        searchField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        regNoLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        
        // Initialize result labels
        ownerLabel = new javax.swing.JLabel();
        carLabel = new javax.swing.JLabel();
        plateLabel = new javax.swing.JLabel();
        phoneLabel = new javax.swing.JLabel();
        employeeLabel = new javax.swing.JLabel();
        serviceLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Car Entry");

        mainPanel.setBackground(new java.awt.Color(242, 246, 253));
        searchPanel.setBackground(new java.awt.Color(242, 246, 253));
        resultPanel.setBackground(new java.awt.Color(255, 255, 255));
        resultPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        titleLabel.setFont(new java.awt.Font("SansSerif", 1, 24));
        titleLabel.setForeground(new java.awt.Color(140, 110, 207));
        titleLabel.setText("Search Car Entry");

        regNoLabel.setFont(new java.awt.Font("SansSerif", 1, 14));
        regNoLabel.setText("Registration Number:");

        searchButton.setBackground(new java.awt.Color(204, 255, 255));
        searchButton.setFont(new java.awt.Font("SansSerif", 1, 14));
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        closeButton.setBackground(new java.awt.Color(204, 255, 255));
        closeButton.setFont(new java.awt.Font("SansSerif", 1, 14));
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dispose();
            }
        });

        // Style result labels
        ownerLabel.setFont(new java.awt.Font("SansSerif", 0, 14));
        carLabel.setFont(new java.awt.Font("SansSerif", 0, 14));
        plateLabel.setFont(new java.awt.Font("SansSerif", 0, 14));
        phoneLabel.setFont(new java.awt.Font("SansSerif", 0, 14));
        employeeLabel.setFont(new java.awt.Font("SansSerif", 0, 14));
        serviceLabel.setFont(new java.awt.Font("SansSerif", 0, 14));

        // Layout components
        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(regNoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchButton)
                .addContainerGap())
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(searchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regNoLabel)
                    .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addContainerGap())
        );

        // Layout result panel
        javax.swing.GroupLayout resultPanelLayout = new javax.swing.GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ownerLabel)
                    .addComponent(carLabel)
                    .addComponent(plateLabel)
                    .addComponent(phoneLabel)
                    .addComponent(employeeLabel)
                    .addComponent(serviceLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        resultPanelLayout.setVerticalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ownerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(carLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(plateLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phoneLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(employeeLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(serviceLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel)
                    .addComponent(searchPanel)
                    .addComponent(resultPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String regNo = searchField.getText().trim();
        
        if (regNo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a registration number",
                "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        searchCarEntry(regNo);
    }

    private void searchCarEntry(String regNo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");
            
            String sql = "SELECT " +
                        "ce.reg_no, " +
                        "CONCAT(c.first_name, ' ', c.last_name) as owner_name, " +
                        "CONCAT(c.car_brand, ' - ', c.car_model) as car_name, " +
                        "c.car_plate, " +
                        "c.phone_number, " +
                        "e.emp_name, " +
                        "s.service_name " +
                        "FROM car_entry ce " +
                        "JOIN customers c ON ce.customer_id = c.customer_id " +
                        "JOIN services s ON ce.service_id = s.service_id " +
                        "JOIN employee e ON ce.emp_id = e.emp_id " +
                        "WHERE ce.reg_no = ?";
                        
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, regNo);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                // Set the result labels with formatted text
                ownerLabel.setText("Owner: " + rs.getString("owner_name"));
                carLabel.setText("Car: " + rs.getString("car_name"));
                plateLabel.setText("Plate Number: " + rs.getString("car_plate"));
                phoneLabel.setText("Phone: " + rs.getString("phone_number"));
                employeeLabel.setText("Employee: " + rs.getString("emp_name"));
                serviceLabel.setText("Service: " + rs.getString("service_name"));
            } else {
                // Clear labels and show message if no results found
                clearLabels();
                JOptionPane.showMessageDialog(this,
                    "No record found for registration number: " + regNo,
                    "Not Found",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error searching for car entry: " + e.getMessage(),
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

    private void clearLabels() {
        ownerLabel.setText("Owner: ");
        carLabel.setText("Car: ");
        plateLabel.setText("Plate Number: ");
        phoneLabel.setText("Phone: ");
        employeeLabel.setText("Employee: ");
        serviceLabel.setText("Service: ");
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
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        registrationNumberTxt = new javax.swing.JTextField();
        SubmitButton = new javax.swing.JButton();
        customerName = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        employeeName = new javax.swing.JComboBox<>();
        serviceType = new javax.swing.JComboBox<>();
        updateButton = new javax.swing.JButton();
        deleteButtton = new javax.swing.JButton();
        SubmitButton2 = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(242, 246, 253));
        jPanel1.setPreferredSize(new java.awt.Dimension(932, 597));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(140, 110, 207));
        jLabel1.setText("Car Entry");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reg-No", "Owner Name", "Car Name", "Car Plate", "Phone No", "Employee Name", "Service Type"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel2.setBackground(new java.awt.Color(242, 246, 253));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Registration Number");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Owner Name");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Employee Name");

        registrationNumberTxt.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        registrationNumberTxt.setForeground(new java.awt.Color(255, 51, 51));
        registrationNumberTxt.setText("Auto generated Field");

        SubmitButton.setBackground(new java.awt.Color(204, 255, 255));
        SubmitButton.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        SubmitButton.setForeground(new java.awt.Color(0, 0, 0));
        SubmitButton.setText("Submit");
        SubmitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButtonActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Service Type");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(SubmitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(registrationNumberTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(serviceType, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(employeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(registrationNumberTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serviceType, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(SubmitButton)
                .addGap(103, 103, 103))
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

        deleteButtton.setBackground(new java.awt.Color(204, 255, 255));
        deleteButtton.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        deleteButtton.setForeground(new java.awt.Color(0, 0, 0));
        deleteButtton.setText("Delete");
        deleteButtton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButttonActionPerformed(evt);
            }
        });

        SubmitButton2.setBackground(new java.awt.Color(204, 255, 255));
        SubmitButton2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        SubmitButton2.setForeground(new java.awt.Color(0, 0, 0));
        SubmitButton2.setText("Search");
        SubmitButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitButton2ActionPerformed(evt);
            }
        });

        resetButton.setBackground(new java.awt.Color(204, 255, 255));
        resetButton.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        resetButton.setForeground(new java.awt.Color(255, 0, 0));
        resetButton.setText("Reset");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(resetButton)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(55, 55, 55)
                                        .addComponent(SubmitButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(deleteButtton, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(28, 28, 28))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateButton)
                    .addComponent(deleteButtton)
                    .addComponent(SubmitButton2))
                .addGap(25, 25, 25)
                .addComponent(resetButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 936, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteButttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButttonActionPerformed
        // TODO add your handling code here:
           String regNo = JOptionPane.showInputDialog(this,
            "Enter Employee ID to delete:",
            "Delete Loan Record",
            JOptionPane.QUESTION_MESSAGE);

        // Check if user canceled the input or entered an empty string
        if (regNo  == null || regNo  .trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid Car Entry ID",
                "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Confirm deletion
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this Fee record for Car Entry ID: " + regNo + "?",
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
            String sql = "DELETE FROM car_entry WHERE reg_no = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, regNo  );

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
                    "No record found with the given CarEntry ID.",
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
    }//GEN-LAST:event_deleteButttonActionPerformed

    private void SubmitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButtonActionPerformed
        // TODO add your handling code here:
           Connection record = null;
    PreparedStatement insert = null;

    try {
        // Validate input fields
//        if (customerName.getSeletectedItem().trim().isEmpty() || 
//            serviceType.getText().trim().isEmpty() || 
//            employeeName.getText().trim().isEmpty() || 
//            {
//            JOptionPane.showMessageDialog(this, "Please fill all required fields!");
//            return;
//        }
String serviceId = getSelectedServiceId();
String customerId = getSelectedServiceId();
String employeeId = getSelectedServiceId(); 

if (serviceId == null || customerId == null || employeeId == null) {
            JOptionPane.showMessageDialog(this, "Please select a valid service, customer, and employee.");
            return;
        }
String regNo = "REG_" + (System.currentTimeMillis() % 100000);
        Class.forName("com.mysql.cj.jdbc.Driver");
        record = DriverManager.getConnection("jdbc:mysql://localhost/garage","root","Matoto@victoire2003");
        
        String sql = "INSERT INTO car_entry (reg_no, customer_id, service_id, emp_id) VALUES (?, ?, ?, ?)";
        
        insert = record.prepareStatement(sql);
        insert.setString(1,regNo);
        insert.setString(2, customerId);
        insert.setString(3, serviceId);
        insert.setString(4, employeeId);
        
        

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

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        // TODO add your handling code here:
        
    Connection conn = null;
    PreparedStatement insertStmt = null;

    try {
        // Validate fields
        String regNo = registrationNumberTxt.getText().trim();
        String serviceId = getSelectedServiceId();
        String customerId = getSelectedCustomerId();
        String employeeId = getSelectedEmployeeId();

        if (regNo.isEmpty() || serviceId == null || customerId == null || employeeId == null) {
            JOptionPane.showMessageDialog(this, "Please ensure all fields are filled correctly.");
            return;
        }

        // Establish connection
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost/garage", "root", "Matoto@victoire2003");

        // Check for duplicate regNo
        String checkSql = "SELECT COUNT(*) FROM car_entry WHERE reg_no = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkSql);
        checkStmt.setString(1, regNo);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        if (rs.getInt(1) > 0) {
            JOptionPane.showMessageDialog(this, "A record with this registration number already exists.");
            return;
        }

        // Insert new record into car_entry
        String sql = "INSERT INTO car_entry (reg_no, customer_id, service_id, emp_id) VALUES (?, ?, ?, ?)";
        insertStmt = conn.prepareStatement(sql);
        insertStmt.setString(1, regNo);
        insertStmt.setString(2, customerId);
        insertStmt.setString(3, serviceId);
        insertStmt.setString(4, employeeId);

        insertStmt.executeUpdate();
        JOptionPane.showMessageDialog(this, "Car entry uploaded successfully!");
        clearFields();
        loadTableData();

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error uploading record: " + ex.getMessage());
        ex.printStackTrace();
    } finally {
        try {
            if (insertStmt != null) insertStmt.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            System.err.println("Error closing connections: " + ex.getMessage());
        }
    }


    }//GEN-LAST:event_updateButtonActionPerformed

    private void SubmitButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitButton2ActionPerformed
        // TODO add your handling code here:
       SearchCarEntryDialog dialog = new SearchCarEntryDialog((Frame) javax.swing.SwingUtilities.getWindowAncestor(this), true);
    dialog.setSize(500, 400);
    dialog.setVisible(true);

    }//GEN-LAST:event_SubmitButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SubmitButton;
    private javax.swing.JButton SubmitButton2;
    private javax.swing.JComboBox<String> customerName;
    private javax.swing.JButton deleteButtton;
    private javax.swing.JComboBox<String> employeeName;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private com.raven.swing.table.Table jTable1;
    private javax.swing.JTextField registrationNumberTxt;
    private javax.swing.JButton resetButton;
    private javax.swing.JComboBox<String> serviceType;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables

    private void clearFields() {
        registrationNumberTxt.setText("");
        customerName.setSelectedItem("");
       serviceType.setSelectedItem("");
       employeeName.setSelectedItem("");
      
          
        
    }
    private void displayDataActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
