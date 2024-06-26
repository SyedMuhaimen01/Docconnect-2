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

public class manageAppointmentController {

    @FXML
    public void Back(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("professionalProfile.fxml", "Professional Profile", 700, 500);
    }

    @FXML
    private void setWorkingHours(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("setWorkingHours.fxml", "setWorkingHours", 292, 198);
    }

    @FXML
    private void MarkCompleted(ActionEvent event) throws IOException {
        int min=getAppointmentOrder();
        String str="Completed";
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


                String updateSql = "UPDATE appointment SET status=? WHERE appointment_id=?";

                try (PreparedStatement updateStatement = connection.prepareStatement(updateSql)) {
                    updateStatement.setString(1, str);
                    updateStatement.setInt(2, min);

                    int rowsUpdated = updateStatement.executeUpdate();

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
        Main.getInstance().changeScene("manageAppointment.fxml", "Manage Appointments", 700, 500);
    }

    private int getAppointmentOrder() {
        
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username1 = "root";
        String password1 = "123456";

        int minAppointmentId = -1;

        try (Connection connection = DriverManager.getConnection(url, username1, password1)) {
            String sql = "SELECT MIN(appointment_id) AS min_appointment_id FROM appointment WHERE status = 'Pending'";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        minAppointmentId = resultSet.getInt("min_appointment_id");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return minAppointmentId;
    }

    @FXML
    private TextArea appointmentsTextArea;

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

                int professionalId = fetchProfessionalIdByUsername(name);
                String str="Pending";
                
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

                    appointmentsTextArea.setText(appointmentDetails.toString());
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

                    appointmentsTextArea.setText(appointmentDetails.toString());
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
}
