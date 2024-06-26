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

public class searchCenterController {

    @FXML
    private Label nameLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label phLabel;
    
    @FXML
    private TextArea DetailsTextArea;

    @FXML
    private Label emailLabel;

    @FXML
    private void initialize() {
       
        loadUserData();
    }
    

    @FXML
 	public void Back(ActionEvent event) throws IOException {
 		
 		Main.getInstance().changeScene("MainPage.fxml","Main Page",700,500);		
 	}
   

    private void loadUserData() {
    	viewProfessional();
   
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
                

                String sql = "SELECT username,centerName ,address, ph, email FROM centers WHERE userName=? ";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, uName);
                    

                    ResultSet resultSet = statement.executeQuery();

                    if (resultSet.next()) {

                    	nameLabel.setText(resultSet.getString("centerName"));
                        addressLabel.setText(resultSet.getString("address"));
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
    private void viewProfessional() {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";

        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username, password);
            String cName = null; 

            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");

                String name = MainPageController.getInstance().getLoginUserName();
                String sql2 = "SELECT centerName FROM centers WHERE username=?";
                try (PreparedStatement statement2 = connection.prepareStatement(sql2)) {
                    statement2.setString(1, name);
                    ResultSet resultSet = statement2.executeQuery();
                    while (resultSet.next()) {
                        cName = resultSet.getString("centerName");
                    }
                }
                String affStatus="Accepted";
                String sql = "SELECT firstName, lastName, designation, affiliation FROM professional WHERE affiliation=? and affiliationStatus=?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, cName);
                    statement.setString(2, affStatus);
                    ResultSet resultSet = statement.executeQuery();

                    StringBuilder details = new StringBuilder();
                    while (resultSet.next()) {
                        details.append("Name : ").append(resultSet.getString("firstName"))
                               .append(" ").append(resultSet.getString("lastName")).append("\n");
                        details.append("Affiliation : ").append(resultSet.getString("affiliation")).append("\n");
                        details.append("Designation : ").append(resultSet.getString("designation")).append("\n");
                        details.append("\n");
                    }

                    DetailsTextArea.setText(details.toString());
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
}
