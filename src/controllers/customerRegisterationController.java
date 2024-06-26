package controllers;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class customerRegisterationController {

    @FXML
    private TextField usernameField;
    
    @FXML
    private Label errorLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField cnicField;

    @FXML
    private TextField phField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField reEnterPasswordField;


    @FXML
    public void registerOptions(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("registerOptions.fxml", "Register Options", 292, 188);
    }
    private boolean flag=false;
    private void saveToDatabase(String username, String firstName, String lastName, String cnic, String ph, String email,
            String password) {
        // Replace these values with your actual database information
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username1, password1);

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");

                if(isUserNameRegistered(username)==false)
                {
                String sql = "INSERT INTO users (username, firstName, lastName, cnic, ph, email, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, firstName);
                    statement.setString(3, lastName);
                    statement.setString(4, cnic);
                    statement.setString(5, ph);
                    statement.setString(6, email);
                    statement.setString(7, password);

                    // Execute the statement
                    statement.executeUpdate();
                }
                }
                else
                {                
                	errorLabel.setText("Username already registered !");
                	flag=true;
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
    private void handleRegisterButton(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String cnic = cnicField.getText();
        String ph = phField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String reEnterPassword = reEnterPasswordField.getText();

        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || cnic.isEmpty() || ph.isEmpty() || email.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields!");
            return; 
        }

       
        saveToDatabase(username, firstName, lastName, cnic, ph, email, password);

       
        clearFields();
        if(flag==false)
        {
        	Main.getInstance().changeScene("customerProfile.fxml","Customer Profile",700,500);
        }
    }

    private void clearFields() {
        
        usernameField.clear();
        firstNameField.clear();
        lastNameField.clear();
        cnicField.clear();
        phField.clear();
        emailField.clear();
        passwordField.clear();
        reEnterPasswordField.clear();
    }
    private boolean isUserNameRegistered(String username) {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username1, password1);

            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0; 
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
        return false;
    }
}
