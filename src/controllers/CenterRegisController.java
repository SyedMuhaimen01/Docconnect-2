package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CenterRegisController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField centerNameField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phField;
    
    @FXML
    private Label errorLabel;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField reEnterPasswordField;

    @FXML
    private Button registerButton;

    private boolean flag = false;

    @FXML
    public void registerOptions(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("registerOptions.fxml", "Register Options", 292, 188);
    }

    private void saveToDatabase(String username, String centerName, String address, String ph, String email, String password) {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        try (Connection connection = DriverManager.getConnection(url, username1, password1)) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");

                if (!isUserNameRegistered(username)) {
                    String sql = "INSERT INTO centers (username, centerName, address, ph, email, password) VALUES (?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement statement = connection.prepareStatement(sql)) {
                        statement.setString(1, username);
                        statement.setString(2, centerName);
                        statement.setString(3, address);
                        statement.setString(4, ph);
                        statement.setString(5, email);
                        statement.setString(6, password);

                        statement.executeUpdate();
                    }
                } else {
                    errorLabel.setText("Username already registered!");
                    flag = true;
                }
            } else {
                System.out.println("Failed to connect to the database!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @FXML
    private void handleRegisterButton(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String centerName = centerNameField.getText();
        String address = addressField.getText();
        String ph = phField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String reEnterPassword = reEnterPasswordField.getText();

        if (username.isEmpty() || centerName.isEmpty() || address.isEmpty() || ph.isEmpty() || email.isEmpty() || password.isEmpty() || reEnterPassword.isEmpty()) {
            errorLabel.setText("Please fill in all fields!");
            return; 
        }
        saveToDatabase(username, centerName, address, ph, email, password);

        clearFields();
        if (!flag) {
            Main.getInstance().changeScene("centerProfile.fxml", "Healthcare Center Profile", 700, 500);
        }
    }

    private void clearFields() {
        usernameField.clear();
        centerNameField.clear();
        phField.clear();
        emailField.clear();
        addressField.clear();
        passwordField.clear();
        reEnterPasswordField.clear();
    }

    private boolean isUserNameRegistered(String username) {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        try (Connection connection = DriverManager.getConnection(url, username1, password1)) {
            if (connection != null && !connection.isClosed()) {
                String sql = "SELECT COUNT(*) FROM centers WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
