package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.*;

public class manageWorkForceController {
    @FXML
    private ComboBox<String> requestComboBox;
    @FXML
    private ComboBox<String> actionComboBox;

    public void initialize() {
    
        populateRequestComboBox();
        populateActionComboBox();
    }

    private void populateRequestComboBox() {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";
        String str = "Pending";

        String sql = "SELECT firstName, lastName FROM professional WHERE affiliationStatus=? ";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, str);

            try (ResultSet resultSet = statement.executeQuery()) {
                requestComboBox.getItems().clear(); 

                while (resultSet.next()) {
                    String professionalName = resultSet.getString("firstName") + " " + resultSet.getString("lastName");
                    requestComboBox.getItems().add(professionalName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    private void populateActionComboBox() {
        // JDBC connection parameters
        ObservableList<String> Designation = FXCollections.observableArrayList();
        Designation.add("Accepted");
        Designation.add("Rejected");

        actionComboBox.setItems(Designation);
    }

    @FXML
    public void exit(ActionEvent event) throws IOException {
        Main.getInstance().changeScene("manageStaff.fxml", "manageStaff", 700, 500);
    }

    @FXML
    private void handleConfirmButton() throws IOException, SQLException {
        String url = "jdbc:mysql://localhost:3306/SDA";
        String username = "root";
        String password = "123456";

        String action = actionComboBox.getValue();
        String name = requestComboBox.getValue();
        String profName = null;
        String query = "SELECT username FROM professional WHERE CONCAT(firstName, ' ', lastName) = ?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    System.out.println("No results found for the given name.");
                    return;
                }

                profName = resultSet.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }


        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            connection.setAutoCommit(false); // Disable auto-commit

            String updateSql = "UPDATE professional SET affiliationStatus=? WHERE userName=? ";

            try (PreparedStatement statement = connection.prepareStatement(updateSql)) {
                statement.setString(1, action);
                statement.setString(2, profName);

                statement.executeUpdate();
                
                connection.commit(); 
            } catch (SQLException e) {
                connection.rollback(); // Rollback the transaction in case of an exception
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Main.getInstance().changeScene("manageStaff.fxml", "manageStaff", 700, 500);
    }
}
