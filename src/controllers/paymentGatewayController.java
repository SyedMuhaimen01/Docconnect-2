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
import javafx.scene.control.*;

public class paymentGatewayController {
	
	  @FXML
	    private ComboBox<String> MethodComboBox;

	    public void initialize() throws SQLException {
	        
	    	
	    	populateMethodComboBox();
	    	setAmount();
	    }

	    private void populateMethodComboBox() {
	        // JDBC connection parameters
	    	 ObservableList<String> type = FXCollections.observableArrayList();
	    	 type.add("EasyPaisa");
	    	 type.add("JazzCash");
	    	 type.add("Credit Card");
	    	 type.add("SadaPay");
	    	 type.add("NayaPay");
	    	 type.add("GPay");
	    	 MethodComboBox.setItems(type);
	    }
	    @FXML
	    private Label amountLabel;
	    @FXML
	    private TextField accNoField;
	    
	    private void setAmount() throws SQLException
	    {
	    	String str=getProfessionalSkill();
	    	int fee=0;
	    	if(str=="Physician")
	    	{
	    		fee=2000;
	    	}
	    	else if(str=="Nurse") {
	    		fee=1000;
	    	}
	    	else if(str=="Nurse Practitione") {
	    		fee=1500;
	    	}
	    	else if(str=="Physician Assistant") {
	    		fee=3000;
	    	}
	    	else if(str=="Pharmacist") {
	    		fee=4000;
	    	}
	    	else if(str=="Dentist") {
	    		fee=10000;
	    	}
	    	else if(str=="Optometrist") {
	    		fee=5000;
	    	}
	    	else if(str=="Physical Therapist") {
	    		fee=4000;
	    	}
	    	else if(str=="Occupational Therapist") {
	    		fee=5000;
	    	}
	    	else if(str=="Speech-Language Pathologist") {
	    		fee=6000;
	    	}
	    	else if(str=="Radiologist") {
	    		fee=2000;
	    	}
	    	else if(str=="Respiratory Therapist") {
	    		fee=7000;
	    	}
	    	else if(str=="Chiropractor") {
	    		fee=8000;
	    	}
	    	else {
	    		
	    	}
	    	String strFee = String.valueOf(fee);
	    	amountLabel.setText(strFee);
	    }
	  private String getAccNum() {
		  
		  String acc=accNoField.getText();
		  return acc;
	  }
	    private String getProfessionalSkill() throws SQLException
	    {
	    	String str=null;
	    	 String url = "jdbc:mysql://localhost:3306/SDA";
		        String username = "root";
		        String password = "123456";
		        int professionalID=0;
		        String Feild=null;
		        int userId=fetchCustomerIdByUsername(MainPageController.getInstance().getLoginUserName());
		        String sql2 = "SELECT professional_id FROM appointment where customer_id=?";
		        try (Connection connection = DriverManager.getConnection(url, username, password)) {
		            try (PreparedStatement statement = connection.prepareStatement(sql2)) {
		                statement.setInt(1, userId);
		  
		                ResultSet resultSet = statement.executeQuery();

		                if (resultSet.next()) {
		                    professionalID=resultSet.getInt("professional_id");
		                }

		                
		            }
		            String sql = "SELECT designation FROM professional where professional_id=?";
		            
		            try (PreparedStatement statement = connection.prepareStatement(sql2)) {
		                statement.setInt(1, professionalID);
		  
		                ResultSet resultSet = statement.executeQuery();

		                if (resultSet.next()) {
		                    Feild=resultSet.getString("designation");
		                }

		                
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            // Handle the exception appropriately
		        }
	    	
	    	return Feild;
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
	    private void pay(ActionEvent event) throws ClassNotFoundException, SQLException, IOException {
	    	
	    	 int userId=fetchCustomerIdByUsername(MainPageController.getInstance().getLoginUserName());
	    	 int ProfessionalID=0;
	    	 int appointmentID=0;
	    	 String acc=getAccNum();
	        String url = "jdbc:mysql://localhost:3306/SDA";
	        String username = "root";
	        String password = "123456";
	        String status="Completed";
	        Connection connection=null;
	        Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            String sql2 = "SELECT professional_id,appointment_id FROM appointment where customer_id=?";
	        try (Connection connection2 = DriverManager.getConnection(url, username, password)) {
	            try (PreparedStatement statement = connection2.prepareStatement(sql2)) {
	                statement.setInt(1, userId);
	  
	                ResultSet resultSet = statement.executeQuery();

	                if (resultSet.next()) {
	                    ProfessionalID=resultSet.getInt("professional_id");
	                    appointmentID=resultSet.getInt("appointment_id");
	                }

	                
	            }
            String sql="INSERT INTO payment (customer_id,professional_id,appointment_id,account_number,payment_status) VALUES (?, ?, ?, ?, ?) ";

	        try (PreparedStatement statement = connection.prepareStatement(sql)) {
	        	statement.setInt(1, userId);
                statement.setInt(2, ProfessionalID);
                statement.setInt(3, appointmentID);
                statement.setString(4, acc);
                statement.setString(5, status);
                

                statement.executeUpdate();
            }
	    


	       

	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Handle the exception appropriately
	        }
	        Main.getInstance().changeScene("customerProfile.fxml", "customer Profile", 700, 500);
	    }
	    @FXML
		public void Back(ActionEvent event) throws IOException {
			
			Main.getInstance().changeScene("book.fxml","Booking",700,500);		
		}
}
	    

