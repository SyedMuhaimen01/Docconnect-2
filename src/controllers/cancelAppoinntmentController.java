package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class cancelAppoinntmentController {
	 @FXML
	    public void exit(ActionEvent event) throws IOException {
	        Main.getInstance().changeScene("BookAppointment.fxml", "Book Appointment", 700, 500);
	    }
    @FXML
    private ComboBox<IntegerProperty> appointmentsComboBox;

    public void initialize() {      
        populateAppointmentsComboBox();
    }

    private void populateAppointmentsComboBox() {
        // JDBC connection parameters
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";
        String str = "Pending";

        int userId = fetchCustomerIdByUsername(MainPageController.getInstance().getLoginUserName());
        String fetchIdQuery = "SELECT appointment_id FROM appointment where customer_id = ? and status = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            try (PreparedStatement statement = connection.prepareStatement(fetchIdQuery)) {
                statement.setInt(1, userId);
                statement.setString(2, str);
                ResultSet resultSet = statement.executeQuery();

                ObservableList<IntegerProperty> appointments = FXCollections.observableArrayList();

                while (resultSet.next()) {
                    int appointmentValue = resultSet.getInt("appointment_id");
                    IntegerProperty appointmentProperty = new SimpleIntegerProperty(appointmentValue);
                    appointments.add(appointmentProperty);
                }

                appointmentsComboBox.setItems(appointments);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private int fetchCustomerIdByUsername(String username) {
        System.out.println("Professional username: " + username);
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
                        System.out.println("Customer ID: " + customerId);
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

    @FXML
    private void cancel(ActionEvent event) throws IOException {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";
        
        int appointmentIdToDelete = appointmentsComboBox.getValue().get();

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM appointment WHERE appointment_id = ?")) {

            statement.setInt(1, appointmentIdToDelete);

            // Execute the delete query
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Appointment canceled successfully!");
            } else {
                System.out.println("No appointment found with the specified ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
        Main.getInstance().changeScene("bookAppointment.fxml", "Book Appointments", 700, 500);
    }
}
