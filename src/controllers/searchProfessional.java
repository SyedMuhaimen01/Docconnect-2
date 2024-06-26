package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class searchProfessional implements SearchStrategy {
	   @Override
	    public void search(String username) throws IOException {
		   String url = "jdbc:mysql://localhost:3306/SDA";
	        String dbUsername = "root";
	        String dbPassword = "123456";

	        Connection connection = null;
	        try {
	            // Load the MySQL JDBC driver
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            // Establish a connection to the database
	            connection = DriverManager.getConnection(url, dbUsername, dbPassword);

	            if (connection != null && !connection.isClosed()) {
	                System.out.println("Connected to the database!");

	                String sql = "SELECT * FROM professional WHERE username = ? ";
	                try (PreparedStatement statement = connection.prepareStatement(sql)) {
	                    statement.setString(1, username);

	                   
	                    ResultSet resultSet = statement.executeQuery();
           
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

	        System.out.println("Searching for professionals with name: " + username);
	        
	         Main.getInstance().changeScene("searchProfessional.fxml", "Search Professional", 700, 500);
	    }

}
