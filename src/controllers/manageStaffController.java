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

public class manageStaffController {

    @FXML
    private TextArea DetailsTextArea;

    @FXML
    public void Back(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("centerProfile.fxml", "HealthCare Center Profile", 700, 500);
    }
    @FXML
    public void manageWorkforce(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("manageWorkForce.fxml", "manageWorkForce", 292, 198);
    }
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
            String cName = null; // Initialize cName to null

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
