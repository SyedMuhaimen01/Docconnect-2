package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.IOException;
public class MainPageController {
	
	 @FXML
	    private Label errorLabel;
	
	  private static MainPageController instance;


	    public static MainPageController getInstance() {
	        if (instance == null) {
	            instance = new MainPageController();
	        }
	        return instance;
	    }

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    private String loginUserName;
    private String loginPassword;

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    @FXML
    private void handleLoginButton() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        MainPageController mainPageController = MainPageController.getInstance();
        
        if (isValidUser(username, password)) {
        	
        	    mainPageController.setLoginUserName(username);
        	    mainPageController.setLoginPassword(password);
 

            System.out.println("Login successful!");
            Main.getInstance().changeScene("customerProfile.fxml","User Profile",700,500);
        }
        else if(isValidProfessional(username, password))
        {
        	 mainPageController.setLoginUserName(username);
     	    mainPageController.setLoginPassword(password);
        	 System.out.println("Login successful!");
             Main.getInstance().changeScene("professionalProfile.fxml","Professional Profile",700,500);
        }
        else if(isValidCenter(username, password))
        {
        	 mainPageController.setLoginUserName(username);
     	    mainPageController.setLoginPassword(password);
        	System.out.println("Login successful!");
            Main.getInstance().changeScene("centerProfile.fxml","HealthCare Center Profile",700,500);
        }
        else {
            
            System.out.println("Invalid username or password!");
            errorLabel.setText("Invalid Username or Password");
        }
    }

    private boolean isValidUser(String username, String password) {
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

                String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);

                    ResultSet resultSet = statement.executeQuery();

                    return resultSet.next();
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

        return false; // Return false if an error occurs or if no matching user is found
    }
    
    private boolean isValidProfessional(String username, String password) {

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

                String sql = "SELECT * FROM professional WHERE username = ? AND password = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);

                    ResultSet resultSet = statement.executeQuery();

                    return resultSet.next();
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
    
    private boolean isValidCenter(String username, String password) {

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

                String sql = "SELECT * FROM centers WHERE username = ? AND password = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    statement.setString(2, password);

                    ResultSet resultSet = statement.executeQuery();

                    // Check if there is a result (matching user)
                    return resultSet.next();
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
    
   
	@FXML
	public void registerOptions(ActionEvent event) throws IOException {
		
		Main.getInstance().changeScene("registerOptions.fxml","Register Options",292,188);
		
		
	}
	@FXML
	public void userGuide(ActionEvent event) throws IOException {
		
		Main.getInstance().changeScene("help.fxml","User Guide",700,500);
		
		
	}
	@FXML private TextField searchTab;
	
	SearchStrategy searchProfessional = new controllers.searchProfessional();
    SearchStrategy searchCenter = new controllers.searchCenter();
    SearchStrategy searchProfile = new controllers.searchProfile();   
    searchContext searchContext = new searchContext();
    
    
    private boolean isValidCenter(String username) {
       
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

                String sql = "SELECT * FROM centers WHERE username = ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, username);
                    
                    ResultSet resultSet = statement.executeQuery();

                    return resultSet.next();
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
    private boolean isValidProfessional(String username) {
        // Replace these values with your actual database information
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

                    return resultSet.next();
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
    @FXML
	private void SearchButton(ActionEvent event) throws IOException 
	{
    	
		Search();
		System.out.println("Button clicked");
	}
	@FXML
	private void Search() throws IOException
	{
		
		 String username = searchTab.getText();
	        
	        MainPageController mainPageController = MainPageController.getInstance();
	        
	        if(isValidProfessional(username))
	        {
	        	 mainPageController.setLoginUserName(username);
	        	   searchContext.setSearchStrategy(searchProfessional);
	               
	               searchContext.executeSearch(username);
	        }
	        else if(isValidCenter(username))
	        {
	        	 mainPageController.setLoginUserName(username);
	     	    
	        	 searchContext.setSearchStrategy(searchCenter);
	             searchContext.executeSearch(username);
	        }
	        else {
	           
	        	searchContext.setSearchStrategy(searchProfile);	            
	            searchContext.executeSearch(username);
	            
	        }
		
		
	}
}
