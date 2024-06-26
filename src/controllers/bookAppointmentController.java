package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class bookAppointmentController {

    @FXML
    public void Back(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("customerProfile.fxml", "Customer Profile", 700, 500);
    }
    @FXML
    public void exit(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("MainPage.fxml", "Main Page", 700, 500);
    }
    @FXML
    public void Book(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("Book.fxml", "Booking Page", 292, 198);
    }
    @FXML
    public void Cancel(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("cancelAppointment.fxml", "Appointment Cancellation", 292, 198);
    }

   
    @FXML
    private TextArea DetailsTextArea;

    @FXML
    private void viewProfessional(ActionEvent event) {
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
                String status="Accepted";
               
                String sql = "SELECT firstName,lastName,designation,affiliation FROM professional where affiliationStatus=?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, status);

                    ResultSet resultSet = statement.executeQuery();

                    StringBuilder Details = new StringBuilder();
                    while (resultSet.next()) {
                        Details.append("Name : ").append(resultSet.getString("firstName")).append(resultSet.getString("lastName")).append("\n");
                        Details.append("Affiliation : ").append(resultSet.getString("affiliation")).append("\n");
                        Details.append("Designation : ").append(resultSet.getString("designation")).append("\n");
                        Details.append("\n");
                    }

                    DetailsTextArea.setText(Details.toString());
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
    @FXML
    private void viewAppointments(ActionEvent event) {
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
                String str="Pending";
                int customerId = fetchCustomerIdByUsername(name);

                String sql = "SELECT appointment_id, time_slot, status FROM appointment WHERE customer_id = ? and status=?";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setInt(1, customerId);
                    statement.setString(2, str);
                    // Execute the query
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
    
    @FXML
    private void viewHistory(ActionEvent event) {
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
                    // Execute the query
                    ResultSet resultSet = statement.executeQuery();

                    // Process the result set and set values in the text area
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

    @FXML
    private void viewMedicalCenter(ActionEvent event) {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";

        Connection connection = null;
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection(url, username, password);
            String center;
            if (connection != null && !connection.isClosed()) {
                System.out.println("Connected to the database!");
                

                String sql = "SELECT centerName FROM centers ";

                try (PreparedStatement statement = connection.prepareStatement(sql)) {
             

                    // Execute the query
                    ResultSet resultSet = statement.executeQuery();

                    StringBuilder Details = new StringBuilder();
                    while (resultSet.next()) {
                        Details.append("Center Name : ").append(resultSet.getString("centerName")).append("\n");
                        Details.append("\n");
                    }

                    DetailsTextArea.setText(Details.toString());
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
                    statement.setString(1, username.trim()); 
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

