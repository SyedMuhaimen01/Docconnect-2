package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class bookController {
    @FXML
    private Label errorLabel;

    @FXML
    private ComboBox<String> centerComboBox;
    @FXML
    private ComboBox<String> professionalComboBox;
    @FXML
    private TextField slotField;

    public void initialize() {
        
        populateCenterComboBox();   
        centerComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           
            populateProfessionalComboBox(newValue);
        });
    }

    private void populateCenterComboBox() {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";

        // SQL query to retrieve center names
        String sql = "SELECT centerName FROM centers";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String centerName = resultSet.getString("centerName");
                centerComboBox.getItems().add(centerName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

   private void populateProfessionalComboBox(String selectedCenter) {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";
        String str="Accepted";
        String sql = "SELECT firstName, lastName FROM professional INNER JOIN centers ON professional.affiliation = centers.centerName WHERE centers.centerName = ? and affiliationStatus=?";
 
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, selectedCenter);
            statement.setString(2, str);

            try (ResultSet resultSet = statement.executeQuery()) {
                professionalComboBox.getItems().clear(); 

                while (resultSet.next()) {
                    String professionalName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
                    professionalComboBox.getItems().add(professionalName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private void saveToDatabase(String professional, String slot, String status) {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username1, password1);
            String name = MainPageController.getInstance().getLoginUserName();
            int customerId = fetchCustomerIdByUsername(name);

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");

                String sql = "INSERT INTO appointment (customer_id, professional_id, time_slot, status) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, customerId);
                    statement.setInt(2, fetchProfessionalIdByName(professional));
                    statement.setString(3, slot);
                    statement.setString(4, status);

                    // Execute the statement
                    statement.executeUpdate();
                }

            } else {
                System.out.println("Failed to connect to the database!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        } finally {
            // Close the connection in the finally block
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
    }
    @FXML
    public void exit(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("BookAppointment.fxml", "Book Appointment", 700, 500);
    }
    @FXML
    public void Calender(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("calender.fxml", "Calender", 700, 500);
    }
    @FXML
    private void handleBookButton() throws IOException {
        // Get input values from fields
        String slot = slotField.getText();
        String center = centerComboBox.getValue();
        String professional = professionalComboBox.getValue();
        String status = "Pending";
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(slot, formatter);

   
        String date = dateTime.toLocalDate().toString();
        String time = dateTime.toLocalTime().toString();
        System.out.println("Time : "+ time);
        if (!isSlotWithinWorkingHours(professional, time)) {
            errorLabel.setText("Professional is unavailable during the selected slot.");
            System.out.println("Professional is unavailable during the selected slot.");
            return;
        }
        if (isUserAppointmentSlotAlreadyBooked(professional, slot)) {
            errorLabel.setText("Error: Slot unavailable. ");
            System.out.println("Error: Slot unavailable. ");
            return;
        }


        // Save data to the database
        saveToDatabase(professional, slot, status);

        clearFields();
        Main.getInstance().changeScene("paymentGateway.fxml", "payment Gateway", 700, 500);
    }
    
    private boolean isSlotWithinWorkingHours(String professional, String slot) {
        
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";
        
        String sql = "SELECT start_time, end_time FROM appointmentHours "
                + "WHERE professional_id = (SELECT id FROM professional WHERE CONCAT(firstName, ' ', lastName) = ?)";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

               statement.setString(1, professional);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String startTime = resultSet.getString("start_time");
                    String endTime = resultSet.getString("end_time");

                    return slot.compareTo(startTime) >= 0 && slot.compareTo(endTime) < 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        return false; 
    }

    private void clearFields() {
        
        slotField.clear();
        centerComboBox.getSelectionModel().clearSelection();
        professionalComboBox.getSelectionModel().clearSelection();
    }

    private int fetchCustomerIdByUsername(String username) {
        int customerId = 0;
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username1, password1);

            if (connection != null && !connection.isClosed()) {
                String fetchIdQuery = "SELECT id FROM users WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(fetchIdQuery)) {
                    statement.setString(1, username.trim()); // Trim to remove whitespace
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        customerId = resultSet.getInt("id");
                        System.out.println("customer ID: " + customerId);
                    } else {
                        System.out.println("No customer found with the given username.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage()); 
            // Handle the exception appropriately
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
        return customerId;
    }

    private int fetchProfessionalIdByName(String professionalName) {
        int professionalId = 0;
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username1, password1);

            if (connection != null && !connection.isClosed()) {
                String fetchIdQuery = "SELECT id FROM professional WHERE CONCAT(firstName, ' ', lastName) = ?";
                try (PreparedStatement statement = connection.prepareStatement(fetchIdQuery)) {
                    statement.setString(1, professionalName.trim()); // Trim to remove whitespace
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        professionalId = resultSet.getInt("id");
                        System.out.println("professional ID: " + professionalId);
                    } else {
                        System.out.println("No professional found with the given name.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage()); 
            // Handle the exception appropriately
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }
        return professionalId;
    }
    
    private boolean isUserAppointmentSlotAlreadyBooked(String professional, String slot) {
    	
    	int customerId = fetchCustomerIdByUsername(MainPageController.getInstance().getLoginUserName());
        int professionalId = fetchProfessionalIdByName(professional);
        String status = "Pending";
        
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";

        String sql = "SELECT * FROM appointment  WHERE customer_id = ? AND professional_id = ? AND time_slot = ? AND status = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, customerId);
            statement.setInt(2, professionalId);
            statement.setString(3, slot);
            statement.setString(4, status);

            ResultSet resultSet = statement.executeQuery();
         
            return resultSet.next();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
            return false;
        }
    }
}
