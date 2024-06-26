package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerProfileController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label cnicLabel;

    @FXML
    private Label phLabel;

    @FXML
    private Label emailLabel;
    
    @FXML
    private TextArea DetailsTextArea;

    @FXML
    private void initialize() {
        loadUserData();
    }
    
    @FXML
 	public void Update(ActionEvent event) throws IOException {
 		
 		Main.getInstance().changeScene("customerUpdateProfile.fxml","Customer Profile",700,500);		
 	}
    @FXML
 	public void Logout(ActionEvent event) throws IOException {
 		
 		Main.getInstance().changeScene("MainPage.fxml","Main Page",700,500);		
 	}
    @FXML
 	public void bookAppointment(ActionEvent event) throws IOException {
 		
 		Main.getInstance().changeScene("BookAppointment.fxml","Book Appointment",700,500);		
 	}

    private void loadUserData() {
    	viewHistory();
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";

        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");

                String uName = MainPageController.getInstance().getLoginUserName();
                String loginPassword = MainPageController.getInstance().getLoginPassword();

                String sql = "SELECT firstName,lastName ,cnic, ph, email FROM users WHERE userName=? and password=?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, uName);
                    statement.setString(2, loginPassword);

                 
                    ResultSet resultSet = statement.executeQuery();


                    if (resultSet.next()) {
                       
                    	nameLabel.setText(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
                        cnicLabel.setText(resultSet.getString("cnic"));
                        phLabel.setText(resultSet.getString("ph"));
                        emailLabel.setText(resultSet.getString("email"));
                    }
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
    private void viewHistory() {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";

        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username, password);

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");
                String name = MainPageController.getInstance().getLoginUserName();
                String str="Completed";
                int customerId = fetchCustomerIdByUsername(name);

          
                String sql = "SELECT appointment_id, time_slot, status FROM appointment WHERE customer_id = ? and status=?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, customerId);
                    statement.setString(2, str);

                    ResultSet resultSet = statement.executeQuery();

                    StringBuilder appointmentDetails = new StringBuilder();
                    while (resultSet.next()) {
                        appointmentDetails.append("Appointment ID: ").append(resultSet.getInt("appointment_id")).append("\n");
                        appointmentDetails.append("Time Slot: ").append(resultSet.getTimestamp("time_slot")).append("\n");
                        appointmentDetails.append("Status: ").append(resultSet.getString("status")).append("\n\n");
                    }

                    DetailsTextArea.setText(appointmentDetails.toString());
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

            }
        }
    }
    private int fetchCustomerIdByUsername(String username) {
        System.out.println("Professional usernamee: " + username);
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


}
