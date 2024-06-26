package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.*;

public class centerUpdateProfileController {

    @FXML
    private TextField newUsernameField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField newEmailField;

    @FXML
    private TextField newPhoneNumberField;

    @FXML
    private Button updateButton;

 

    @FXML
    private void initialize() {
        
    }
    @FXML
	public void Back(ActionEvent event) throws IOException {
		
		Main.getInstance().changeScene("centerProfile.fxml","Center Profile",700,500);		
	}
    @FXML
    private void handleUpdateUserName() throws IOException {
        
        String newUsername = newUsernameField.getText();

        
        boolean updateSuccessful = updateUserName(newUsername);

        if (updateSuccessful) {
            
            System.out.println("User data updated successfully!");
            Main.getInstance().changeScene("centerProfile.fxml","Customer Profile",700,500);		
        } 
        else {
            
            System.out.println("Failed to update user data.");
        }
    }
    
    @FXML
    private void handleUpdatePassword() throws IOException {
        
       
        String newPassword = newPasswordField.getText();

        
        boolean updateSuccessful = updatePassword( newPassword);

        if (updateSuccessful) {
            
            System.out.println("User data updated successfully!");
            Main.getInstance().changeScene("centerProfile.fxml","HealthCare Center Profile",700,500);		
        } 
        else {
           
            System.out.println("Failed to update user data.");
        }
    }
    
    @FXML
    private void handleUpdateEmail() throws IOException {
        

        String newEmail = newEmailField.getText();

       
        boolean updateSuccessful = updateEmail(newEmail);

        if (updateSuccessful) {
           
            System.out.println("User data updated successfully!");
            Main.getInstance().changeScene("centerProfile.fxml","HealthCare Center Profile",700,500);		
        } 
        else {
          
            System.out.println("Failed to update user data.");
        }
    }
    
    @FXML
    private void handleUpdatePhoneNumber() throws IOException {
       

        String newPhoneNumber = newPhoneNumberField.getText();

        
        boolean updateSuccessful = updatePhoneNumber( newPhoneNumber);

        if (updateSuccessful) {
            
            System.out.println("User data updated successfully!");
            Main.getInstance().changeScene("centerProfile.fxml","HealthCare Center Profile",700,500);		
        } 
        else {
            
            System.out.println("Failed to update user data.");
        }
    }

    private boolean updateUserName(String newUsername) {
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

                String updateSql = "UPDATE centers SET userName=? WHERE userName=? and password=?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setString(1, newUsername);
                    updateStatement.setString(2, uName);
                    updateStatement.setString(3, loginPassword);

                    int rowsUpdated = updateStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("User information updated successfully!");
                        return true;
                    } else {
                        System.out.println("Failed to update user information. User not found.");
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

        return false;
    }
    
    private boolean updatePassword(String newPassword) {
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

                String updateSql = "UPDATE centers SET password=? WHERE userName=? and password=?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setString(1, newPassword);
                    updateStatement.setString(2, uName);
                    updateStatement.setString(3, loginPassword);

                    // Execute the update query
                    int rowsUpdated = updateStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("User information updated successfully!");
                        return true;
                    } else {
                        System.out.println("Failed to update user information. User not found.");
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

        return false;
    }
    
    private boolean updateEmail(String newEmail) {
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

                String updateSql = "UPDATE centers SET email=? WHERE userName=? and password=?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setString(1, newEmail);
                    updateStatement.setString(2, uName);
                    updateStatement.setString(3, loginPassword);

                    int rowsUpdated = updateStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("User information updated successfully!");
                        return true;
                    } else {
                        System.out.println("Failed to update user information. User not found.");
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

        return false;
    }
    
    private boolean updatePhoneNumber(String newPhoneNumber) {
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

                String updateSql = "UPDATE centers SET ph=? WHERE userName=? and password=?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setString(1, newPhoneNumber);
                    updateStatement.setString(2, uName);
                    updateStatement.setString(3, loginPassword);

                    int rowsUpdated = updateStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        System.out.println("User information updated successfully!");
                        return true;
                    } else {
                        System.out.println("Failed to update user information. User not found.");
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

        return false;
    }

    }
    
