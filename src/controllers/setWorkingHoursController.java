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

public class setWorkingHoursController {
	@FXML
	private TextField DayField;
	
	@FXML
	private TextField startTimeField;
	
	@FXML
	private TextField endTimeField;
	public void exit(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("manageAppointment.fxml", "Main Page", 700, 500);
    }
	 private void saveToDatabase(int ProfessionalId , String Day, String startTime, String endTime) {
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


	                String sql = "INSERT INTO appointmentHours (professional_id, day_of_week, start_time, end_time) VALUES (?, ?, ?, ?)";
	                try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                    statement.setInt(1, ProfessionalId);
	                    statement.setString(2, Day);
	                    statement.setString(3, startTime);
	                    statement.setString(4, endTime);

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
	    private void handleSetButton(ActionEvent event) throws IOException {

	        String Day = DayField.getText();
	        String startTime = startTimeField.getText();
	        String endTime = endTimeField.getText();
	        
	        
            String name=MainPageController.getInstance().getLoginUserName();
            
            int id = fetchProfessionalIdByUsername(name);

	        saveToDatabase(id,Day,startTime,endTime);

	        clearFields();

	        	Main.getInstance().changeScene("professionalProfile.fxml","Professional Profile",700,500);
	        
	    }

	    private void clearFields() {
	        
	        DayField.clear();
	        startTimeField.clear();
	        endTimeField.clear();

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
	                    statement.setString(1, username.trim()); 
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
}
