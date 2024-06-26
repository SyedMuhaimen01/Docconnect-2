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

public class searchProfessionalController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label cnicLabel;

    @FXML
    private Label phLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label medicalIdLabel;

    @FXML
    private Label medicalCenterLabel;
    
    @FXML
    private TextArea DetailsTextArea;

    @FXML
    private void initialize() {
        
        loadUserData();
    }



    @FXML
    public void Back(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("MainPage.fxml", "Main Page", 700, 500);
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
                

                String sql = "SELECT firstName,lastName ,cnic, ph, email,medicalId,affiliation,affiliationStatus FROM professional WHERE userName=? ";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, uName);
                    
                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {
                        nameLabel.setText(resultSet.getString("firstName") + " " + resultSet.getString("lastName"));
                        cnicLabel.setText(resultSet.getString("cnic"));
                        phLabel.setText(resultSet.getString("ph"));
                        emailLabel.setText(resultSet.getString("email"));
                        medicalIdLabel.setText(resultSet.getString("medicalId"));
                        if ("Accepted".equals(resultSet.getString("affiliationStatus"))) {
                            medicalCenterLabel.setText(resultSet.getString("affiliation"));
                        } else 
                        {
                            medicalCenterLabel.setText("");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception appropriately
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
                int professionalId = fetchProfessionalIdByUsername(name);

                String sql = "SELECT appointment_id, time_slot, status FROM appointment WHERE professional_id = ? and status=?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, professionalId);
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
    private int fetchProfessionalIdByUsername(String username) {
    	System.out.println("Professional usernamee: " + username);
        int professionalId = 0;
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username1, password1);

            if (connection != null && !connection.isClosed()) {
                String fetchIdQuery = "SELECT id FROM professional WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(fetchIdQuery)) {
                    statement.setString(1, username.trim()); // Trim to remove whitespace
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        professionalId = resultSet.getInt("id");
                        System.out.println("Professional ID: " + professionalId);
                    } else {
                        System.out.println("No professional found with the given username.");
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.err.println("Error: " + e.getMessage()); // Print the error message
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
}
